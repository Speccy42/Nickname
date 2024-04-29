package fr.speccy.nickname;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Nickname extends JavaPlugin {
    public static Map<String, CustomPlayer> players = new HashMap<>();
    public static String PREFIX = "§6[Nickname]§r ";

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new NickListener(), this);
        getCommand("nick").setExecutor(new NickCommand());
    }

    @Override
    public void onDisable() {
    }
}
