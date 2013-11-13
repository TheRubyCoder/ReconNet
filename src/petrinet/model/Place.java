/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package petrinet.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

/**
 * This class represents a place within a petrinet, holding information about
 * name, mark and arcs
 * 
 * @author Reiter, Safai
 * @version 1.0
 */

public class Place implements INode {
	private String name;
	/**
	 * Capacity of the node
	 */
	private int mark;
	/**
	 * Unique id
	 */
	private final int id;
	/**
	 * bijective Map of incoming arcs
	 */
	private BidiMap<Integer, PostArc> incomingArcs;
	/**
	 * bijective Map of outgoing arcs
	 */
	private BidiMap<Integer, PreArc> outgoingArcs;

	/**
	 * Creates a new {@link Place} without name or arcs
	 * @param id
	 */
	public Place(int id) {
		this.id           = id;
		this.incomingArcs = new DualHashBidiMap<Integer, PostArc>();
		this.outgoingArcs = new DualHashBidiMap<Integer, PreArc>();
	}

	public void addIncomingArc(PostArc arc) {
		this.incomingArcs.put(arc.getId(), arc);
	}

	public void addOutgoingArc(PreArc arc) {
		this.outgoingArcs.put(arc.getId(), arc);
	}

	public Set<PostArc> getIncomingArcs() {
		return incomingArcs.values();
	}

	public Set<PreArc> getOutgoingArcs() {
		return outgoingArcs.values();
	}

	
	public boolean hasIncomingArc(Transition source) {
		for (PostArc arc : this.incomingArcs.values()) {
			if (arc.getTransition().equals(source)) {
				return true;
			}
		}
		
		return false;
	}
	
	public PostArc getIncomingArc(Transition source) {
		for (PostArc arc : this.incomingArcs.values()) {
			if (arc.getTransition().equals(source)) {
				return arc;
			}
		}
		
		return null;
	}
	
	public boolean hasOutgoingArc(Transition target) {
		for (PreArc arc : this.outgoingArcs.values()) {
			if (arc.getTransition().equals(target)) {
				return true;
			}
		}
		
		return false;
	}
	
	public PreArc getOutgoingArc(Transition target) {
		for (PreArc arc : this.outgoingArcs.values()) {
			if (arc.getTransition().equals(target)) {
				return arc;
			}
		}
		
		return null;
	}
	
	
	/**
	 * Removes a single arc from the outgoing arcs
	 * @param arc
	 * @return <code>false</code> if arc was not in the outgoing arcs
	 */
	public boolean removeOutgoingArc(PreArc arc) {
		return outgoingArcs.remove(arc.getId()) != null;
	}

	/**
	 * Removes a single arc from the incoming arcs
	 * @param arc
	 * @return <code>false</code> if arc was not in the incoming arcs
	 */
	public boolean removeIncomingArc(PostArc arc) {
		return incomingArcs.remove(arc.getId()) != null;
	}

	/**
	 * Returns the {@link Transition transitions} that are at the other end
	 * of incoming arcs
	 * @return
	 */
	public Set<Transition> getIncomingTransitions() {
		Set<Transition> in = new HashSet<Transition>();
		
		for (PostArc arc : incomingArcs.values()) {
			in.add(arc.getTransition());
		}
		
		return in;
	}

	/**
	 * Returns the {@link Transition transitions} that are at the other end
	 * of outgoing arcs
	 * @return
	 */
	public Set<Transition> getOutgoingTransitions() {
		Set<Transition> out = new HashSet<Transition>();
		
		for (PreArc arc : outgoingArcs.values()) {
			out.add(arc.getTransition());
		}
		
		return out;
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#getName()
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#getId()
	 */
	public int getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#getMark()
	 */
	public int getMark() {
		return mark;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see haw.wp.rcpn.Place#setMark(int)
	 */
	public void setMark(int mark) {
		if (mark < 0) {
			throw new IllegalArgumentException("mark is negative");
		}
		
		this.mark = mark;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Place [name=" + name + ", capacity=" + capacity + ", mark=" + mark + ", id=" + id + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		
		if (!(object instanceof Place)) {
			return false;
		}
		
		Place place = (Place) object;
		
		return id == place.getId();
	}
}
