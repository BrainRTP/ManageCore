package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import ru.brainrtp.managecore.QuickManage;
import ru.brainrtp.managecore.bukkit.Scheduler;
import ru.brainrtp.managecore.yml.LanguageConfig;

public class CreateMobCmd implements CommandExecutor {

    private QuickManage plugin;
    private LanguageConfig lang;

    public CreateMobCmd(QuickManage plugin, LanguageConfig lang) {
        this.plugin = plugin;
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("createmob")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        if (!sender.hasPermission("quickmanage.bcastp")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        Player player = (Player) sender;
        LivingEntity mob = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.SHEEP);
        Sheep sheep = (Sheep) mob;

        sheep.setCustomName("§cSheep Custom");
        sheep.setCustomNameVisible(true);
        sheep.setVelocity(player.getVelocity());
        sheep.setCollidable(true);
        sheep.setInvulnerable(true);
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 99999, false, false, false);
        PotionEffect potionEffect2 = new PotionEffect(PotionEffectType.SLOW, 99999, 99999, false, false, false);
        mob.addPotionEffect(potionEffect);
        mob.addPotionEffect(potionEffect2);
//        sheep.set
        sheep.setHealth(1);

        Scheduler.mobs.put(mob, player.getLocation());
//        Bukkit.getScheduler().runTaskLater(plugin, () -> {
//            sheep.setVelocity(player.getVelocity());
//        }, 200L);

        sender.sendMessage("§6§lMOBS: §fMob custom spawned!");
        return false;
    }
}
