package engine.net.impl;

import engine.net.NetPart;

abstract class NetPartImpl implements NetPart {
	
	private static int nextId = 0; 
	
	private static synchronized int getNextId() {
		return nextId++;
	}
	
	private String label;
	private final int id;
	
	protected NetPartImpl(String label) {
		this.label = label;	
		id = getNextId();
	}

	public String getLabel() {		
		return label;
	}

	public int getId() {		
		return id;
	}
	
	@Override
	public String toString() {
		return "["+this.getClass().getSimpleName()+" id: "+getId()+", label: "+getLabel()+"]";
	}
}
