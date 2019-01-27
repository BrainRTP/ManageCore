package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.brainrtp.managecore.QuickManage;
import ru.brainrtp.managecore.yml.LanguageConfig;

public class FallCmd implements CommandExecutor {

    private QuickManage plugin;
    private LanguageConfig lang;

    public FallCmd(QuickManage plugin, LanguageConfig lang) {
        this.plugin = plugin;
        this.lang = lang;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("phys")) {
            return false;
        }
        if (!sender.hasPermission("quickmanage.fall")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
//        if (args.length > 0) {
//            try {
//                final Integer id = Integer.parseInt(args[0]);
//                if (!sender.hasPermission("onlycore.fall." + id)) {
//                    sender.sendMessage(lang.getMsg("permissionDeny", true));
//                    return true;
//                }
//                if (plugin.fb.contains(id)) {
//                    plugin.fb.remove(id);
//                    sender.sendMessage("§2[OnlyCore] §aФизика снова включена для блока с ID " + id);
//                    return true;
//                }
//                plugin.fb.add(id);
//                sender.sendMessage("§2[OnlyCore] §aФизика §cотключена§a для блока с ID " + id);
//                return true;
//            } catch (NumberFormatException e) {
//                sender.sendMessage("§4[OnlyCore] §cID блока должен быть числом");
//                return true;
//            }
//        }

        plugin.nophys = !plugin.nophys;
        sender.sendMessage(lang.getMsg("physics", true) + (plugin.nophys ? lang.getMsg("physicsOff", false) : lang.getMsg("physicsOn", false)));
        return true;
    }
}