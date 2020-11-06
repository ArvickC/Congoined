package me.crazybanana.conjoinedplugin.handler;

import me.crazybanana.conjoinedplugin.ConjoinedPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;

public class ConjoinedHandlers implements Listener {
    @EventHandler
    public void handle(PlayerMoveEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            Player playerMoving = event.getPlayer();
            if(playerMoving.getUniqueId().equals(ConjoinedPlugin.movement)) {
                Player interactController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
                if(!interactController.isDead())
                    interactController.teleport(playerMoving.getLocation());
            }
            else
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        if(ConjoinedPlugin.isConjoined == true && event.getPlayer().getUniqueId().equals(ConjoinedPlugin.movement))
            event.setCancelled(true);
    }

    /*@EventHandler
    public void handle(EntityTargetLivingEntityEvent event) {
	if(Main.isConjoined == true && event.getTarget().getUniqueId().equals(Main.interactionController))
	    event.setTarget(Bukkit.getPlayer(Main.movementController));
    }*/

    @EventHandler
    public void handle(EntityToggleSwimEvent event) {
        if(ConjoinedPlugin.isConjoined == true && event.getEntityType() == EntityType.PLAYER && event.getEntity().getUniqueId().equals(ConjoinedPlugin.movement)) {
            Bukkit.getPlayer(ConjoinedPlugin.interaction).setSwimming(true);
        }
    }

    @EventHandler
    public void handle(EntityDamageByEntityEvent event) {
        if(ConjoinedPlugin.isConjoined == true && event.getDamager().getType() == EntityType.PLAYER && event.getDamager().getUniqueId().equals(ConjoinedPlugin.movement)) {
            event.setCancelled(true);
        }
    }

    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
