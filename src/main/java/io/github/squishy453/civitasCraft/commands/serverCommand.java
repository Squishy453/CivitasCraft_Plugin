package io.github.squishy453.civitasCraft.commands;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class serverCommand implements CommandExecutor {

    private final CivitasCraft plugin;

    public serverCommand(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //1. Check Permissions
        if (!sender.hasPermission("civitas.server")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        //2. Check Arguments
        if (args.length < 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /server <reload|restart|close>");
            return true;
        }

        String action = args[0].toLowerCase();

        //3. Run Command Action
        switch (action) {
            case "reload":
                sender.sendMessage(ChatColor.GREEN + "Reloading server...");
                plugin.logCommand("server", sender.getName(), "SERVER", "N/A", "Reloaded the server");
                Bukkit.reload();
                break;

            case "restart":
                sender.sendMessage(ChatColor.GREEN + "Restarting server...");
                plugin.logCommand("server", sender.getName(), "SERVER", "N/A", "Restarted the server");
                Bukkit.spigot().restart();
                break;

            case "stop":
                sender.sendMessage(ChatColor.RED + "Shutting down server...");
                plugin.logCommand("server", sender.getName(), "SERVER", "N/A", "Shut down the server");
                Bukkit.shutdown();
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown action. Use: reload, restart, or close.");
                break;
        }

        return true;
    }
}