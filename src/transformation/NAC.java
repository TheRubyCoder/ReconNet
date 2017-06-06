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

package transformation;

import static transformation.dependency.PetrinetAdapter.createPetrinet;

import java.util.UUID;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

import petrinet.model.IArc;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

/**
 * Represents a negative application condition (NAC) of a rule.
 * <p>
 * A NAC consists of two parts: a petrinet (Nac) and an injective morphism
 * L->Nac (n)<br/>
 * For a rule to comply with a NAC on a given morphism g: L->G (i.e. a match)
 * there must be no morphism x: Nac->G such as (x after n) == g
 */
public class NAC {

  // internal UID to avoid collisions during modifications
  private final UUID id;

  // Nac part of the NAC
  private Petrinet nac;

  // Mapping part of the NAC
  private final BidiMap<Place, Place> placeMappingLToNac;
  private final BidiMap<PostArc, PostArc> postArcMappingLToNac;
  private final BidiMap<PreArc, PreArc> preArcMappingLToNac;
  private final BidiMap<Transition, Transition> transitionMappingLToNac;

  /**
   * Constructs a new NAC from a given L-part of a rule.
   *
   * @param l
   *        The L-part of the rule the new NAC belongs to.
   */
  protected NAC(Petrinet l) {

    nac = createPetrinet();

    id = UUID.randomUUID();

    placeMappingLToNac = new DualHashBidiMap<Place, Place>();
    postArcMappingLToNac = new DualHashBidiMap<PostArc, PostArc>();
    preArcMappingLToNac = new DualHashBidiMap<PreArc, PreArc>();
    transitionMappingLToNac = new DualHashBidiMap<Transition, Transition>();

    for (Place p : l.getPlaces()) {
      Place nacPlace = nac.addPlace(p.getName());
      placeMappingLToNac.put(p, nacPlace);
    }

    // TODO Variante mit Renews
    for (Transition t : l.getTransitions()) {
      Transition nacTrans = nac.addTransition(t.getName());
      transitionMappingLToNac.put(t, nacTrans);
    }

    for (PostArc post : l.getPostArcs()) {
      PostArc nacPost =
        nac.addPostArc(post.getName(), post.getTransition(), post.getPlace());
      postArcMappingLToNac.put(post, nacPost);
    }

    for (PreArc pre : l.getPreArcs()) {
      PreArc nacPre =
        nac.addPreArc(pre.getName(), pre.getPlace(), pre.getTransition());
      preArcMappingLToNac.put(pre, nacPre);
    }

    System.out.println(NAC.class + " - constructor: created NAC with UUID "
      + id + "; nac has " + nac.getPlaces().size() + " nodes");
  }

  /**
   * Constructs a plain NAC without any further logic.
   */
  protected NAC() {

    nac = createPetrinet();

    id = UUID.randomUUID();

    placeMappingLToNac = new DualHashBidiMap<Place, Place>();
    postArcMappingLToNac = new DualHashBidiMap<PostArc, PostArc>();
    preArcMappingLToNac = new DualHashBidiMap<PreArc, PreArc>();
    transitionMappingLToNac = new DualHashBidiMap<Transition, Transition>();
  }

  // Getters
  public Petrinet getNac() {

    return nac;
  }

  /**
   * Returns the corresponding Place in Nac
   *
   * @param p
   *        Place in L
   * @return Place in N
   */
  public Place fromLtoNac(Place p) {

    return placeMappingLToNac.get(p);
  }

  /**
   * Returns the corresponding PreArc in Nac
   *
   * @param pre
   *        PreArc in L
   * @return PreArc in N
   */
  public PreArc fromLtoNac(PreArc pre) {

    return preArcMappingLToNac.get(pre);
  }

  /**
   * Returns the corresponding PostArc in Nac
   *
   * @param post
   *        PostArc in L
   * @return PostArc in N
   */
  public PostArc fromLtoNac(PostArc post) {

    return postArcMappingLToNac.get(post);
  }

  /**
   * Returns the corresponding Transition in Nac
   *
   * @param t
   *        Transition in L
   * @return Transition in N
   */
  public Transition fromLtoNac(Transition t) {

    return transitionMappingLToNac.get(t);
  }

  /**
   * Returns the corresponding Place in Nac
   *
   * @param p
   *        Place in L
   * @return Place in N
   */
  public Place fromNacToL(Place p) {

    return placeMappingLToNac.getKey(p);
  }

  /**
   * Returns the corresponding PreArc in Nac
   *
   * @param pre
   *        PreArc in L
   * @return PreArc in N
   */
  public PreArc fromNacToL(PreArc pre) {

    return preArcMappingLToNac.getKey(pre);
  }

  /**
   * Returns the corresponding PostArc in Nac
   *
   * @param post
   *        PostArc in L
   * @return PostArc in N
   */
  public PostArc fromNacToL(PostArc post) {

    return postArcMappingLToNac.getKey(post);
  }

  /**
   * Returns the corresponding Transition in Nac
   *
   * @param t
   *        Transition in L
   * @return Transition in N
   */
  public Transition fromNacToL(Transition t) {

    return transitionMappingLToNac.getKey(t);
  }

  /**
   * @return the placeMappingLToNac
   */
  protected BidiMap<Place, Place> getPlaceMappingLToNac() {

    return placeMappingLToNac;
  }

  /**
   * @return the postArcMappingLToNac
   */
  protected BidiMap<PostArc, PostArc> getPostArcMappingLToNac() {

    return postArcMappingLToNac;
  }

  /**
   * @return the preArcMappingLToNac
   */
  protected BidiMap<PreArc, PreArc> getPreArcMappingLToNac() {

    return preArcMappingLToNac;
  }

  /**
   * @return the transitionMappingLToNac
   */
  protected BidiMap<Transition, Transition> getTransitionMappingLToNac() {

    return transitionMappingLToNac;
  }

  /**
   * @return The UUID, set at creation of the NAC
   */
  public UUID getId() {

    return id;
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result;
    if (id != null) {
      result += id.hashCode();
    }
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof NAC)) {
      return false;
    }
    NAC other = (NAC) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  public boolean isPlaceSafeToChange(Place place) {

    return !this.placeMappingLToNac.containsValue(place);
  }

  public boolean isTransitionSafeToChange(Transition transition) {

    return !this.transitionMappingLToNac.containsValue(transition);
  }

  public boolean isArcSafeToChange(IArc arc) {

    if (this.preArcMappingLToNac.containsValue(arc)) {
      return false;
    }

    if (this.postArcMappingLToNac.containsValue(arc)) {
      return false;
    }

    return true;
  }
}
