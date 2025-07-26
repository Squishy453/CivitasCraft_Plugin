package io.github.squishy453.civitasCraft.listeners;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final CivitasCraft plugin;

    public ChatListener(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.isPlayerMuted(player.getUniqueId())) {
            event.setCancelled(true);
            CivitasCraft.MuteInfo info = plugin.getMuteInfo(player.getUniqueId());
            String durationMsg = (info.unmuteTime > 0)
                    ? " until " + new java.util.Date(info.unmuteTime).toString()
                    : " indefinitely";

            player.sendMessage(ChatColor.RED + "You are muted" + durationMsg + ". Reason: " + info.reason);
        }
    }
}