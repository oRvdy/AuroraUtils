package com.auroramc.ryverday.utils.bungee.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.auroramc.ryverday.utils.bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Objects;

public class ManutencaoCommand extends Commands {

    public ManutencaoCommand() {
        super("manutencao");
    }

    @Deprecated
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (!sender.hasPermission("utils.maitence.ativar")) {
            sender.sendMessage(TextComponent.fromLegacyText("§cExclusivo para §4Gerente §cou superior."));
            return;
        }

        if (Bungee.getMaintence()) {
            sender.sendMessage(TextComponent.fromLegacyText("§c§lMANUTENÇÃO §cManutenção desativada com sucesso!"));
            Bungee.setMaintence(false);
            Objects.requireNonNull(Bungee.getYaml("options.yml", "plugins/" + Bungee.getInstance().getDescription().getName())).set("manutencao", false);
        } else {
            sender.sendMessage(TextComponent.fromLegacyText("§c§lMANUTENÇÃO §aManutenção ativada com sucesso!"));
            Bungee.setMaintence(true);
            Objects.requireNonNull(Bungee.getYaml("options.yml", "plugins/" + Bungee.getInstance().getDescription().getName())).set("manutencao", true);
            for (ProxiedPlayer player : Bungee.getInstance().getProxy().getPlayers()) {
                if (!player.hasPermission("maintence.bypass")) {
                    player.disconnect(TextComponent.fromLegacyText("§cAURORA-MC\n \n§cEstamos em manutenção, voltamos já!"));
                    return;
                }

                player.sendMessage(TextComponent.fromLegacyText("§cManutenção ativa pelo jogador: " + player.getName()));
                ByteArrayDataOutput output = ByteStreams.newDataOutput();
                output.writeUTF("SOUND");
                output.writeUTF("ENDERDRAGON_GROWL");
                output.writeFloat(1.0F);
                output.writeFloat(2.0F);
                output.writeUTF(player.getName());
                player.getServer().sendData("kCore", output.toByteArray());
            }
        }
    }
}
