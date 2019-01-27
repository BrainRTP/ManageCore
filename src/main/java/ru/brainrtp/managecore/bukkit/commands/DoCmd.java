package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.brainrtp.managecore.yml.LanguageConfig;

public class DoCmd implements CommandExecutor  {
    private LanguageConfig lang;

    public DoCmd(LanguageConfig lang){
        this.lang = lang;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("do") || args.length < 2) {
            return false;
        }
        if (!sender.hasPermission("quickmanage.do")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null || !player.isOnline()) {
            sender.sendMessage(lang.getMsg("playerNotFound", true));
            return true;
        }
        StringBuilder what = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            final String arg = args[i] + " ";
            what.append(arg);
        }
        player.chat(what.toString());
        return true;
    }
}
