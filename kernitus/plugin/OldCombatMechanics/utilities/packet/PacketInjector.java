package kernitus.plugin.OldCombatMechanics.utilities.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import kernitus.plugin.OldCombatMechanics.utilities.packet.PacketEvent.ConnectionDirection;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;
import org.bukkit.entity.Player;

class PacketInjector extends ChannelDuplexHandler {
	private volatile boolean isClosed;
	private Channel channel;
	private List<PacketListener> packetListeners = new CopyOnWriteArrayList();
	private WeakReference<Player> playerWeakReference;

	PacketInjector(Player player) {
		Objects.requireNonNull(player, "player can not be null!");
		this.playerWeakReference = new WeakReference(player);

		try {
			this.attach(player);
		} catch (Exception var3) {
			throw new RuntimeException(var3);
		}
	}

	private void attach(Player player) throws Exception {
		Object playerConnection = PacketSender.getInstance().getConnection(player);
		Object manager = Reflector.getFieldValue(playerConnection, "networkManager");
		this.channel = (Channel) Reflector.getFieldValue(manager, "channel");
		if (this.channel.pipeline().get("ocm_handler") != null) {
			debug("Old listener lingered around");
			ChannelHandler old = this.channel.pipeline().get("ocm_handler");
			if (old instanceof PacketInjector) {
				debug("Detaching old listener");
				((PacketInjector) old).detach();
			}

			this.channel.pipeline().remove("ocm_handler");
		}

		try {
			this.channel.pipeline().addBefore("packet_handler", "ocm_handler", this);
		} catch (NoSuchElementException var5) {
			throw new NoSuchElementException("No base handler found. Was the player instantly disconnected?");
		}
	}

	void detach() {
		debug("Detaching injector... (%d)", this.hashCode());
		if (!this.isClosed && this.channel.isOpen()) {
			this.channel.eventLoop().submit(() -> {
				this.channel.pipeline().remove(this);
				this.channel = null;
				this.isClosed = true;
				this.playerWeakReference.clear();
				this.packetListeners.clear();
				debug("Injector successfully detached (%d)", this.hashCode());
			});
		} else {
			debug("Closed(%b) or channel closed(%b) already! (%d)", this.isClosed, !this.channel.isOpen(),
					this.hashCode());
		}
	}

	void addPacketListener(PacketListener packetListener) {
		Objects.requireNonNull(packetListener, "packetListener can not be null");
		if (this.isClosed) {
			throw new IllegalStateException("Channel already closed. Adding of listener invalid");
		} else {
			this.packetListeners.remove(packetListener);
			this.packetListeners.add(packetListener);
		}
	}

	void removePacketListener(PacketListener packetListener) {
		this.packetListeners.remove(packetListener);
	}

	int getListenerAmount() {
		return this.packetListeners.size();
	}

	public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise)
			throws Exception {
		if (this.playerWeakReference != null && this.playerWeakReference.get() != null) {
			if (!Packet.isNmsPacket(packet)) {
				debug("Received a packet THAT IS NO PACKET: " + packet.getClass() + " " + packet);
			} else {
				PacketEvent event = new PacketEvent(packet, ConnectionDirection.TO_CLIENT,
						(Player) this.playerWeakReference.get());
				Iterator var5 = this.packetListeners.iterator();

				while (var5.hasNext()) {
					PacketListener packetListener = (PacketListener) var5.next();

					try {
						if (!this.isClosed) {
							packetListener.onPacketSend(event);
						}
					} catch (Exception var8) {
						Messenger.warn(var8, "Error in a packet listener (send).", new Object[0]);
					}
				}

				if (!event.isCancelled()) {
					super.write(channelHandlerContext, packet, channelPromise);
				}

			}
		} else {
			debug("playerWeakReference or its value is null. This should NOT happen at this stage. (write@%d)",
					this.hashCode());
			this.detach();
			super.write(channelHandlerContext, packet, channelPromise);
		}
	}

	public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
		if (this.playerWeakReference != null && this.playerWeakReference.get() != null) {
			if (!Packet.isNmsPacket(packet)) {
				debug("Received a packet THAT IS NO PACKET: " + packet.getClass() + " " + packet);
			} else {
				PacketEvent event = new PacketEvent(packet, ConnectionDirection.TO_SERVER,
						(Player) this.playerWeakReference.get());
				Iterator var4 = this.packetListeners.iterator();

				while (var4.hasNext()) {
					PacketListener packetListener = (PacketListener) var4.next();

					try {
						if (!this.isClosed) {
							packetListener.onPacketReceived(event);
						}
					} catch (Exception var7) {
						Messenger.warn(var7, "Error in a packet listener (receive).", new Object[0]);
					}
				}

				if (!event.isCancelled()) {
					super.channelRead(channelHandlerContext, packet);
				}

			}
		} else {
			debug("playerWeakReference or its value is null. This should NOT happen at this stage. (read@%d)",
					this.hashCode());
			this.detach();
			super.channelRead(channelHandlerContext, packet);
		}
	}

	private static void debug(String message, Object... formatArgs) {
		Messenger.debug("PacketInjector: " + message, formatArgs);
	}
}