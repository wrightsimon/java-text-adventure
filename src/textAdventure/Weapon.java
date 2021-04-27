package textAdventure;

public class Weapon extends Item {

	private double damageDie;
	
	public Weapon(String name, Location location, int weight, double damageDie) {
		super(name, location, weight);
		this.damageDie = damageDie;
	}
	
	public double getDamageDie() {
		return this.damageDie;
	}
}
