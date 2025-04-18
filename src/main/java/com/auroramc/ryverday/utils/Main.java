package com.auroramc.ryverday.utils;

import com.auroramc.ryverday.utils.listeners.Listeners;
import com.auroramc.ryverday.utils.commands.Commands;
import com.auroramc.ryverday.utils.utils.SlimeJumperManager;
import com.auroramc.ryverday.utils.utils.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class Main extends JavaPlugin {

    private static Main instance;
    private static final List<String> OFFENCIVE_WORDS = new ArrayList<>();
    public static Boolean blockChat = false;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        Commands.setupCommands();
        VanishManager.setupVanish();
        Listeners.setupListeners();
        SlimeJumperManager.setupSlimes();
        OFFENCIVE_WORDS.addAll(this.getConfig().getStringList("offencive_words"));

        sendMessage("O plugin iniciou com sucesso!");
    }

    @Override
    public void onDisable() {
        VanishManager.getTask().cancel();
        sendMessage("Todas as palavras ofencivas foram salvas com sucesso!");
        sendMessage("O plugin desligou com sucesso!");
    }

    public void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage("§a[" + this.getDescription().getName() + "] " + message);
    }

    public static Main getInstance() {
        return instance;
    }

    public static List<String> getOffenciveWords() {
        return OFFENCIVE_WORDS;
    }

    public static void addOffenciveWork(String ofw) {
        OFFENCIVE_WORDS.add(ofw);
        Main.getInstance().getConfig().set("offencive_words", OFFENCIVE_WORDS);
        try {
            Main.getInstance().getConfig().save(new File("plugins/" +  Main.getInstance().getDescription().getName() + "/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeOffenciveWork(String ofw) {
        OFFENCIVE_WORDS.remove(ofw);
        Main.getInstance().getConfig().set("offencive_words", OFFENCIVE_WORDS);
        try {
            Main.getInstance().getConfig().save(new File("plugins/" +  Main.getInstance().getDescription().getName() + "/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
