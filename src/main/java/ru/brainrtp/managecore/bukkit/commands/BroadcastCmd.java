package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.brainrtp.managecore.QuickManage;
import ru.brainrtp.managecore.yml.LanguageConfig;

public class BroadcastCmd implements CommandExecutor {

    private LanguageConfig lang;
    private QuickManage plugin;

    public BroadcastCmd(LanguageConfig lang) {
        this.lang = lang;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!command.getName().equalsIgnoreCase("bcast") || args.length <= 0) {
            return false;
        }
        if (!sender.hasPermission("quickmanage.bcast")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        StringBuilder msgBuilder = new StringBuilder();
        for (String arg1 : args) {
            String arg = arg1 + " ";
            msgBuilder.append(arg);
        }
        String msg = msgBuilder.toString();
        msg = msg.trim();
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(msg);
        }
        return true;
    }
}