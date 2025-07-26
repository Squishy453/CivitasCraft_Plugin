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

        //1. Check Permissions
        if (!sender.hasPermission("civitas.unban")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        //2. Check Arguments
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /unban <player> [reason]");
            return true;
        }

        String playerName = args[0];

        //3. Get Player / Argument Return
        BanEntry banEntry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(playerName);
        if (banEntry == null) {
            sender.sendMessage(ChatColor.YELLOW + playerName + " is not banned.");
            return true;
        }

        //4. Optional - Grab Reason
        String reason = "Unbanned by " + sender.getName();
        if (args.length > 1) {
            reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        }

        //5. Run Command Action
        Bukkit.getBanList(BanList.Type.NAME).pardon(playerName);

        //6. Notify Sender
        sender.sendMessage(ChatColor.GREEN + playerName + " has been unbanned. Reason: " + reason);


        //7. Log Command
        plugin.getLogger().info(sender.getName() + " unbanned " + playerName + " for: " + reason);

        plugin.logCommand("unban", sender.getName(),
                playerName, banEntry.getTarget(),
                "Unbanned for: " + reason);

        return true;
    }
}