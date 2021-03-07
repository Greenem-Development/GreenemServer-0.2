package kernitus.plugin.OldCombatMechanics.module;

import java.util.Locale;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleProjectileKnockback.1;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

public class ModuleProjectileKnockback extends Module {
	public ModuleProjectileKnockback(OCMMain plugin) {
		super(plugin, "projectile-knockback");
	}

	@EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onEntityHit(EntityDamageByEntityEvent e) {
      if (this.isEnabled(e.getEntity().getWorld())) {
         EntityType type = e.getDamager().getType();
         switch(1.$SwitchMap$org$bukkit$entity$EntityType[type.ordinal()]) {
         case 1:
         case 2:
         case 3:
            e.setDamage(this.module().getDouble("damage." + type.toString().toLowerCase(Locale.ROOT)));
            if (e.isApplicable(DamageModifier.ABSORPTION)) {
               e.setDamage(DamageModifier.ABSORPTION, 0.0D);
            }
         default:
         }
      }
   }
}