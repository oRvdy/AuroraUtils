package com.auroramc.ryverday.utils.utils;

import com.auroramc.ryverday.utils.Main;
import com.auroramc.ryverday.utils.nms.NMS;
import dev.auroramc.laas.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class VanishManager {

    private static final List<Player> PLAYERS_VANISHS = new ArrayList<>();
    private static BukkitTask task;

    public static void setupVanish() {
        startRunnable();
    }

    public static BukkitTask getTask() {
        return task;
    }

    public static void addPlayerVanish(Player vanisher) {
        PLAYERS_VANISHS.add(vanisher);
    }

    public static void removePlayerVanish(Player vanisher) {
        PLAYERS_VANISHS.remove(vanisher);
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.showPlayer(vanisher);
        }
    }

    public static boolean isPlayerVanish(Player vanisher) {
        return PLAYERS_VANISHS.contains(vanisher);
    }

    public static void startRunnable() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : PLAYERS_VANISHS) {
                    if (Profile.getProfile(player.getName()).playingGame()) {
                        player.sendMessage("§cVanish desativado pelo fato de você estar em partida.");
                        VanishManager.removePlayerVanish(player);
                        player.setAllowFlight(false);
                        return;
                    }

                    NMS.sendActionBar(player, "§aVocê está invisivel para os outros jogadores!");
                    if (!player.getAllowFlight()) {
                        player.setAllowFlight(true);
                    }
                    for (Player online : Bukkit.getOnlinePlayers()) {
                       if (!online.hasPermission("utils.vanish.see")) {
                            online.hidePlayer(player);
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 5L);
    }
}
