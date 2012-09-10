package com.pilotpax.skywriter;

/*
Copyright (c) 2012 Daniel Van Blerkom

This file is part of SkyWriter

SkyWriter is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SkyWriter is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SkyWriter.  If not, see <http://www.gnu.org/licenses/>.
*/

import org.bukkit.Material;

public class ParseCommand {
	
	private int agespeed;        // how long the message should last
	private boolean disperse;    // should the message disperse over time
	private Material material;   // what the message should be made of
	private boolean locused;     // indicates the command specified a specific x,z location
	private int xloc;            // where the message should be written
	private int zloc;
	private boolean upright;     // should the message be upright or not
	private String message;      // the message that should be written
	private String world;        // world in which the message should appear
	private boolean get_font;    // was the font command used
	
	public int getSpeed() {
		return agespeed;
	}
	
	public boolean getDisperse() {
		return disperse;
	}
	
	public boolean getUpright() {
		return upright;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getXloc() {
		return xloc;
	}
	
	public boolean getLocUsed() {
		return locused;
	}
	
	public int getZloc() {
		return zloc;
	}
	
	public String getWorld() {
		return world;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public int getDefaultSpeed() {
		return 1;
	}
	
	public boolean getDefaultDisperse() {
		return true;
	}
	
	public Material getDefaultMaterial() {
		return Material.WOOL;
	}
	
	public boolean getFontused() {
		return get_font;
	}
		
	// Parse the command arguments:
	// /skywrite [-speed n] [-block b] [-loc x,z] [-upright] [-perm] [-w world] message...
	//    n is scaling for the speed of dispersing (i.e. 2 = 2x slower, etc)
	//    b is the name or id of the material for the cloud
	//    x,z are the x and z coordinates to be drawn at
	//
	// /skywrite -font fontname
	//    sets the font to fontname
	//
	// by default, the cloud will be made of wool and drawn where the player is looking.
	//
	public ParseCommand(String[] args) {
		BlockType b;
		
		boolean inmessage = false;
		boolean get_age = false;
		boolean get_matl = false;
		boolean get_loc = false;
		boolean get_world = false;
		
		agespeed = getDefaultSpeed();
		disperse = getDefaultDisperse();
		material = getDefaultMaterial();
		locused = false;
		xloc = 0;
		zloc = 0;
		upright = false;
		message = "";
		world = "world";
		get_font = false;
		
		for(String word : args) {
			if (!inmessage && word.matches("^-(f|(font))$")) {
				get_age = false;
				get_matl = false;
				get_loc = false;
				get_world = false;
				get_font = true;
			} else if (!inmessage && word.matches("^-(s|(speed))$")) {
				get_age = true;
				get_matl = false;
				get_loc = false;
				get_world = false;
				get_font = false;
			} else if (!inmessage && word.matches("^-(b|(block))$")) {
				get_age = false;
				get_matl = true;
				get_loc = false;
				get_world = false;
				get_font = false;
			} else if (!inmessage && word.matches("^-(l|(loc))$")) {
				get_age = false;
				get_matl = false;
				get_loc = true;
				get_world = false;
				get_font = false;
			} else if (!inmessage && word.matches("^-(u|(upright))$")) {
				upright = true;
				get_age = false;
				get_matl = false;
				get_loc = false;
				get_world = false;
				get_font = false;
			} else if (!inmessage && word.matches("^-(p|(perm))$")) {
				disperse = false;
				get_age = false;
				get_matl = false;
				get_loc = false;
				get_world = false;
				get_font = false;
			}else if (!inmessage && word.matches("^-(w|(world))$")) {
				get_age = false;
				get_matl = false;
				get_loc = false;
				get_world = true;				
				get_font = false;
			} else if (!inmessage && get_age && word.matches("^[1-9]$")) {
				get_age = false;
				agespeed = Integer.parseInt(word);
			} else if (!inmessage && get_matl && (b = BlockType.lookup(word)) != null) {
				get_matl = false;
				material = Material.getMaterial(b.getID());
			} else if (!inmessage && get_loc && word.matches("^(-|)[0-9]+,(-|)[0-9]+$")) {
				get_loc = false;
				String[] scoord = word.split(",");
				xloc = Integer.parseInt(scoord[0]);
				zloc = Integer.parseInt(scoord[1]);
				locused = true;
			} else if (!inmessage && get_world) {
				get_world = false;
				world = word;
			} else {
				if (inmessage) { message = message + " "; }
				inmessage = true;
				message = message + word;
			}
		}	
	}	
}
