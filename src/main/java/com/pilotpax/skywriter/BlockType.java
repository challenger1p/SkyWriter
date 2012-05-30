package com.pilotpax.skywriter;

/*
Code in this file is taken from WorldEdit
Copyright (C) 2010 sk89q <http://www.sk89q.com> and contributors

This file is part of SkyWriter
Copyright (c) 2012 Daniel Van Blerkom

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


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Block types.
 *
 * @author sk89q
 */

public enum BlockType {
	AIR(BlockID.AIR, "Air", "air"),
	STONE(BlockID.STONE, "Stone", "stone", "rock"),
	GRASS(BlockID.GRASS, "Grass", "grass"),
	DIRT(BlockID.DIRT, "Dirt", "dirt"),
	COBBLESTONE(BlockID.COBBLESTONE, "Cobblestone", "cobblestone", "cobble"),
	WOOD(BlockID.WOOD, "Wood", "wood", "woodplank", "plank", "woodplanks", "planks"),
	SAPLING(BlockID.SAPLING, "Sapling", "sapling", "seedling"),
	BEDROCK(BlockID.BEDROCK, "Bedrock", "adminium", "bedrock"),
	WATER(BlockID.WATER, "Water", "watermoving", "movingwater", "flowingwater", "waterflowing"),
	STATIONARY_WATER(BlockID.STATIONARY_WATER, "Water (stationary)", "water", "waterstationary", "stationarywater", "stillwater"),
	LAVA(BlockID.LAVA, "Lava", "lavamoving", "movinglava", "flowinglava", "lavaflowing"),
	STATIONARY_LAVA(BlockID.STATIONARY_LAVA, "Lava (stationary)", "lava", "lavastationary", "stationarylava", "stilllava"),
	SAND(BlockID.SAND, "Sand", "sand"),
	GRAVEL(BlockID.GRAVEL, "Gravel", "gravel"),
	GOLD_ORE(BlockID.GOLD_ORE, "Gold ore", "goldore"),
	IRON_ORE(BlockID.IRON_ORE, "Iron ore", "ironore"),
	COAL_ORE(BlockID.COAL_ORE, "Coal ore", "coalore"),
	LOG(BlockID.LOG, "Log", "log", "tree", "pine", "oak", "birch", "redwood"),
	LEAVES(BlockID.LEAVES, "Leaves", "leaves", "leaf"),
	SPONGE(BlockID.SPONGE, "Sponge", "sponge"),
	GLASS(BlockID.GLASS, "Glass", "glass"),
	LAPIS_LAZULI_ORE(BlockID.LAPIS_LAZULI_ORE, "Lapis lazuli ore", "lapislazuliore", "blueore", "lapisore"),
	LAPIS_LAZULI(BlockID.LAPIS_LAZULI_BLOCK, "Lapis lazuli", "lapislazuli", "lapislazuliblock", "bluerock"),
	DISPENSER(BlockID.DISPENSER, "Dispenser", "dispenser"),
	SANDSTONE(BlockID.SANDSTONE, "Sandstone", "sandstone"),
	NOTE_BLOCK(BlockID.NOTE_BLOCK, "Note block", "musicblock", "noteblock", "note", "music", "instrument"),
	BED(BlockID.BED, "Bed", "bed"),
	POWERED_RAIL(BlockID.POWERED_RAIL, "Powered Rail", "poweredrail", "boosterrail", "poweredtrack", "boostertrack", "booster"),
	DETECTOR_RAIL(BlockID.DETECTOR_RAIL, "Detector Rail", "detectorrail", "detector"),
	PISTON_STICKY_BASE(BlockID.PISTON_STICKY_BASE, "Sticky Piston", "stickypiston"),
	WEB(BlockID.WEB, "Web", "web", "spiderweb"),
	LONG_GRASS(BlockID.LONG_GRASS, "Long grass", "longgrass", "tallgrass"),
	DEAD_BUSH(BlockID.DEAD_BUSH, "Shrub", "deadbush", "shrub", "deadshrub", "tumbleweed"),
	PISTON_BASE(BlockID.PISTON_BASE, "Piston", "piston"),
	PISTON_EXTENSION(BlockID.PISTON_EXTENSION, "Piston extension", "pistonextendsion", "pistonhead"),
	CLOTH(BlockID.CLOTH, "Wool", "cloth", "wool"),
	PISTON_MOVING_PIECE(BlockID.PISTON_MOVING_PIECE, "Piston moving piece", "movingpiston"),
	YELLOW_FLOWER(BlockID.YELLOW_FLOWER, "Yellow flower", "yellowflower", "flower"),
	RED_FLOWER(BlockID.RED_FLOWER, "Red rose", "redflower", "redrose", "rose"),
	BROWN_MUSHROOM(BlockID.BROWN_MUSHROOM, "Brown mushroom", "brownmushroom", "mushroom"),
	RED_MUSHROOM(BlockID.RED_MUSHROOM, "Red mushroom", "redmushroom"),
	GOLD_BLOCK(BlockID.GOLD_BLOCK, "Gold block", "gold", "goldblock"),
	IRON_BLOCK(BlockID.IRON_BLOCK, "Iron block", "iron", "ironblock"),
	DOUBLE_STEP(BlockID.DOUBLE_STEP, "Double step", "doubleslab", "doublestoneslab", "doublestep"),
	STEP(BlockID.STEP, "Step", "slab", "stoneslab", "step", "halfstep"),
	BRICK(BlockID.BRICK, "Brick", "brick", "brickblock"),
	TNT(BlockID.TNT, "TNT", "tnt", "c4", "explosive"),
	BOOKCASE(BlockID.BOOKCASE, "Bookcase", "bookshelf", "bookshelves", "bookcase", "bookcases"),
	MOSSY_COBBLESTONE(BlockID.MOSSY_COBBLESTONE, "Cobblestone (mossy)", "mossycobblestone", "mossstone", "mossystone", "mosscobble", "mossycobble", "moss", "mossy", "sossymobblecone"),
	OBSIDIAN(BlockID.OBSIDIAN, "Obsidian", "obsidian"),
	TORCH(BlockID.TORCH, "Torch", "torch", "light", "candle"),
	FIRE(BlockID.FIRE, "Fire", "fire", "flame", "flames"),
	MOB_SPAWNER(BlockID.MOB_SPAWNER, "Mob spawner", "mobspawner", "spawner"),
	WOODEN_STAIRS(BlockID.WOODEN_STAIRS, "Wooden stairs", "woodstair", "woodstairs", "woodenstair", "woodenstairs"),
	CHEST(BlockID.CHEST, "Chest", "chest", "storage", "storagechest"),
	REDSTONE_WIRE(BlockID.REDSTONE_WIRE, "Redstone wire", "redstone", "redstoneblock"),
	DIAMOND_ORE(BlockID.DIAMOND_ORE, "Diamond ore", "diamondore"),
	DIAMOND_BLOCK(BlockID.DIAMOND_BLOCK, "Diamond block", "diamond", "diamondblock"),
	WORKBENCH(BlockID.WORKBENCH, "Workbench", "workbench", "table", "craftingtable", "crafting"),
	CROPS(BlockID.CROPS, "Crops", "crops", "crop", "plant", "plants"),
	SOIL(BlockID.SOIL, "Soil", "soil", "farmland"),
	FURNACE(BlockID.FURNACE, "Furnace", "furnace"),
	BURNING_FURNACE(BlockID.BURNING_FURNACE, "Furnace (burning)", "burningfurnace", "litfurnace"),
	SIGN_POST(BlockID.SIGN_POST, "Sign post", "sign", "signpost"),
	WOODEN_DOOR(BlockID.WOODEN_DOOR, "Wooden door", "wooddoor", "woodendoor", "door"),
	LADDER(BlockID.LADDER, "Ladder", "ladder"),
	MINECART_TRACKS(BlockID.MINECART_TRACKS, "Minecart tracks", "track", "tracks", "minecrattrack", "minecarttracks", "rails", "rail"),
	COBBLESTONE_STAIRS(BlockID.COBBLESTONE_STAIRS, "Cobblestone stairs", "cobblestonestair", "cobblestonestairs", "cobblestair", "cobblestairs"),
	WALL_SIGN(BlockID.WALL_SIGN, "Wall sign", "wallsign"),
	LEVER(BlockID.LEVER, "Lever", "lever", "switch", "stonelever", "stoneswitch"),
	STONE_PRESSURE_PLATE(BlockID.STONE_PRESSURE_PLATE, "Stone pressure plate", "stonepressureplate", "stoneplate"),
	IRON_DOOR(BlockID.IRON_DOOR, "Iron Door", "irondoor"),
	WOODEN_PRESSURE_PLATE(BlockID.WOODEN_PRESSURE_PLATE, "Wooden pressure plate", "woodpressureplate", "woodplate", "woodenpressureplate", "woodenplate", "plate", "pressureplate"),
	REDSTONE_ORE(BlockID.REDSTONE_ORE, "Redstone ore", "redstoneore"),
	GLOWING_REDSTONE_ORE(BlockID.GLOWING_REDSTONE_ORE, "Glowing redstone ore", "glowingredstoneore"),
	REDSTONE_TORCH_OFF(BlockID.REDSTONE_TORCH_OFF, "Redstone torch (off)", "redstonetorchoff", "rstorchoff"),
	REDSTONE_TORCH_ON(BlockID.REDSTONE_TORCH_ON, "Redstone torch (on)", "redstonetorch", "redstonetorchon", "rstorchon", "redtorch"),
	STONE_BUTTON(BlockID.STONE_BUTTON, "Stone Button", "stonebutton", "button"),
	SNOW(BlockID.SNOW, "Snow", "snow"),
	ICE(BlockID.ICE, "Ice", "ice"),
	SNOW_BLOCK(BlockID.SNOW_BLOCK, "Snow block", "snowblock"),
	CACTUS(BlockID.CACTUS, "Cactus", "cactus", "cacti"),
	CLAY(BlockID.CLAY, "Clay", "clay"),
	SUGAR_CANE(BlockID.REED, "Reed", "reed", "cane", "sugarcane", "sugarcanes", "vine", "vines"),
	JUKEBOX(BlockID.JUKEBOX, "Jukebox", "jukebox", "stereo", "recordplayer"),
	FENCE(BlockID.FENCE, "Fence", "fence"),
	PUMPKIN(BlockID.PUMPKIN, "Pumpkin", "pumpkin"),
	NETHERRACK(BlockID.NETHERRACK, "Netherrack", "redmossycobblestone", "redcobblestone", "redmosstone", "redcobble", "netherstone", "netherrack", "nether", "hellstone"),
	SOUL_SAND(BlockID.SLOW_SAND, "Soul sand", "slowmud", "mud", "soulsand", "hellmud"),
	GLOWSTONE(BlockID.LIGHTSTONE, "Glowstone", "brittlegold", "glowstone", "lightstone", "brimstone", "australium"),
	PORTAL(BlockID.PORTAL, "Portal", "portal"),
	JACK_O_LANTERN(BlockID.JACKOLANTERN, "Pumpkin (on)", "pumpkinlighted", "pumpkinon", "litpumpkin", "jackolantern"),
	CAKE(BlockID.CAKE_BLOCK, "Cake", "cake", "cakeblock"),
	REDSTONE_REPEATER_OFF(BlockID.REDSTONE_REPEATER_OFF, "Redstone repeater (off)", "diodeoff", "redstonerepeater", "repeateroff", "delayeroff"),
	REDSTONE_REPEATER_ON(BlockID.REDSTONE_REPEATER_ON, "Redstone repeater (on)", "diodeon", "redstonerepeateron", "repeateron", "delayeron"),
	LOCKED_CHEST(BlockID.LOCKED_CHEST, "Locked chest", "lockedchest", "steveco", "supplycrate", "valveneedstoworkonep3nottf2kthx"),
	TRAP_DOOR(BlockID.TRAP_DOOR, "Trap door", "trapdoor", "hatch", "floordoor"),
	SILVERFISH_BLOCK(BlockID.SILVERFISH_BLOCK, "Silverfish block", "silverfish", "silver"),
	STONE_BRICK(BlockID.STONE_BRICK, "Stone brick", "stonebrick", "sbrick", "smoothstonebrick"),
	RED_MUSHROOM_CAP(BlockID.RED_MUSHROOM_CAP, "Red mushroom cap", "giantmushroomred", "redgiantmushroom", "redmushroomcap"),
	BROWN_MUSHROOM_CAP(BlockID.BROWN_MUSHROOM_CAP, "Brown mushroom cap", "giantmushroombrown", "browngiantmushoom", "brownmushroomcap"),
	IRON_BARS(BlockID.IRON_BARS, "Iron bars", "ironbars", "ironfence"),
	GLASS_PANE(BlockID.GLASS_PANE, "Glass pane", "window", "glasspane", "glasswindow"),
	MELON_BLOCK(BlockID.MELON_BLOCK, "Melon (block)", "melonblock"),
	PUMPKIN_STEM(BlockID.PUMPKIN_STEM, "Pumpkin stem", "pumpkinstem"),
	MELON_STEM(BlockID.MELON_STEM, "Melon stem", "melonstem"),
	VINE(BlockID.VINE, "Vine", "vine", "vines", "creepers"),
	FENCE_GATE(BlockID.FENCE_GATE, "Fence gate", "fencegate", "gate"),
	BRICK_STAIRS(BlockID.BRICK_STAIRS, "Brick stairs", "brickstairs", "bricksteps"),
	STONE_BRICK_STAIRS(BlockID.STONE_BRICK_STAIRS, "Stone brick stairs", "stonebrickstairs", "smoothstonebrickstairs"),
	MYCELIUM(BlockID.MYCELIUM, "Mycelium", "fungus", "mycel"),
	LILY_PAD(BlockID.LILY_PAD, "Lily pad", "lilypad", "waterlily"),
	NETHER_BRICK(BlockID.NETHER_BRICK, "Nether brick", "netherbrick"),
	NETHER_BRICK_FENCE(BlockID.NETHER_BRICK_FENCE, "Nether brick fence", "netherbrickfence", "netherfence"),
	NETHER_BRICK_STAIRS(BlockID.NETHER_BRICK_STAIRS, "Nether brick stairs", "netherbrickstairs", "netherbricksteps", "netherstairs", "nethersteps"),
	NETHER_WART(BlockID.NETHER_WART, "Nether wart", "netherwart", "netherstalk"),
	ENCHANTMENT_TABLE(BlockID.ENCHANTMENT_TABLE, "Enchantment table", "enchantmenttable", "enchanttable"),
	BREWING_STAND(BlockID.BREWING_STAND, "Brewing Stand", "brewingstand"),
	CAULDRON(BlockID.CAULDRON, "Cauldron"),
	END_PORTAL(BlockID.END_PORTAL, "End Portal", "endportal", "blackstuff", "airportal", "weirdblackstuff"),
	END_PORTAL_FRAME(BlockID.END_PORTAL_FRAME, "End Portal Frame", "endportalframe", "airportalframe", "crystalblock"),
	END_STONE(BlockID.END_STONE, "End Stone", "endstone", "enderstone", "endersand"),
	DRAGON_EGG(BlockID.DRAGON_EGG, "Dragon Egg", "dragonegg", "dragons"),
	REDSTONE_LAMP_OFF(BlockID.REDSTONE_LAMP_OFF, "Redstone lamp (off)", "redstonelamp", "redstonelampoff", "rslamp", "rslampoff", "rsglow", "rsglowoff"),
	REDSTONE_LAMP_ON(BlockID.REDSTONE_LAMP_ON, "Redstone lamp (on)", "redstonelampon", "rslampon", "rsglowon");

	/**
	 * Stores a map of the IDs for fast access.
	 */
	private static final Map<Integer, BlockType> ids = new HashMap<Integer, BlockType>();
	/**
	 * Stores a map of the names for fast access.
	 */
	private static final Map<String, BlockType> lookup = new HashMap<String, BlockType>();

	private final int id;
	private final String name;
	private final String[] lookupKeys;

	static {
		for (BlockType type : EnumSet.allOf(BlockType.class)) {
			ids.put(type.id, type);
			for (String key : type.lookupKeys) {
				lookup.put(key, type);
			}
		}
	}

	/**
	 * Construct the type.
	 *
	 * @param id
	 * @param name
	 */
	BlockType(int id, String name, String lookupKey) {
		this.id = id;
		this.name = name;
		this.lookupKeys = new String[] { lookupKey };
	}

	/**
	 * Construct the type.
	 *
	 * @param id
	 * @param name
	 */
	BlockType(int id, String name, String... lookupKeys) {
		this.id = id;
		this.name = name;
		this.lookupKeys = lookupKeys;
	}

	/**
	 * Return type from ID. May return null.
	 *
	 * @param id
	 * @return
	 */
	public static BlockType fromID(int id) {
		return ids.get(id);
	}

	/**
	 * Return type from name. May return null.
	 *
	 * @param name
	 * @return
	 */
	public static BlockType lookup(String name) {
		return lookup(name, true);
	}

	/**
	 * Return type from name. May return null.
	 *
	 * @param name
	 * @param fuzzy
	 * @return
	 */
	public static BlockType lookup(String name, boolean fuzzy) {
		try {
			return fromID(Integer.parseInt(name));
		} catch (NumberFormatException e) {
			return StringUtil.lookup(lookup, name, fuzzy);
		}
	}

	/**
	 * Get block numeric ID.
	 *
	 * @return
	 */
	public int getID() {
		return id;
	}

	/**
	 * Get user-friendly block name.
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

}
