package textAdventure;

public class Item {

	private String name;
	private Location location;
	private int weight;
	
	
	public Item(String name, Location location, int weight) {
		
		this.name = name;
		this.location = location;
		this.weight = weight;
	}
	
	/**
	 * Get the name of the item.
	 * @return the item's name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the weight of the item.
	 * @return the item's weight.
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Get the location of this item.
	 * @return the item's location.
	 */
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
}
