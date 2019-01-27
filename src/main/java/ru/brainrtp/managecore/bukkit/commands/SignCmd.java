// 
// Decompiled by Procyon v0.5.30
// 

package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.brainrtp.managecore.yml.LanguageConfig;

public class SignCmd implements CommandExecutor {

    private LanguageConfig lang;

    public SignCmd(LanguageConfig lang) {
        this.lang = lang;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("sign") || !(sender instanceof Player) || args.length < 1) {
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("quickmanage.sign")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        BlockState bs = player.getTargetBlock(null, 5).getState();
        if (bs instanceof Sign) {
            int line;
            try {
                line = Integer.parseInt(args[0]);
                if (line < 1 || line > 4) {
                    sender.sendMessage(lang.getMsg("signLines", true));
                    return true;
                }
            }
            catch (NumberFormatException e) {
                sender.sendMessage(lang.getMsg("signNumberFormat", true));
                return true;
            }
            Sign s = (Sign)bs;
            String t = null;
            if (args.length > 1) {
                StringBuilder tBuilder = new StringBuilder();
                for (int i = 1; i < args.length; ++i) {
                    String arg = args[i] + " ";
                    tBuilder.append(arg);
                }
                t = tBuilder.toString();
                t = t.trim();
                t = ChatColor.translateAlternateColorCodes('&', t);
            }
            s.setLine(line - 1, t);
            s.update();
            return true;
        }
        sender.sendMessage(lang.getMsg("signNotFound", true));
        return true;
    }
}
