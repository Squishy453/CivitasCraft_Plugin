package io.github.squishy453.civitasCraft.commands;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public class banCommand implements CommandExecutor {

    private final CivitasCraft plugin;

    //Time Calculation
    private long parseDuration(String input) {
        try {
            char timeUnit = input.charAt(input.length() - 1);
            long timeValue = Long.parseLong(input.substring(0, input.length() - 1));

            switch (timeUnit) {
                case 's': return timeValue * 1000;
                case 'm': return timeValue * 60 * 1000;
                case 'h': return timeValue * 60 * 60 * 1000;
                case 'd': return timeValue * 24 * 60 * 60 * 1000;
                default: return -1; // Invalid unit
            }
        } catch (Exception e) {
            return -1;
        }
    }


    public banCommand(CivitasCraft plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Define reason
        String reason = "Banned by an operator."; // default
        if (args.length > 2) {
            reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
        }

        //1. Check Permissions
        if (!(sender.hasPermission("command.ban"))) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!.");
            return true;
        }

        //2. Check Arguements
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /command ban <player> [reason] [duration]");
            return true;
        }

        //3. Get Player
        Player target = Bukkit.getPlayer(args[0]);
        if (target ==null) {
            sender.sendMessage(ChatColor.RED + "That player is not found!");
            return true;
        }

        //4. Parse Duration
        long durationMillis = parseDuration(args[1]);
        if (durationMillis < 0) {
            sender.sendMessage(ChatColor.RED + "Invalid duration! Use formats like 1h, 2d, 30m.");
            return true;
        }

        //5. Building
        if (args.length > 2) {
            reason = String.join(" ", args).substring(args[0].length() + args[1].length()).trim();
        }

        //6. Calculate Expiration Date
        Date expiry = durationMillis == 0 ? null : new Date(System.currentTimeMillis() + durationMillis);

        //7. Ban & Kick
        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, expiry, sender.getName());
        target.kickPlayer(ChatColor.RED + "You have been banned!\nReason: " + reason +
                (expiry != null ? "\nExpires: " + expiry : "\nDuration: Permanent"));

        //8. Notify Sender
        sender.sendMessage(ChatColor.GREEN + target.getName() + " has been banned for: " + reason +
                (expiry != null ? " (Expires: " + expiry + ")" : " (Permanent)"));
        return true;
    }
}