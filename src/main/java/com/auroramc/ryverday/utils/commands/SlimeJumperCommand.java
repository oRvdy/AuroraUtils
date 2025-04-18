package com.auroramc.ryverday.utils.commands;

import com.auroramc.ryverday.utils.utils.SlimeJumperManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SlimeJumperCommand extends Commands {

    protected SlimeJumperCommand() {
        super("slime", true);
    }

    @Override
    void perfomaceExecute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSomente jogadores podem executar este comando.");
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("utils.slime")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando.");
            return;
        }

        if (args.length < 2) {
            player.sendMessage("§cUtilize /slime [add/remove] [id]");
            return;
        }

        String action = args[0];
        String id = args[1];

        try {
            switch (action.toLowerCase()) {
                case "add": {
                    if (SlimeJumperManager.findById(id) != null) {
                        player.sendMessage("§cTente utilizar outro id! Esse já existe.");
                        return;
                    }

                    SlimeJumperManager.createSlimeJumper(id, player.getLocation().getBlock().getLocation());
                    player.sendMessage("§aSlime Jump criado com sucesso!");
                    break;
                }

                case "remove": {
                    if (SlimeJumperManager.findById(id) == null) {
                        player.sendMessage("§cTente utilizar outro id! Esse não existe.");
                        return;
                    }

                    SlimeJumperManager.deleteSlimeJumper(id);
                    player.sendMessage("§aSlime Jump deletado com sucesso!");
                    break;
                }

                default: {
                    player.sendMessage("§cUtilize /slime [add/remove] [id]");
                    break;
                }
            }
        } catch (Exception e) {
            // Log o erro e notifique o jogador
            e.printStackTrace();
            player.sendMessage("§cOcorreu um erro ao processar o comando. Verifique o console para mais informações.");
        }
    }
}
