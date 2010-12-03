package engine.impl.mock;

import petrinetze.INode;

public class Node implements INode {

	private static int counter = 0;

	private int id;

	public Node() {
		id = counter++;
	}

	private String name = null;

	public String getName() {
		return name != null ? name : getClass().getSimpleName()+"_"+id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}
}
