package com.auroramc.ryverday.utils.commands;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GmCommand extends Commands {
    protected GmCommand() {
        super("gm", true);
    }

    @Override
    void perfomaceExecute(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("utils.gm")) {
                sender.sendMessage("§cEste comando é exclusivo para o grupo §4Gerente §cou superior.");
                return;
            }

            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage("§cUse /gm [0/1/2/3]");
                return;
            }

            switch (args[0]) {

                case "0": {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage("§aGamemode alterado para o Survival.");
                    break;
                }

                case "1": {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage("§aGamemode alterado para o Creative.");
                    break;
                }

                case "2": {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage("§aGamemode alterado para o Adventure.");
                    break;
                }

                case "3": {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage("§aGamemode alterado para o Spectator.");
                    break;
                }
            }
        }
    }
}
