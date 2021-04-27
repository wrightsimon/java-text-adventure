package textAdventure;

public class Character {

	private String name;
	private int health;
	private int maxHealth;
	
	public Character(String name, int health, int maxHealth) {
		this.name = name;
		this.health = health;
		this.maxHealth = maxHealth;
	}

	
	/**
	 * Retrieves the name of a character.
	 * @return Name of the character.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the health of a character.
	 * @param health Health of a character.
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
	/**
	 * Retrieves the health of a character.
	 * @return Health of the character.
	 */
	public int getHealth() {
		return this.health;
	}
	
	public void modifyHealth(double damage) {
		health = getHealth();
		health -= damage;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
}
