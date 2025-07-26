package io.github.squishy453.civitasCraft;

//Java Imports
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

//YAML File Imports
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

//Command Imports
import io.github.squishy453.civitasCraft.commands.banCommand;
import io.github.squishy453.civitasCraft.commands.civitasCommand;
import io.github.squishy453.civitasCraft.commands.announceCommand;
//import io.github.squishy453.civitasCraft.commands.invCommand;
import io.github.squishy453.civitasCraft.commands.kickCommand;
import io.github.squishy453.civitasCraft.commands.muteCommand;
import io.github.squishy453.civitasCraft.commands.unmuteCommand;
import io.github.squishy453.civitasCraft.commands.serverCommand;
import io.github.squishy453.civitasCraft.commands.unbanCommand;
//import io.github.squishy453.civitasCraft.commands.whitelistCommand;
//import io.github.squishy453.civitasCraft.commands.vanishCommand;
//import io.github.squishy453.civitasCraft.commands.warnCommand;

//Listener Imports
import io.github.squishy453.civitasCraft.listeners.PlayerJoinListener;
import io.github.squishy453.civitasCraft.listeners.ChatListener;

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

    //Load Muted Players from players.yml
    public void loadMutedPlayers() {
        if (playersConfig.contains("muted")) {
            for (String uuidStr : playersConfig.getConfigurationSection("muted").getKeys(false)) {
                UUID uuid = UUID.fromString(uuidStr);
                String reason = playersConfig.getString("muted." + uuidStr + ".reason");
                long unmuteTime = playersConfig.getLong("muted." + uuidStr + ".unmuteTime");
                mutedPlayers.put(uuid, new MuteInfo(reason, unmuteTime));
            }
        }
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

    //Save Muted Players from players.yml
    public void saveMutedPlayers() {
        for (UUID uuid : mutedPlayers.keySet()) {
            MuteInfo info = mutedPlayers.get(uuid);
            playersConfig.set("muted." + uuid.toString() + ".reason", info.reason);
            playersConfig.set("muted." + uuid.toString() + ".unmuteTime", info.unmuteTime);
        }
        savePlayersConfig();
    }

    //Check Muted Players Status
    public boolean isPlayerMuted(UUID uuid) {
        MuteInfo info = mutedPlayers.get(uuid);
        if (info == null) return false;

        if (info.unmuteTime > 0 && System.currentTimeMillis() > info.unmuteTime) {
            mutedPlayers.remove(uuid);
            playersConfig.set("muted." + uuid.toString(), null);
            savePlayersConfig();
            return false;
        }
        return true;
    }

    //Mute / Unmute Command Process
    public void mutePlayer(UUID uuid, String reason, long durationMillis) {
        long unmuteTime = durationMillis > 0 ? System.currentTimeMillis() + durationMillis : -1;
        mutedPlayers.put(uuid, new MuteInfo(reason, unmuteTime));
        playersConfig.set("muted." + uuid.toString() + ".reason", reason);
        playersConfig.set("muted." + uuid.toString() + ".unmuteTime", unmuteTime);
        savePlayersConfig();
    }
    public boolean unmutePlayer(UUID uuid) {
        if (mutedPlayers.containsKey(uuid)) {
            mutedPlayers.remove(uuid);
            playersConfig.set("muted." + uuid.toString(), null);
            savePlayersConfig();
            return true;
        }
        return false;
    }
    public static class MuteInfo {
        public String reason;
        public long unmuteTime;  // -1 means indefinite mute

        public MuteInfo(String reason, long unmuteTime) {
            this.reason = reason;
            this.unmuteTime = unmuteTime;
        }
    }

    private Map<UUID, MuteInfo> mutedPlayers = new HashMap<>();

    public CivitasCraft.MuteInfo getMuteInfo(UUID uuid) {
        return mutedPlayers.get(uuid);
    }

    //Log Commands Process
    public void logCommand(String action, String senderName, String targetName, String targetUUID, String details) {
        String logMessage = String.format("[%s] %s executed by %s on %s (%s): %s",
                action.toUpperCase(), action, senderName, targetName, targetUUID, details);

        getLogger().log(Level.INFO, logMessage);

        try {
            File logFile = new File(getDataFolder(), "mod-logs.log");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.write("[" + new java.util.Date() + "] " + logMessage + "\n");
            }
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

        loadMutedPlayers();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        this.getCommand("civitas").setExecutor(new civitasCommand(this)); //Register /civitas
        this.getCommand("server").setExecutor(new serverCommand(this)); //Register /server
        this.getCommand("ban").setExecutor(new banCommand(this)); //Register /ban
        this.getCommand("unban").setExecutor(new unbanCommand(this)); //Register /unban
        this.getCommand("kick").setExecutor(new kickCommand(this)); //Register /kick
        this.getCommand("mute").setExecutor(new muteCommand(this)); //Register /mute
        this.getCommand("unmute").setExecutor(new unmuteCommand(this)); //Register /unmute
        //this.getCommand("warn").setExecutor(new warnCommand(this)); //Register /warn
        //this.getCommand("whitelist").setExecutor(new whitelistCommand(this)); //Register /whitelist
        //this.getCommand("vanish").setExecutor(new vanishCommand(this)); //Register /vanish
        //this.getCommand("inv").setExecutor(new invCommand(this)); //Register /inv
        this.getCommand("announce").setExecutor(new announceCommand(this)); //Register /announce

    }

    //On Close
    @Override
    public void onDisable() {

        saveMutedPlayers();

        getLogger().info("CivitasCraft has been disabled."); //Plugin Shutdown
    }
}
