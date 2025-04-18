package com.auroramc.ryverday.utils.commands;

import dev.auroramc.laas.player.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CCCommand extends Commands {

    protected CCCommand() {
        super("cc", false, "clearchat");
    }

    @Override
    void perfomaceExecute(CommandSender sender, String s, String[] args) {
        if (!sender.hasPermission("utils.clearchat")) {
            sender.sendMessage("§cEste comando é exclusivo para o grupo §4Gerente §cou superior.");
            return;
        }

        for (int i = 0; i < 100; i++) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(" "));
        }

        if (sender instanceof Player) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("§fO chat foi limpo pelo " + Role.getPrefixed(sender.getName())));
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("§fO chat foi limpo pelo §aConsole"));
        }
    }
}
