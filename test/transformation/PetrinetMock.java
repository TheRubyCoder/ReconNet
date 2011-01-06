package transformation;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import petrinetze.*;
import petrinetze.impl.*;

public class PetrinetMock implements IPetrinet {

private static final Pattern pat = Pattern.compile("#");
	
	private static int nextPlaceId;
	private static int nextTransitionId;

	

	private final int[][] preMatrix;
	private final int[][] postMatrix;
	
	private final int[] transitionIds;
	private final int[] placeIds;
	
	private final ITransition[] transitions;
	private final IPlace[] places;
	
	private final IPre pre = new IPre() {

		@Override
		public int[] getTransitionIds() {
			return transitionIds;
		}

		@Override
		public int[][] getPreAsArray() {
			return preMatrix;
		}

		@Override
		public int[] getPlaceIds() {
			return placeIds;
		}
	};
	
	private final IPost post = new IPost() {

		@Override
		public int[] getTransitionIds() {
			return transitionIds;
		}

		@Override
		public int[][] getPostAsArray() {
			return postMatrix;
		}

		@Override
		public int[] getPlaceIds() {
			return placeIds;
		}
	};
	
	
	
	
	
	public PetrinetMock(InputStream is) throws FileNotFoundException {
		
		nextPlaceId = 0;
		nextTransitionId = 0;
		
		Scanner sc = new Scanner(is);

		
		
		sc.next("numPlaces");
		final int numPlaces = sc.nextInt();
		sc.next(pat);
		
		sc.next("placeNames");
		places = new IPlace[numPlaces];
		placeIds = new int[numPlaces];
		for (int i = 0; i < numPlaces; i++) {
			String name = sc.next();
			placeIds[i] = nextPlaceId++;
			places[i] = new Place(placeIds[i]);
			places[i].setName(name);
		}
		sc.next(pat);
		
		sc.next("token");
		for (int i = 0; i < numPlaces; i++) {
			short token = sc.nextShort();
			places[i].setMark(token);
		}
		sc.next(pat);
		
		sc.next("numTransitions");
		final int numTransitions = sc.nextInt();
		sc.next(pat);
		
		sc.next("transitionNames");
		transitions = new ITransition[numTransitions];
		transitionIds = new int[numTransitions];
		for (int i = 0; i < numTransitions; i++) {
			String name = sc.next();
			transitionIds[i] = nextTransitionId++;
			transitions[i] = new Transition(transitionIds[i], null, null);
			transitions[i].setName(name);
			
		}
		sc.next(pat);
		
		sc.next("pre");
		preMatrix = new int[numPlaces][numTransitions];
		for (int r = 0; r < numPlaces; r++) {
			for (int c = 0; c < numTransitions; c++) {
				preMatrix[r][c] = sc.nextShort();
			}
		}
		sc.next(pat);
		
		sc.next("post");
		postMatrix = new int[numPlaces][numTransitions];
		for (int r = 0; r < numPlaces; r++) {
			for (int c = 0; c < numTransitions; c++) {
				postMatrix[r][c] = sc.nextShort();
			}
		}
		sc.next(pat);
			
	}
	
	@Override
	public IPlace createPlace(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deletePlaceById(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ITransition createTransition(String name, IRenew rnw) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ITransition createTransition(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteTransitionByID(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IArc createArc(String name, INode start, INode end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteArcByID(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<ITransition> getActivatedTransitions() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<INode> fire(int id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<INode> fire() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IPre getPre() {
		return pre;
	}

	@Override
	public IPost getPost() {
		return post;
	}

	@Override
	public IPlace getPlaceById(int id) {
		for (int i = 0; i < placeIds.length; i++) {
			if (placeIds[i] == id) {
				return places[i];
			}
		}
		return null;
	}

	@Override
	public ITransition getTransitionById(int id) {
		for (int i = 0; i < transitionIds.length; i++) {
			if (transitionIds[i] == id) {
				return transitions[i];
			}
		}
		return null;
	}

	@Override
	public int getId() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<IPlace> getAllPlaces() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<ITransition> getAllTransitions() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<IArc> getAllArcs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IGraphElement getAllGraphElement() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addPetrinetListener(IPetrinetListener l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removePetrinetListener(IPetrinetListener l) {
		throw new UnsupportedOperationException();
	}

}
