package textAdventure;

import java.util.ArrayList;

public class Map {

	 private ArrayList<Location> map;     // The game area is a collection of locations.
	 private int height;                   // The number of rows in the game area.
	 private int width;                    // The maximum width of the game area.
	 private static final char OPEN = '.';   // Code for an open (non-wall) area.
	 



	 /**
	  * Create a Map.
	  * @param gameMap A String[] that defines the map (one string for each row).
	  */
	 public Map(String[] gameMap) {
		 this.map = new ArrayList<>();
		 this.height = gameMap.length;

		 // Define all the open areas.
		 for (int row = 0; row < gameMap.length; row++) {
			 if (gameMap[row].length() > this.width) this.width = gameMap[row].length();
			 for (int column = 0; column < gameMap[row].length(); column++) {
				 if (gameMap[row].charAt(column) == OPEN) {
					 this.map.add(new Location(row, column));
				 }
			 }
		 }

		 // Define the neighbours.
		 for (Location l : this.map) {
			 int row = l.getRow();
			 int column = l.getColumn();
			 l.setExit(Location.NORTH, findLocation(row - 1, column));
			 l.setExit(Location.EAST, findLocation(row, column + 1));
			 l.setExit(Location.SOUTH, findLocation(row + 1, column));
			 l.setExit(Location.WEST, findLocation(row, column - 1));
		 }
	 }

	 /**
	  * Find a location in this map.
	  * @param row A row number.
	  * @param column A column number.
	  * @return the maze Location that has the given row and column numbers or null if no
	  *         location in this map has the given row and column numbers.
	  */
	Location findLocation(int row, int column) {
	    for (Location l : this.map) {
	      if ((l.getRow() == row) && (l.getColumn() == column)) return l;
	    }
	    return null;
	  }
	
	public String toString(Path path) {
	    StringBuilder sb = new StringBuilder();

	    // Include a top boundary.
	    sb.append('+');
	    for (int i = 0; i < this.width; i++) sb.append('-');
	    sb.append('+').append('\n');

	    // Define the actual maze.
	    for (int row = 0; row < this.height; row++) {
	      sb.append('|');
	      for (int column = 0; column < this.width; column++) {
	        Location l = findLocation(row, column);
	        if (l == null) sb.append('X');
	        else if (path.contains(l)) sb.append('\u2022');
	        else sb.append(' ');;
	      }
	      sb.append('|').append('\n');
	    }

	    // Include a bottom boundary.
	    sb.append('+');
	    for (int i = 0; i < this.width; i++) sb.append('-');
	    sb.append('+').append('\n');

	    return sb.toString();
	  }
	

}
