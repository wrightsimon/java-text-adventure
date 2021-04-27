package textAdventure;

import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Character {

	ArrayList<Item> inventory = new ArrayList<Item>();
	private static int carryWeight;
	private int score;

	static boolean dropStuff = false;	//This turns to true when the player is done dropping stuff.

	
	/**
	 * Creates a player.
	 * @param name Name of the player.
	 * @param health Health of the player.
	 */
	public Player(String name, int health, int maxHealth) {
		super(name, health, maxHealth);
		carryWeight = 5;
		score = 0;
	}
	
	
	/**
	 * Sets the inventory of the player.
	 * @param inventory Inventory of the player.
	 */
	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * Retrieves the inventory of the player.
	 * @return Inventory of the player.
	 */
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	/**
	 * Add an item to the player's inventory if it isn't too heavy.
	 * @param item The item to add.
	 */
	public void addInventory(Item item) {
		if (item.getWeight() <= carryWeight) {
			inventory.add(item);
		}
		else 
			System.out.println("You can't carry that!");
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void modifyScore(int sc) {
		score += sc;
	}
	
	public void checkInventory(ArrayList<Item> inventory) {
		System.out.println("You are holding:");
		for (Item item : inventory) {
			System.out.println(item.getName());
		}
	}
}
