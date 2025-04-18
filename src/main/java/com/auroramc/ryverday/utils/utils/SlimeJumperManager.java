package com.auroramc.ryverday.utils.utils;

import com.auroramc.ryverday.utils.Main;
import dev.auroramc.laas.utils.BukkitUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("All")
public class SlimeJumperManager {

    private static final List<SlimeJumperManager> SLIMES_CACHE = new ArrayList<>();
    private final String id;
    private final Location location;

    public static void setupSlimes() {
        File file = new File("plugins/" + Main.getInstance().getDescription().getName() + "/locations.yml");
        if (!file.exists()) {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                file.mkdirs();
            }

            try {
                Files.copy(Objects.requireNonNull(SlimeJumperManager.class.getResourceAsStream("/locations.yml")), file.toPath());
            } catch (Exception e) {
                System.out.print("O arquivo não existe.");
            }
        }

        YamlConfiguration CONFIG = YamlConfiguration.loadConfiguration(file);
        for (String key : CONFIG.getConfigurationSection("locations.slimes").getKeys(false)) {
            String location = CONFIG.getString("locations.slimes." + key + ".location");
            SlimeJumperManager manager = new SlimeJumperManager(key, BukkitUtils.deserializeLocation(location));
            SLIMES_CACHE.add(manager);
        }

        updateSlimes();
    }

    public static void createSlimeJumper(String id, Location location) {
        SlimeJumperManager jumper = new SlimeJumperManager(id, location);
        jumper.updateParticle();
        SLIMES_CACHE.add(jumper);
        File file = new File("plugins/" + Main.getInstance().getDescription().getName() + "/locations.yml");
        YamlConfiguration CONFIG = YamlConfiguration.loadConfiguration(file);
        CONFIG.set("locations.slimes." + id + ".location", BukkitUtils.serializeLocation(location));
        try {
            CONFIG.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteSlimeJumper(String id) {
        SlimeJumperManager jumper = findById(id);
        SLIMES_CACHE.remove(jumper);
        File file = new File("plugins/" + Main.getInstance().getDescription().getName() + "/locations.yml");
        YamlConfiguration CONFIG = YamlConfiguration.loadConfiguration(file);
        CONFIG.set("locations.slimes." + id + ".location", null);
        try {
            CONFIG.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SlimeJumperManager findById(String id) {
        return SLIMES_CACHE.stream().filter(slimeJumperManager -> slimeJumperManager.getId().equals(id)).findFirst().orElse(null);
    }

    public static void updateSlimes() {
        new BukkitRunnable() {
            @Override
            public void run() {
                SLIMES_CACHE.forEach(SlimeJumperManager::updateParticle);
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 10L);
    }

    public static List<SlimeJumperManager> getSlimesCache() {
        return SLIMES_CACHE;
    }

    public SlimeJumperManager(String id, Location location) {
        this.id = id;
        this.location = location;
    }

    public void updateParticle() {
        for (Player player : this.location.getWorld().getPlayers()) {
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, (float) ((float) this.location.getX() + ThreadLocalRandom.current().nextDouble(1.0)), (float) this.location.getY() + 0.1F, (float) ((float) this.location.getZ() + ThreadLocalRandom.current().nextDouble(1.0)), 0,0,0,0,3);
            CraftPlayer craftPlayer = (CraftPlayer) player;
            craftPlayer.getHandle().playerConnection.sendPacket(packet);
        }
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }
}
