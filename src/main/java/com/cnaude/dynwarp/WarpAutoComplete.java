package com.cnaude.dynwarp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

public class WarpAutoComplete implements TabCompleter {
	private final DynWarp plugin;
    private final DynmapCommonAPI dynmapCommonAPI;
	private final MarkerAPI markerAPI;
	
	public WarpAutoComplete(DynWarp instance) {
        this.plugin = instance;
        this.dynmapCommonAPI = (DynmapCommonAPI) plugin.getServer().getPluginManager().getPlugin("dynmap");
        markerAPI = dynmapCommonAPI.getMarkerAPI();
    }
	
	@Override
	public List<String> onTabComplete(final CommandSender sender, Command cmd, String commandLabel, String[] args) {
		List<String> markers = new ArrayList<>(); 
		for (MarkerSet ms : markerAPI.getMarkerSets()) {
            for (Marker m : ms.getMarkers()) {
                markers.add(m.getMarkerID());
            }
        }
		return markers;
	}
}
