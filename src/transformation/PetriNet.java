package transformation;


import transformation.tempPetriNet.Matrix;

public interface PetriNet {

	public Matrix getPre();
	
	public Matrix getPost();
	
	public int getNumPlaces();
	
	public int getNumTransitions();
	
	public String getPlaceNameByIndex(int index);

	public String getTransitionNameByIndex(int index);

	public short getTokenByIndex(int index);
	
	
	
	
	//	public int getNumEdges();
//	
//
//	/**
//	 * @return       int
//	 * @param        label
//	 */
//	public int addPlace( String label );
//
//
//	/**
//	 * @param        placeId
//	 */
//	public void removePlace( int placeId );
//
//
//	/**
//	 * @return       int
//	 * @param        label
//	 */
//	public int addTransition( String label );
//
//
//	/**
//	 * @param        transitionId
//	 */
//	public void removeTransition( int transitionId );
//
//
//	/**
//	 * @return       String
//	 * @param        placeId
//	 */
//
//
//	/**
//	 * @param        placeId
//	 * @param        label
//	 */
//	public void setPlaceName( int placeId, String name );
//
//
//	/**
//	 * @return       String
//	 * @param        transitionId
//	 */
//
//
//	/**
//	 * @param        transitionId
//	 * @param        label
//	 */
//	public void setTransitionName( int transitionId, String name );
//
//	
//
//	/**
//	 * @return       short
//	 * @param        transitionId
//	 * @param        placeId
//	 */
//	public short getPreEdge( int transitionId, int placeId );
//
//
//	/**
//	 * @param        transitionId
//	 * @param        placeId
//	 * @param        weight
//	 */
//	public void setPreEdge( int transitionId, int placeId, short weight );
//
//
//	/**
//	 * @return       short
//	 * @param        transitionId
//	 * @param        placeId
//	 */
//	public short getPostEdge( int transitionId, int placeId );
//
//
//	/**
//	 * @param        transitionId
//	 * @param        placeId
//	 * @param        weight
//	 */
//	public void setPostEdge( int transitionId, int placeId, short weight );


}
