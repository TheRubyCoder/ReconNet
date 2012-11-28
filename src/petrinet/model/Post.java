package petrinet.model;

import java.util.Arrays;

/**
 * Das Objekt bildet das Post-State eines Petrinetzes.
 * Und Liefert entsprechende Methoden.
 * 
 */
public class Post{

	private int[][] post;

	private int[] tIds;

	private int[] pIds;

	Post(int[][] post, int[] pId, int[] tId) {
		this.pIds = pId;
		this.tIds = tId;
		this.post = post;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Post))
			return false;
		Post other = (Post) obj;
		return Arrays.deepEquals(getPostAsArray(), other.getPostAsArray());
	}

	/**
	 * Die Identifier der Transitionen, die in dem Pre Matrix vorhanden sind.
	 * 
	 * @return Array mit den Ids.
	 */
	public int[] getTransitionIds() {
		return this.tIds;
	}

	/**
	 * Die Identifier der Places, die in dem Pre Matrix vorhanden sind.
	 * 
	 * @return Array mit den Ids.
	 */
	public int[] getPlaceIds() {
		return this.pIds;
	}

	/**
	 * Returns only the part of toString() that represents the matrix
	 * 
	 * @return Matrix like looking String
	 */
	public String matrixStringOnly() {
		return toString().split("=")[1].split("tIds")[0];
	}

	/*
	 * (non-Javadoc)
	 * 
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
				.append(", pIds=").append(Arrays.toString(pIds)).append(']')
				.toString();
	}

	/**
	 * Liefert das Post von dem entsprechenden Petrinetz. Das heißt, die
	 * Kantengewichte der jeweiligen Transitionen und Stellen.
	 * 
	 * 0 1 3 6 1 1 3 2 2 2 5 3 3 1 3 1
	 * 
	 * @return Senkrecht: Stellen Waagerecht: Transitionen
	 */
	public int[][] getPostAsArray() {
		return this.post;
	}
}
