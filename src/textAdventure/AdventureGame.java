package textAdventure;


public class AdventureGame {
			 
	public static void main(String[] args) {
		
		System.out.println("Welcome to Adventure!");
		new AdventureGame();
		
		
	}//End main()
	
	//Instance definitions
	/** The game. */			private Game game;
	
	public AdventureGame() {
		this.game = new Game();
	}
	
}
