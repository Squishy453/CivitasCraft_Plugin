package io.github.squishy453.civitasCraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class civitasCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public civitasCommand(JavaPlugin plugin) {

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Check permission (civitas.use)
        if (!sender.hasPermission("civitas.command")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission!");
            return true;
        }

        //Handle /civitas or /civitas help
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.GREEN + "----[ CivitasCraft Commands ]----");
            sender.sendMessage(ChatColor.YELLOW + "/civitas help" + ChatColor.GRAY + " - Show this help menu");
            sender.sendMessage(ChatColor.YELLOW + "/civitas reload" + ChatColor.GRAY + " - Reload plugin config (coming soon)");
            sender.sendMessage(ChatColor.YELLOW + "/civitas rank" + ChatColor.GRAY + " - Rank commands (coming soon)");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /civitas help for usage.!");
        return true;
    }
}