/*
 * BSD-Lizenz Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind
 * unter den folgenden Bedingungen zulässig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT;
 * VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
 * BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE
 * MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
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
 * “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package transformation;

import static transformation.dependency.PetrinetAdapter.createPetrinet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

import exceptions.AccessForbiddenException;
import exceptions.ShowAsWarningException;
import petrinet.model.IRenew;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
// import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// TODO In den Javadoc Kommentaren der Methoden die NACs beruecksichtigen.

/**
 * An Interface for Rules<br\>
 * Rules define how a petrinet can be reconfigured<br\>
 * Where L must be found in the petrinet, K is the context of all involved
 * nodes and R is the resulting part-graph
 */

public class Rule {

  public enum Net {
    L, K, R, NAC
  }

  /** K part of the rule */
  private final Petrinet k;
  /** L part of the rule */
  private final Petrinet l;
  /** R part of the rule */
  private final Petrinet r;

  /** NACs that belong to the rule */
  private HashMap<UUID, NAC> nacs;

  private final BidiMap<Place, Place> placeMappingKToL;
  private final BidiMap<PostArc, PostArc> postArcMappingKToL;
  private final BidiMap<PreArc, PreArc> preArcMappingKToL;
  private final BidiMap<Transition, Transition> transitionMappingKToL;

  private final BidiMap<Place, Place> placeMappingKToR;
  private final BidiMap<PostArc, PostArc> postArcMappingKToR;
  private final BidiMap<PreArc, PreArc> preArcMappingKToR;
  private final BidiMap<Transition, Transition> transitionMappingKToR;

  /**
   * Creates an empty rule
   */
  public Rule() {

    k = createPetrinet();
    l = createPetrinet();
    r = createPetrinet();

    nacs = new HashMap<UUID, NAC>();

    placeMappingKToL = new DualHashBidiMap<Place, Place>();
    postArcMappingKToL = new DualHashBidiMap<PostArc, PostArc>();
    preArcMappingKToL = new DualHashBidiMap<PreArc, PreArc>();
    transitionMappingKToL = new DualHashBidiMap<Transition, Transition>();

    placeMappingKToR = new DualHashBidiMap<Place, Place>();
    postArcMappingKToR = new DualHashBidiMap<PostArc, PostArc>();
    preArcMappingKToR = new DualHashBidiMap<PreArc, PreArc>();
    transitionMappingKToR = new DualHashBidiMap<Transition, Transition>();
  }

  /**
   * Creates a new minimal NAC in the rule's set of NACs
   *
   * @return The new NAC
   */
  public NAC createNAC() {

    System.out.println(Rule.class + " - createNAC");

    NAC newNAC = new NAC(l);
    nacs.put(newNAC.getId(), newNAC);

    return newNAC;
  }

  public NAC createPlainNAC() {

    NAC nac = new NAC();

    this.nacs.put(nac.getId(), nac);

    return nac;
  }

  public void deleteNac(UUID nacId) {

    this.nacs.remove(nacId);
  }

  /**
   * Returns the gluing Petrinet of this rule.
   *
   * @return the gluing Petrinet of this rule.
   */
  public Petrinet getK() {

    return k;
  }

  /**
   * Returns the left Petrinet of this rule.
   *
   * @return the left Petrinet of this rule.
   */
  public Petrinet getL() {

    return l;
  }

  /**
   * Returns the right Petrinet of this rule.
   *
   * @return the right Petrinet of this rule.
   */
  public Petrinet getR() {

    return r;
  }

  /**
   * Returns an unmodifiable set of NACs.
   * <p>
   * Only for reading purposes. NAC manipulation is available via the
   * according Rule.
   *
   * @return Set of this rule's NACs.
   */
  public Set<NAC> getNACs() {

    HashSet<NAC> nacSet = new HashSet<NAC>(nacs.values());
    return Collections.unmodifiableSet(nacSet);
  }

  /**
   * Returns the postArcs (in R) that will be added on applying the rule.
   */
  public Set<PostArc> getPostArcsToAdd() {

    Set<PostArc> postArcs = new HashSet<PostArc>();

    for (Map.Entry<PostArc, PostArc> entry : postArcMappingKToR.entrySet()) {
      if (!postArcMappingKToL.containsKey(entry.getKey())) {
        postArcs.add(entry.getValue());
      }
    }

    return postArcs;
  }

  /**
   * Returns the postArcs (in L) that will be deleted on applying the rule.
   */
  public Set<PostArc> getPostArcsToDelete() {

    Set<PostArc> postArcs = new HashSet<PostArc>();

    for (Map.Entry<PostArc, PostArc> entry : postArcMappingKToL.entrySet()) {
      if (!postArcMappingKToR.containsKey(entry.getKey())) {
        postArcs.add(entry.getValue());
      }
    }

    return postArcs;
  }

  /**
   * Returns the preArcs (in R) that will be added on applying the rule.
   */
  public Set<PreArc> getPreArcsToAdd() {

    Set<PreArc> preArcs = new HashSet<PreArc>();

    for (Map.Entry<PreArc, PreArc> entry : preArcMappingKToR.entrySet()) {
      if (!preArcMappingKToL.containsKey(entry.getKey())) {
        preArcs.add(entry.getValue());
      }
    }

    return preArcs;
  }

  /**
   * Returns the preArcs (in L) that will be deleted on applying the rule.
   */
  public Set<PreArc> getPreArcsToDelete() {

    Set<PreArc> preArcs = new HashSet<PreArc>();

    for (Map.Entry<PreArc, PreArc> entry : preArcMappingKToL.entrySet()) {
      if (!preArcMappingKToR.containsKey(entry.getKey())) {
        preArcs.add(entry.getValue());
      }
    }

    return preArcs;
  }

  /**
   * Returns the places (in R) that will be added on applying the rule.
   */
  public Set<Place> getPlacesToAdd() {

    Set<Place> places = new HashSet<Place>();

    for (Map.Entry<Place, Place> entry : placeMappingKToR.entrySet()) {
      if (!placeMappingKToL.containsKey(entry.getKey())) {
        places.add(entry.getValue());
      }
    }

    return places;
  }

  /**
   * Returns the places (in L) that will be deleted on applying the rule.
   */
  public Set<Place> getPlacesToDelete() {

    Set<Place> places = new HashSet<Place>();

    for (Map.Entry<Place, Place> entry : placeMappingKToL.entrySet()) {
      if (!placeMappingKToR.containsKey(entry.getKey())) {
        places.add(entry.getValue());
      }
    }

    return places;
  }

  /**
   * Returns the transitions (in R) that will be added on applying the rule.
   */
  public Set<Transition> getTransitionsToAdd() {

    Set<Transition> transtions = new HashSet<Transition>();
    Set<Map.Entry<Transition, Transition>> entries =
      transitionMappingKToR.entrySet();

    for (Map.Entry<Transition, Transition> entry : entries) {
      if (!transitionMappingKToL.containsKey(entry.getKey())) {
        transtions.add(entry.getValue());
      }
    }

    return transtions;
  }

  /**
   * Returns the transitions (in L) that will be deleted on applying the rule.
   */
  public Set<Transition> getTransitionsToDelete() {

    Set<Transition> transtions = new HashSet<Transition>();
    Set<Map.Entry<Transition, Transition>> entries =
      transitionMappingKToL.entrySet();

    for (Map.Entry<Transition, Transition> entry : entries) {
      if (!transitionMappingKToR.containsKey(entry.getKey())) {
        transtions.add(entry.getValue());
      }
    }

    return transtions;
  }

  /**
   * Sets the mark for the place and its mappings, that is only in L, K and
   * NACs if given
   *
   * @param place
   * @param mark
   */
  public void setMarkInL(Place place, int mark) {

    place.setMark(mark);
    fromLtoK(place).setMark(mark);

    Place rightPlace = fromLtoR(place);

    if (rightPlace != null) {
      rightPlace.setMark(mark);
    }

    for (NAC nac : nacs.values()) {
      nac.fromLtoNac(place).setMark(mark);
    }
  }

  /**
   * Sets the mark for the place and its mappings, thats only in K and R and
   * NACs if given
   *
   * @param place
   * @param mark
   */
  public void setMarkInK(Place place, int mark) {

    Place leftPlace = fromKtoL(place);

    if (leftPlace != null) {
      leftPlace.setMark(mark);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPlace).setMark(mark);
      }
    }

    place.setMark(mark);

    if (fromKtoR(place) != null) {
      fromKtoR(place).setMark(mark);
    }
  }

  /**
   * Sets the mark for the place and its mappings, thats in L, K and R
   *
   * @param place
   * @param mark
   */
  public void setMarkInR(Place place, int mark) {

    fromRtoK(place).setMark(mark);
    place.setMark(mark);

    Place leftPlace = fromRtoL(place);

    if (leftPlace != null) {
      leftPlace.setMark(mark);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPlace).setMark(mark);
      }
    }
  }

  /**
   * @param place
   * @param mark
   * @param nac
   */
  public void setMarkInNac(Place place, int mark, NAC nac) {

    checkIfcontained(nac);

    if (nac.fromNacToL(place) != null) {
      throw new ShowAsWarningException(new AccessForbiddenException(
        "This operation is not allowed "
          + "for Elements that have a representation in L"));
    } else {
      place.setMark(mark);
    }
  }

  /**
   * Sets the capacity for the place and its mappings, that is only in L and K
   *
   * @param place
   * @param capacity
   */
  public void setCapacityInL(Place place, int capacity) {

    place.setCapacity(capacity);
    fromLtoK(place).setCapacity(capacity);

    Place rightPlace = fromLtoR(place);

    if (rightPlace != null) {
      rightPlace.setCapacity(capacity);
    }

    for (NAC nac : nacs.values()) {
      nac.fromLtoNac(place).setCapacity(capacity);
    }
  }

  /**
   * Sets the capacity for the place and its mappings, thats only in K and R
   *
   * @param place
   * @param capacity
   */
  public void setCapacityInK(Place place, int capacity) {

    Place leftPlace = fromKtoL(place);

    if (leftPlace != null) {
      fromKtoL(place).setCapacity(capacity);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPlace).setCapacity(capacity);
      }
    }

    place.setCapacity(capacity);

    if (fromKtoR(place) != null) {
      fromKtoR(place).setCapacity(capacity);
    }
  }

  /**
   * Sets the capacity for the place and its mappings, thats in L, K and R
   *
   * @param place
   * @param capacity
   */
  public void setCapacityInR(Place place, int capacity) {

    fromRtoK(place).setCapacity(capacity);
    place.setCapacity(capacity);

    Place leftPlace = fromRtoL(place);

    if (leftPlace != null) {
      leftPlace.setCapacity(capacity);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPlace).setCapacity(capacity);
      }
    }
  }

  /**
   * Sets the capacity for the place and its mappings, that is only in L and
   * K, Nac
   *
   * @param place
   * @param capacity
   */
  public void setCapacityInNac(Place place, int capacity, NAC nac) {

    checkIfcontained(nac);

    if (nac.fromNacToL(place) != null) {
      throw new ShowAsWarningException(new AccessForbiddenException(
        "This operation is not allowed "
          + "for Elements that have a representation in L"));
    } else {
      place.setCapacity(capacity);
    }

    // TODO: CHO - This method was not implemented by blumreiter for some
    // reason. Maybe it is not intended to set the capacity of a place in a
    // nac
    //
    // throw new UnsupportedOperationException();

  }

  /**
   * Sets the name of a place in a rule and modifies other parts of the rule
   * accordingly
   *
   * @param place
   * @param name
   */
  public void setNameInL(Place place, String name) {

    place.setName(name);
    fromLtoK(place).setName(name);

    Place rightPlace = fromLtoR(place);

    if (rightPlace != null) {
      rightPlace.setName(name);
    }

    for (NAC nac : nacs.values()) {
      nac.fromLtoNac(place).setName(name);
    }
  }

  /**
   * Sets the name of a place in a rule and modifies other parts of the rule
   * accordingly
   *
   * @param place
   * @param name
   */
  public void setNameInK(Place place, String name) {

    Place leftPlace = fromKtoL(place);
    if (leftPlace != null) {
      fromKtoL(place).setName(name);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPlace).setName(name);
      }
    }

    place.setName(name);

    if (fromKtoR(place) != null) {
      fromKtoR(place).setName(name);
    }
  }

  /**
   * Sets the name of a place in a rule and modifies other parts of the rule
   * accordingly
   *
   * @param place
   * @param name
   */
  public void setNameInR(Place place, String name) {

    fromRtoK(place).setName(name);
    place.setName(name);

    Place leftPlace = fromRtoL(place);

    if (leftPlace != null) {
      leftPlace.setName(name);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPlace).setName(name);
      }
    }
  }

  /**
   * Sets the name of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param name
   */
  public void setNameInL(Transition transition, String name) {

    transition.setName(name);
    fromLtoK(transition).setName(name);

    Transition rightTransition = fromLtoR(transition);

    if (rightTransition != null) {
      rightTransition.setName(name);
    }

    for (NAC nac : nacs.values()) {
      nac.fromLtoNac(transition).setName(name);
    }
  }

  /**
   * Sets the name of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param name
   */
  public void setNameInK(Transition transition, String name) {

    Transition leftTrans = fromKtoL(transition);

    if (leftTrans != null) {
      fromKtoL(transition).setName(name);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftTrans).setName(name);
      }
    }

    transition.setName(name);

    if (fromKtoR(transition) != null) {
      fromKtoR(transition).setName(name);
    }
  }

  /**
   * Sets the name of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param name
   */
  public void setNameInR(Transition transition, String name) {

    fromRtoK(transition).setName(name);
    transition.setName(name);

    Transition leftTransition = fromRtoL(transition);

    if (leftTransition != null) {
      leftTransition.setName(name);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftTransition).setName(name);
      }
    }
  }

  public void setNameInNac(Transition transition, String name, NAC nac) {

    checkIfcontained(nac);

    if (nac.fromNacToL(transition) != null) {
      throw new ShowAsWarningException(new AccessForbiddenException(
        "This operation is not allowed "
          + "for Elements that have a representation in L"));
    } else {
      transition.setName(name);
    }
  }

  /**
   * Sets the tlb of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param tlb
   */
  public void setTlbInL(Transition transition, String tlb) {

    transition.setTlb(tlb);
    fromLtoK(transition).setTlb(tlb);

    Transition rightTransition = fromLtoR(transition);

    if (rightTransition != null) {
      rightTransition.setTlb(tlb);
    }

    for (NAC nac : nacs.values()) {
      nac.fromLtoNac(transition).setTlb(tlb);
    }
  }

  /**
   * Sets the tlb of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param name
   */
  public void setTlbInK(Transition transition, String tlb) {

    Transition leftTransition = fromKtoL(transition);
    if (leftTransition != null) {
      fromKtoL(transition).setTlb(tlb);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftTransition).setTlb(tlb);
      }
    }

    transition.setTlb(tlb);

    if (fromKtoR(transition) != null) {
      fromKtoR(transition).setTlb(tlb);
    }
  }

  /**
   * Sets the tlb of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param name
   */
  public void setTlbInR(Transition transition, String tlb) {

    fromRtoK(transition).setTlb(tlb);
    transition.setTlb(tlb);

    Transition leftTransition = fromRtoL(transition);

    if (leftTransition != null) {
      leftTransition.setTlb(tlb);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftTransition).setTlb(tlb);
      }
    }
  }

  public void setTlbInNac(Transition transition, String tlb, NAC nac) {

    checkIfcontained(nac);

    if (nac.fromNacToL(transition) != null) {
      throw new ShowAsWarningException(new AccessForbiddenException(
        "This operation is not allowed "
          + "for Elements that have a representation in L"));
    } else {
      transition.setTlb(tlb);
    }
  }

  /**
   * Sets the rnw of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param rnw
   */
  public void setRnwInL(Transition transition, IRenew rnw) {

    transition.setRnw(rnw);
    fromLtoK(transition).setRnw(rnw);

    Transition rightTransition = fromLtoR(transition);

    if (rightTransition != null) {
      rightTransition.setRnw(rnw);
    }

    for (NAC nac : nacs.values()) {
      nac.fromLtoNac(transition).setRnw(rnw);
    }
  }

  /**
   * Sets the rnw of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param rnw
   */
  public void setRnwInK(Transition transition, IRenew rnw) {

    Transition leftTransition = fromKtoL(transition);

    if (leftTransition != null) {
      leftTransition.setRnw(rnw);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftTransition).setRnw(rnw);
      }
    }

    transition.setRnw(rnw);

    if (fromKtoR(transition) != null) {
      fromKtoR(transition).setRnw(rnw);
    }
  }

  /**
   * Sets the rnw of a transition in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param transition
   * @param rnw
   */
  public void setRnwInR(Transition transition, IRenew rnw) {

    fromRtoK(transition).setRnw(rnw);
    transition.setRnw(rnw);

    Transition leftTransition = fromRtoL(transition);

    if (leftTransition != null) {
      leftTransition.setRnw(rnw);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftTransition).setRnw(rnw);
      }
    }
  }

  public void setRnwInNac(Transition transition, IRenew rnw, NAC nac) {

    checkIfcontained(nac);

    if (nac.fromNacToL(transition) != null) {
      throw new ShowAsWarningException(new AccessForbiddenException(
        "This operation is not allowed "
          + "for Elements that have a representation in L"));
    } else {
      transition.setRnw(rnw);
    }
  }

  /**
   * Sets the weight of a preArc in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param preArc
   * @param weight
   */
  public void setWeightInL(PreArc preArc, int weight) {

    preArc.setWeight(weight);
    fromLtoK(preArc).setWeight(weight);

    PreArc rightPreArc = fromLtoR(preArc);

    if (rightPreArc != null) {
      rightPreArc.setWeight(weight);
    }

    for (NAC nac : nacs.values()) {
      nac.fromLtoNac(preArc).setWeight(weight);
    }
  }

  /**
   * Sets the weight of a preArc in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param preArc
   * @param weight
   */
  public void setWeightInK(PreArc preArc, int weight) {

    PreArc leftPreArc = fromKtoL(preArc);
    if (leftPreArc != null) {
      leftPreArc.setWeight(weight);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPreArc).setWeight(weight);
      }
    }

    preArc.setWeight(weight);

    if (fromKtoR(preArc) != null) {
      fromKtoR(preArc).setWeight(weight);
    }
  }

  /**
   * Sets the weight of a preArc in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param preArc
   * @param weight
   */
  public void setWeightInR(PreArc preArc, int weight) {

    fromRtoK(preArc).setWeight(weight);
    preArc.setWeight(weight);

    PreArc leftPreArc = fromRtoL(preArc);

    if (leftPreArc != null) {
      leftPreArc.setWeight(weight);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPreArc).setWeight(weight);
      }
    }
  }

  public void setWeightInNac(PreArc preArc, int weight, NAC nac) {

    checkIfcontained(nac);

    if (nac.fromNacToL(preArc) != null) {
      throw new ShowAsWarningException(new AccessForbiddenException(
        "This operation is not allowed "
          + "for Elements that have a representation in L"));
    } else {
      preArc.setWeight(weight);
    }
  }

  /**
   * Sets the weight of a postArc in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param postArc
   * @param weight
   */
  public void setWeightInL(PostArc postArc, int weight) {

    postArc.setWeight(weight);
    fromLtoK(postArc).setWeight(weight);

    PostArc rightPostArc = fromLtoR(postArc);

    if (rightPostArc != null) {
      rightPostArc.setWeight(weight);
    }

    for (NAC nac : nacs.values()) {
      nac.fromLtoNac(postArc).setWeight(weight);
    }
  }

  /**
   * Sets the weight of a postArc in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param postArc
   * @param weight
   */
  public void setWeightInK(PostArc postArc, int weight) {

    PostArc leftPostArc = fromKtoL(postArc);
    if (leftPostArc != null) {
      leftPostArc.setWeight(weight);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPostArc).setWeight(weight);
      }
    }

    postArc.setWeight(weight);

    if (fromKtoR(postArc) != null) {
      fromKtoR(postArc).setWeight(weight);
    }
  }

  /**
   * Sets the weight of a postArc in a rule and modifies other parts of the
   * rule accordingly
   *
   * @param postArc
   * @param weight
   */
  public void setWeightInR(PostArc postArc, int weight) {

    fromRtoK(postArc).setWeight(weight);
    postArc.setWeight(weight);

    PostArc leftPostArc = fromRtoL(postArc);

    if (leftPostArc != null) {
      leftPostArc.setWeight(weight);
      for (NAC nac : nacs.values()) {
        nac.fromLtoNac(leftPostArc).setWeight(weight);
      }
    }
  }

  public void setWeightInNac(PostArc postArc, int weight, NAC nac) {

    checkIfcontained(nac);

    if (nac.fromNacToL(postArc) != null) {
      throw new ShowAsWarningException(new AccessForbiddenException(
        "This operation is not allowed "
          + "for Elements that have a representation in L"));
    } else {
      postArc.setWeight(weight);
    }
  }

  public Place addPlaceToL(String name) {

    Place leftPlace = getL().addPlace(name);

    placeMappingKToL.put(getK().addPlace(name), leftPlace);

    for (NAC nac : nacs.values()) {
      nac.getPlaceMappingLToNac().put(leftPlace, nac.getNac().addPlace(name));
    }

    return leftPlace;
  }

  public Place addPlaceToK(String name) {

    Place place = getK().addPlace(name);

    Place leftPlace = getL().addPlace(name);

    placeMappingKToL.put(place, leftPlace);
    placeMappingKToR.put(place, getR().addPlace(name));

    for (NAC nac : nacs.values()) {
      nac.getPlaceMappingLToNac().put(leftPlace, nac.getNac().addPlace(name));
    }

    return place;
  }

  public Place addPlaceToR(String name) {

    Place rightPlace = getR().addPlace(name);

    placeMappingKToR.put(getK().addPlace(name), rightPlace);

    return rightPlace;
  }

  public Place addPlaceToNac(String name, NAC nac) {

    checkIfcontained(nac);

    return nac.getNac().addPlace(name);
  }

  public Place addPlaceToNacWithMappingToPlaceInL(Place lPlace, UUID nacId) {

    NAC nac = nacs.get(nacId);

    Place nacPlace = nac.getNac().addPlace(lPlace.getName());
    nac.getPlaceMappingLToNac().put(lPlace, nacPlace);

    // additional attributes
    nacPlace.setCapacity(lPlace.getCapacity());
    nacPlace.setMark(lPlace.getMark());

    return nacPlace;
  }

  public Transition addTransitionToL(String name) {

    Transition leftTransition = getL().addTransition(name);

    transitionMappingKToL.put(getK().addTransition(name), leftTransition);

    for (NAC nac : nacs.values()) {
      nac.getTransitionMappingLToNac().put(leftTransition,
        nac.getNac().addTransition(name));
    }

    return leftTransition;
  }

  public Transition addTransitionToL(String name, IRenew rnw) {

    Transition transition = addTransitionToL(name);
    setRnwInL(transition, rnw);

    return transition;
  }

  public Transition addTransitionToK(String name) {

    Transition transition = getK().addTransition(name);

    Transition leftTransition = getL().addTransition(name);

    transitionMappingKToL.put(transition, leftTransition);
    transitionMappingKToR.put(transition, getR().addTransition(name));

    for (NAC nac : nacs.values()) {
      nac.getTransitionMappingLToNac().put(leftTransition,
        nac.getNac().addTransition(name));
    }

    return transition;
  }

  public Transition addTransitionToK(String name, IRenew rnw) {

    Transition transition = addTransitionToK(name);
    setRnwInK(transition, rnw);

    return transition;
  }

  public Transition addTransitionToR(String name) {

    Transition rightTransition = getR().addTransition(name);

    transitionMappingKToR.put(getK().addTransition(name), rightTransition);

    return rightTransition;
  }

  public Transition addTransitionToR(String name, IRenew rnw) {

    Transition transition = addTransitionToR(name);
    setRnwInR(transition, rnw);

    return transition;
  }

  public Transition addTransitionToNac(String name, NAC nac) {

    checkIfcontained(nac);

    return nac.getNac().addTransition(name);
  }

  public Transition addTransitionToNacWithMappingToTransitionInL(
    Transition lTransition, UUID nacId) {

    NAC nac = nacs.get(nacId);

    Transition nacTransition =
      nac.getNac().addTransition(lTransition.getName());
    nac.getTransitionMappingLToNac().put(lTransition, nacTransition);

    // additional attributes
    nacTransition.setRnw(lTransition.getRnw());
    nacTransition.setTlb(lTransition.getTlb());

    return nacTransition;
  }

  public Transition addTransitionToNac(String name, IRenew rnw, NAC nac) {

    checkIfcontained(nac);
    Transition newTransition = addTransitionToNac(name, nac);
    setRnwInNac(newTransition, rnw, nac);

    return newTransition;
  }

  public PreArc addPreArcToL(String name, Place place, Transition transition) {

    PreArc leftPreArc = getL().addPreArc(name, place, transition);

    Place kPlace = fromLtoK(place);
    Transition kTransition = fromLtoK(transition);

    preArcMappingKToL.put(getK().addPreArc(name, kPlace, kTransition),
      leftPreArc);

    for (NAC nac : nacs.values()) {
      PreArc newPreArc =
        nac.getNac().addPreArc(leftPreArc.getName(),
          nac.fromLtoNac(leftPreArc.getPlace()),
          nac.fromLtoNac(leftPreArc.getTransition()));
      nac.getPreArcMappingLToNac().put(leftPreArc, newPreArc);
    }

    return leftPreArc;
  }

  public PreArc addPreArcToK(String name, Place place, Transition transition) {

    PreArc preArc = getK().addPreArc(name, place, transition);

    Place leftPlace = fromKtoL(place);
    Transition leftTransition = fromKtoL(transition);
    Place rightPlace = fromKtoR(place);
    Transition rightTransition = fromKtoR(transition);

    PreArc leftPreArc = getL().addPreArc(name, leftPlace, leftTransition);

    preArcMappingKToL.put(preArc, leftPreArc);
    preArcMappingKToR.put(preArc, getR().addPreArc(name, rightPlace,
      rightTransition));

    for (NAC nac : nacs.values()) {
      Place nacPlace = nac.fromLtoNac(leftPlace);
      Transition nacTransition = nac.fromLtoNac(leftTransition);
      nac.getPreArcMappingLToNac().put(leftPreArc,
        nac.getNac().addPreArc(name, nacPlace, nacTransition));
    }

    return preArc;
  }

  public PreArc addPreArcToR(String name, Place place, Transition transition) {

    PreArc rightPreArc = getR().addPreArc(name, place, transition);

    Place kPlace = fromRtoK(place);
    Transition kTransition = fromRtoK(transition);

    preArcMappingKToR.put(getK().addPreArc(name, kPlace, kTransition),
      rightPreArc);

    return rightPreArc;
  }

  public PreArc addPreArcToNac(String name, Place place,
    Transition transition, NAC nac) {

    checkIfcontained(nac);

    return nac.getNac().addPreArc(name, place, transition);
  }

  public PostArc
  addPostArcToL(String name, Transition transition, Place place) {

    PostArc leftPostArc = getL().addPostArc(name, transition, place);

    Place kPlace = fromLtoK(place);
    Transition kTransition = fromLtoK(transition);

    postArcMappingKToL.put(getK().addPostArc(name, kTransition, kPlace),
      leftPostArc);

    for (NAC nac : nacs.values()) {
      Place nacPlace = nac.fromLtoNac(place);
      Transition nacTransition = nac.fromLtoNac(transition);
      PostArc nacPostArc =
        nac.getNac().addPostArc(name, nacTransition, nacPlace);
      nac.getPostArcMappingLToNac().put(leftPostArc, nacPostArc);
    }

    return leftPostArc;
  }

  public PostArc
  addPostArcToK(String name, Transition transition, Place place) {

    PostArc postArc = getK().addPostArc(name, transition, place);

    Place leftPlace = fromKtoL(place);
    Transition leftTransition = fromKtoL(transition);
    Place rightPlace = fromKtoR(place);
    Transition rightTransition = fromKtoR(transition);

    PostArc leftPostArc = getL().addPostArc(name, leftTransition, leftPlace);

    postArcMappingKToL.put(postArc, leftPostArc);
    postArcMappingKToR.put(postArc, getR().addPostArc(name, rightTransition,
      rightPlace));

    for (NAC nac : nacs.values()) {
      Place nacPlace = nac.fromLtoNac(leftPlace);
      Transition nacTransition = nac.fromLtoNac(leftTransition);
      PostArc nacPostArc =
        nac.getNac().addPostArc(name, nacTransition, nacPlace);
      nac.getPostArcMappingLToNac().put(leftPostArc, nacPostArc);
    }

    return postArc;
  }

  public PostArc
  addPostArcToR(String name, Transition transition, Place place) {

    PostArc rightPostArc = getR().addPostArc(name, transition, place);

    Place kPlace = fromRtoK(place);
    Transition kTransition = fromRtoK(transition);

    postArcMappingKToR.put(getK().addPostArc(name, kTransition, kPlace),
      rightPostArc);

    return rightPostArc;
  }

  public PostArc addPostArcToNac(String name, Transition transition,
    Place place, NAC nac) {

    checkIfcontained(nac);
    return nac.getNac().addPostArc(name, transition, place);
  }

  public PostArc addPostArcToNacWithMappingToPostArcInL(PostArc lPostArc,
    UUID nacId) {

    NAC nac = nacs.get(nacId);

    PostArc nacPostArc =
      nac.getNac().addPostArc(lPostArc.getName(),
        nac.fromLtoNac(lPostArc.getTransition()),
        nac.fromLtoNac(lPostArc.getPlace()));
    nac.getPostArcMappingLToNac().put(lPostArc, nacPostArc);

    // additional attributes
    nacPostArc.setWeight(lPostArc.getWeight());

    return nacPostArc;
  }

  public PreArc addPreArcToNacWithMappingToPreArcInL(PreArc lPreArc,
    UUID nacId) {

    NAC nac = nacs.get(nacId);

    PreArc nacPreArc =
      nac.getNac().addPreArc(lPreArc.getName(),
        nac.fromLtoNac(lPreArc.getPlace()),
        nac.fromLtoNac(lPreArc.getTransition()));

    nac.getPreArcMappingLToNac().put(lPreArc, nacPreArc);

    // additional attributes
    nacPreArc.setWeight(lPreArc.getWeight());

    return nacPreArc;
  }

  // TODO die Remover laufen alle ueber K, d.h. ein entferntes Element wird
  // immer aus allen Teilen der Regel entfernt. das ist unintuitiv und doof

  public void removePlaceFromL(Place place) {

    removePlaceFromK(fromLtoK(place));
  }

  public void removePlaceFromK(Place place) {

    Place leftPlace = fromKtoL(place);
    if (leftPlace != null) {
      getL().removePlace(leftPlace);
      for (NAC nac : nacs.values()) {
        removePlaceFromNac(nac.fromLtoNac(leftPlace), nac);
      }
    }

    getK().removePlace(place);

    if (fromKtoR(place) != null) {
      getR().removePlace(fromKtoR(place));
    }

    placeMappingKToL.remove(place);
    placeMappingKToR.remove(place);
  }

  public void removePlaceFromR(Place place) {

    removePlaceFromK(fromRtoK(place));
  }

  public void removePlaceFromNac(Place place, NAC nac) {

    nac.getNac().removePlace(place);
    nac.getPlaceMappingLToNac().remove(nac.fromNacToL(place));
  }

  public void removeTransitionFromL(Transition transition) {

    removeTransitionFromK(fromLtoK(transition));
  }

  public void removeTransitionFromK(Transition transition) {

    Transition leftTransition = fromKtoL(transition);
    if (leftTransition != null) {
      getL().removeTransition(leftTransition);
      for (NAC nac : nacs.values()) {
        removeTransitionFromNac(nac.fromLtoNac(leftTransition), nac);
      }
    }

    getK().removeTransition(transition);

    if (fromKtoR(transition) != null) {
      getR().removeTransition(fromKtoR(transition));
    }

    transitionMappingKToL.remove(transition);
    transitionMappingKToR.remove(transition);
  }

  public void removeTransitionFromR(Transition transition) {

    removeTransitionFromK(fromRtoK(transition));
  }

  public void removeTransitionFromNac(Transition transition, NAC nac) {

    checkIfcontained(nac);
    nac.getNac().removeTransition(transition);
    nac.getTransitionMappingLToNac().remove(nac.fromNacToL(transition));
  }

  public void removePreArcFromL(PreArc preArc) {

    removePreArcFromK(fromLtoK(preArc));
  }

  public void removePreArcFromK(PreArc preArc) {

    PreArc leftPreArc = fromKtoL(preArc);
    if (leftPreArc != null) {
      getL().removeArc(leftPreArc);
      for (NAC nac : nacs.values()) {
        removePreArcFromNac(nac.fromLtoNac(leftPreArc), nac);
      }
    }

    getK().removeArc(preArc);

    if (fromKtoR(preArc) != null) {
      getR().removeArc(fromKtoR(preArc));
    }

    preArcMappingKToL.remove(preArc);
    preArcMappingKToR.remove(preArc);
  }

  public void removePreArcFromR(PreArc preArc) {

    removePreArcFromK(fromRtoK(preArc));
  }

  public void removePreArcFromNac(PreArc preArc, NAC nac) {

    checkIfcontained(nac);
    nac.getNac().removeArc(preArc);
    nac.getPreArcMappingLToNac().remove(nac.fromNacToL(preArc));
  }

  public void removePostArcFromL(PostArc postArc) {

    removePostArcFromK(fromLtoK(postArc));
  }

  public void removePostArcFromK(PostArc postArc) {

    PostArc leftPostArc = fromKtoL(postArc);
    if (leftPostArc != null) {
      getL().removeArc(leftPostArc);
      for (NAC nac : nacs.values()) {
        removePostArcFromNac(nac.fromLtoNac(leftPostArc), nac);
      }
    }

    getK().removeArc(postArc);

    if (fromKtoR(postArc) != null) {
      getR().removeArc(fromKtoR(postArc));
    }

    postArcMappingKToL.remove(postArc);
    postArcMappingKToR.remove(postArc);
  }

  public void removePostArcFromR(PostArc postArc) {

    removePostArcFromK(fromRtoK(postArc));
  }

  public void removePostArcFromNac(PostArc postArc, NAC nac) {

    checkIfcontained(nac);
    nac.getNac().removeArc(postArc);
    nac.getPostArcMappingLToNac().remove(nac.fromNacToL(postArc));
  }

  /**
   * Returns the corresponding place in L.
   *
   * @param place
   *        a place in K.
   * @return the corresponding place in L.
   */
  public Place fromKtoL(Place place) {

    return placeMappingKToL.get(place);
  }

  /**
   * Returns the corresponding preArc in L.
   *
   * @param preArc
   *        a preArc in K.
   * @return the corresponding preArc in L.
   */
  public PreArc fromKtoL(PreArc preArc) {

    return preArcMappingKToL.get(preArc);
  }

  /**
   * Returns the corresponding postArc in L.
   *
   * @param postArc
   *        a postArc in K.
   * @return the corresponding postArc in L.
   */
  public PostArc fromKtoL(PostArc postArc) {

    return postArcMappingKToL.get(postArc);
  }

  /**
   * Returns the corresponding transition in L.
   *
   * @param transition
   *        a transition in K.
   * @return the corresponding transition in L.
   */
  public Transition fromKtoL(Transition transition) {

    return transitionMappingKToL.get(transition);
  }

  /**
   * Returns the corresponding place in R.
   *
   * @param place
   *        a place in K.
   * @return the corresponding place in R.
   */
  public Place fromKtoR(Place place) {

    return placeMappingKToR.get(place);
  }

  /**
   * Returns the corresponding preArc in R.
   *
   * @param preArc
   *        a preArc in K.
   * @return the corresponding preArc in R.
   */
  public PreArc fromKtoR(PreArc preArc) {

    return preArcMappingKToR.get(preArc);
  }

  /**
   * Returns the corresponding postArc in R.
   *
   * @param postArc
   *        a postArc in K.
   * @return the corresponding postArc in R.
   */
  public PostArc fromKtoR(PostArc postArc) {

    return postArcMappingKToR.get(postArc);
  }

  /**
   * Returns the corresponding transition in R.
   *
   * @param transition
   *        a transition in K.
   * @return the corresponding transition in R.
   */
  public Transition fromKtoR(Transition transition) {

    return transitionMappingKToR.get(transition);
  }

  /**
   * Returns the corresponding place in K.
   *
   * @param place
   *        a place in L.
   * @return the corresponding place in K.
   */
  public Place fromLtoK(Place place) {

    return placeMappingKToL.getKey(place);
  }

  /**
   * Returns the corresponding preArc in K.
   *
   * @param preArc
   *        a preArc in L.
   * @return the corresponding preArc in K.
   */
  public PreArc fromLtoK(PreArc preArc) {

    return preArcMappingKToL.getKey(preArc);
  }

  /**
   * Returns the corresponding postArc in K.
   *
   * @param postArc
   *        a postArc in L.
   * @return the corresponding postArc in K.
   */
  public PostArc fromLtoK(PostArc postArc) {

    return postArcMappingKToL.getKey(postArc);
  }

  /**
   * Returns the corresponding transition in K.
   *
   * @param transition
   *        a transition in L.
   * @return the corresponding transition in K.
   */
  public Transition fromLtoK(Transition transition) {

    return transitionMappingKToL.getKey(transition);
  }

  /**
   * Returns the corresponding place in R.
   *
   * @param place
   *        a place in L.
   * @return the corresponding place in R.
   */
  public Place fromLtoR(Place place) {

    return fromKtoR(fromLtoK(place));
  }

  /**
   * Returns the corresponding preArc in R.
   *
   * @param preArc
   *        a preArc in L.
   * @return the corresponding preArc in R.
   */
  public PreArc fromLtoR(PreArc preArc) {

    return fromKtoR(fromLtoK(preArc));
  }

  /**
   * Returns the corresponding postArc in R.
   *
   * @param postArc
   *        a postArc in L.
   * @return the corresponding postArc in R.
   */
  public PostArc fromLtoR(PostArc postArc) {

    return fromKtoR(fromLtoK(postArc));
  }

  /**
   * Returns the corresponding transition in R.
   *
   * @param transition
   *        a transition in L.
   * @return the corresponding transition in R.
   */
  public Transition fromLtoR(Transition transition) {

    return fromKtoR(fromLtoK(transition));
  }

  /**
   * Returns the corresponding place in K.
   *
   * @param place
   *        a place in R.
   * @return the corresponding place in K.
   */
  public Place fromRtoK(Place place) {

    return placeMappingKToR.getKey(place);
  }

  /**
   * Returns the corresponding preArc in K.
   *
   * @param preArc
   *        a preArc in R.
   * @return the corresponding preArc in K.
   */
  public PreArc fromRtoK(PreArc preArc) {

    return preArcMappingKToR.getKey(preArc);
  }

  /**
   * Returns the corresponding postArc in K.
   *
   * @param postArc
   *        a postArc in R.
   * @return the corresponding postArc in K.
   */
  public PostArc fromRtoK(PostArc postArc) {

    return postArcMappingKToR.getKey(postArc);
  }

  /**
   * Returns the corresponding transition in K.
   *
   * @param transition
   *        a transition in R.
   * @return the corresponding transition in K.
   */
  public Transition fromRtoK(Transition transition) {

    return transitionMappingKToR.getKey(transition);
  }

  /**
   * Returns the corresponding place in L.
   *
   * @param place
   *        a place in R.
   * @return the corresponding place in L.
   */
  public Place fromRtoL(Place place) {

    return fromKtoL(fromRtoK(place));
  }

  /**
   * Returns the corresponding preArc in L.
   *
   * @param preArc
   *        a preArc in R.
   * @return the corresponding preArc in L.
   */
  public PreArc fromRtoL(PreArc preArc) {

    return fromKtoL(fromRtoK(preArc));
  }

  /**
   * Returns the corresponding postArc in L.
   *
   * @param postArc
   *        a postArc in R.
   * @return the corresponding postArc in L.
   */
  public PostArc fromRtoL(PostArc postArc) {

    return fromKtoL(fromRtoK(postArc));
  }

  /**
   * Returns the corresponding transition in L.
   *
   * @param transition
   *        a transition in R.
   * @return the corresponding transition in L.
   */
  public Transition fromRtoL(Transition transition) {

    return fromKtoL(fromRtoK(transition));
  }

  /**
   * Returns all corresponding places in the nacs for a specific L-place
   *
   * @param place
   *        The place in L
   * @return Corresponding places in nacs
   */
  public List<Place> fromLtoNAC(Place place) {

    List<Place> result = new ArrayList<Place>();

    for (NAC nac : this.getNACs()) {

      Place nacPlace = nac.fromLtoNac(place);

      if (nacPlace != null) {
        result.add(nacPlace);
      }
    }

    return result;
  }

  /**
   * Returns all corresponding transitions in the nacs for a specific
   * L-transition
   *
   * @param place
   *        The place in L
   * @return Corresponding places in nacs
   */
  public List<Transition> fromLtoNAC(Transition transition) {

    List<Transition> result = new ArrayList<Transition>();

    for (NAC nac : this.getNACs()) {

      Transition nacTransition = nac.fromLtoNac(transition);

      if (nacTransition != null) {
        result.add(nacTransition);
      }
    }

    return result;
  }

  /**
   * Checks if the passed NAC is part of this rules NAC set
   *
   * @param nac
   *        The NAC that is supposed to be contained
   * @throws NACnotContainedException
   *         If it isn't
   */
  private void checkIfcontained(NAC nac) {

    if (!nacs.values().contains(nac)) {
      throw new IllegalArgumentException("NAC not contained in this rule");
    }
  }

  /**
   * Returns the NAC with the specified nacID.
   *
   * @param nacId
   * @return The NAC
   */
  public NAC getNAC(UUID nacId) {

    return nacs.get(nacId);
  }

}
