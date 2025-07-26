package io.github.squishy453.civitasCraft;

import io.github.squishy453.civitasCraft.commands.civitasCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class CivitasCraft extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CivitasCraft has been enabled."); //Plugin Initialization

        this.getCommand("civitas").setExecutor(new civitasCommand(this)); //Register /civitas command
    }

    @Override
    public void onDisable() {
        getLogger().info("CivitasCraft has been disabled."); //Plugin Shutdown
    }
}
