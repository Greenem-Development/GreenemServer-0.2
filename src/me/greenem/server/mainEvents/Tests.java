package me.greenem.server.mainEvents;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class Tests implements Listener {
	public Plugin plugin = Bukkit.getPluginManager().getPlugin("GreenemServerMainPlugin");
	private static volatile Tests INSTANCE;
	public Logger log = plugin.getLogger();
	
	public static Tests getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Tests();
		}
		return INSTANCE;
	}
	
	private Server getServer() {
		return plugin.getServer();
	}
	
	@EventHandler
	public void onVehicleDamage(VehicleDamageEvent e) {
		if(e.isCancelled()) return;
		if(!e.getAttacker().getWorld().getName().equalsIgnoreCase("testworld2")) return;
		if(e.getVehicle() instanceof Boat) {
			if(e.getAttacker() instanceof Player) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
	         
	Player p = e.getPlayer();
		if(e.isCancelled()) return;
		if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("testworld2")) return;
	    if(p.getVehicle() != null && p.getVehicle() instanceof Boat) {
	        Boat boat = (Boat) p.getVehicle();
	        if(Math.abs(p.getVelocity().getX())>1 || Math.abs(p.getVelocity().getZ())>1) return;
//	        log.info("playerX = " + p.getVelocity().getX());
//	        log.info("playerZ = " + p.getVelocity().getZ());
//	        log.info("boatX = " + boat.getVelocity().getX());
//	        log.info("boatZ = " + boat.getVelocity().getZ());
	        log.info("" + p.isGliding());
	        //p.setGliding(true);
	        
	        //log.info(boat.getVelocity().toString());
	        //log.info(p.getVelocity().toString());
//	        if(1==1) return;
//	        double pitch = ((boat.getLocation().getPitch() + 90) * Math.PI) / 180;
//	        double yaw = ((boat.getLocation().getYaw() + 90) * Math.PI) / 180;
//	        double x = Math.sin(pitch) * Math.cos(yaw);
//	        double y = Math.sin(pitch) * Math.sin(yaw);
//	        double z = Math.cos(pitch);
//	        Vector vector = new Vector(x, z, y);
	        
//	        Vector vector = new Vector(p.getVelocity().getX()*1.5, p.getVelocity()., p.getVelocity().getZ()*1.5);
	        
	        p.setVelocity(new Vector(p.getVelocity().getX()*4.5, p.getVelocity().getY(), p.getVelocity().getZ()*4.5));

	        //boat.getVelocity().setX(p.getVelocity().getX()*3.5);
	        //boat.getVelocity().setZ(p.getVelocity().getZ()*3.5);
	        }
	}   
	
	@EventHandler
	public void sneaking(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
	}
	
	@EventHandler
	public void onPlayerMoveonHorse(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(e.isCancelled()) return;
		if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("testworld2")) return;
		//log.info(p.getVehicle() + "");
		if(p.getVehicle() != null && p.getVehicle() instanceof Horse) {
		    log.info("Moved on horse");
		}
	}
}
