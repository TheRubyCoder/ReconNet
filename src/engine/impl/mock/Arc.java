package engine.impl.mock;

import petrinetze.IArc;
import petrinetze.INode;

public class Arc extends Node implements IArc{

	private INode start, end;
	private int mark;

	public Arc() {
		this(null,null,1);
	}

	public Arc(INode s, INode e, int mark) {
		start = s; end = e; this.mark = mark;
	}

	public INode getStart() {
		return start;
	}

	public void setStart(INode start) {
		this.start = start;
	}

	public INode getEnd() {
		return end;
	}

	public void setEnd(INode end) {
		this.end = end;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}




}
