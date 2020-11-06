package me.crazybanana.conjoinedplugin.handler;

import me.crazybanana.conjoinedplugin.ConjoinedPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class InventorySyncHandlers implements Listener {
    @EventHandler
    public void handle(InventoryClickEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            HumanEntity entity = event.getWhoClicked();
            if(entity != null && entity instanceof Player) {
                UUID playerWhoClicked = entity.getUniqueId();
                if(event.getInventory().getType() == InventoryType.CRAFTING) {
                    if(playerWhoClicked.equals(ConjoinedPlugin.interaction))
                        interactControllerInventoryChange();
                    else if(playerWhoClicked.equals(ConjoinedPlugin.movement))
                        movementControllerInventoryChange();
                }
                else {
                    if(playerWhoClicked.equals(ConjoinedPlugin.movement))
                        event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handle(PlayerSwapHandItemsEvent event){
        if(ConjoinedPlugin.isConjoined == true) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(ConjoinedPlugin.interaction))
                interactControllerInventoryChange();
            else if(player.equals(ConjoinedPlugin.movement))
                movementControllerInventoryChange();
        }
    }

    @EventHandler
    public void handle(InventoryDragEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            HumanEntity entity = event.getWhoClicked();
            if(entity != null && entity instanceof Player) {
                UUID playerWhoClicked = entity.getUniqueId();
                if(event.getInventory().getType() == InventoryType.CRAFTING) {
                    if(playerWhoClicked.equals(ConjoinedPlugin.interaction))
                        interactControllerInventoryChange();
                    else if(playerWhoClicked.equals(ConjoinedPlugin.movement))
                        movementControllerInventoryChange();
                }
                else {
                    if(playerWhoClicked.equals(ConjoinedPlugin.movement))
                        event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handle(EntityPickupItemEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            if(event.getEntity() != null && event.getEntityType() == EntityType.PLAYER) {
                UUID playerWhoClicked = event.getEntity().getUniqueId();
                if(playerWhoClicked.equals(ConjoinedPlugin.interaction))
                    interactControllerInventoryChange();
                else if(playerWhoClicked.equals(ConjoinedPlugin.movement))
                    movementControllerInventoryChange();
            }
        }
    }

    @EventHandler
    public void handle(PlayerDropItemEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(ConjoinedPlugin.interaction))
                interactControllerInventoryChange();
            else if(player.equals(ConjoinedPlugin.movement))
                movementControllerInventoryChange();
        }
    }

    @EventHandler
    public void handle(PlayerItemConsumeEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(ConjoinedPlugin.interaction))
                interactControllerInventoryChange();
            else if(player.equals(ConjoinedPlugin.movement))
                movementControllerInventoryChange();
        }
    }

    @EventHandler
    public void handle(BlockPlaceEvent event) {
        if(ConjoinedPlugin.isConjoined == true) {
            UUID player = event.getPlayer().getUniqueId();
            if(player.equals(ConjoinedPlugin.interaction))
                interactControllerInventoryChange();
            else if(player.equals(ConjoinedPlugin.movement))
                event.setCancelled(true);
        }
    }

    /*@EventHandler
    public void handle(InventoryOpenEvent event) {
	if(Main.isConjoined == true) {
	    Player movementController = Bukkit.getPlayer(Main.movementController);
	    if(!event.getViewers().contains(movementController) && event.getPlayer().getUniqueId().equals(Main.interactionController)) {
		new BukkitRunnable() {
		    @Override
		    public void run() {
			event.getViewers().add(movementController);
			movementController.openInventory(event.getView());
		    }
		}.runTaskLater(Main.plugin, 5);
	    }
	}
    }
    @EventHandler
    public void handle(InventoryCloseEvent event) {
	Bukkit.broadcastMessage(event.getPlayer().getName());
	HumanEntity player = event.getPlayer();
	if(player.getUniqueId().equals(Main.interactionController)) {
	    new BukkitRunnable() {
		@Override
		public void run() {
		    try {
			for(HumanEntity viewer : event.getViewers()) {
			    if(viewer.getOpenInventory().equals(event.getView()))
				viewer.closeInventory();
			}
		    }
		    catch (ConcurrentModificationException exception){
		    }
		}
	    }.runTaskLater(Main.plugin, 5);
	}
    }
    */

    private void movementControllerInventoryChange() {
        Player movementController = Bukkit.getPlayer(ConjoinedPlugin.movement);
        Player interactController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
        new BukkitRunnable() {
            int tick = 10;
            @Override
            public void run() {
                if(tick == 5) {
                    movementController.updateInventory();
                    tick--;
                }
                else if(tick == 0) {
                    interactController.getInventory().setContents(movementController.getInventory().getContents());
                    tick--;
                }
                else if(tick == -1) {
                    interactController.updateInventory();
                    this.cancel();
                }
                else
                    tick--;

            }
        }.runTaskTimer(ConjoinedPlugin.plugin, 0l, 1);
    }

    private void interactControllerInventoryChange() {
        Player movementController = Bukkit.getPlayer(ConjoinedPlugin.movement);
        Player interactController = Bukkit.getPlayer(ConjoinedPlugin.interaction);
        new BukkitRunnable() {
            int tick = 10;
            @Override
            public void run() {
                if(tick == 5) {
                    interactController.updateInventory();
                    tick--;
                }
                else if(tick == 0) {
                    movementController.getInventory().setContents(interactController.getInventory().getContents());
                    tick--;
                }
                else if(tick == -1) {
                    movementController.updateInventory();
                    this.cancel();
                }
                else
                    tick--;

            }
        }.runTaskTimer(ConjoinedPlugin.plugin, 0l, 1);
    }
}
