package com.auroramc.ryverday.utils.utils;

import com.auroramc.ryverday.utils.utils.invsee.InvseeInventory;
import org.bukkit.entity.Player;

public class InvseeManager {

    private final Player player;
    private final Player targetPlayer;

    public InvseeManager(Player player, Player targetPlayer) {
        this.player = player;
        this.targetPlayer = targetPlayer;
    }

    public void setupInv() {
        new InvseeInventory(this.player, this.targetPlayer).open();
    }

    public Player getPlayer() {
        return player;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }
}
