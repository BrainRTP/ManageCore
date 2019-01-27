package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.brainrtp.managecore.yml.LanguageConfig;

public class CastPlayerCmd implements CommandExecutor {
    private LanguageConfig lang;

    public CastPlayerCmd(LanguageConfig lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("bcastp")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(lang.getMsg("bcastpName", true));
            return true;
        }
        if (!sender.hasPermission("quickmanage.bcastp")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        Player player1 = Bukkit.getPlayer(args[0]);
        if (player1 == null || !player1.isOnline()) {
            player.sendMessage(lang.getMsg("playerNotFound", true));
            return true;
        }

        StringBuilder msgBuilder = new StringBuilder();
        for (int arg1 = 1; arg1 < args.length; arg1++) {
            String arg = arg1 + " ";
            msgBuilder.append(arg);
        }
        String msg = msgBuilder.toString();
        msg = msg.trim();
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        player1.sendMessage(msg);

        return false;
    }
}
