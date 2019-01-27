package ru.brainrtp.managecore.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Scheduler extends BukkitRunnable {
    public static Map<LivingEntity, Location> mobs = new ConcurrentHashMap<>();

    @Override
    public void run() {
        for (Map.Entry<LivingEntity, Location> mob : mobs.entrySet()) {
            if (!mob.getKey().isDead()) {
                mob.getKey().teleport(mob.getValue());
            } else {
                mobs.remove(mob.getKey());
            }
        }
    }

}
