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

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class SkyWriter extends JavaPlugin {
	
    //ClassListeners
    public final SkyWriterCommandExecutor commandExecutor = new SkyWriterCommandExecutor(this);
     //ClassListeners

	Logger log = Logger.getLogger("Minecraft");//Define your logger

	public void onDisable() {
		log.info("Disabling SkyWriter Plugin");
		SkyWriterCommandExecutor.removeAllLetters();
	}

	public void onEnable() {
        log.info("Enabling SkyWriter Plugin");

        PluginManager pm = this.getServer().getPluginManager();
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AgeLetter(), 0, 100L);
        getCommand("skywrite").setExecutor(commandExecutor);
	}
	
}
