package me.crazybanana.conjoinedplugin;

import me.crazybanana.conjoinedplugin.commands.ForceEnd;
import me.crazybanana.conjoinedplugin.commands.Interact;
import me.crazybanana.conjoinedplugin.commands.Movement;
import me.crazybanana.conjoinedplugin.commands.Start;
import me.crazybanana.conjoinedplugin.handler.ConjoinedHandlers;
import me.crazybanana.conjoinedplugin.handler.HealthAndHungerSyncHandlers;
import me.crazybanana.conjoinedplugin.handler.InventorySyncHandlers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class ConjoinedPlugin extends JavaPlugin implements Listener {
    public static ConjoinedPlugin plugin;
    public static boolean isConjoined = false;
    public static UUID movement;
    public static UUID interaction;
    public static String prefix = "&8[&bMinecraft But-&d]";
    public static HashMap<UUID, UUID> groups = new HashMap<>();

    public HealthAndHungerSyncHandlers has = new HealthAndHungerSyncHandlers();
    public ConjoinedHandlers ch = new ConjoinedHandlers();
    public InventorySyncHandlers ish = new InventorySyncHandlers();

    public Interact interactCommand = new Interact();
    public Movement movementCommand = new Movement();
    public Start startCommand = new Start();
    public ForceEnd forceendCommand = new ForceEnd();

    public static WorldCreator conjoined = new WorldCreator("Conjoined");
    public static WorldCreator conjoined_nether = new WorldCreator("Conjoined_Nether");
    public static WorldCreator conjoined_end = new WorldCreator("Conjoined_End");

    private World world;

    @Override
    public void onEnable() {
        plugin = this;

        conjoined.environment(World.Environment.NORMAL);
        conjoined_nether.environment(World.Environment.NETHER);
        conjoined_end.environment(World.Environment.THE_END);

        getCommand("start").setExecutor(startCommand);
        getCommand("movement").setExecutor(movementCommand);
        getCommand("interact").setExecutor(interactCommand);
        getCommand("forceend").setExecutor(forceendCommand);

        Bukkit.getPluginManager().registerEvents(has, this);
        Bukkit.getPluginManager().registerEvents(ch, this);
        Bukkit.getPluginManager().registerEvents(ish, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerWin(EntityDeathEvent e) {
        if(e instanceof EnderDragon) {
            if(((EnderDragon) e).getKiller() instanceof Player) {
                Player i = ((EnderDragon) e).getKiller();
                Player m = Bukkit.getPlayer(groups.get(i.getUniqueId()));

                i.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "&a You won!"));
                i.performCommand("warp l");
                m.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix + "&a You won!"));
                m.performCommand("warp l");

                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(player.getWorld() == conjoined || player.getWorld() == conjoined_nether || player.getWorld() == conjoined_end) {
                        player.performCommand("warp l");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&d You&c lost&d to&a " + i.getDisplayName() + "&d and&a " + m.getDisplayName()));
                    }
                }

                isConjoined = false;

                World delete = Bukkit.getWorld(conjoined.name());
                File deleteFolder = delete.getWorldFolder();
                deleteWorld(deleteFolder);

                delete = Bukkit.getWorld(conjoined_nether.name());
                deleteFolder = delete.getWorldFolder();
                deleteWorld(deleteFolder);

                delete = Bukkit.getWorld(conjoined_end.name());
                deleteFolder = delete.getWorldFolder();
                deleteWorld(deleteFolder);
            }
        }
    }
    public boolean deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }
}
