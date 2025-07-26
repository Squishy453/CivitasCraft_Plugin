package io.github.squishy453.civitasCraft;

//Imports

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.squishy453.civitasCraft.commands.civitasCommand;
import io.github.squishy453.civitasCraft.listeners.PlayerJoinListener;

//Start of Class

public class CivitasCraft extends JavaPlugin {

    private File playersFile;
    private FileConfiguration playersConfig;

    private File rolesFile;
    private FileConfiguration rolesConfig;

    //Getter for playerConfig
    public FileConfiguration getPlayersConfig() {
        return playersConfig;
    }

    //Load players.yml
    public void loadPlayersConfig() {
        playersFile = new File(getDataFolder(), "players.yml");
        if (!playersFile.exists()) {
            saveResource("players.yml", false);
        }
        playersConfig = YamlConfiguration.loadConfiguration(playersFile);
    }

    //Load roles.yml
    public void loadRolesConfig() {
        rolesFile = new File(getDataFolder(), "roles.yml");
        if (!rolesFile.exists()) {
            saveResource("roles.yml", false);
        }
        rolesConfig = YamlConfiguration.loadConfiguration(rolesFile);
    }

    public void savePlayersConfig() {
        try {
            playersConfig.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRolesConfig() {
        try {
            rolesConfig.save(rolesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //On Start
    @Override
    public void onEnable() {
        getLogger().info("CivitasCraft has been enabled."); //Plugin Initialization

        loadPlayersConfig();
        loadRolesConfig();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        this.getCommand("civitas").setExecutor(new civitasCommand(this)); //Register /civitas
        this.getCommand("server").setExecutor(new serverCommand(this)); //Register /server
        this.getCommand("ban").setExecutor(new banCommand(this)); //Register /ban
        this.getCommand("unban").setExecutor(new unbanCommand(this)); //Register /unban
        this.getCommand("kick").setExecutor(new kickCommand(this)); //Register /kick
        this.getCommand("mute").setExecutor(new muteCommand(this)); //Register /mute
        this.getCommand("warn").setExecutor(new warnCommand(this)); //Register /warn
        this.getCommand("whitelist").setExecutor(new whitelistCommand(this)); //Register /whitelist
        this.getCommand("vanish").setExecutor(new vanishCommand(this)); //Register /vanish
        this.getCommand("inv").setExecutor(new invCommand(this)); //Register /inv
        this.getCommand("announce").setExecutor(new announceCommand(this)); //Register /announce

    }

    //On Close
    @Override
    public void onDisable() {
        getLogger().info("CivitasCraft has been disabled."); //Plugin Shutdown
    }
}
