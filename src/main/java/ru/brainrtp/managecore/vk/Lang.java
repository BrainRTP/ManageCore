package ru.brainrtp.managecore.vk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.brainrtp.managecore.QuickManage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public enum Lang {
    FIRST_JOIN("first-join", "&#127881;{player} join to the server for the first time", "&#127881;{player} зашёл на сервер в первый раз"),
    JOIN("join", "&#10133;{player} join to server", "&#10133;{player} зашёл на сервер"),
    EXIT("exit", "&#10133;{player} left the server", "&#10134;{player} вышел с сервера"),
    SERVER_ON("server-on", "&#9989;Server on", "&#9989;Сервер включился"),
    SERVER_OFF("server-off", "&#9989;Server off", "&#9940;Сервер выключился"),
    URL("url", "&eClick me to open vk chat", "&eНажми на меня чтобы открыть беседу в вк"),
    NO_PERMS("no-permissions", "&cYou dont have perms", "&cПохоже, у тебя нет permission на это"),
    SETUP_PEER_INFO("setup-peer-info", "&ePress me to set this peer", "&eНажми сюда чтобы установить этот диалог"),
    USER_TEXT("user-text", "&cClick here to open the user page", "&cКликни сюда чтобы открыть страницу пользователя в вк"),
    SET_PEER("set-peer", "&c[SETUP]");

    private static YamlConfiguration LANG;
    private static String langString;
    private String ru;
    private String path;
    private String en;
    private static QuickManage plugin;

    private Lang(String path, String en) {
        this.path = path;
        this.en = en;
        this.ru = en;
    }

    private Lang(String path, String en, String ru) {
        this.path = path;
        this.en = en;
        this.ru = ru;
    }

    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    public static void loadLang(String lang) {
        langString = lang;
        File langFile = new File(plugin.getDataFolder() + File.separator + "lang", lang + ".yml");
        if (!langFile.exists()) {
            try {
                langFile.createNewFile();
            } catch (Exception var8) {
                Bukkit.getLogger().warning("VkChat: unable to save " + lang + ".yml file");
                Bukkit.getPluginManager().disablePlugin(plugin);
                return;
            }
        }

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(langFile);
        Lang[] var3 = values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Lang item = var3[var5];
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getEn());
            }
        }

        setFile(conf);

        try {
            conf.save(langFile);
        } catch (IOException var7) {
            Bukkit.getLogger().log(Level.WARNING, "VkChat: Failed to save " + lang + ".yml.");
            var7.printStackTrace();
        }

    }

    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, this.en));
    }

    public String getEn() {
        return langString.equals("ru") ? this.ru : this.en;
    }

    public String getPath() {
        return this.path;
    }
}
