package io.github.squishy453.civitasCraft.commands;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unmuteCommand implements CommandExecutor {

    private final CivitasCraft plugin;

    public unmuteCommand(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //1. Check Permissions
        if (!sender.hasPermission("civitas.mute")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        //2. Check Arguments
        if (args.length != 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /unmute <player>");
            return true;
        }

        //3. Get Player / Argument Return
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found or not online.");
            return true;
        }

        //4. Notify Sender / Player
        if (plugin.unmutePlayer(target.getUniqueId())) {
            target.sendMessage(ChatColor.GREEN + "You have been unmuted.");
            sender.sendMessage(ChatColor.GREEN + target.getName() + " has been unmuted.");
            plugin.logCommand("unmute", sender.getName(), target.getName(), target.getUniqueId().toString(), "Unmuted");
        } else {
            sender.sendMessage(ChatColor.RED + "That player is not muted.");
        }

        return true;
    }
}