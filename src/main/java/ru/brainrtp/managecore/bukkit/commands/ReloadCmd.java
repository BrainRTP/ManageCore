package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.brainrtp.managecore.yml.LanguageConfig;

public class ReloadCmd  implements CommandExecutor {
    private LanguageConfig lang;

    public ReloadCmd(LanguageConfig lang){
        this.lang = lang;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("qmreload")) {
            return false;
        }
        if (!sender.hasPermission("quickmanage.reload")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        lang.reload();
        sender.sendMessage(lang.getMsg("reloadSuccess", true));
        return true;
    }
}
