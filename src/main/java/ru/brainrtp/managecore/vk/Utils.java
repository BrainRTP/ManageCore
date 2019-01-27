package ru.brainrtp.managecore.vk;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

public class Utils {
    public Utils() {
    }

    public static void addExtra(TextComponent textComponent, BaseComponent[] component) {
        BaseComponent[] var2 = component;
        int var3 = component.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            BaseComponent baseComponent = var2[var4];
            textComponent.addExtra(baseComponent);
        }

    }

    public static boolean isEmpty(String s) {
        return s == null ? true : s.equals("");
    }

    public static void addExtra(TextComponent component, int i, BaseComponent[] sets) {
        List<BaseComponent> list = component.getExtra();
        BaseComponent[] var4 = sets;
        int var5 = sets.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            BaseComponent set = var4[var6];
            list.add(i, set);
        }

    }
}
