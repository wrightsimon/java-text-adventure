package textAdventure;

public class Puzzle extends Object{

	String solution;
	
	public Puzzle(String name, Location location, int weight, String solution) {
		super (name, location, weight);
		this.solution = solution;
	}
}
