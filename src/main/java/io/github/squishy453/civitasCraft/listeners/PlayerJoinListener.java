package io.github.squishy453.civitasCraft.listeners;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final CivitasCraft plugin;

    public PlayerJoinListener(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getPlayersConfig().contains("players." + player.getUniqueId())) {
            plugin.getPlayersConfig().set("players." + player.getUniqueId(), "visitor");
            plugin.savePlayersConfig();
            player.sendMessage("You have been assigned the role: visitor");
        }
    }
}