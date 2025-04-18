package com.auroramc.ryverday.utils.listeners;

import com.auroramc.ryverday.utils.Main;
import com.auroramc.ryverday.utils.utils.SlimeJumperManager;
import com.auroramc.ryverday.utils.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

import java.util.Locale;

@SuppressWarnings("all")
public class Listeners implements Listener {

    public static void setupListeners() {
        Bukkit.getPluginManager().registerEvents(new Listeners(), Main.getInstance());
    }

    @EventHandler
    public void onInteractPlayer(PlayerInteractEntityEvent evt) {
        if (evt.getRightClicked() instanceof Player) {
            Player player = evt.getPlayer();
            if (VanishManager.isPlayerVanish(player)) {
                Player playerTarget = (Player) evt.getRightClicked();
                //player.performCommand("invsee " + playerTarget.getName());
            }
        }
    }

    @EventHandler
    public void onPlayerDamageVanished(EntityDamageEvent event) {
        if (event.getEntity() instanceof  Player) {
            Player player = (Player) event.getEntity();
            if (VanishManager.isPlayerVanish(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMoveListeners(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        try {
            if (SlimeJumperManager.getSlimesCache().stream().anyMatch(slimeJumperManager -> slimeJumperManager.getLocation().equals(player.getLocation().getBlock().getLocation()))) {
                Vector vector = player.getLocation().getDirection().normalize();
                vector.multiply(4);
                vector.setY(1);
                player.setVelocity(vector);
                player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 2.5F);
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onPlayerDisonnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (VanishManager.isPlayerVanish(player)) {
            VanishManager.removePlayerVanish(player);
        }
    }

    @EventHandler
    public void onPlayerChatSendOffenciveMessage(AsyncPlayerChatEvent event) {
        if (Main.blockChat && !event.getPlayer().hasPermission("utils.broken.chat")) {
            event.setCancelled(true);
            return;
        }

        String message = event.getMessage();
        StringBuilder finalMessage = new StringBuilder();
        for (String a : message.split(" ")) {
            if (Main.getOffenciveWords().stream().anyMatch(s -> a.toLowerCase(Locale.ROOT).contains(s))) {
                finalMessage.append(a.replace(a, "****")).append(" ");
            } else {
                finalMessage.append(a).append(" ");
            }
        }
        event.setMessage(finalMessage.toString());
    }
}
