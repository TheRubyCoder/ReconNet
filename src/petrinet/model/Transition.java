/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veraenderung, sind
 * unter den folgenden Bedingungen zulaessig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare muessen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare muessen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden duerfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFUEGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FUER
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FUER IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFAELLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHAEDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGUETERN ODER -DIENSTLEISTUNGEN; EINSCHRAENKUNG DER NUTZUNGSFAEHIGKEIT;
 * VERLUST VON NUTZUNGSFAEHIGKEIT; DATEN; PROFIT ODER GESCHAEFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLAESSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
 * BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE
 * MOEGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
 * and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. Neither the name of the
 * University nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written
 * permission. THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS
 * AS IS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FUER ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package petrinet.model;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

/**
 * This class represents a Transition within a {@link Petrinet}, holding
 * information about name, renew and label
 */
public class Transition
  implements INode {

  /**
   * Id of this {@link Transition}
   */
  private final int id;

  /**
   * Name of this {@link Transition}
   */
  private String name;

  /**
   * {@link IRenew Renew} of this {@link Transition}
   */
  private IRenew rnw;

  /**
   * Label of this {@link Transition}
   */
  private String tlb = "";

  /**
   * bijective Map of outgoing arcs
   */
  private BidiMap<Integer, PostArc> outgoingArcs;

  /**
   * bijective Map of incoming arcs
   */
  private BidiMap<Integer, PreArc> incomingArcs;

  /**
   * Creates a new {@link Transition} with no name and no arcs
   * 
   * @param id
   *        Id of the {@link Transition}
   * @param rnw
   *        Renew of the {@link Transition}
   */
  public Transition(int id, IRenew rnw) {

    this.id = id;
    this.rnw = rnw;
    this.incomingArcs = new DualHashBidiMap<Integer, PreArc>();
    this.outgoingArcs = new DualHashBidiMap<Integer, PostArc>();
  }

  public void addIncomingArc(PreArc arc) {

    this.incomingArcs.put(arc.getId(), arc);
  }

  public void addOutgoingArc(PostArc arc) {

    this.outgoingArcs.put(arc.getId(), arc);
  }

  public boolean hasIncomingArc(Place source) {

    for (PreArc arc : this.incomingArcs.values()) {
      if (arc.getPlace().equals(source)) {
        return true;
      }
    }

    return false;
  }

  public PreArc getIncomingArc(Place source) {

    for (PreArc arc : this.incomingArcs.values()) {
      if (arc.getPlace().equals(source)) {
        return arc;
      }
    }

    return null;
  }

  public boolean hasOutgoingArc(Place target) {

    for (PostArc arc : this.outgoingArcs.values()) {
      if (arc.getPlace().equals(target)) {
        return true;
      }
    }

    return false;
  }

  public PostArc getOutgoingArc(Place target) {

    for (PostArc arc : this.outgoingArcs.values()) {
      if (arc.getPlace().equals(target)) {
        return arc;
      }
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * @see haw.wp.rcpn.Transition#getName()
   */
  @Override
  public String getName() {

    return this.name;
  }

  /*
   * (non-Javadoc)
   * @see haw.wp.rcpn.Transition#getId()
   */
  @Override
  public int getId() {

    return this.id;
  }

  /*
   * (non-Javadoc)
   * @see haw.wp.rcpn.Transition#setName(java.lang.String)
   */
  @Override
  public void setName(String name) {

    this.name = name;
  }

  public String getTlb() {

    return tlb;
  }

  /**
   * Sets the label of this {@link Transition}
   * 
   * @param tlb
   *        New Label
   * @throws IllegalArgumentException
   *         if Label is not valid for current {@link IRenew renew}
   * @see Transition#setRnw(IRenew)
   */
  public void setTlb(String tlb) {

    if (!rnw.isTlbValid(tlb)) {
      throw new IllegalArgumentException("Invalid tlb: " + tlb + " for rnw "
        + rnw);
    }

    this.tlb = tlb;
  }

  public String rnw() {

    this.tlb = rnw.renew(this.tlb);
    return tlb;
  }

  /**
   * Sets the new renew without checking of the new renew fits to the label
   * 
   * @param renew
   *        The new Renew
   * @see Transition#setTlb(String)
   */
  public void setRnw(IRenew renew) {

    this.rnw = renew;
  }

  public IRenew getRnw() {

    return this.rnw;
  }

  /**
   * Returns the {@link Place places} that are at the other end of outgoing
   * {@link IArc arcs}
   * 
   * @return Empty set if there are no outgoing arcs
   */
  public Set<Place> getOutgoingPlaces() {

    Set<Place> out = new HashSet<Place>();

    for (PostArc arc : outgoingArcs.values()) {
      out.add(arc.getTarget());
    }

    return out;
  }

  /**
   * Returns the {@link Place places} that are at the other end of incoming
   * {@link IArc arcs}
   * 
   * @return Empty set if there are no incoming arcs
   */
  public Set<Place> getIncomingPlaces() {

    Set<Place> in = new HashSet<Place>();

    for (PreArc arc : incomingArcs.values()) {
      in.add(arc.getSource());
    }

    return in;
  }

  /**
   * @precondition getOutgoingPlaces()
   */
  public Hashtable<Integer, Integer> getPre() {

    final Hashtable<Integer, Integer> pre = new Hashtable<Integer, Integer>();

    for (PreArc arc : incomingArcs.values()) {
      Place place = arc.getSource();
      pre.put(Integer.valueOf(place.getId()), arc.getWeight());
    }

    return pre;
  }

  /**
   * @precondition getIncomingPlaces()
   */
  public Hashtable<Integer, Integer> getPost() {

    final Hashtable<Integer, Integer> post =
      new Hashtable<Integer, Integer>();

    for (PostArc arc : outgoingArcs.values()) {
      Place place = arc.getTarget();
      post.put(Integer.valueOf(place.getId()), arc.getWeight());
    }

    return post;
  }

  public Set<PostArc> getOutgoingArcs() {

    return outgoingArcs.values();
  }

  public Set<PreArc> getIncomingArcs() {

    return incomingArcs.values();
  }

  boolean removeOutgoingArc(PostArc arc) {

    return outgoingArcs.remove(arc.getId()) != null;
  }

  boolean removeIncomingArc(PreArc arc) {

    return incomingArcs.remove(arc.getId()) != null;
  }

  /**
   * Checks whether this {@link Transition} is active. Incoming arcs must have
   * places with enough marks to pay. Outgoing arcs must have places with a
   * capacity thats big enough.
   * 
   * @return
   */
  public boolean isActivated() {

    for (PreArc arc : incomingArcs.values()) {
      if (arc.getPlace().getMark() < arc.getWeight()) {
        return false;
      }
    }
    for (PostArc arc : outgoingArcs.values()) {
      int minCapacity = arc.getWeight() + arc.getPlace().getMark();
      if (arc.getPlace().getCapacity() < minCapacity) {
        return false;
      }
    }

    return true;
  }

  /**
   * Fires a {@link Transition} with the <code>id</code>
   * 
   * @return Changed places
   */
  public Set<Place> fire() {

    if (!this.isActivated()) {
      throw new IllegalArgumentException();
    }

    // all changed nodes
    Set<Place> changedNodes = new HashSet<Place>();

    // set tokens
    for (PreArc arc : incomingArcs.values()) {
      arc.getPlace().setMark(arc.getPlace().getMark() - arc.getWeight());

      changedNodes.add(arc.getPlace());
    }

    for (PostArc arc : outgoingArcs.values()) {
      arc.getPlace().setMark(arc.getPlace().getMark() + arc.getWeight());

      changedNodes.add(arc.getPlace());
    }

    // Renew
    this.rnw();

    return changedNodes;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return "Transition [Label=" + tlb + ", id=" + id + ", name=" + name + "]";
  }

  /*
   * (non-Javadoc)
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
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {

    if (this == object) {
      return true;
    }

    if (!(object instanceof Transition)) {
      return false;
    }

    Transition transition = (Transition) object;

    return id == transition.getId();
  }
}
