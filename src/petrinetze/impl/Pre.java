package petrinetze.impl;

import petrinetze.IPre;

public class Pre implements IPre {

	private int [][] pre;
	
	public Pre (int m, int n) {
		pre = new int[m][n];
	}
	
	@Override
	public int[][] getPreAsArray() {
		return null;
	}

}
