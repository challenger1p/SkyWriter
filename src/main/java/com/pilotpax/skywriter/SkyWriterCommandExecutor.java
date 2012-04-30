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
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.logging.Logger;

public class SkyWriterCommandExecutor implements CommandExecutor {

	private static final int skyLevel = 120;
	private static final double lookUp = 0.2;
	
	private static ArrayList<MatrixLetter> allLetters = new ArrayList<MatrixLetter>(); 
	
    private SkyWriter plugin;
    Logger log = Logger.getLogger("Minecraft");

    public SkyWriterCommandExecutor(SkyWriter plugin) {
        this.plugin = plugin;
    }

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
    
    public MatrixLetter.Orientation orientToDraw(Player player) {

    	Vector v = player.getEyeLocation().getDirection();
    	
    	if (Math.abs(v.getX()) < Math.abs(v.getZ())) {
    		if (v.getZ() > 0) {
    			return(MatrixLetter.Orientation.XMINUS);
    		} else {
    			return(MatrixLetter.Orientation.XPLUS);
    		}
    	} else {
    		if (v.getX() > 0) {
    			return(MatrixLetter.Orientation.ZPLUS);
    		} else {
    			return(MatrixLetter.Orientation.ZMINUS);
    		}    		
    	}	
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("skywrite")) {

        	if (sender == null || !(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player");
    		} else {
    			Player player = (Player) sender;
    			if (player.hasPermission("skywriter.use")) {
    				log.info("skywrite command used by " + player.getName());

    				Location bloc = whereToDraw(player);
    				MatrixLetter.Orientation o = orientToDraw(player);
    				
    				if (bloc != null) {   					
    					for (String word : args) {
    						for (int j=0; j<word.length(); j++) {
    							MatrixLetter m = new MatrixLetter(word.charAt(j),bloc,o); 
    							bloc = m.nextLocation();
    							allLetters.add(m);
    						}
    						MatrixLetter m = new MatrixLetter(' ',bloc,o); 
    						bloc = m.nextLocation();
    						allLetters.add(m);    					
    					}
    					return true;
    				}
    			}
    		}
        }
        return false;
    }

    public static void incrementAllLetters() {
    	ArrayList<MatrixLetter> toRemove = new ArrayList<MatrixLetter>();
    	for (MatrixLetter m : allLetters) {
    		m.incrementTime();
    		if (m.isLetterOld()) {
    			toRemove.add(m);
    		}
    	}
    	allLetters.removeAll(toRemove);
    }
        
    public static void removeAllLetters() {
    	for (MatrixLetter m : allLetters) {
    		m.eraseLetter();
    	}
    	allLetters = new ArrayList<MatrixLetter>();
    }
}
