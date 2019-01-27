package ru.brainrtp.managecore.vk;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.brainrtp.managecore.QuickManage;

public class VkApi {
    private final String API_URL = "https://api.vk.com/method/";
    private final String V = "5.85";
    private String server = null;
    private String key;
    private int ts;
    private String peer;
    private String apikey;
    private String id;
    private boolean create;
    private int error = 0;
    private QuickManage plugin;

    public VkApi(String apikey, String peer, String id) {
        this.apikey = apikey;
        this.peer = peer;
        this.id = id;
        this.getLongPool();
        this.create = this.check() == 0;
    }

    private void getLongPool() {
        JsonObject json = this.vkCall("groups.getLongPollServer", "group_id", this.id).getAsJsonObject("response");
        this.key = json.get("key").getAsString();
        this.server = json.get("server").getAsString();
        this.ts = json.get("ts").getAsInt();
    }

    private int check() {
        return this.error;
    }

    public boolean checkCreate() {
        return this.create;
    }

    private JsonObject vkCall(String method, String... arrStr) {
        HashMap<String, String> data = new HashMap();
        data.put("v", "5.85");
        data.put("access_token", this.apikey);
        String str1 = null;
        boolean bol = true;
        String[] var6 = arrStr;
        int var7 = arrStr.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String str = var6[var8];
            if (bol) {
                str1 = str;
                bol = false;
            } else {
                data.put(str1, str);
                bol = true;
            }
        }

        String request = Http.sendPost("https://api.vk.com/method/" + method, data);
        JsonObject json = (new JsonParser()).parse(request).getAsJsonObject();
        if (json.has("error")) {
            this.error = json.getAsJsonObject("error").get("error_code").getAsInt();
        }

        return json;
    }

    public void sendMessage(String message) {
        if (!Utils.isEmpty(this.peer)) {
            this.vkCall("messages.send", "peer_id", this.peer, "message", message);
        }
    }

    public HashMap<Integer, info> getMessage() {
        if (this.server == null) {
            return null;
        } else {
            String url = this.server + "?act=a_check&key=" + this.key + "&ts=" + this.ts + "&wait=0";
            String request = Http.sendPost(url, (Map)null);
            JsonObject json = (new JsonParser()).parse(request).getAsJsonObject();
            HashMap<Integer, info> list = new HashMap();
            if (json.has("failed")) {
                int error = json.get("failed").getAsInt();
                if (error != 1) {
                    this.getLongPool();
                    return list;
                }
            }

            this.ts = json.get("ts").getAsInt();
            JsonArray update = json.getAsJsonArray("updates");
            if (update == null) {
                return list;
            } else if (update.size() < 1) {
                return list;
            } else {
                for(int i = 0; i < update.size(); ++i) {
                    JsonObject arr = update.get(i).getAsJsonObject();
                    if (arr.get("type").getAsString().equals("message_new") && (Utils.isEmpty(this.peer) || Integer.toString(arr.getAsJsonObject("object").get("peer_id").getAsInt()).equals(this.peer))) {
                        String msg = arr.getAsJsonObject("object").get("text").getAsString();
                        if (!msg.equals("")) {
                            String uid = Integer.toString(arr.getAsJsonObject("object").get("from_id").getAsInt());
                            JsonObject user = this.vkCall("users.get", "user_ids", uid).getAsJsonArray("response").get(0).getAsJsonObject();
                            info in = new info(msg, user.get("first_name").getAsString() + " " + user.get("last_name").getAsString(), uid);
                            if (Utils.isEmpty(this.peer)) {
                                in.peer = Integer.toString(arr.getAsJsonObject("object").get("peer_id").getAsInt());
                            }

                            list.put(i, in);
                        }
                    }
                }

                return list;
            }
        }
    }

    public String getPeer() {
        return this.peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public BukkitRunnable startLongPool(int speed_update, final String link) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            this.sendMessage(Lang.SERVER_ON.toString());
        });
        BukkitRunnable run = new BukkitRunnable() {
            public void run() {
                if (!plugin.isEnabled()) {
                    this.cancel();
                } else {
                    HashMap<Integer, info> map = VkApi.this.getMessage();
                    if (map != null && !map.isEmpty()) {
                        map.forEach((k, v) -> {
                            TextComponent component = new TextComponent();
                            Utils.addExtra(component, (new ComponentBuilder("[VK]")).color(ChatColor.BLUE).event(new ClickEvent(Action.OPEN_URL, link)).event(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(Lang.URL.toString())).create())).create());
                            Utils.addExtra(component, (new ComponentBuilder(v.getName())).color(ChatColor.GREEN).event(new ClickEvent(Action.OPEN_URL, "https://vk.com/id" + v.getId())).event(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(Lang.USER_TEXT.toString())).create())).create());
                            Utils.addExtra(component, (new ComponentBuilder(": ")).color(ChatColor.YELLOW).create());
                            Utils.addExtra(component, (new ComponentBuilder(v.getMsg())).color(ChatColor.WHITE).create());
                            Iterator var5;
                            Player player;
                            if (Utils.isEmpty(VkApi.this.peer)) {
                                Utils.addExtra(component, 0, (new ComponentBuilder(Lang.SET_PEER.toString())).event(new ClickEvent(Action.RUN_COMMAND, "/vksetpeer " + v.getPeer())).event(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(Lang.SETUP_PEER_INFO.toString())).create())).create());
                                var5 = Bukkit.getServer().getOnlinePlayers().iterator();

                                while(var5.hasNext()) {
                                    player = (Player)var5.next();
                                    if (player.hasPermission("vkchat.admin")) {
                                        player.spigot().sendMessage(component);
                                    }
                                }
                            } else {
                                var5 = Bukkit.getServer().getOnlinePlayers().iterator();

                                while(var5.hasNext()) {
                                    player = (Player)var5.next();
                                    if (player.hasPermission("vkchat.player.receive")) {
                                        player.spigot().sendMessage(component);
                                    }
                                }
                            }

                        });
                    }
                }
            }
        };
        run.runTaskTimer(plugin, 0L, (long)(20 * speed_update));
        return run;
    }

    public class info {
        String msg;
        String name;
        String id;
        String peer;

        info(String msg, String name, String id) {
            this.msg = msg;
            this.name = name;
            this.id = id;
        }

        public String getMsg() {
            return this.msg;
        }

        public String getName() {
            return this.name;
        }

        public String getId() {
            return this.id;
        }

        public String getPeer() {
            return !Utils.isEmpty(this.peer) ? this.peer : this.peer;
        }
    }
}
