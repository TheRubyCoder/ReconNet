package petrinetze;

import java.util.Arrays;


class Post implements IPost {

	private int[][] post;

    private int[] tIds;

    private int[] pIds;
	
	Post (int[][] post, int[] pId, int[] tId) {
		this.pIds = pId;
		this.tIds = tId;
		this.post = post;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		if(!(obj instanceof IPost))
			return false;
		IPost other = (IPost) obj;
		return Arrays.deepEquals(getPostAsArray(),other.getPostAsArray());
	}
	
	@Override
	public int[] getTransitionIds() {
		return this.tIds;
	}

	@Override
	public int[] getPlaceIds() {
		return this.pIds;
	}
	
	@Override
	public String matrixStringOnly(){
		return toString().split("=")[1].split("tIds")[0];
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        final String nl = System.getProperty("line.separator", "\n");
        StringBuilder sb = new StringBuilder("Post [post=").append(nl);

        for (int i = 0; i < pIds.length; i++) {
            for (int z = 0; z < tIds.length; z++) {
                sb.append(" [").append(post[i][z]).append(']');
            }
            sb.append(nl);
        }

		return sb.append(", tIds=").append(Arrays.toString(tIds))
                 .append(", pIds=").append(Arrays.toString(pIds))
                .append(']').toString();
	}

	@Override
	public int[][] getPostAsArray() {
		return this.post;
	}
}
