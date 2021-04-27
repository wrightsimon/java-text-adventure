package textAdventure;

import java.util.ArrayList;

/**
 * A Path is an ordered sequence of locations.
 * @author Paul Rushton
 */
public class Path {
  private ArrayList<Location> path;   // The location sequence.

  /**
   * Create an empty Path.
   */
  public Path() {
    this.path = new ArrayList<>();
  }

  /**
   * Add a location to this path.
   * @param location A Location.
   */
  public void add(Location location) {
    location.mark();          // The location has been visited.
    this.path.add(location);
  }

  /**
   * Determine if a location is part of this path.
   * @param location A Location.
   * @return true if a location is part of this path or false if not.
   */
  public boolean contains(Location location) {
    for (Location pathloc : this.path) {
      if (location == pathloc) return true;
    }
    return false;
  }

  /**
   * Move to the previous location of this path.
   * @return the previous path Location.
   */
  public Location goBack() {
    if (size() > 0) this.path.remove(size() - 1);
    return size() > 0 ? this.path.get(size() - 1) : null;
  }

  /**
   * Determine the size of the path.
   * @return the number of locations in this path.
   */
  public int size() {
    return this.path.size();
  }
  
  public Path getPath(Location start) {
	  Path path = new Path();
	  path.add(start);
	  return path;
  }
}