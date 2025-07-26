package io.github.squishy453.civitasCraft.commands;

import io.github.squishy453.civitasCraft.CivitasCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class muteCommand implements CommandExecutor {

    private final CivitasCraft plugin;

    public muteCommand(CivitasCraft plugin) {
        this.plugin = plugin;
    }

    private boolean isDuration(String arg) {
        return arg.matches("\\d+[smhd]");
    }

    private long parseDuration(String arg) {
        long multiplier = 0;
        int value = Integer.parseInt(arg.replaceAll("[^\\d]", ""));
        if (arg.endsWith("s")) multiplier = 1000L;
        else if (arg.endsWith("m")) multiplier = 60 * 1000L;
        else if (arg.endsWith("h")) multiplier = 60 * 60 * 1000L;
        else if (arg.endsWith("d")) multiplier = 24 * 60 * 60 * 1000L;
        return value * multiplier;
    }

    private String formatDuration(long millis) {
        long seconds = millis / 1000;
        if (seconds < 60) return seconds + " seconds";
        long minutes = seconds / 60;
        if (minutes < 60) return minutes + " minutes";
        long hours = minutes / 60;
        if (hours < 24) return hours + " hours";
        long days = hours / 24;
        return days + " days";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //1. Check Permissions
        if (!sender.hasPermission("civitas.mute")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        //2. Check Arguments
        if (args.length < 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /mute <player> [reason] [duration]");
            return true;
        }

        //3. Get Player / Argument Return
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found or not online.");
            return true;
        }

        //4. Parse Reason / Duration
        String reason = "No reason specified";
        long durationMillis = -1;
        if (args.length >= 2) {
            String lastArg = args[args.length - 1];
            if (isDuration(lastArg)) {
                durationMillis = parseDuration(lastArg);
                if (args.length > 2) {
                    reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length - 1));
                }
            } else {
                reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            }
        }

        //5. Notify Sender / Player
        target.sendMessage(ChatColor.RED + "You have been muted. Reason: " + reason +
                (durationMillis > 0 ? " Duration: " + formatDuration(durationMillis) : " Duration: indefinite"));
        sender.sendMessage(ChatColor.GREEN + target.getName() + " has been muted.");

        //6. Log Command
        plugin.logCommand("mute", sender.getName(),
                target.getName(), target.getUniqueId().toString(),
                "Reason: " + reason + (durationMillis > 0 ? ", Duration: " + formatDuration(durationMillis) : ", Duration: indefinite"));

        return true;
    }
}