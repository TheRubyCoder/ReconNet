package engine.net.impl;

import engine.net.Net;

public class NetFactory {
	
	private static final NetFactory instance = new NetFactory();
	
	public static NetFactory getInstance() {
		return instance;
	}

	private NetFactory() {}
	
	public Net createNet() {
		//TODO
		return null;
	}
	
	
}
