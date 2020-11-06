package me.crazybanana.conjoinedplugin.commands;

import me.crazybanana.conjoinedplugin.ConjoinedPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Movement implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 2) {
            Player player = Bukkit.getPlayer(args[0]);
            if(player!=null) {
                ConjoinedPlugin.movement = player.getUniqueId();
                if(ConjoinedPlugin.groups.containsKey(Bukkit.getPlayer(args[1]).getUniqueId())) {
                    ConjoinedPlugin.groups.replace(ConjoinedPlugin.interaction, null, ConjoinedPlugin.movement);
                    Bukkit.getPlayer(ConjoinedPlugin.movement).sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&d You have been assigned&a Movement&d!"));
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
