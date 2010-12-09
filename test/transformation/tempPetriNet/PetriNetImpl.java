package transformation.tempPetriNet;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;



public class PetriNetImpl implements PetriNet {

	private static final Pattern pat = Pattern.compile("#");
	
//	private static int nextPlaceId;
//	private static int nextTransitionId;

	
	private final ResizableMatrix pre;
	private final ResizableMatrix post;
	
//	private int numEdges;
	
	private final List<Place> places;
	
	private final List<Transition> transitions;
	
	
	
	
	
	
	public PetriNetImpl(InputStream is) throws FileNotFoundException {
		
		
		Scanner sc = new Scanner(is);

		
		
		sc.next("numPlaces");
		int numPlaces = sc.nextInt();
		sc.next(pat);
		
		sc.next("placeNames");
		places = new ArrayList<Place>(numPlaces);
		for (int i = 0; i < numPlaces; i++) {
			String name = sc.next();
			places.add(new Place(name));
		}
		sc.next(pat);
		
		sc.next("token");
		for (int i = 0; i < numPlaces; i++) {
			short token = sc.nextShort();
			places.get(i).setToken(token);
		}
		sc.next(pat);
		
		sc.next("numTransitions");
		int numTransitions = sc.nextInt();
		sc.next(pat);
		
		sc.next("transitionNames");
		transitions = new ArrayList<Transition>(numTransitions);
		for (int i = 0; i < numTransitions; i++) {
			String name = sc.next();
			transitions.add(new Transition(name));
		}
		sc.next(pat);
		
		pre = new ResizableMatrix(numPlaces, numTransitions);
		sc.next("pre");
		for (int r = 0; r < pre.getNumRows(); r++) {
			for (int c = 0; c < pre.getNumCols(); c++) {
				pre.set(r, c, sc.nextShort());
			}
		}
		sc.next(pat);
		
		post = new ResizableMatrix(numPlaces, numTransitions);
		sc.next("post");
		for (int r = 0; r < post.getNumRows(); r++) {
			for (int c = 0; c < post.getNumCols(); c++) {
				post.set(r, c, sc.nextShort());
			}
		}
		sc.next(pat);
			
	}
	
	
//	private static ResizableMatrix readMatrix(Scanner sc, int rows, int cols) {
//		
//		ResizableMatrix result = new ResizableMatrix(rows, cols);
//		for (int r = 0; r < rows; r++) {
//			for (int c = 0; c < cols; c++) {
//				result.set(r, c, sc.nextShort());
//			}
//		}
//		sc.next(pat);
//		return result;
//	}
	
	
//	private PetriNetImpl(List<Place> places, List<Transition> transitions, ResizableMatrix pre, ResizableMatrix post) {
//		this.places = places;
//		this.transitions = transitions;
//		this.pre = pre;
//		this.post = post;
//	}
	
	
	
	@Override
	public Matrix getPre() {
		return pre;
	}

	
	@Override
	public Matrix getPost() {
		return post;
	}

	
	@Override
	public int getNumPlaces() {
		return pre.getNumRows();
	}

	
	@Override
	public int getNumTransitions() {
		return pre.getNumCols();
	}

	
//	@Override
//	public int getNumEdges() {
//		return numEdges;
//	}
	
	
//	@Override
//	public int addPlace(String label) {
//		if (label == null) {
//			throw new NullPointerException();
//		}
//		int id = nextPlaceId++;
////		placeIds.add(id);
//		places.add(new Place(label));
//		pre.addRows(1);
//		post.addRows(1);
//		return id;
//	}
//
//	@Override
//	public void removePlace(int placeId) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public int addTransition(String label) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void removeTransition(int transitionId) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public String getPlaceNameByIndex(int index) {
		return places.get(index).getName();
	}

//	@Override
//	public void setPlaceName(int placeId, String name) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public String getTransitionNameByIndex(int index) {
		return transitions.get(index).getName();
	}

//	@Override
//	public void setTransitionName(int transitionId, String name) {
//		// TODO Auto-generated method stub
//		
//	}
	
	
	@Override
	public short getTokenByIndex(int index) {
		return places.get(index).getToken();
	}

//	@Override
//	public short getPreEdge(int transitionId, int placeId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setPreEdge(int transitionId, int placeId, short weight) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public short getPostEdge(int transitionId, int placeId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setPostEdge(int transitionId, int placeId, short weight) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public String toString() {
		return String.format("places: %s%ntransitions: %s%npre%n%s%npost%n%s%n",
				places, transitions, pre, post);
	}
	
}
