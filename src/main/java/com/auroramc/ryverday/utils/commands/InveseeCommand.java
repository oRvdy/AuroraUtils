package com.auroramc.ryverday.utils.commands;

import com.auroramc.ryverday.utils.utils.InvseeManager;
import dev.auroramc.laas.utils.enums.EnumSound;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InveseeCommand extends Commands{

    protected InveseeCommand() {
        super("invsee", true);
    }

    @Override
    void perfomaceExecute(CommandSender sender, String s, String[] args) {
        Player player = (Player) sender;
        Player target = null;

        if (args.length < 1) {
            player.sendMessage("§cUtilize /invsee (player).");
            return;
        }

        try {
            target = Bukkit.getPlayer(args[0]);
        } catch (Exception e) {
            player.sendMessage("§cEste jogador não existe ou está offline.");
        }

        assert target != null;
        new InvseeManager(player, target).setupInv();
        player.sendMessage("§cAbrindo o inventário do jogador " + target.getName() + ".");
        EnumSound.NOTE_PLING.play(player, 1.0F, 1.0F);
    }
}
