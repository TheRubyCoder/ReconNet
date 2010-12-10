package transformation;

import petrinetze.IPetrinet;

public class PetrinetWrapper implements PetriNet {

	private final IPetrinet petrinet;
	
	private final Matrix pre;
	private final Matrix post;
	
	
	public PetrinetWrapper(IPetrinet petrinet) {
		this.petrinet = petrinet;
		pre = new MatrixImpl(petrinet.getPre().getPreAsArray());
		post = new MatrixImpl(petrinet.getPost().getPostAsArray());
	}
	
	
	public IPetrinet getIPetrinet() {
		return petrinet;
	}
	
	
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
		return post.getNumCols();
	}

	@Override
	public String getPlaceNameByIndex(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransitionNameByIndex(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getTokenByIndex(int index) {
		// TODO Auto-generated method stub
		return 0;
	}

}
