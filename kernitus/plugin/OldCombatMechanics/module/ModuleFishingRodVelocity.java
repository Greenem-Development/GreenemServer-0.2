package kernitus.plugin.OldCombatMechanics.module;

import java.lang.reflect.Method;
import java.util.Random;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleFishingRodVelocity.1;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;
import org.bukkit.Location;
import org.bukkit.entity.FishHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.util.Vector;

public class ModuleFishingRodVelocity extends Module {
	private Random random;
	private boolean hasDifferentGravity;
	private Method getHook;

	public ModuleFishingRodVelocity(OCMMain plugin) {
		super(plugin, "fishing-rod-velocity");
		this.reload();
	}

	public void reload() {
		this.random = new Random();
		this.hasDifferentGravity = Reflector.versionIsNewerOrEqualAs(1, 14, 0);
		this.getHook = Reflector.getMethod(PlayerFishEvent.class, "getHook");
	}

	@EventHandler
   public void onFishEvent(PlayerFishEvent event) {
      FishHook fishHook = (FishHook)Reflector.invokeMethod(this.getHook, event, new Object[0]);
      if (this.isEnabled(fishHook.getWorld()) && event.getState() == State.FISHING) {
         Location location = event.getPlayer().getLocation();
         double playerYaw = (double)location.getYaw();
         double playerPitch = (double)location.getPitch();
         float oldMaxVelocity = 0.4F;
         double velocityX = -Math.sin(playerYaw / 180.0D * 3.1415927410125732D) * Math.cos(playerPitch / 180.0D * 3.1415927410125732D) * 0.4000000059604645D;
         double velocityZ = Math.cos(playerYaw / 180.0D * 3.1415927410125732D) * Math.cos(playerPitch / 180.0D * 3.1415927410125732D) * 0.4000000059604645D;
         double velocityY = -Math.sin(playerPitch / 180.0D * 3.1415927410125732D) * 0.4000000059604645D;
         double oldVelocityMultiplier = 1.5D;
         double vectorLength = (double)((float)Math.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ));
         velocityX /= vectorLength;
         velocityY /= vectorLength;
         velocityZ /= vectorLength;
         velocityX += this.random.nextGaussian() * 0.007499999832361937D;
         velocityY += this.random.nextGaussian() * 0.007499999832361937D;
         velocityZ += this.random.nextGaussian() * 0.007499999832361937D;
         velocityX *= 1.5D;
         velocityY *= 1.5D;
         velocityZ *= 1.5D;
         fishHook.setVelocity(new Vector(velocityX, velocityY, velocityZ));
         if (this.hasDifferentGravity) {
            (new 1(this, fishHook)).runTaskTimer(this.plugin, 1L, 1L);
         }
      }
   }
}