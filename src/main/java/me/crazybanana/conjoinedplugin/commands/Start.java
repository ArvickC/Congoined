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
            if(sender.hasPermission("Conjoined.Admin")) {
                if (ConjoinedPlugin.groups.size() > 0) {
                    ConjoinedPlugin.isConjoined = true;

                    ConjoinedPlugin.conjoined.createWorld();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&a World Created! Overworld"));
                    ConjoinedPlugin.conjoined_nether.createWorld();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&a World Created!&c Nether"));
                    ConjoinedPlugin.conjoined_end.createWorld();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ConjoinedPlugin.prefix + "&a World Created!&f End"));

                    Bukkit.getWorld(ConjoinedPlugin.conjoined.name()).getWorldBorder().setCenter(0, 0);
                    Bukkit.getWorld(ConjoinedPlugin.conjoined.name()).getWorldBorder().setSize(200000);
                    Bukkit.getWorld(ConjoinedPlugin.conjoined_nether.name()).getWorldBorder().setCenter(0, 0);
                    Bukkit.getWorld(ConjoinedPlugin.conjoined_nether.name()).getWorldBorder().setSize(200000);
                    Bukkit.getWorld(ConjoinedPlugin.conjoined_end.name()).getWorldBorder().setCenter(0, 0);
                    Bukkit.getWorld(ConjoinedPlugin.conjoined_end.name()).getWorldBorder().setSize(200000);

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.teleport(Bukkit.getWorld(ConjoinedPlugin.conjoined.name()).getSpawnLocation());
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
                    interactionController.hidePlayer(ConjoinedPlugin.plugin, movementController);

                    HealthAndHungerSyncHandlers.syncHunger();
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c Pick People Controllers"));
                }
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c Incorrect Usage"));
        }
        return false;
    }
}
