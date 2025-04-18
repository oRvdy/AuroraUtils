package com.auroramc.ryverday.utils.utils.invsee;

import com.auroramc.ryverday.utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;

public class InvseeInventory implements Listener {

    private final Inventory inventory;
    private final Player player;
    private final Player target;

    public InvseeInventory(Player player, Player target) {
        this.player = player;
        this.target = target;
        this.inventory = Bukkit.createInventory(null, 4 * 9, "Invetário do jogador " + target.getName());
        this.inventory.setContents(target.getInventory().getContents());
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    public void open() {
        player.openInventory(this.inventory);
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (event.getDestination().equals(this.inventory)) {
            this.target.getInventory().clear();
            this.target.getInventory().setContents(this.inventory.getContents());
            this.inventory.clear();
            this.inventory.setContents(this.target.getInventory().getContents());
        }
    }

    @EventHandler
    public void onInventoryGetItem(InventoryPickupItemEvent event) {
        if (event.getInventory().equals(this.inventory)) {
            this.inventory.clear();
            this.inventory.setContents(this.target.getInventory().getContents());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(this.inventory)) {
            destroy();
        }
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getTarget() {
        return target;
    }
}
