package me.crazybanana.conjoinedplugin.commands;

import me.crazybanana.conjoinedplugin.ConjoinedPlugin;
import me.crazybanana.conjoinedplugin.handler.ConjoinedHandlers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Interact implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if(player!=null) {
                ConjoinedPlugin.interaction = player.getUniqueId();
                if(!ConjoinedPlugin.groups.containsKey(ConjoinedPlugin.interaction)) {
                    ConjoinedPlugin.groups.put(ConjoinedPlugin.interaction, null);
                    Bukkit.getPlayer(ConjoinedPlugin.interaction).sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&d You have been assigned&a Interaction&d!"));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&c Player Not Found"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&c Please input a player!"));
        }
        return false;
    }
}
