package petrinetze.impl;

import java.util.Arrays;

import petrinetze.IPre;

public class Pre implements IPre {
	
	private int [][] pre;
	private int []tIds;
	private int []pIds;
	
	public Pre (int[][] pre, int[] pId, int[] tId) {
		this.pIds = pId;
		this.tIds = tId;
		this.pre = pre;
		
		
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
		return this.pIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String output = "\n";
		
		for (int i = 0; i < pIds.length; i++) {
			for (int z = 0; z < tIds.length; z++) {
				output += " [" + pre[i][z] + "]";
			}
			output += "\n";
		}
		

	return "Pre [pre=" + output + ", tIds="
			+ Arrays.toString(tIds) + ", pIds=" + Arrays.toString(pIds)
			+ "]";
}
}
