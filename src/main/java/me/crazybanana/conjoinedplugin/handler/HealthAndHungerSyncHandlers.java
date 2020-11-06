package me.crazybanana.conjoinedplugin.handler;

import me.crazybanana.conjoinedplugin.ConjoinedPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class HealthAndHungerSyncHandlers implements Listener {
    @EventHandler
    public void handle(EntityDamageEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            if(event.getEntityType() == EntityType.PLAYER) {
                UUID damagedPlayer = event.getEntity().getUniqueId();
                Player interactController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
                Player movementController = Bukkit.getPlayer(ConjoinedPlugin.movement);
                if(damagedPlayer.equals(ConjoinedPlugin.movement)) {
                    if(movementController.getHealth() - event.getDamage() > 0)
                        interactController.setHealth(movementController.getHealth() - event.getDamage());
                    else
                        interactController.setHealth(0);
                }
                else if(damagedPlayer.equals(ConjoinedPlugin.interaction)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handle(EntityRegainHealthEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            if(event.getEntityType() == EntityType.PLAYER) {
                UUID damagedPlayer = event.getEntity().getUniqueId();
                Player interactController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
                Player movementController = Bukkit.getPlayer(ConjoinedPlugin.movement);
                if(damagedPlayer.equals(ConjoinedPlugin.interaction))
                    if(interactController.getHealth() + event.getAmount() < 20)
                        movementController.setHealth(interactController.getHealth() + event.getAmount());
                    else
                        movementController.setHealth(20);
                else if(damagedPlayer.equals(ConjoinedPlugin.movement))
                    if(movementController.getHealth() + event.getAmount() < 20)
                        interactController.setHealth(movementController.getHealth() + event.getAmount());
                    else
                        interactController.setHealth(20);
            }
        }
    }

    @EventHandler
    public void handle(FoodLevelChangeEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            if(event.getEntityType() == EntityType.PLAYER) {
                UUID playerWhoAte = event.getEntity().getUniqueId();
                if(playerWhoAte.equals(ConjoinedPlugin.interaction) && event.getFoodLevel() > ((Player)event.getEntity()).getFoodLevel()) {
                    Player interactController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
                    Player movementController = Bukkit.getPlayer(ConjoinedPlugin.movement);
                    movementController.setFoodLevel(event.getFoodLevel());
                    movementController.setSaturation(interactController.getSaturation());
                    movementController.setExhaustion(interactController.getExhaustion());
                }
            }
        }
    }

    public static void syncHunger() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player interactController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
                Player movementController = Bukkit.getPlayer(ConjoinedPlugin.movement);
                interactController.setFoodLevel(movementController.getFoodLevel());
                interactController.setSaturation(movementController.getSaturation());
                interactController.setExhaustion(movementController.getExhaustion());
            }
        }.runTaskTimer(ConjoinedPlugin.plugin, 900l, 300l);
    }
}

