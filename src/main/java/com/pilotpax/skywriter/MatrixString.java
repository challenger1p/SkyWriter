package com.pilotpax.skywriter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class MatrixString {
	private static int startDispersal = 5;
	private static int oldAge = 25;
	private static int drawThreshold = 10;
	
	int charMatrixHeight;
	int charMatrixWidth;
	
	String myString;            // characters for this letter
	int[][] myMatrix;           // integer matrix holding "pixel" values, which degrade as letter disperses
	Location myLoc;             // location of this letter
	int myTime;                 // age of this letter
	int myTick;                 // clock ticks for speed adjustments
	Orientation myOrientation;  // letter orientation
	int myAgeSpeed;             // scales standard aging by this factor
	boolean myDisperse;         // true if letter will disperse over time
	Material myMaterial;        // the type of material this letter is made of
	boolean myUpright;          // true if the letter should be upright
	Font myFont;                // font to display
	boolean myAntiAlias;        // if anti-aliasing should be applied
	
	private BufferedImage getFontImage(String s) {
		// Create a temporary image to extract the character's size
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.createGraphics();
		if (myAntiAlias) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setFont(myFont);
		FontMetrics fontMetrics = g.getFontMetrics();
		Rectangle2D r = fontMetrics.getStringBounds(s, g);
		
		charMatrixWidth = (int) r.getWidth();

		if (charMatrixWidth <= 0) {
			charMatrixWidth = 1;
		}
		charMatrixHeight = (int) r.getHeight();

		if (charMatrixHeight <= 0) {
			charMatrixHeight = 1;
		}

		// Create another image holding the character we are creating
		BufferedImage fontImage = new BufferedImage(charMatrixWidth, charMatrixHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.createGraphics();
		if (myAntiAlias) {
			gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		gt.setFont(myFont);

		gt.setColor(Color.WHITE);
		gt.drawString(s, 0, fontMetrics.getAscent());

		return fontImage;
	}
	
	
	
	public void setString(String s) {

		int[] pixel = null;		

		BufferedImage bi = getFontImage(s);
		Raster r = bi.getData();
//		System.out.println(charMatrixHeight);
//		System.out.println(charMatrixWidth);
		myMatrix = new int[charMatrixHeight][charMatrixWidth];
		
		for (int y = 0; y < charMatrixHeight; y++) {
		   for (int x = 0; x < charMatrixWidth; x++) {
				pixel = r.getPixel(x,y,pixel);
				if (pixel[0] != 0) {
					myMatrix[y][x] = 100;
				} else {
					myMatrix[y][x] = 0;
				}
			}
		}		
	}
	
	public void setLocation(Location loc) {
		myLoc = loc;
	}
	
	public void setTime(int t) {
		myTime = t;
		myTick = 0;
	}
	
	public void setOrientation(Orientation o) {
		myOrientation = o;
	}
	
	// sort of a one-sided gaussian blur kernel...
	public void disperseCloud() {
		int[][] convolve = new int[charMatrixHeight][charMatrixWidth];
		
		for (int z = 0; z < charMatrixWidth; z++) {
			for (int x = 0; x < charMatrixHeight; x++) {
				convolve[x][z] =  (int) ( (((double) myMatrix[x][z]) * 0.57) +
						(x>0 ? ((double) myMatrix[x-1][z]) * 0.21 : 0.0) +
						(x>1 ? ((double) myMatrix[x-2][z]) * 0.01 : 0.0) );						
			}
		}
		myMatrix = convolve;
	}

	public void incrementTime() {
		if (myTick < (myAgeSpeed-1)) {
			myTick++;
		} else {
			myTick = 0;
			myTime = myTime + 1;
			if (myTime > startDispersal && myDisperse) {
				eraseString();
				if (myTime < oldAge) {
					disperseCloud();
					makeString();
				}
			}
		}
	}
	
	public boolean isStringOld() {
		if (myTime < oldAge) {
			return false;  
		} else {
			return myDisperse;   // delete this string if disperse is true
		}
	}
	
	public boolean isDisperse() {
		return myDisperse;
	}
	
	public int getTime() {
		return myTime;
	}
	
	public Orientation getOrientation() {
		return myOrientation;
	}
	
	public void printString() {
		for (int x = 0; x < charMatrixHeight; x++) {
			for (int y = 0; y < charMatrixWidth; y++) {
				if (myMatrix[x][y] != 0) {
					System.out.print('*');
				} else {
					System.out.print(' ');
				}
			}
			System.out.println("");			
		}
	}	

	public Location nextLocation() {
		World world = myLoc.getWorld();   	 
		int xloc = myLoc.getBlockX();   
		int yloc = myLoc.getBlockY();    
		int zloc = myLoc.getBlockZ();

		switch (myOrientation) {
		case XMINUS: xloc -= charMatrixHeight;
		break;
		case XPLUS: xloc += charMatrixHeight;
		break;
		case ZPLUS: zloc += charMatrixHeight;
		break;
		case ZMINUS: zloc -= charMatrixHeight;
		break;
		}

		return(new Location(world, xloc, yloc, zloc));
	}
	
	private DyeColor mapTimeToColor(int value) {
		if (value > 50) {
			return(DyeColor.LIGHT_BLUE);  // blue
		} else if (value > 20) {
			return(DyeColor.SILVER);  // Light gray
		} else {
			return(DyeColor.WHITE);  // White
		}
	}

	public void modString(boolean erase) {
		Block b = null;
    	World world = myLoc.getWorld();   	 
    	int xloc = myLoc.getBlockX();   
    	int yloc = myLoc.getBlockY();    
    	int zloc = myLoc.getBlockZ();
		
		for (int x = 0; x < charMatrixHeight; x++) {
			for (int z = 0; z < charMatrixWidth; z++) {
				if (myMatrix[x][z] > drawThreshold) {
					switch(myOrientation) {
					case XMINUS: b = myUpright ? world.getBlockAt(xloc+charMatrixHeight-x,yloc-charMatrixWidth+z,zloc) : 
						world.getBlockAt(xloc+charMatrixHeight-x,yloc,zloc+charMatrixWidth-z);
					break;
					case XPLUS: b = myUpright ? world.getBlockAt(xloc+x,yloc-charMatrixWidth+z,zloc) : 
						world.getBlockAt(xloc+x,yloc,zloc+z);
					break;
					case ZPLUS: b = myUpright ? world.getBlockAt(xloc,yloc-charMatrixWidth+z,zloc+x) : 
						world.getBlockAt(xloc+charMatrixWidth-z,yloc,zloc+x);
					break;
					case ZMINUS: b = myUpright ? world.getBlockAt(xloc,yloc-charMatrixWidth+z,zloc+charMatrixHeight-x) : 
						world.getBlockAt(xloc+z,yloc,zloc+charMatrixHeight-x);
					break;
					}
            		if (b.getType() == myMaterial && erase) {
            			b.setType(Material.AIR);
            		} else if (b.getType() == Material.AIR && !erase) {
            			b.setType(myMaterial);
            			if (myMaterial == Material.WOOL) {
            				b.setData(mapTimeToColor(myMatrix[x][z]).getData());  
            			}
            		} else {
            			myMatrix[x][z] = 0;
            		}
				} 
			}
		}		
	}
	
	public void makeString() {
		modString(false);
	}
	
	public void eraseString() {
		modString(true);
	}
		
	public MatrixString(String s, Location loc, Orientation o, int agespeed, boolean disp, Material m, boolean upright, Font font) {
		setLocation(loc);
		setTime(0);
		setOrientation(o);
		myAgeSpeed = agespeed;
		myDisperse = disp;
		myMaterial = m;
		myUpright = upright;
		myFont = font;
		myAntiAlias = false;
		
		setString(s);
		makeString();
	}
	
//	public static void main(String[] args) {
//		
//		Location l = new Location(null, 0, 0, 0);
//		Orientation o = Orientation.XPLUS;
//		Material m = null;
//		MatrixString ms = new MatrixString("\u597d A \u597d", l, o, 0, true, m, true, new Font("MingLiU", Font.PLAIN, 25));
//
//		ms.printString();
//	
//	}
	
}
