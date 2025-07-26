package io.github.squishy453.civitasCraft.commands;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class unbanCommand implements CommandExecutor {

    private final CivitasCraft plugin;

    public unbanCommand(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Permission check
        if (!sender.hasPermission("civitas.unban")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        // Argument check
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /unban <player> [reason]");
            return true;
        }

        String playerName = args[0];

        // Check if the player is actually banned
        BanEntry banEntry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(playerName);
        if (banEntry == null) {
            sender.sendMessage(ChatColor.YELLOW + playerName + " is not banned.");
            return true;
        }

        // Optional reason
        String reason = "Unbanned by " + sender.getName();
        if (args.length > 1) {
            reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        }

        // Remove ban
        Bukkit.getBanList(BanList.Type.NAME).pardon(playerName);

        // Notify sender
        sender.sendMessage(ChatColor.GREEN + playerName + " has been unbanned. Reason: " + reason);


        plugin.getLogger().info(sender.getName() + " unbanned " + playerName + " for: " + reason);

        plugin.logCommand("unban", sender.getName(),
                playerName, banEntry.getTarget(),
                "Unbanned for: " + reason);

        return true;
    }
}