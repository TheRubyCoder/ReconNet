package petrinetze.impl;

import java.util.Arrays;

import petrinetze.IPre;

class Pre implements IPre {
	
	private int[][] pre;
	private int[] tIds;
	private int[] pIds;
	
	Pre(int[][] pre, int[] pId, int[] tId) {
		this.pIds = pId;
		this.tIds = tId;
		this.pre = pre;
	}
	
	@Override
	public int[][] getPreAsArray() {
		return pre;
	}

	@Override
	public int[] getTransitionIds() {
		return tIds;
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
		final String nl = System.getProperty("line.separator", "\n");
        StringBuilder sb = new StringBuilder("Pre [pre=").append(nl);
		
		for (int i = 0; i < pIds.length; i++) {
			for (int z = 0; z < tIds.length; z++) {
				sb.append(" [").append(pre[i][z]).append(']');
			}
			sb.append(nl);
		}
		

	    return sb.append("tIds=").append(Arrays.toString(tIds))
                 .append(", pIds=").append(Arrays.toString(pIds))
                 .append(']').toString();
    }
}
