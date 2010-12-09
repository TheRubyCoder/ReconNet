package transformation.tempPetriNet;

public class Place extends Vertex {

	private short token;
	
	
	public Place(String name) {
		super(name);
	}

	
	public short getToken() {
		return token;
	}

	
	public void setToken(short token) {
		this.token = token;
	}

	
}
