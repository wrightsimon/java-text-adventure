package textAdventure;

public class Object extends Item{

	private Object object;
	
		
	public Object(String name, Location location, int weight) {
		super(name, location, weight);
		
	}

	
	public Object getObject() {
		return this.object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
}
