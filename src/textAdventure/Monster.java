package textAdventure;

import java.util.Random;

public class Monster extends Character {

	private Location location;
	private int RNG;
	
	public Monster(String name, int health, int maxHealth, Location location) {
		super(name, health, maxHealth);
		this.location = location;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void moveMonster() {
		try {
			RNG = (int)(Math.random() * 4);
			if (RNG == 0) {
				if (getLocation().getNorthNeighbour() != null) {
					setLocation(getLocation().getNorthNeighbour());
				} 
			}
			
			else if (RNG == 1) {
				if (getLocation().getEastNeighbour() != null) {
					setLocation(getLocation().getEastNeighbour());
				}
			}
			if (RNG == 2) {
				if (getLocation().getSouthNeighbour() != null) {
					setLocation(getLocation().getSouthNeighbour());
				}
			}
			if (RNG == 3) {
				if (getLocation().getWestNeighbour() != null) {
					setLocation(getLocation().getWestNeighbour());
				}
			}
		} catch (NullPointerException exc) {
			
		}
		
	}
	
	
}
