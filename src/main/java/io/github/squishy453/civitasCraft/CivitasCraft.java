package io.github.squishy453.civitasCraft;

//Java Imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//YAML File Imports
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

//Command Imports
import io.github.squishy453.civitasCraft.commands.banCommand;
import io.github.squishy453.civitasCraft.commands.civitasCommand;
//import io.github.squishy453.civitasCraft.commands.announceCommand;
//import io.github.squishy453.civitasCraft.commands.invCommand;
//import io.github.squishy453.civitasCraft.commands.kickCommand;
//import io.github.squishy453.civitasCraft.commands.muteCommand;
//import io.github.squishy453.civitasCraft.commands.serverCommand;
//import io.github.squishy453.civitasCraft.commands.unbanCommand;
//import io.github.squishy453.civitasCraft.commands.whitelistCommand;
//import io.github.squishy453.civitasCraft.commands.vanishCommand;
//import io.github.squishy453.civitasCraft.commands.warnCommand;

//Listener Imports
import io.github.squishy453.civitasCraft.listeners.PlayerJoinListener;

public class CivitasCraft extends JavaPlugin {

    //YML Configuration
    private File playersFile;
    private FileConfiguration playersConfig;
    private File rolesFile;
    private FileConfiguration rolesConfig;

    //Get playerConfig
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

    //Save players.yml
    public void savePlayersConfig() {
        try {
            playersConfig.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Save roles.yml
    public void saveRolesConfig() {
        try {
            rolesConfig.save(rolesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Log Commands
    public void logCommand(String commandName, String executor, String targetName, String targetUUID, String details) {
        try {
            File logFile = new File(getDataFolder(), "mod-logs.log");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            FileWriter writer = new FileWriter(logFile, true);
            writer.write("[" + new java.util.Date() + "] [" + commandName.toUpperCase() + "] "
                    + executor + " -> " + targetName + " (" + targetUUID + "): " + details + "\n");
            writer.close();
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
        //this.getCommand("server").setExecutor(new serverCommand(this)); //Register /server
        this.getCommand("ban").setExecutor(new banCommand(this)); //Register /ban
        //this.getCommand("unban").setExecutor(new unbanCommand(this)); //Register /unban
        //this.getCommand("kick").setExecutor(new kickCommand(this)); //Register /kick
        //this.getCommand("mute").setExecutor(new muteCommand(this)); //Register /mute
        //this.getCommand("warn").setExecutor(new warnCommand(this)); //Register /warn
        //this.getCommand("whitelist").setExecutor(new whitelistCommand(this)); //Register /whitelist
        //this.getCommand("vanish").setExecutor(new vanishCommand(this)); //Register /vanish
        //this.getCommand("inv").setExecutor(new invCommand(this)); //Register /inv
        //this.getCommand("announce").setExecutor(new announceCommand(this)); //Register /announce

    }

    //On Close
    @Override
    public void onDisable() {
        getLogger().info("CivitasCraft has been disabled."); //Plugin Shutdown
    }
}
