package io.github.squishy453.civitasCraft.commands;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class civitasCommand implements CommandExecutor {

    private final CivitasCraft plugin;
    public civitasCommand(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //A. No Arguements
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "----[ CivitasCraft Menu Directory ]----");
            sender.sendMessage(ChatColor.GRAY + "Use /civitas [menu]");
            sender.sendMessage(ChatColor.GRAY + "Options: General, Player Info, Server Info, Rank, Moderation, or Permissions");
            return true;
        }

        //Logic
        String menu = args[0].toLowerCase();

        switch(menu) {

            //A. General
            case "general" :

                if (!sender.hasPermission("civitas.use")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                    return true;
                }

                sender.sendMessage(ChatColor.GREEN + "--[ CivitasCraft General Menu ]--");
                break;

            //B. Player Info
            case "player_info" :

                if (!sender.hasPermission("civitas.use")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                    return true;
                }

                sender.sendMessage(ChatColor.GREEN + "--[ CivitasCraft Player Info Menu ]--");
                break;

            //C. Server Info
            case "server_info" :

                if (!sender.hasPermission("civitas.use")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                    return true;
                }

                sender.sendMessage(ChatColor.GREEN + "--[ CivitasCraft Server Info Menu ]--");
                break;

            //D. Rank
            case "rank" :

                if (!sender.hasPermission("civitas.use")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                    return true;
                }

                sender.sendMessage(ChatColor.GREEN + "--[ CivitasCraft Rank Menu ]--");
                break;

            //E. Moderation
            case "moderation" :

                if (!sender.hasPermission("civitas.staff")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                    return true;
                }

                sender.sendMessage(ChatColor.GREEN + "--[ CivitasCraft Moderation Menu ]--");
                break;

            //F. Permissions
            case "permissions" :

                if (!sender.hasPermission("civitas.owners")) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                    return true;
                }

                sender.sendMessage(ChatColor.GREEN + "--[ CivitasCraft Permissions Menu ]--");
                break;

        }

        return true;
    }
}