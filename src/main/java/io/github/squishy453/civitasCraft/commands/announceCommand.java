package io.github.squishy453.civitasCraft.commands;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class announceCommand implements CommandExecutor {

    private final CivitasCraft plugin;

    public announceCommand(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //1. Check Permissions
        if (!sender.hasPermission("civitas.announce")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }

        //2. Check Arguments
        if (args.length < 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /announce <message>");
            return true;
        }

        //3. Build
        String message = String.join("", args);

        //4. Run Command Action
        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[ANNOUNCEMENT] " + ChatColor.RESET + ChatColor.YELLOW + message);

        //5. Notify Sender
        sender.sendMessage(ChatColor.GREEN + "Announcement sent.");

        //6. Log Command
        plugin.logCommand("announce", sender.getName(), "ALL", "N/A", "Announcement: " + message);

        return true;

    }
}