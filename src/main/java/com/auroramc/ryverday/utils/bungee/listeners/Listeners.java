package com.auroramc.ryverday.utils.bungee.listeners;

import com.auroramc.ryverday.utils.bungee.Bungee;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.Objects;

public class Listeners implements Listener {

    @Deprecated
    @EventHandler
    public void onPingServer(ProxyPingEvent event) {
        List<String> moted = Objects.requireNonNull(Bungee.getYaml("options.yml", "plugins/" + Bungee.getInstance().getDescription().getName())).getStringList("moted");
        ServerPing serverPing = new ServerPing();
        if (Bungee.getMaintence()) {
            serverPing.setVersion(new ServerPing.Protocol("MANUTENÇÃO", 0));
            serverPing.setFavicon(event.getResponse().getFavicon());
            serverPing.setDescription("§d§lAURORAMC §8[1.8 - 1.19.3]\n§cEstamos em manutenção. Voltamos já!");
        } else {
            serverPing.setVersion(event.getResponse().getVersion());
            serverPing.setFavicon(event.getResponse().getFavicon());
            serverPing.setPlayers(event.getResponse().getPlayers());
            serverPing.setDescription(moted.get((int) (Math.random() * moted.size())).replace("\\n", "\n"));
        }
        event.setResponse(serverPing);
    }

    @EventHandler
    public void onLoginServer(ServerConnectEvent event) {
        if (Bungee.getMaintence()) {
            if (!event.getPlayer().hasPermission("maintence.bypass")) {
                event.getPlayer().disconnect(new TextComponent("§cAURORA-MC\n \n§cEstamos em manutenção, voltamos já!"));
            }
        }
    }
}
