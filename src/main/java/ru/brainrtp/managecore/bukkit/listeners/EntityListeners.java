package ru.brainrtp.managecore.bukkit.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import ru.brainrtp.managecore.QuickManage;

import java.util.UUID;

public class EntityListeners implements Listener {
    private QuickManage plugin;

    public EntityListeners(QuickManage plugin){
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOW)
    private void onBlockPhysics(BlockPhysicsEvent event) {
        if (plugin.nophys) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            Player p = (Player)e.getDamager();
            LivingEntity en = (LivingEntity)e.getEntity();
            UUID uuid = p.getUniqueId();
            if (plugin.setname.containsKey(uuid)) {
                e.setCancelled(true);
                String value = plugin.setname.get(uuid);
                if (value.equals("")) {
                    en.setCustomName(value);
                    en.setCustomNameVisible(false);
                }
                else {
                    en.setCustomName(value);
                    en.setCustomNameVisible(true);
                }
                plugin.setname.remove(uuid);
            }
        }
//        }
    }
}
