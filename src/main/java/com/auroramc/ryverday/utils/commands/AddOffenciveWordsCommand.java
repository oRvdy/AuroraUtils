package com.auroramc.ryverday.utils.commands;

import com.auroramc.ryverday.utils.Main;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class AddOffenciveWordsCommand extends Commands {

    protected AddOffenciveWordsCommand() {
        super("ofw", false);
    }

    @Override
    void perfomaceExecute(CommandSender sender, String s, String[] args) {
        if (!sender.hasPermission("utils.add.offencive")) {
            sender.sendMessage("§cEste recurso é somente para staffers.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUse /ofw banir [PALAVRA] ou /ofw liberar [PALAVRA]");
            return;
        }

        String type = args[0];
        String offenciveWord = args[1];

        switch (type) {

            case "banir": {
                if (Main.getOffenciveWords().stream().anyMatch(s1 -> s1.toLowerCase(Locale.ROOT).equalsIgnoreCase(offenciveWord))) {
                    sender.sendMessage("§cEsta palavra já foi banida.");
                    break;
                }

                sender.sendMessage("§aVocê baniu com sucesso a palavra: " + offenciveWord);
                Main.addOffenciveWork(offenciveWord);
                break;
            }

            case "liberar": {
                if (Main.getOffenciveWords().stream().noneMatch(s1 -> s1.toLowerCase(Locale.ROOT).equalsIgnoreCase(offenciveWord))) {
                    sender.sendMessage("§cEsta palavra ainda não foi banida.");
                    break;
                }

                sender.sendMessage("§aVocê liberou com sucesso a palavra: " + offenciveWord);
                Main.removeOffenciveWork(offenciveWord);
                break;
            }
        }
    }
}
