package com.cnaude.dynwarp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

/**
 *
 * @author cnaude
 */
public class WarpCommand implements CommandExecutor {

    private final DynWarp plugin;
    private final DynmapCommonAPI dynmapCommonAPI;
    private final MarkerAPI markerAPI;

    public WarpCommand(DynWarp instance) {
        this.plugin = instance;
        this.dynmapCommonAPI = (DynmapCommonAPI) plugin.getServer().getPluginManager().getPlugin("dynmap");
        markerAPI = dynmapCommonAPI.getMarkerAPI();
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender == null) {
            return true;
        }
        if (args.length == 0) {
            return false;
        }
        String markerID = args[0];
        if (sender instanceof Player) {
            if (sender.hasPermission("dynwarp.tp") || sender.hasPermission("dynwarp.tp." + markerID)) {
                Marker dm = null;
                for (MarkerSet ms : markerAPI.getMarkerSets()) {
                    // first loop to get exact match
                    for (Marker m : ms.getMarkers()) {
                        if (m.getMarkerID().equalsIgnoreCase(markerID)) {
                            dm = m;
                        }
                    }
                    // second loop for partial match
                    if (dm == null) {
                        for (Marker m : ms.getMarkers()) {
                            if (m.getMarkerID().toLowerCase().contains(markerID.toLowerCase())) {
                                dm = m;
                            }
                        }
                    }
                }
                if (dm != null) {
                    sender.sendMessage(ChatColor.GOLD + "Warping to " + ChatColor.AQUA + dm.getMarkerID() + ": " + ChatColor.WHITE + dm.getLabel());
                    Location loc = new Location(Bukkit.getWorld(dm.getWorld()), dm.getX(), dm.getY(), dm.getZ());
                    ((Player) sender).teleport(loc);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.GOLD + "No matching marker ID found.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You're not a player!");
        }
        return true;
    }
}
