package com.auroramc.ryverday.utils.bungee;

import com.auroramc.ryverday.utils.bungee.commands.Commands;
import com.auroramc.ryverday.utils.bungee.listeners.Listeners;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Bungee extends Plugin {

    private static Bungee instance;
    private static Boolean isMaintence;
    private static List<String> CHAT_TOGGLE;

    @Override
    public void onLoad() {
        instance = this;
        isMaintence = Objects.requireNonNull(getYaml("options.yml", "plugins/" + getDescription().getName())).getBoolean("manutencao");
        CHAT_TOGGLE = new ArrayList<>();
        getProxy().registerChannel("AuroraCommon");
    }

    @Override
    public void onEnable() {
        Commands.setupCommands();
        getProxy().getPluginManager().registerListener(this, new Listeners());
        this.getLogger().log(Level.FINE, "Plugin iniciado com sucesso.");
        this.getLogger().log(Level.WARNING, "ESSA POHA LIGOU E VC GAY");
    }

    @Override
    public void onDisable() {}

    @SuppressWarnings("All")
    public static Configuration getYaml(String fileName, String filePath) {
        File file = new File(filePath + "/" + fileName);
        if (!file.exists()) {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }
            try {
                Files.copy(Objects.requireNonNull(Bungee.class.getResourceAsStream("/" + fileName)), file.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            return YamlConfiguration.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
    public static Bungee getInstance() {
        return instance;
    }

    public static List<String> getChatToggle() {
        return CHAT_TOGGLE;
    }

    public static Boolean getMaintence() {
        return isMaintence;
    }

    public static void setMaintence(Boolean isMaintence) {
        Bungee.isMaintence = isMaintence;
    }
}
