/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
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

import java.util.HashSet;
import java.util.Set;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.matcher.NacVisitor;
import transformation.matcher.PNVF2;
import transformation.matcher.PNVF2.MatchException;
import transformation.matcher.PNVF2.MatchVisitor;
import exceptions.EngineException;

/**
 * An Transformation on a Petrinet<br/>
 * The Transformation applies a rule on an petrinet under a certain match
 */
public final class Transformation {

  protected static final class CheckContactConditionFulfilledMatchVisitor
    implements PNVF2.MatchVisitor {

    @SuppressWarnings("unused")
    private Petrinet petrinet;
    @SuppressWarnings("unused")
    private Rule rule;

    public CheckContactConditionFulfilledMatchVisitor(Petrinet petrinet,
      Rule rule) {

      this.petrinet = petrinet;
      this.rule = rule;
    }

    @Override
    public boolean visit(Match match) {

      return true;
      // return contactConditionFulfilled(this.petrinet, this.rule,
      // match);
    }
  }

  private final Petrinet petrinet;
  private final Rule rule;
  private final Match match;

  // New for Engine
  private Set<Place> addedPlaces = null;
  private Set<Place> deletedPlaces = null;

  private Set<Transition> addedTransitions = null;
  private Set<Transition> deletedTransitions = null;

  private Set<PreArc> addedPreArcs = null;
  private Set<PreArc> deletedPreArcs = null;

  private Set<PostArc> addedPostArcs = null;
  private Set<PostArc> deletedPostArcs = null;

  /**
   * Constructor for the class Transformation
   *
   * @param net
   *        the petrinet to transform
   * @param morph
   *        the match to use
   * @param rule
   *        the rule that should apply
   */
  private Transformation(Petrinet petrinet, Match match, Rule rule) {

    this.petrinet = petrinet;
    this.match = match;
    this.rule = rule;
  }

  /**
   * Creates a new Transformation with given parameters
   *
   * @param petrinet
   *        Petrinet to transform
   * @param match
   *        Match to use the rule under
   * @param rule
   *        Rule to apply to petrinet
   * @return the transformation
   */
  static Transformation createTransformation(Petrinet petrinet, Match match,
    Rule rule) {

    return new Transformation(petrinet, match, rule);
  }

  /**
   * Creates a new Transformation with given parameters
   *
   * @param petrinet
   *        Petrinet to transform
   * @param match
   *        Match to use the rule under
   * @param rule
   *        Rule to apply to petrinet
   * @return the transformation<br/>
   *         <tt>null</tt>if no Match found
   */
  static Transformation createTransformationWithAnyMatch(Petrinet petrinet,
    Rule rule) {

    // VF2.MatchVisitor visitor = new
    // CheckContactConditionFulfilledMatchVisitor(petrinet, rule);

    // Match match = Ullmann.createMatch(rule.getL(), petrinet);

    try {
      Match match =
        PNVF2.getInstance(rule.getL(), petrinet).getMatch(false,
          rule.getPlacesToDelete());
      return new Transformation(petrinet, match, rule);
    } catch (MatchException e) {
      return null;
    }
  }

  // WLAD Aenderung 2 match mit nac methode
  public static Transformation createTransformationWithNAC(Petrinet petrinet,
    Rule rule) {

    try {
      MatchVisitor nacVisitor = new NacVisitor(rule);
      Match match =
        PNVF2.getInstance(rule.getL(), petrinet).getMatch(false,
          rule.getPlacesToDelete(), nacVisitor);
      return new Transformation(petrinet, match, rule);
    } catch (MatchException e) {
      return null;
    }
  }

  /**
   * Returns the Petrinet of this transformation. This net will be changed
   * when transform() is called.
   *
   * @return the Rule of this transformation.
   */
  public Petrinet getPetrinet() {

    return petrinet;
  }

  /**
   * Returns the Match of this transformation.
   *
   * @return the Match of this transformation.
   */
  public Match getMatch() {

    return match;
  }

  /**
   * Returns the Rule of this transformation.
   *
   * @return the Rule of this transformation.
   */
  public Rule getRule() {

    return rule;
  }

  /**
   * This will transform the petrinet using the Rule returned by getRule() and
   * the Match returned by getMatch().
   *
   * @return the Transformation that was used (<tt>this</tt>)
   * @throws EngineException
   *         When contact condition is not fulfilled
   */
  Transformation transform()
    throws EngineException {

    if (!contactConditionFulfilled(getPetrinet(), getRule(), getMatch())) {
      throw new EngineException("Kontaktbedingung verletzt");
    }

    addedPlaces = new HashSet<Place>();
    deletedPlaces = new HashSet<Place>();

    addedTransitions = new HashSet<Transition>();
    deletedTransitions = new HashSet<Transition>();

    addedPreArcs = new HashSet<PreArc>();
    deletedPreArcs = new HashSet<PreArc>();

    addedPostArcs = new HashSet<PostArc>();
    deletedPostArcs = new HashSet<PostArc>();

    Petrinet kNet = rule.getK();

    // Add new places, map k to these new Places
    for (Place placeToAdd : rule.getPlacesToAdd()) {
      Place newPlace = petrinet.addPlace(placeToAdd.getName());
      newPlace.setMark(placeToAdd.getMark());
      newPlace.setCapacity(placeToAdd.getCapacity());
      addedPlaces.add(newPlace);
      match.getPlaces().put(rule.fromRtoK(placeToAdd), newPlace);
    }

    // Add new transitions, map k to these new Places
    for (Transition transitionToAdd : rule.getTransitionsToAdd()) {
      Transition newTransition =
        petrinet.addTransition(transitionToAdd.getName(),
          transitionToAdd.getRnw());
      if (transitionToAdd.getTlb() != null) {
        newTransition.setTlb(transitionToAdd.getTlb());
      }
      addedTransitions.add(newTransition);
      match.getTransitions().put(rule.fromRtoK(transitionToAdd),
        newTransition);
    }

    // map remaining old K places to the match of L
    for (Place place : kNet.getPlaces()) {
      if (match.getPlaces().get(place) == null) {
        match.getPlaces().put(place, match.getPlace(rule.fromKtoL(place)));
      }
    }

    // map remaining old K transitions to the match of L
    for (Transition transition : kNet.getTransitions()) {
      if (match.getTransitions().get(transition) == null) {
        match.getTransitions().put(transition,
          match.getTransition(rule.fromKtoL(transition)));
      }
    }

    // Add new preArcs, map k preArcs to these
    for (PreArc preArcToAdd : rule.getPreArcsToAdd()) {
      PreArc newPreArc =
        petrinet.addPreArc(preArcToAdd.getName(),
          match.getPlace(rule.fromRtoK(preArcToAdd.getPlace())),
          match.getTransition(rule.fromRtoK(preArcToAdd.getTransition())),
          preArcToAdd.getWeight());
      addedPreArcs.add(newPreArc);
      match.getPreArcs().put(preArcToAdd, newPreArc);
    }

    // Add new postArcs, map k preArcs to these
    for (PostArc postArcToAdd : rule.getPostArcsToAdd()) {
      PostArc newPostArc =
        petrinet.addPostArc(postArcToAdd.getName(),
          match.getTransition(rule.fromRtoK(postArcToAdd.getTransition())),
          match.getPlace(rule.fromRtoK(postArcToAdd.getPlace())),
          postArcToAdd.getWeight());
      addedPostArcs.add(newPostArc);
      match.getPostArcs().put(postArcToAdd, newPostArc);
    }

    // map remaining old K preArcs to the match of L
    for (PreArc preArc : kNet.getPreArcs()) {
      if (match.getPreArcs().get(preArc) == null) {
        match.getPreArcs().put(preArc, match.getPreArc(rule.fromKtoL(preArc)));
      }
    }

    // map remaining old K postArcs to the match of L
    for (PostArc postArc : kNet.getPostArcs()) {
      if (match.getPostArcs().get(postArc) == null) {
        match.getPostArcs().put(postArc,
          match.getPostArc(rule.fromKtoL(postArc)));
      }
    }

    // Delete K - R Places
    for (Place placeToDelete : rule.getPlacesToDelete()) {
      Place deletedPlace = match.getPlace(rule.fromLtoK(placeToDelete));
      deletedPlaces.add(deletedPlace);
      petrinet.removePlace(deletedPlace);
    }

    // Delete K - R Transitions
    for (Transition transitionToDelete : rule.getTransitionsToDelete()) {
      Transition deletedTransition =
        match.getTransition(rule.fromLtoK(transitionToDelete));
      deletedTransitions.add(deletedTransition);
      petrinet.removeTransition(deletedTransition);
    }

    // Deleted K - R PreArcs
    for (PreArc preArcToDelete : rule.getPreArcsToDelete()) {
      PreArc deletedArc = match.getPreArc(rule.fromLtoK(preArcToDelete));

      deletedPreArcs.add(deletedArc);

      if (petrinet.containsPreArc(deletedArc)) {
        petrinet.removeArc(deletedArc);
      }
    }

    // Deleted K - R PostArcs
    for (PostArc postArcToDelete : rule.getPostArcsToDelete()) {
      PostArc deletedArc = match.getPostArc(rule.fromLtoK(postArcToDelete));

      deletedPostArcs.add(deletedArc);

      if (petrinet.containsPostArc(deletedArc)) {
        petrinet.removeArc(deletedArc);
      }
    }

    return this;
  }

  /**
   * Returns <tt>true</tt> if the contact condition for <tt>node</tt> is
   * fulfilled. Which means: Are all incident Arcs of <tt>node</tt> also
   * mapped in the match?
   */
  private static boolean contactConditionFulfilled(Place place, Match match,
    Petrinet toNet, Petrinet fromNet) {

    Place mappedPlace = match.getPlace(place);

    for (PostArc arc : mappedPlace.getIncomingArcs()) {
      if (!match.getPostArcs().containsValue(arc)) {
        return false;
      }
    }

    for (PreArc arc : mappedPlace.getOutgoingArcs()) {
      if (!match.getPreArcs().containsValue(arc)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Returns <tt>true</tt> if the contact condition for <tt>node</tt> is
   * fulfilled. Which means: Are all incident Arcs of <tt>node</tt> also
   * mapped in the match?
   */
  private static boolean contactConditionFulfilled(Transition transition,
    Match match, Petrinet toNet, Petrinet fromNet) {

    Transition mappedTransition = match.getTransition(transition);

    for (PreArc arc : mappedTransition.getIncomingArcs()) {
      if (!match.getPreArcs().containsValue(arc)) {
        return false;
      }
    }

    for (PostArc arc : mappedTransition.getOutgoingArcs()) {
      if (!match.getPostArcs().containsValue(arc)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Returns <tt>true</tt> if the contact condition for all Nodes in K-R is
   * fulfilled.
   *
   * @param petrinet
   * @param rule
   * @param match
   * @return
   */
  // doesn't exists?!
  // @see {link
  // {@link Transformation#contactConditionFulfilled(INode, Match, Petrinet)}
  private static boolean contactConditionFulfilled(Petrinet petrinet,
    Rule rule, Match match) {

    Petrinet l = rule.getL();

    for (Place place : rule.getPlacesToDelete()) {
      if (!contactConditionFulfilled(place, match, petrinet, l)) {
        return false;
      }
    }

    for (Transition transition : rule.getTransitionsToDelete()) {
      if (!contactConditionFulfilled(transition, match, petrinet, l)) {
        return false;
      }
    }

    return true;

  }

  public Set<Place> getAddedPlaces() {

    if (addedPlaces == null) {
      return new HashSet<Place>();
    }

    return addedPlaces;
  }

  public Set<Place> getDeletedPlaces() {

    if (deletedPlaces == null) {
      return new HashSet<Place>();
    }

    return deletedPlaces;
  }

  public Set<Transition> getAddedTransitions() {

    if (addedTransitions == null) {
      return new HashSet<Transition>();
    }

    return addedTransitions;
  }

  public Set<Transition> getDeletedTransitions() {

    if (deletedTransitions == null) {
      return new HashSet<Transition>();
    }

    return deletedTransitions;
  }

  public Set<PreArc> getAddedPreArcs() {

    if (addedPreArcs == null) {
      return new HashSet<PreArc>();
    }

    return addedPreArcs;
  }

  public Set<PreArc> getDeletedPreArcs() {

    if (deletedPreArcs == null) {
      return new HashSet<PreArc>();
    }

    return deletedPreArcs;
  }

  public Set<PostArc> getAddedPostArcs() {

    if (addedPostArcs == null) {
      return new HashSet<PostArc>();
    }

    return addedPostArcs;
  }

  public Set<PostArc> getDeletedPostArcs() {

    if (deletedPostArcs == null) {
      return new HashSet<PostArc>();
    }

    return deletedPostArcs;
  }
}
