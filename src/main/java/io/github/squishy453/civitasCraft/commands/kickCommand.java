package io.github.squishy453.civitasCraft.commands;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class kickCommand implements CommandExecutor {

    private final CivitasCraft plugin;

    public kickCommand(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //1. Check Permissions
        if (!sender.hasPermission("civitas.kick")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        //2. Check Arguments
        if (args.length < 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /kick <player> [reason]");
            return true;
        }

        //3. Get Player / Argument Return
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found or not online.");
            return true;
        }

        //4. Get Reason
        String reason = "You have been kicked from the server.";
        if (args.length > 1) {
            reason = String.join(" ", args).substring(args[0].length()).trim();
        }

        //5. Run Command Action
        target.kickPlayer(ChatColor.RED + reason);
        sender.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " has been kicked.");

        //6. Log Command
        plugin.logCommand("kick", sender.getName(),
                target.getName(), target.getUniqueId().toString(),
                "Kicked for: " + reason);

        return true;
    }
}