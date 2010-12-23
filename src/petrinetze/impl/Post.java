package petrinetze.impl;

import java.util.Arrays;

import petrinetze.IPost;

public class Post implements IPost {

	private int [][] post;
	private int []tIds;
	private int []pIds;
	
	public Post (int[][] post, int[] pId, int[] tId) {
		this.pIds = pId;
		this.tIds = tId;
		this.post = post;
		
		
	}
	
	@Override
	public int[][] getPreAsArray() {
		return this.post;
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
					output += " [" + post[i][z] + "]";
				}
				output += "\n";
			}
			

		return "Post [post=" + output + ", tIds="
				+ Arrays.toString(tIds) + ", pIds=" + Arrays.toString(pIds)
				+ "]";
	}

}
