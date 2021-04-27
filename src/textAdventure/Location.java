package textAdventure;

import java.util.ArrayList;
import java.util.Random;


/**
 * Location defines a location in a maze.
 * @author Paul Rushton
 * @editor Simon Wright
 */
public class Location {
  static final int NORTH = 0;   // Position number for north neighbour.
  static final int EAST = 1;		// Position number for west neighbour.
  static final int SOUTH = 2;	// Position number for south neighbour.
  static final int WEST = 3;    	// Position number for west neighbour.
  private static final Random RNG = new Random();   // A random number generator.

  private final int row;        // Row number
  private final int column;     // Column number.
  private boolean visited;      // Has this location been visited.
  private Location[] exits;     // The neighbours.
  
 

  /**
   * Create a Location
   * @param row Row number for this location.
   * @param column Column number for this location.
   */
  public Location(int row, int column) {
    this.row = row;
    this.column = column;
    this.visited = false;
    this.exits = new Location[4];
  }

  /**
   * Obtain the row.
   * @return the row number for this location.
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Obtain the column.
   * @return the column number for this location.
   */
  public int getColumn() {
    return this.column;
  }

  /**
   * Mark this location as visited.
   */
  public void mark() {
    this.visited = true;
  }

  /**
   * Define an exit.
   * @param direction An integer that indicates the neighbour's direction.
   * @param neighbour A Location.
   */
  public void setExit(int direction, Location neighbour) {
    this.exits[direction] = neighbour;
  }
  
  /**
   * Obtain the north neighbour.
   * @return Location of the north neighbour.
   */
  public Location getNorthNeighbour() {
	  return this.exits[NORTH];
  }
  
  /**
   * Obtain the east neighbour.
   * @return Location of the east neighbour.
   */
  public Location getEastNeighbour() {
	  return this.exits[EAST];
  }
  
  /**
   * Obtain the south neighbour
   * @return Location of the south neighbour.
   */
  public Location getSouthNeighbour() {
	  return this.exits[SOUTH];
  }
  
  /**
   * Obtain the west neighbour
   * @return Location of the west neighbour.
   */
  public Location getWestNeighbour() {
	  return this.exits[WEST];
  }
  
	
	public static void toString(Location location) {
		System.out.println(location.toString());
		System.out.println("x= " + location.getRow() + ", y= " + location.getColumn());
		
		if (location.getNorthNeighbour() != null) {
			location = location.getNorthNeighbour();
			System.out.println("North exit x= " + location.getRow() + ", y= " + location.getColumn());
		}
		else 
			System.out.println("North exit is null");
		
		if (location.getEastNeighbour() != null) {
			location = location.getEastNeighbour();
			System.out.println("East exit x= " + location.getRow() + ", y= " + location.getColumn());
		}
		else 
			System.out.println("East exit is null");
		
		if (location.getSouthNeighbour() != null) {
			location = location.getSouthNeighbour();
			System.out.println("South exit x= " + location.getRow() + ", y= " + location.getColumn());
		}
		else 
			System.out.println("South exit is null");
		
		if (location.getWestNeighbour() != null) {
			location = location.getWestNeighbour();
			System.out.println("West exit x= " + location.getRow() + ", y= " + location.getColumn());
		}
		else 
			System.out.println("West exit is null");
	}
}