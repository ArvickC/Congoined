package me.crazybanana.conjoinedplugin.commands;

import me.crazybanana.conjoinedplugin.ConjoinedPlugin;
import me.crazybanana.conjoinedplugin.handler.HealthAndHungerSyncHandlers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Start implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            if(ConjoinedPlugin.movement != null && ConjoinedPlugin.interaction != null) {
                ConjoinedPlugin.isConjoined = true;
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.setBedSpawnLocation(player.getLocation(), true);
                    player.setHealth(20.0);
                    player.setFoodLevel(20);
                    player.setCollidable(false);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&a Conjoined Has Begun!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 2.0f, 1.0f);
                }
                Player movementController = Bukkit.getPlayer(ConjoinedPlugin.movement);
                Player interactionController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
                movementController.hidePlayer(ConjoinedPlugin.plugin, interactionController);
                HealthAndHungerSyncHandlers.syncHunger();
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c Pick People Controllers"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c Incorrect Usage"));
        }
        return false;
    }
}
