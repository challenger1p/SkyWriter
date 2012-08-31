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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SkyWriterCommandExecutor implements CommandExecutor {

	private static final int skyLevel = 120;  // height at which letters are rendered
	private static final double lookUp = 0.2; // player needs to look up greater than arcsin(0.2) = 12 degrees
	
	private static ArrayList<MatrixLetter> allLetters = new ArrayList<MatrixLetter>(); 
	private static ArrayList<MatrixString> allStrings = new ArrayList<MatrixString>();
	
    private SkyWriter plugin;
    Logger log = Logger.getLogger("Minecraft");

    public SkyWriterCommandExecutor(SkyWriter plugin) {
        this.plugin = plugin;
    }

    // figure out where to put letters, by extending unit vector of where player is looking to sky level
    public Location whereToDraw(Player player) {
		Location loc = player.getLocation();
		World world = loc.getWorld(); 
		int x_s = loc.getBlockX();   
		int y_s = loc.getBlockY();   
		int z_s = loc.getBlockZ();
		
		Vector v = player.getEyeLocation().getDirection();  // unit vector where player is looking
		if (v.getY() < lookUp) {
			player.sendMessage("You need to look higher up in the sky!");
			return(null);
		} else {
			double mult = (skyLevel - y_s) / v.getY();
			v = v.multiply(mult);
			x_s += (int) v.getX();
			z_s += (int) v.getZ();
			return(new Location(world, x_s, skyLevel, z_s));
		}
    }
    
    // based on quadrant in which letters are being placed, adjust orientation so they appear upright
    public Orientation orientToDraw(Player player) {

    	Vector v = player.getEyeLocation().getDirection();
    	
    	if (Math.abs(v.getX()) < Math.abs(v.getZ())) {
    		if (v.getZ() > 0) {
    			return(Orientation.XMINUS);
    		} else {
    			return(Orientation.XPLUS);
    		}
    	} else {
    		if (v.getX() > 0) {
    			return(Orientation.ZPLUS);
    		} else {
    			return(Orientation.ZMINUS);
    		}    		
    	}	
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("skywrite")) {

        	ParseCommand cmd = new ParseCommand(args);
        	
        	if (sender == null || !(sender instanceof Player)) {
        		
                World w = plugin.getServer().getWorld(cmd.getWorld());
                int xloc = cmd.getXloc();
                int zloc = cmd.getZloc();


                if(w != null  && cmd.getLocUsed()) {

        			sender.sendMessage("Placing message in world " + cmd.getWorld() + " at (" + xloc + "," + zloc + ")");
                	
                	Location bloc = new Location(w, xloc, skyLevel, zloc);
					
    				Orientation o = Orientation.XPLUS;

                	int speed = cmd.getSpeed();
					boolean disperse = cmd.getDisperse();
    				Material matl = cmd.getMaterial();
    				boolean upright = cmd.getUpright();
    				Font f = cmd.getFont();
    				
       				if (f == null) {
        				addLetters(cmd.getMessage(), bloc, o, speed, disperse, matl, upright);                    
    				} else {
    					addString(cmd.getMessage(), bloc, o, speed, disperse, matl, upright, f);
    				}
 
    				if (bloc != null) { return true; }
                }		
    		} else {
    			Player player = (Player) sender;
    			if (player.hasPermission("skywriter.use")) {
    				log.info("skywrite command used by " + player.getName());

    				Location bloc = whereToDraw(player);
    				
    				if (cmd.getLocUsed() && player.hasPermission("skywriter.location")) {
    					bloc.setX((double) cmd.getXloc());
    					bloc.setZ((double) cmd.getZloc());
    				}
    				
    				Orientation o = orientToDraw(player);

    				// add permission check
					int speed = player.hasPermission("skywriter.speed") ? cmd.getSpeed() : cmd.getDefaultSpeed();
					boolean disperse = player.hasPermission("skywriter.permanent") ? cmd.getDisperse() : cmd.getDefaultDisperse();
    				Material matl = player.hasPermission("skywriter.material") ? cmd.getMaterial() : cmd.getDefaultMaterial();
    				boolean upright = cmd.getUpright();
    				Font f = cmd.getFont();
    				
    				if (f == null) {
        				addLetters(cmd.getMessage(), bloc, o, speed, disperse, matl, upright);   					
    				} else {
    					addString(cmd.getMessage(), bloc, o, speed, disperse, matl, upright, f);
    				}
    				
    				if (bloc != null) { return true; }
    			}
    		}
        }
        return false;
    }

    // create string and add it to the list of strings
    public static void addString(String msg, Location loc, Orientation o, int speed,
    		boolean disperse, Material matl, boolean upright, Font font) {
    	if (loc != null) {
    		MatrixString ms = new MatrixString(msg, loc, o, speed, disperse, matl, upright, font);
    		allStrings.add(ms);
    	}
    }
    
    // create letters and add them to the list of letters
    public static void addLetters(String msg, Location loc, Orientation o, int speed,
    		                      boolean disperse, Material matl, boolean upright) {
    	
		if (loc != null) {
			for (int j=0; j<msg.length(); j++) {
				MatrixLetter m = new MatrixLetter(msg.charAt(j), loc, o, speed, disperse, matl, upright); 
				loc = m.nextLocation();
				allLetters.add(m);
			}
		}   	
    }
    
    // increment the age of all the letters - called from AgeLetter, which is called by the scheduler
    public static void incrementAllLetters() {
    	ArrayList<MatrixLetter> toRemove = new ArrayList<MatrixLetter>();
    	for (MatrixLetter m : allLetters) {
    		m.incrementTime();
    		if (m.isLetterOld()) {
    			toRemove.add(m);
    		}
    	}
    	allLetters.removeAll(toRemove);

    	ArrayList<MatrixString> toRemove2 = new ArrayList<MatrixString>();
    	for (MatrixString ms : allStrings) {
    		ms.incrementTime();
    		if (ms.isStringOld()) {
    			toRemove2.add(ms);
    		}
    	}
    	allStrings.removeAll(toRemove2);
    }
        
    // remove all the letters - called when the plugin is disabled
    public static void removeAllLetters() {
    	for (MatrixLetter m : allLetters) {
    		if (m.isDisperse()) { 
    			m.eraseLetter(); 
    		}
    	}
    	allLetters = new ArrayList<MatrixLetter>();

    	for (MatrixString ms : allStrings) {
    		if (ms.isDisperse()) { 
    			ms.eraseString(); 
    		}
    	}
    	allStrings = new ArrayList<MatrixString>();    
    }
}
