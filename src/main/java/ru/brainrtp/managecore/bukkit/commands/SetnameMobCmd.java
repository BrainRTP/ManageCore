// 
// Decompiled by Procyon v0.5.30
// 

package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.brainrtp.managecore.QuickManage;
import ru.brainrtp.managecore.yml.LanguageConfig;

import java.util.UUID;

public class SetnameMobCmd implements CommandExecutor {

    private LanguageConfig lang;
    private QuickManage plugin;

    public SetnameMobCmd(QuickManage plugin, LanguageConfig lang) {
        this.lang = lang;
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("setname")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("quickmanage.setname")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        UUID uuid = player.getUniqueId();
        StringBuilder name = new StringBuilder();
        for (String arg1 : args) {
            String arg = arg1 + " ";
            name.append(arg);
        }
        name = new StringBuilder(name.toString().trim());
        if (player.hasPermission("quickmanage.setname.color")) {
            name = new StringBuilder(ChatColor.translateAlternateColorCodes('&', name.toString()));
        }
        if (!plugin.setname.containsKey(uuid)) {
            plugin.setname.put(uuid, name.toString());
            player.sendMessage(lang.getMsg("setNameAction", true));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (plugin.setname.containsKey(uuid)) {
                    plugin.setname.remove(uuid);
                    if (player.isOnline()) {
                        player.sendMessage(lang.getMsg("setNameMobCancel", true));
                    }
                }
            }, 200L);
        }
        return true;
    }
}
