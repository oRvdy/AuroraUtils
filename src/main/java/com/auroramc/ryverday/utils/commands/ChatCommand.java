package com.auroramc.ryverday.utils.commands;

import com.auroramc.ryverday.utils.Main;
import org.bukkit.command.CommandSender;

public class ChatCommand extends Commands {

    protected ChatCommand() {
        super("chat", false);
    }

    @Override
    void perfomaceExecute(CommandSender sender, String s, String[] args) {
        if (!sender.hasPermission("utils.chat.admin")) {
            sender.sendMessage("§cEste comando é exclusivo para §4Gerente §cou superior.");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUse /chat [OFF/ON]");
        }

        if (args[0].equalsIgnoreCase("ON")) {
            sender.sendMessage("§aVocê ativou com sucesso o chat!");
            Main.blockChat = true;
        } else if (args[0].equalsIgnoreCase("OFF")){
            sender.sendMessage("§aVocê desativou com sucesso o chat!");
            Main.blockChat = false;
        }
    }
}
