package transformation.tempPetriNet;


public abstract class Vertex {
	
//	private int index;
	
	private String name;
	
	
//	public Vertex() {
//		
//	}
	
	
	public Vertex(String name) {
		setName(name);
		
	}
	
//	public int getIndex() {
//		return index;
//	}
//
//	public void setIndex(int index) {
//		this.index = index;
//	}

	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}


	@Override
		public String toString() {
			return name;
		}
	
}
