package petrinetze.impl;

import petrinetze.IPre;

public class Pre implements IPre {
	
	private int [][] pre;
	private int []tIds;
	private int []pIds;
	
	public Pre (int m, int n) {
		pre = new int[m][n];
	}
	
	@Override
	public int[][] getPreAsArray() {
		return this.pre;
	}

	@Override
	public int[] getTransitionIds() {
		return this.tIds;
	}

	@Override
	public int[] getPlaceIds() {
		return null;
	}

}
