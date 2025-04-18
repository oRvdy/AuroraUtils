package com.auroramc.ryverday.utils.bungee.commands;

import com.auroramc.ryverday.utils.bungee.Bungee;
import dev.auroramc.laas.player.role.Role;
import dev.auroramc.laas.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Objects;


public class StaffChatCommand extends Commands {

    public StaffChatCommand() {
        super("s", "sc");
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cSomente jogadores podem utilizar este comando."));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (!player.hasPermission("utils.staffchat.use")) {
            player.sendMessage(TextComponent.fromLegacyText("§cSomente ajudante ou superior podem executar este comando."));
            return;
        }
        if (args.length < 1) {
            player.sendMessage(TextComponent.fromLegacyText("§cUtilize \"/s [mensagem]\". para enviar uma mensagem\nUtilize \"/s toggle\" para desativar o chat staff."));
            return;
        }

        if (Objects.equals(args[0], "toggle")) {
            if (Bungee.getChatToggle().contains(player.getName())) {
                player.sendMessage(TextComponent.fromLegacyText("§aVocê ativou o chat staff!"));
                Bungee.getChatToggle().remove(player.getName());
            } else {
                player.sendMessage(TextComponent.fromLegacyText("§aVocê desativou o chat staff!"));
                Bungee.getChatToggle().add(player.getName());
            }
            return;
        }
        String format = StringUtils.formatColors(StringUtils.join((Object[])args, " "));
        Bungee.getInstance().getProxy().getPlayers().stream().filter(player1 -> player1.hasPermission("utils.staffchat.use") && !Bungee.getChatToggle().contains(player1.getName())).forEach(player1 -> player1.sendMessage(TextComponent.fromLegacyText("§d§l[§dStaff§d§l] " + Role.getPrefixed(player.getName(), true) + "§f: " + format)));
    }
}
