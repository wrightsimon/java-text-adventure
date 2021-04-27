package textAdventure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A fun text-adventure game.
 * @author Simon 300120617
 *
 */

public class Game {

	/** Save file name. */							private static final String SAVE_NAME = "adventure_save.dat";
	
	boolean complete = false; 			//  This turns to true when the game is finished.
	boolean win = false;
	boolean grabbing = false;			//  This turns to true when the player is done picking something up.
	boolean dropStuff = false;			//  This turns to true when the player is done dropping stuff.
	boolean using = false;				//  This turns to true when the player is done using an object.
	boolean solving = false;			//  This turns to true when the player is done solving a puzzle.
	
	boolean isTreeSolved;
	boolean isChestSolved;
	boolean isStatueSolved;
	boolean isCryptEntered;
	boolean isSkeletonDefeated;
		
	/** Scanner for the game. */					Scanner scan = new Scanner(System.in); 
	String input;									//  Initialize the scanner

	/** Data value separator. */                    static final char DATA_SEPARATOR = '\r';

	
	/** Map of the Game. */		private Map map = new Map(new String[] {"X....",
								 									    "XX.XX",
																		"XX.XX",
																		"X...X",
																		"XX.XX",
																		"X...X",
																		"X...X" });
	
	//  Define the map
	/** Location (0, 1) */		Location backAlcove 	 	= map.findLocation(0, 1);
	/** Location (0, 2) */		Location backMiddleHall	 	= map.findLocation(0, 2);
	/** Location (0, 3) */		Location backRightHall 	 	= map.findLocation(0, 3);
	/** Location (0, 4) */		Location lichLair	 	 	= map.findLocation(0, 4);
	/** Location (1, 4) */		Location middleMiddleHall 	= map.findLocation(1, 2);
	/** Location (2, 0) */		Location frontMiddleHall 	= map.findLocation(2, 2);
	/** Location (2, 2) */		Location religionRoom 	 	= map.findLocation(3, 1);
	/** Location (3, 2) */		Location mainEntrance  	 	= map.findLocation(3, 2);
	/** Location (2, 4) */		Location chestAlcove	 	= map.findLocation(3, 3);
	/** Location (4, 2) */		Location graveyard 		 	= map.findLocation(4, 2);	  
	/** Location (5, 1) */		Location westForest 	 	= map.findLocation(5, 1);
	/** Location (5, 2) */		Location daybrook 		 	= map.findLocation(5, 2);
	/** Location (5, 3) */		Location eastForest 	 	= map.findLocation(5, 3);
	/** Location (6, 1) */		Location southWestForest 	= map.findLocation(6, 1);
	/** Location (6, 2) */		Location southForest 	 	= map.findLocation(6, 2);
	/** Location (6, 3) */		Location abandonedHouse  	= map.findLocation(6, 3);

	/** Character's name. */						String name;
	/** Path of the character. */					Path path = new Path();
	/** Character's current location. */			Location current;


	//  Instance definitions
	//  Player
	/** The player's character. */					Player player;
	//  Monsters
	/** The lich. */								Monster lich = new Monster("Lich-Lord Itam", 25, 25, lichLair);
	/** The skeleton. */							Monster skeleton = new Monster("Skeleton Champion", 20, 20, lichLair);
	
	//  Items
	/** The holy relic. */							Item holyRelic = new Item("Holy Relic", this.abandonedHouse, 1);
	/** The armor. */								Item armor = new Item("Armor", null, 5);
	/** The phylactery. */							Item phylactery = new Item("Itam's Phylactery", null, 1);
	
	//  Objects
	Object enhancePotion = new Object ("Health Enhancement Potion", null, 6);
	Object restorePotion = new Object ("Health Restore Potion", null, 6);
	Puzzle statue = new Puzzle ("Puzzle Statue", religionRoom, 10, "Candle");
	Puzzle tree = new Puzzle ("Puzzle Tree", southWestForest, 10, "1 5 3 2 4 6");
	Puzzle chest = new Puzzle ("Puzzle Chest", chestAlcove, 10, "Pride");
	
	//  Weapons
	/** The stick. */								Weapon stick = new Weapon("Stick", this.westForest, 1, 4);
	/** The sword. */								Weapon sword = new Weapon("Sword", this.backAlcove, 3, 6);


	/**
	 * Creates a new game.
	 */
	public Game() {

		//  Create a new character
		System.out.println("What is your name?");
		this.name = scan.nextLine();
		this.player = new Player (name, 10, 10);
		
		//  Note down the game's progress.
		this.isTreeSolved = false;
		this.isStatueSolved = false;
		this.isChestSolved = false;
		this.isCryptEntered = false;
		
		//  Start the new character in Daybrook.
		this.current = this.daybrook;
		
		//  Set the new character's score to 0.
		this.player.setScore(0);
		
		
		System.out.println("The town of Daybrook has hired you to defeat the Lich Lord Itam, who was been plaguing their small town for centuries.");
		this.describeLocation(current);
		System.out.println("Type 'help' for information on what you can do!");
		this.playGame();	//  Start playing.
		
	}//  End new game.

	/**
	 * Restore a game from a save.
	 * @param gameSave reads from the save file.
	 */
	public void restoreGame(Scanner gameSave) {
		
		//  Restore the player.
		String restoreName = gameSave.nextLine();						// Reads the next line line of save file and sets it to restoreName.
		int restoreHP = gameSave.nextInt();								// Reads the next line line of save file and sets it to restoreHP.
		int restoreMaxHP = gameSave.nextInt();							// Reads the next line line of save file and sets it to restoreMaxHP.
		player = new Player(restoreName, restoreHP, restoreMaxHP);	// Replaces the old player with a new one.
		
		//  Restore game progress.
		isTreeSolved = gameSave.nextBoolean();						// Reads the next line line of save file and figures out if the tree puzzle has been solved.
		isStatueSolved = gameSave.nextBoolean();					// Reads the next line line of save file and figures out if the statue puzzle has been solved.
		isChestSolved = gameSave.nextBoolean();					// Reads the next line line of save file and figures out if the chest puzzle has been solved.
		isCryptEntered = gameSave.nextBoolean();					// Reads the next line of save file and figures out if the player has entered the crypt.
		isSkeletonDefeated = gameSave.nextBoolean();				// Reads the next line of save file and figures out if the skeleton has been defeated.
		
		if (isSkeletonDefeated != false) {
			skeleton.setLocation(null);
			holyRelic.setLocation(null);
		}
		
		//  Restore current location.
		current = this.map.findLocation(gameSave.nextInt(), gameSave.nextInt());	// Reads the next two lines of the save file to find the location where the player left off.

		//  Restore the player's score.
		player.setScore(gameSave.nextInt());						// Reads the next line of save file and gives the player their score back.
		
		//  Restore the player's inventory.
		this.player.inventory = new ArrayList<Item>();					// Starts an ArrayList to hold the player's items.
		while (gameSave.hasNextLine() != false) {						// While the file still have lines, do a loop. Problem isn't with this, it's true.
			String readFile = gameSave.nextLine();						// Reads the next line of save file and sets it to readFile.
			if (readFile.equals(holyRelic.getName())) {					// if readFile is holyRelic.getName();
				player.addInventory(holyRelic);							// Add it to the player's new inventory.
				holyRelic.setLocation(null);							// And make sure it's no longer in the map.
			}
			else if (readFile.equals(stick.getName())) {				// Do the same for this.stick
				player.addInventory(stick);
				stick.setLocation(null);
			}
			else if (readFile.equals(sword.getName())) {				// Do the same for this.stick
				player.addInventory(sword);
				sword.setLocation(null);
			}
			else if (readFile.equals(armor.getName())) {				// Do the same for this.stick
				player.addInventory(armor);
				armor.setLocation(null);
			}
			else if (readFile.equals(phylactery.getName())) {			// Do the same for this.phylactery
				player.addInventory(phylactery);
				phylactery.setLocation(null);
			}
		}//  End while loop for player's inventory restoration.

	}//  End restored game.
	
	public void playGame() {

		//  Start the path.
		this.path.getPath(this.current);

		while (complete != true) {	//  While the game hasn't been completed, ask the user what they want to do and do it.

			System.out.println("What would you like to do?");
			input = this.scan.nextLine();
			if (input.matches("(?i:help)")) {	//  If the player inputted help, tell them what their options are at this menu.
				System.out.println("All commands can be referenced in all uppercase and all lowercase. Directions can also be referenced through initials");
				System.out.println("Movement:");
				System.out.println("	NORTH: moves the character north.");
				System.out.println("	EAST: moves the character east.");
				System.out.println("	SOUTH: moves the character south.");
				System.out.println("	WEST: moves the character west.");
				System.out.println("	BACK: moves the character to their previous location.");
				System.out.println("Actions:");
				System.out.println("	ATTACK: the character attacks an available target.");
				System.out.println("	CARRY, GRAB, TAKE: the player picks up an available item of their choice.");
				System.out.println("	USE: the player uses an available item in the room. This is used for consumables.");
				System.out.println("	SOLVE: the player enters puzzle solving mood if there is a puzzle in the room.");
				System.out.println("Player:");
				System.out.println("	BACKPACK, INVENTORY: informs the user of the character's inventory contents.");
				System.out.println("    LOCATION: informs the user of their current location.");
				System.out.println("	PLAYER: informs the user of their name, health, max health and score.");
				System.out.println("System:");
				System.out.println("	SAVE: saves the current game.");
				System.out.println("	LOAD, RESTORE: loads the last saved game.");
				System.out.println("	QUIT: quits the game.");
			}//  End if for HELP.
 
			else if (input.matches("(?i:(north|n))")) {	//  If the player inputted north, move north one location.
				this.move("North");
			}//  End if for NORTH.

			else if (input.matches("(?i:(east|e))")) {	//  If the player inputted east, move east one location.
				this.move("East");
			}//  End if for EAST.

			else if (input.matches("(?i:(south|s))")) {	//  If the player inputted south, move south one location.
				this.move("South");
			}//  End if for SOUTH.

			else if (input.matches("(?i:(west|w))")) {	//  If the player inputted west, move west one location.
				this.move("West");
			}//  End if for WEST.

			else if (input.matches("(?i:back)")) {		//  If the player inputted back, move to the player's last location.
				this.current = this.path.goBack();
				this.describeLocation(this.current);
			}//  End if for BACK.

			else if (input.matches("(?i:attack)")) {						//  If the player inputted attack:
				if (current.equals(skeleton.getLocation())) {				//  Check to see if they're in the same location as the skeleton.
					attack(player, skeleton);								//  Invoke the attack method with the player attacking the skeleton.
				} 
				else if (current.equals(lich.getLocation())) {				// If they're with the lich, attack the lich.
					attack(player, lich);
				}
				else if (current.equals(phylactery.getLocation())) {		//  If they're with the lich's phylactery, attack it instead.
					System.out.println("You stab your sword into the phylactery; a strong stench of undeath fills the room as a ghostly apparition flies out of the gem and disappears."); 
					this.phylactery.setLocation(null);
					this.win = true;
					this.complete = true;
				} 
				else {
					System.out.println("There's nothing here to attack.");
				}
			}//  End if for ATTACK.

			else if (input.matches("(?i:(take|grab|carry))")) {
				this.grab(player);
			}//  End if for TAKE/GRAB/CARRY.

			else if (input.matches("(?i:use)")) {
				this.use(this.player);
			}//  End if for USE.

			else if (input.matches("(?i:solve)")) {
				this.solve(current);
			}

			else if (input.matches("(?i:(backpack|inventory))")) {
				if (player.inventory.size() != 0) {
					this.player.checkInventory(this.player.inventory);
					System.out.println("Would you like to drop something?");											//  Prevents else statement from triggering early.
					String specialInput = scan.nextLine();

					dropStuff = false;
					while (dropStuff != true) {
						if(specialInput.matches("(?i:yes)")) {								
							this.checkInventory(this.player);
							dropStuff = true;
						}
						else if(specialInput.matches("(?i:no)")) {
							dropStuff = true;
						}
						else {
							System.out.println("You entered: " + specialInput + "; that is not an option. Try again.");
							dropStuff = true;
						}

					}
				} else {
					System.out.println("There's nothing in your inventory.");
				}
			}//  End if for BACKPACK/INVENTORY.*/

			else if (input.matches("(?i:player)")) {
				System.out.println("Name: " + this.player.getName());
				System.out.println("Health: " + this.player.getHealth() + " / " + this.player.getMaxHealth());
				System.out.println("Score: " + this.player.getScore());
			}//  End if for SCORE.

			else if (input.matches("(?i:location)")) {
				Location.toString(this.current);
			}//  End if for LOCATION.

			else if (input.matches("(?i:map)")) {
				System.out.println(this.map.toString(path));
			}//  End if for MAP.

			else if (input.matches("(?i:save)")) { 
				this.save();
			}//  End if for SAVE.

			else if (input.matches("(?i:(load|restore))")) { 
				this.restore();
			}//  End if for LOAD/RESTORE.

			else if (input.matches("(?i:quit)")) { 
				System.out.println("See you soon.");
				this.complete = true;
			}//  End if for QUIT.

			else {
				System.out.println("You inputted: " + input + "; this is not an option. Please enter an option");
			}

		}// End of while loop.
		if (this.win != false) 
			System.out.println("Congratulations! You defeated the lich and saved Daybrook!");
			System.out.println("You scored: " + this.player.getScore());
	}// End playGame method.

	/**
	 * Saves the game.
	 */
	public void save() {
		try {
			FileWriter file = new FileWriter(SAVE_NAME);
			file.write(this.writeSave());
			file.close();
			System.out.println("Save successful!");
		}
		catch (IOException ioe) {
			System.out.println("Save failed.");
		}
	}
	/**
	 * Writes the save file.
	 */
	public String writeSave() {

		StringBuilder sb = new StringBuilder();

		// Need to know player name.
		sb.append(this.player.getName()).append(DATA_SEPARATOR);

		// Need to know player health.
		sb.append(this.player.getHealth()).append(DATA_SEPARATOR);
		sb.append(this.player.getMaxHealth()).append(DATA_SEPARATOR);

		// Need to know game progress (treeSolved, statueSolved, chestSolved, etc.)
		sb.append(this.isTreeSolved).append(DATA_SEPARATOR);
		sb.append(this.isStatueSolved).append(DATA_SEPARATOR);
		sb.append(this.isChestSolved).append(DATA_SEPARATOR);
		sb.append(this.isCryptEntered).append(DATA_SEPARATOR);
		sb.append(this.isSkeletonDefeated).append(DATA_SEPARATOR);

		// Need to know current location.
		sb.append(this.current.getRow()).append(DATA_SEPARATOR);
		sb.append(this.current.getColumn()).append(DATA_SEPARATOR);

		// Need to know score.
		sb.append(this.player.getScore()).append(DATA_SEPARATOR);

		// Need to know inventory.
		for (Item inventory : this.player.inventory) {
			sb.append(inventory.getName()).append(DATA_SEPARATOR);
		}

		return sb.toString();
	}// End save()
	
	public void restore() {
		try {
			FileReader file = new FileReader(SAVE_NAME);
			Scanner gameSave = new Scanner(file);
			this.restoreGame(gameSave);
			System.out.println("Successfully restored, welcome back " + player.getName());
			gameSave.close();
			file.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("No save file found.");
		}
		catch (IOException ioe) {
			System.out.println("Failed to restore.");
		}
	}

	public void attack(Player player, Monster monster) {

		int damage = 0;
		Weapon weapon = new Weapon("Fist", current, 0, 2);										// Give the player a punch attack.

		if (player.inventory.contains(this.stick) && !player.inventory.contains(this.sword)) {	// Replace the punch attack with the stick if he isn't holding the sword and is holding the stick.
			weapon = this.stick;
		} else if (player.inventory.contains(this.sword)) {										// Replace the punch attack with the sword if the player is holding it.
			weapon = this.sword;
		}

		if (current.equals(skeleton.getLocation())) {
			if (player.inventory.contains(this.holyRelic)) {
				damage = (int)(((Math.random() + 1) * weapon.getDamageDie()) * 2);
				monster.modifyHealth(damage);
			}
			else if (!player.inventory.contains(holyRelic)) {
				damage = (int)((Math.random() + 1) * weapon.getDamageDie());
			}
			System.out.println("You attack the " + monster.getName() + " and deal " + damage + " damage.");
			if (monster.getHealth() > 0) {
				if (!player.inventory.contains(this.armor)) {
					damage = 6;
				} 
				else {
					damage = 3;
				}
				player.modifyHealth(damage);System.out.println("The " + monster.getName() + " attacks back and deals " + damage + " damage.");
				System.out.println("Your current health is: " + player.getHealth() + ".");
			}
			else if (monster.getHealth() <= 0) {
				System.out.println("The " + skeleton.getName() + " crumbles to dust as you strike your " + weapon.getName() + " into his skeletal body.");
				System.out.println("The skeleton's sword clatters to the floor.");
				if (player.inventory.contains(holyRelic)) {
					System.out.println("The " + holyRelic.getName() + " disappears from your backpack.");
					player.inventory.remove(holyRelic);
				}
				holyRelic.setLocation(null);
				sword.setLocation(skeleton.getLocation());
				skeleton.setLocation(null);
				isSkeletonDefeated = true;
				player.modifyScore(1);
			}
		}
		
		if (current.equals(lich.getLocation())) {

			damage = (int)((Math.random() * weapon.getDamageDie()) * 2);
			monster.modifyHealth(damage);

			System.out.println("You attack the " + monster.getName() + " and deal " + damage + " damage.");
			if (monster.getHealth() > 0) {
				if (!player.inventory.contains(this.armor)) {
					player.modifyHealth(player.getMaxHealth());
					System.out.println("The lich jabs his hand into your chest through your clothes and crushes your heart.");
				} else {
					damage = 5;
					player.modifyHealth(damage);
					System.out.println("The lich attacks back and deals " + damage + " damage.");
					System.out.println("Your current health is: " + player.getHealth() + ".");
				}
			}
			else if (monster.getHealth() <= 0) {
				System.out.println("The Lich Lord crumbles to dust as you strike your " + weapon.getName() + " into his skeletal body.");
				System.out.println("You see the dust of the Lich Lord swirl into a dull blue gem and clatter to the floor.");
				this.phylactery.setLocation(this.lich.getLocation());
				this.lich.setLocation(null);
				player.modifyScore(1);
			}
		}
		
		if (player.getHealth() <= 0) {		//If the player takes too much damage during the fight, they die.
			System.out.println("You died.");
			this.complete = true;
		}
	}

	public void grab(Player player) {

		grabbing = false;
		System.out.println("What would you like to take?");
		while (grabbing != true) {
			String specialInput = null;												// Prevents the else statement from triggering early.
			specialInput = scan.nextLine();

			if (specialInput.matches("(?i:holy (symbol|relic))")) {
				if (current == this.holyRelic.getLocation()) {
					player.addInventory(this.holyRelic);
					this.holyRelic.setLocation(null);
					if (player.inventory.contains(this.holyRelic)) {
						System.out.println("You grabbed the " + this.holyRelic.getName() + "!");
						player.modifyScore(1);
					}
					grabbing = true;
				} else {
					System.out.println("There is no " + specialInput + " to carry.");
					grabbing = true;
				} 
			}// End if statement for holyRelic

			else if (specialInput.matches("(?i:(stick|branch))")) {
				if (current == this.stick.getLocation()) {
					player.addInventory(this.stick);
					this.stick.setLocation(null);
					if (player.inventory.contains(this.stick)) {
						System.out.println("You grabbed the " + this.stick.getName() + "!");
					}
					this.grabbing = true;
				} else {
					System.out.println("There is no " + specialInput + " to carry.");
					this.grabbing = true;
				}
			}// End if statement for stick.

			else if(specialInput.matches("(?i:sword)")) {
				if (current == this.sword.getLocation()) {										// Ensure the player is actually where the sword is.
					player.addInventory(this.sword);
					this.sword.setLocation(null);
					if (player.inventory.contains(this.sword)) {									// Ensure the player actually grabbed the sword.
						System.out.println("You grabbed the " + this.sword.getName() + "!");
						player.modifyScore(1);
					}																	
					this.grabbing = true;
				} else {
					System.out.println("There is no " + specialInput + " to carry.");
					this.grabbing = true;
				}// End else statement for sword.
			}// End if statement for sword.

			else if (specialInput.matches("(?i:armor)")) {
				if (current == this.armor.getLocation()) {
					player.addInventory(this.armor);
					this.armor.setLocation(null);
					if (player.inventory.contains(this.armor)) {
						System.out.println("You grabbed the " + this.armor.getName() + "!");
						player.modifyScore(1);
					}
					this.grabbing = true;
				} else {
					System.out.println("There is no " + specialInput + " to carry.");
					this.grabbing = true;
				}// End else statement for armor.
			}// End if statement for armor.
			
			else if (specialInput.matches("(?i:phylactery)")) {
				if (current == this.phylactery.getLocation()) {
					player.addInventory(this.phylactery);
					this.phylactery.setLocation(null);
					if (player.inventory.contains(this.phylactery)) {
						System.out.println("You grabbed " + this.phylactery.getName() + "!");
						player.modifyScore(-1);
					}
					this.grabbing = true;
				} else {
					System.out.println("There is no " + specialInput + " to carry.");
					this.grabbing = true;
				}
			}
			
			else {
				System.out.println("There is no " + specialInput + " to carry.");
				this.grabbing = true;
			}// End else for grab mode.
		}// Exit while grabbing mode.
	}// End grab

	public void move(String direction) {
		try {
			if (direction == "North") {
				if (current.getNorthNeighbour() != null) {
					this.current = this.current.getNorthNeighbour();
				} 
				else {
					System.out.println("A force prevents you from moving this way.");
				}
			}
			else if (direction == "East") {
				if (current.getEastNeighbour() != null) {
					this.current = this.current.getEastNeighbour();
				} 
				else {
					System.out.println("A force prevents you from moving this way.");
				}
			}
			if (direction == "South") {
				if (current.getSouthNeighbour() != null) {
					this.current = this.current.getSouthNeighbour();
				} else {
					System.out.println("A force prevents you from moving this way.");
				}
			}
			if (direction == "West") {
				if (current.getWestNeighbour() != null) {
					this.current = this.current.getWestNeighbour();
				} else {
					System.out.println("A force prevents you from moving this way.");
				}
			}

			this.path.add(this.current);
			this.describeLocation(this.current);

			if (isCryptEntered != false) {
				skeleton.moveMonster();
			}
		} catch (NullPointerException exc) {
			System.out.println("A force prevents you from moving this way.");
			this.describeLocation(this.current);

		}
		if (isCryptEntered != true && current == this.mainEntrance) {
			this.isCryptEntered = true;
			System.out.println("You set off the Lich's trap. You hear the rattling of bones and metal from down the crypt's hallway.");
		}
	}



	/**
	 * Describes the environment the character is in.
	 */
	public void describeLocation(Location location) {
		if (location == this.backAlcove) {
			System.out.println("I am currently standing in a small room. Pushed into an alcove is skeletal remains.");
			System.out.println("To the east is a long hallway to the Lich Lord's lair.");
			System.out.println("To the west is a wall with an alcove.");
			System.out.println("To the north and west are crypt walls.");
		}
		else if (location == this.backMiddleHall) {
			System.out.println("I am currently standing in the northernmost part of the hall.");
			System.out.println("To the north is a crypt wall.");
			System.out.println("To the east is a long hallway to the Lich Lord's lair.");
			System.out.println("To the south is the hallway back to Daybrook.");
			System.out.println("To the west is a small room.");
		}
		else if (location == this.backRightHall) {
			System.out.println("I am currently standing in the hall closest to the Lich Lord's lair");
			System.out.println("To the east is the Lich Lord's lair.");
			System.out.println("To the west is a hallway back to Daybrook.");
			System.out.println("To the north and south are crypt walls.");
		}
		else if (location == this.lichLair) {
			System.out.println("I am currently standing in the Lich Lord's lair, the floor is covered in pages of runes and glyphs.");
			System.out.println("To the west is the only exit from this room.");
		}
		else if (location == this.middleMiddleHall) {
			System.out.println("I am currently standing in the middle of the main hall.");
			System.out.println("To the north is the hall to the Lich Lord's lair.");
			System.out.println("To the south is the hallway back to Daybrook.");
			System.out.println("To the east and west are crypt walls.");
		}
		else if (location == this.frontMiddleHall) {
			System.out.println("I am currently standing in the start of the main hall.");
			System.out.println("To the north is the hall to the Lich Lord's lair.");
			System.out.println("To the south is the entrance to the crypt.");
			System.out.println("To the east and west are crypt walls.");
		}
		else if (location == this.religionRoom) {
			System.out.println("I am currently standing in a small room covered with burnt out candles.");
			System.out.println("To the east is the main entrance of the crypt.");
			System.out.println("To the north, south, and west are crypt walls.");
		}
		else if (location == this.mainEntrance) {
			System.out.println("I am currently standing in the entrance to the crypt.");
			System.out.println("To the north is a long hall further into the crypt.");
			System.out.println("To the east is a small room with a statue.");
			System.out.println("Up the stairs to the south is Daybrook's graveyard.");
			System.out.println("To the west is a small room with what looks to be a chest.");
		}
		else if (location == this.chestAlcove) {
			System.out.println("I am currently standing in a small room with ruined gifts for the dead.");
			System.out.println("To the west is the main hall of the crypt.");
			System.out.println("To the north, east, and south are crypt walls.");
		}
		else if (location == this.graveyard) {
			System.out.println("I am currently standing in Daybrook's graveyard");
			System.out.println("To the north is Daybrook's crypt.");
			System.out.println("To the south is Daybrook.");
			System.out.println("To the east and west is an unending forest.");
		}
		else if (location == this.westForest) {
			System.out.println("I am currently standing in the forest west of Daybrook.");
			System.out.println("To the east is Daybrook");
			System.out.println("To the south is an open glade");
			System.out.println("To the north and the west is an unending forest");
			System.out.println("Broken branches are scattered on the ground");
		}
		else if (location == this.daybrook) {
			System.out.println("I am currently standing in Daybrook");
			System.out.println("To the north is the graveyard.");
			System.out.println("To the east is some forest.");
			System.out.println("To the south is the path I took to get to Daybrook.");
			System.out.println("To the west is some forest.");
		}
		else if (location == this.eastForest) {
			System.out.println("I am currently standing in the forest east of Daybrook.");
			System.out.println("To the north and the east is an unending forest.");
			System.out.println("To the south is an abandoned house.");
			System.out.println("To the west is Daybrook.");
		}
		else if (location == this.southWestForest) {
			System.out.println("I am currently standing on the edge of a glade in the southwestern forest.");
			System.out.println("To the north is some forest");
			System.out.println("To the east is the path I took to get to Daybrook.");
			System.out.println("To the south and the west is unending forest.");
		}
		else if (location == this.southForest) {
			System.out.println("I am currently standing on the path I took to get to Daybrook.");
			System.out.println("To the north is Daybrook.");
			System.out.println("To the east is an abandoned house.");
			System.out.println("To the south is the path away from Daybrook. I can't go this way until I complete my mission.");
			System.out.println("To the west is an open glade");
		}
		else if (location == this.abandonedHouse) {
			System.out.println("I am currently standing in an abandoned house. There are scattered belongings on the floor. In the middle of the room is a slightly broken table and two chairs.");
			System.out.println("To the north is some forest.");
			System.out.println("To the east and the south is unending forest.");
			System.out.println("To the west is the path I took to get to Daybrook");
		}
		else {
			System.out.println("Something went wrong. I am currently standing in the void.");
		}
		
		if (location == this.stick.getLocation()) {
			System.out.println("There's a stick on the ground that looks wieldable.");
		}
		if (location == this.sword.getLocation()) {
			System.out.println("A shiny sword can be seen on the ground.");
		}
		if (location == this.holyRelic.getLocation()) {
			System.out.println("A beautiful holy symbol is on the ground.");
		}
		if (location == this.enhancePotion.getLocation()) {
			System.out.println("A vial containing orange liquid is nicely placed in the tree's opening.");
		}
		if (location == this.restorePotion.getLocation()) {
			System.out.println("A vial containing red liquid is held nicely in the statue's hand.");
		}
		if (location == this.tree.getLocation()) {
			System.out.println("One tree here looks out of the ordinary. Maybe this is a puzzle!");
			if (isTreeSolved != false)
				System.out.println("The glyphs are still lit up and the opening is still there.");
		}
		if (location == this.statue.getLocation()) {
			System.out.println("A large statue of a hooded figure stands in the middle of the room. It's holding it's right hand in the air and it's left closed in front of it. Maybe this is a puzzle!");
			if (isStatueSolved != false)
				System.out.println("Its left hand is still open.");
		}
		if (location == this.chest.getLocation()) {
			System.out.println("A large chest sits in the middle of the room. Maybe this is a puzzle!");
			if (isChestSolved != false)
				System.out.println("It's still wide open.");
		}
		
		if (location == this.skeleton.getLocation()) {
			System.out.println("An armored skeleton stands tall in front of you. It raises it's weapon high as cold blue light shines from its eye sockets.");
		}
		
		if (location == this.lich.getLocation()) {
			System.out.println("Once fine robes hang in tatters from this withered corpse's frame. A pale blue light shines from where its eyes should be. The lich lifts itself from the ground and readies itself for attack.");
		}
		
		if (location == this.phylactery.getLocation()) {
			System.out.println("A dull blue gem is on the floor. Looking closely you can see a dark swirl in the middle.");
		}
		
	}// End findLocation

	public void checkInventory(Player player) {

		while (dropStuff != true) {
			System.out.println("What would you like to drop?");																		// Prevents else statement from triggering early.
			String removal = this.scan.nextLine();
			if(removal.matches("(?i:sword)")) {
				if (player.inventory.contains(this.sword) ) {
					player.inventory.remove(this.sword);
					this.sword.setLocation(this.current);
					System.out.println("You dropped the " + this.sword.getName() + ".");
					player.modifyScore(-1);
					dropStuff = true;
				} else {
					System.out.println("You do not have " + removal + " in your inventory, try again.");
				}
			}
			
			else if(removal.matches("(?i:(branch|stick))")) {
				if (player.inventory.contains(this.stick)) {
					player.inventory.remove(this.stick);
					this.stick.setLocation(this.current);
					System.out.println("You dropped the " + this.stick.getName() + ".");
					dropStuff = true;
				} else {
					System.out.println("You do not have " + removal + " in your inventory, try again.");
				}
			}
			
			else if(removal.matches("(?i:holy (relic|symbol))")) {
				if (player.inventory.contains(this.holyRelic)) {
					player.inventory.remove(this.holyRelic);
					this.holyRelic.setLocation(this.current);
					System.out.println("You dropped the " + this.holyRelic.getName() + ".");
					player.modifyScore(-1);
					dropStuff = true;
				} else {
					System.out.println("You do not have " + removal + " in your inventory, try again.");
				}
			}
			
			else if (removal.matches("(?i:armor)")) {
				if (player.inventory.contains(this.armor)) {
					player.inventory.remove(this.armor);
					this.armor.setLocation(this.current);
					System.out.println("You dropped the " + this.armor.getName() + ".");
					player.modifyScore(-1);
					dropStuff = true;
				} else {
					System.out.println("You do not have " + removal + " in your inventory, try again.");
				}
			}
			
			else if (removal.matches("(?i:phylactery)")) {
				if (player.inventory.contains(this.phylactery)) {
					player.inventory.remove(this.phylactery);
					this.phylactery.setLocation(this.current);
					System.out.println("You dropped " + this.phylactery.getName() + ".");
					player.modifyScore(1);
					dropStuff = true;
				} else {
					System.out.println("You do not have " + removal + " in your inventory, try again.");
				}
			}
			
			else if(removal.matches("(?i quit)")) {
				dropStuff = true;
			}
			
			else
				System.out.println("You do not have " + removal + " in your inventory, try again.");
		}// End while loop for checkInventory.
	}// End checkInventory method.

	public void use(Player player) {

		this.using = false;
		System.out.println("What would you like to use?");
		String specialInput = null;
		while (this.using != true) {
			specialInput = this.scan.nextLine();

			if (specialInput.matches("(?i:enhancement potion)")) {
				System.out.println("You drink the potion.");
				this.enhancePotion.setLocation(null);
				player.setMaxHealth(20);
				player.setHealth(20);
				System.out.println("You feel invigorated as your maximum health increases to " + player.getMaxHealth() + ".");
				player.modifyScore(1);
				using = true;
			}

			else if (specialInput.matches("(?i:restoration potion)")) {
				System.out.println("You drink the potion.");
				this.restorePotion.setLocation(null);
				if (player.getMaxHealth() == 20) {
					player.setHealth(20);
				}
				else if (player.getMaxHealth() == 10) {
					player.setHealth(10);
				}
				System.out.println("You feel regenerated as your health returns to " + player.getHealth() + ".");
				player.modifyScore(1);
				using = true;
			}

			else  {
				System.out.println("You entered: " + specialInput + "; This is not an option. Try again.");
				using = true;
			}
		}

	}

	public void solve(Location location) {
		
		this.solving = false;
		String specialInput = null;
		
		
			if (location == tree.getLocation() && isTreeSolved != true) {
				System.out.println("There are 6 glyphs on this tree. You get the feeling you need to touch the glyphs in the correct order.");
				System.out.println("What is the order in which you press these glyphs? Enter the order of the numbers with a space between. i.e. 1 2 3 4 5 6");
				specialInput = scan.nextLine();
				
				if (specialInput.matches(this.tree.solution)) {
					System.out.println("The glyphs light up as you press them and stay lit as you finish. An opening appears under the glyphs revealing a vial of orange liquid, it looks like an enhancement potion.");
					this.enhancePotion.setLocation(this.tree.getLocation());
					isTreeSolved = true;
					this.player.modifyScore(1);
					solving = true;
				}// End if for correct solution.
				
				else {
					System.out.println("The glyphs light up as you press them, but dim out after you finish. You get the feeling you didn't press them in the right order.");
					solving = true;
				}// End else for incorrect solution.
			}// End if for Puzzle Tree
			
			if (location == chest.getLocation() && isChestSolved != true) {
				System.out.println("The chest has a scroll attached to it's top. It reads: 'What can be swallowed but swallow you also?'");
				System.out.println("On the floor beside the chest is an inkwell and quill.");
				System.out.println("What do you write on the scroll?");
				specialInput = scan.nextLine();
				
				if (specialInput.matches("(?i:pride)")) {
					System.out.println("You hear a click as the chest opens revealing a full suit of armor.");
					this.armor.setLocation(this.chest.getLocation());
					isChestSolved = true;
					this.player.modifyScore(1);
					solving = true;
				}
				
				else {
					System.out.println("You hear nothing. You try to open the chest but it doesn't budge.");
					solving = true;
				}
			}// End if for Puzzle Chest
			
			if (location == statue.getLocation() && isStatueSolved != true) {
				System.out.println("As you approach the statue a voice echoes throughout the room.");
				System.out.println("'I'm tall when I'm young and short when I'm old. What am I?'");
				System.out.println("How do you respond?");
				specialInput = scan.nextLine();
				
				if (specialInput.matches("(?i:candle)")) {
					System.out.println("The statue's right hand opens up revealing a vial with red liquid. It looks like a restoration potion.");
					this.restorePotion.setLocation(this.statue.getLocation());
					isStatueSolved = true;
					this.player.modifyScore(1);
					solving = true;
				}// End if for correct solution.
				
				else {
					System.out.println("The statue stays stagnant. The voice disappears. You get the feeling you answered incorrectly.");
				}// End else for incorrect solution.
			}// End if for Puzzle Statue
			
		
	}// End solve()

	
}
