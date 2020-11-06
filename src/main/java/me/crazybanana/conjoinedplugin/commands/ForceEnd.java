package me.crazybanana.conjoinedplugin.commands;

import me.crazybanana.conjoinedplugin.ConjoinedPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class ForceEnd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("Conjoined.Admin")) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&c Game Force-Ended"));
                p.performCommand("warp l");
                Player movementController = Bukkit.getPlayer(ConjoinedPlugin.movement);
                Player interactionController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
                movementController.hidePlayer(ConjoinedPlugin.plugin, interactionController);
                interactionController.hidePlayer(ConjoinedPlugin.plugin, movementController);
            }

            ConjoinedPlugin.isConjoined = false;

            World delete = Bukkit.getWorld(ConjoinedPlugin.conjoined.name());
            File deleteFolder = delete.getWorldFolder();
            deleteWorld(deleteFolder);

            delete = Bukkit.getWorld(ConjoinedPlugin.conjoined_nether.name());
            deleteFolder = delete.getWorldFolder();
            deleteWorld(deleteFolder);

            delete = Bukkit.getWorld(ConjoinedPlugin.conjoined_end.name());
            deleteFolder = delete.getWorldFolder();
            deleteWorld(deleteFolder);
        }
        return false;
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
