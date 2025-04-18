package com.auroramc.ryverday.utils.commands;

import com.auroramc.ryverday.utils.utils.VanishManager;
import dev.auroramc.laas.cosmetics.api.CosmeticsAPI;
import dev.auroramc.laas.player.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand extends Commands{

    protected VanishCommand() {
        super("vanish", true, "v");
    }

    @Override
    void perfomaceExecute(CommandSender sender, String s, String[] args) {
        Player player = (Player) sender;

        if (!player.hasPermission("utils.vanish.use")) {
            player.sendMessage("§cEste comando é exclusivo para §eAjudante §cou superior.");
            return;
        }

        if (Profile.getProfile(player.getName()).playingGame()) {
            player.sendMessage("§cNão é possível ativar o vanish em partida.");
            return;
        }

        if (VanishManager.isPlayerVanish(player)) {
            player.sendMessage("§cVocê desativou o modo vanish!");
            VanishManager.removePlayerVanish(player);
            player.setAllowFlight(false);
            CosmeticsAPI.enable(player);
        } else {
            player.sendMessage("§aVocê entrou no modo vanish!");
            VanishManager.addPlayerVanish(player);
            player.setAllowFlight(true);
            CosmeticsAPI.disable(player);
        }
    }
}
