package me.crazybanana.conjoinedplugin;

import me.crazybanana.conjoinedplugin.handler.ConjoinedHandlers;
import me.crazybanana.conjoinedplugin.handler.HealthAndHungerSyncHandlers;
import me.crazybanana.conjoinedplugin.handler.InventorySyncHandlers;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class ConjoinedPlugin extends JavaPlugin implements Listener {
    public static ConjoinedPlugin plugin;
    public static boolean isConjoined = false;
    public static UUID movement;
    public static UUID interaction;
    public static String prefix = "&8[&bMinecraft But-&d]";

    public HealthAndHungerSyncHandlers has = new HealthAndHungerSyncHandlers();
    public ConjoinedHandlers ch = new ConjoinedHandlers();
    public InventorySyncHandlers ish = new InventorySyncHandlers();

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getPluginManager().registerEvents(has, this);
        Bukkit.getPluginManager().registerEvents(ch, this);
        Bukkit.getPluginManager().registerEvents(ish, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
