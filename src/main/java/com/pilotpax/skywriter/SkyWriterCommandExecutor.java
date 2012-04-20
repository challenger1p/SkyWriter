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


import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SkyWriterCommandExecutor implements CommandExecutor {

	private static ArrayList<MatrixLetter> allLetters = new ArrayList<MatrixLetter>(); 
	
    private SkyWriter plugin;
    Logger log = Logger.getLogger("Minecraft");//Define your logger

    public SkyWriterCommandExecutor(SkyWriter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        log.info("onCommand Reached in SkyWriter");

        if (command.getName().equalsIgnoreCase("skywrite")) {
        	log.info("skywrite used");

        	Player player = (Player) sender;
        	Location loc = player.getLocation();
        	World world = loc.getWorld(); 
        	int x_s = loc.getBlockX();   
        	int y_s = loc.getBlockY();    
        	int z_s = loc.getBlockZ();
        	Location bloc = new Location(world, x_s, y_s + 20, z_s);
        	
        	String word = args[0];
        	for (int j=0; j<word.length(); j++) {
        		MatrixLetter m = new MatrixLetter(word.charAt(j),bloc); 
        		bloc = m.nextLocation();
        		allLetters.add(m);
        		}
            return true;
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
        
}
