package ru.brainrtp.managecore.bukkit.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.brainrtp.managecore.yml.LanguageConfig;

public class RenameCmd implements CommandExecutor {
    LanguageConfig lang;

    public RenameCmd(LanguageConfig lang){
        this.lang = lang;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("rename")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("quickmanage.rename")) {
            sender.sendMessage(lang.getMsg("permissionDeny", true));
            return true;
        }
        StringBuilder name = new StringBuilder();
        for (String arg1 : args) {
            String arg = arg1 + " ";
            name.append(arg);
        }
        name = new StringBuilder(name.toString().trim());
        if (player.hasPermission("quickmanage.rename.color")) {
            name = new StringBuilder(ChatColor.translateAlternateColorCodes('&', name.toString()));
        }

        ItemStack is = player.getInventory().getItemInMainHand();
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name.toString());
        is.setItemMeta(im);
        return true;
    }
}