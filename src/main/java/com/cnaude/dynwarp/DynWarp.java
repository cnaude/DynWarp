package com.cnaude.dynwarp;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author cnaude
 */
public class DynWarp extends JavaPlugin {

    public boolean configLoaded = false;
    public static final String PLUGIN_NAME = "DynWarp";
    public static final String LOG_HEADER = "[" + PLUGIN_NAME + "]";
    static final Logger LOG = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        getCommand("dwarp").setExecutor(new WarpCommand(this));
        getCommand("dwlist").setExecutor(new ListCommand(this));
    }

    @Override
    public void onDisable() {
    }

    public void logInfo(String message) {
        LOG.log(Level.INFO, String.format("%s %s", LOG_HEADER, message));
    }

    public void logError(String message) {
        LOG.log(Level.SEVERE, String.format("%s %s", LOG_HEADER, message));
    }

}
