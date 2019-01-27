package ru.brainrtp.managecore;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import ru.brainrtp.managecore.bukkit.CommandsRegistrator;
import ru.brainrtp.managecore.bukkit.Scheduler;
import ru.brainrtp.managecore.bukkit.commands.*;
import ru.brainrtp.managecore.bukkit.listeners.EntityListeners;
import ru.brainrtp.managecore.yml.LanguageConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class QuickManage extends JavaPlugin {

    private static Plugin plugin;
    private LanguageConfig languageConfig;
    public boolean nophys;
    public Map<UUID, String> setname = new HashMap<>();
    private BukkitTask task;

    @Override
    public void onEnable() {
        try{
            plugin = this;
            languageConfig = new LanguageConfig(this);

            registerCommands();
            registerListeners();
//            this.task = new Scheduler().runTaskTimer(this, 0l, 10l);
            getLogger().info("§aPlugin loaded successfully");
        } catch (Exception e){
            getLogger().severe("§aPlugin not loaded :(");
            e.printStackTrace();

        }

    }

    private void registerListeners() {
        plugin.getServer().getPluginManager().registerEvents(new EntityListeners(this),this);
    }

    private void registerCommands() {
        CommandsRegistrator.reg(this, new DoCmd(languageConfig), new String[] {"do"}, "Do command", "/do");
        CommandsRegistrator.reg(this, new RenameCmd(languageConfig), new String[] {"rename"}, "Rename command", "/rename");
        CommandsRegistrator.reg(this, new ReloadCmd(languageConfig), new String[] {"mgreload"}, "Reload command", "/qmreload");
        CommandsRegistrator.reg(this, new FallCmd(this, languageConfig), new String[] {"phys"}, "Physics command", "/phys");
        CommandsRegistrator.reg(this, new BroadcastCmd(languageConfig), new String[] {"bcast"}, "BroadCast command", "/bcast");
        CommandsRegistrator.reg(this, new CastPlayerCmd(languageConfig), new String[] {"bcastp"}, "BroadCast to player command", "/bcastp");
        CommandsRegistrator.reg(this, new SignCmd(languageConfig), new String[] {"sign"}, "SignEdit command", "/sign");
        CommandsRegistrator.reg(this, new SetnameMobCmd(this, languageConfig), new String[] {"setname"}, "SetNameMob command", "/setname");
//        CommandsRegistrator.reg(this, new CreateMobCmd(this, languageConfig), new String[] {"createmob"}, "Create Mob command", "/createmob");
    }

    public static Plugin getPlugin() {
        return plugin;
    }

}
