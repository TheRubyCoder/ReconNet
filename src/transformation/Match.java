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

import java.util.HashMap;
import java.util.Map;

import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

/**
 * A match maps places, transitions and arcs in a way that pre and post have
 * the same "structure" in 'from' and 'to'. For more details look at documents
 * of the petrinet course.
 */
public class Match {

  /**
   * The source petrinet (left part of match)
   */
  private final Petrinet source;
  /**
   * The target petrinet (right part of match)
   */
  private final Petrinet target;

  /**
   * Matchs between places
   */
  private final Map<Place, Place> places;

  /**
   * Matchs between transitions
   */
  private final Map<Transition, Transition> transitions;

  /**
   * Matchs between arcs
   */
  private final Map<PreArc, PreArc> preArcs;

  /**
   * Matchs between arcs
   */
  private final Map<PostArc, PostArc> postArcs;

  /**
   * Creates a new Match with the given parameters
   * 
   * @param source
   *        the petrinet from which this match starts.
   * @param target
   *        the petrinet into which this match maps to.
   * @param places
   *        mapping of places
   * @param transitions
   *        mapping of transitions
   * @param preArcs
   *        mapping of preArcs
   * @param postArcs
   *        mapping of postArcs
   */
  public Match(Petrinet source, Petrinet target, Map<Place, Place> places,
    Map<Transition, Transition> transitions, Map<PreArc, PreArc> preArcs,
    Map<PostArc, PostArc> postArcs) {

    this.source = source;
    this.target = target;
    this.places = new HashMap<Place, Place>(places);
    this.transitions = new HashMap<Transition, Transition>(transitions);
    this.preArcs = new HashMap<PreArc, PreArc>(preArcs);
    this.postArcs = new HashMap<PostArc, PostArc>(postArcs);
  }

  /**
   * Returns the matchs of all transition.
   * 
   * @return the matchs of all transition.
   */
  public Map<Transition, Transition> getTransitions() {

    return transitions;
  }

  /**
   * Returns the matchs of all places.
   * 
   * @return the matchs of all places.
   */
  public Map<Place, Place> getPlaces() {

    return places;
  }

  /**
   * Returns the match of all pre arcs.
   * 
   * @return the match of all pre arcs.
   */
  public Map<PreArc, PreArc> getPreArcs() {

    return preArcs;
  }

  /**
   * Returns the match of all post arcs.
   * 
   * @return the match of all post arcs.
   */
  public Map<PostArc, PostArc> getPostArcs() {

    return postArcs;
  }

  /**
   * Returns the match to a single transition.
   * 
   * @param transition
   *        transition in the "from" net
   * @return the respective transition in the "to" net
   */
  public Transition getTransition(Transition transition) {

    return transitions.get(transition);
  }

  /**
   * Returns the match to a single place.
   * 
   * @param place
   *        place in the "from" net
   * @return the respective place in the "to" net
   */
  public Place getPlace(Place place) {

    return places.get(place);
  }

  /**
   * Returns the match to a single pre arc.
   * 
   * @param preArc
   *        pre arc in the "from" net
   * @return the respective pre arc in the "to" net
   */
  public PreArc getPreArc(PreArc preArc) {

    return preArcs.get(preArc);
  }

  /**
   * Returns the match to a single arc.
   * 
   * @param postArc
   *        post arc in the "from" net
   * @return the respective post arc in the "to" net
   */
  public PostArc getPostArc(PostArc postArc) {

    return postArcs.get(postArc);
  }

  /**
   * Returns the Petrinet from which this match starts.
   * 
   * @return the Petrinet from which this match starts.
   */
  public Petrinet getSource() {

    return source;
  }

  /**
   * Returns the Petrinet into which this match maps to.
   * 
   * @return the Petrinet into which this match maps to.
   */
  public Petrinet getTarget() {

    return target;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {

    final int prime = 31;

    int result = prime;

    if (places != null) {
      result += places.hashCode();
    }

    result = prime * result;
    if (postArcs != null) {
      result += postArcs.hashCode();
    }

    result = prime * result;
    if (preArcs != null) {
      result += preArcs.hashCode();
    }

    result = prime * result;
    if (source != null) {
      result += source.hashCode();
    }

    result = prime * result;
    if (target != null) {
      result += target.hashCode();
    }

    result = prime * result;
    if (transitions != null) {
      result += transitions.hashCode();
    }

    return result;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Match)) {
      return false;
    }
    Match other = (Match) obj;
    if (places == null) {
      if (other.places != null) {
        return false;
      }
    } else if (!places.equals(other.places)) {
      return false;
    }
    if (postArcs == null) {
      if (other.postArcs != null) {
        return false;
      }
    } else if (!postArcs.equals(other.postArcs)) {
      return false;
    }
    if (preArcs == null) {
      if (other.preArcs != null) {
        return false;
      }
    } else if (!preArcs.equals(other.preArcs)) {
      return false;
    }
    if (source == null) {
      if (other.source != null) {
        return false;
      }
    } else if (!source.equals(other.source)) {
      return false;
    }
    if (target == null) {
      if (other.target != null) {
        return false;
      }
    } else if (!target.equals(other.target)) {
      return false;
    }
    if (transitions == null) {
      if (other.transitions != null) {
        return false;
      }
    } else if (!transitions.equals(other.transitions)) {
      return false;
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return "Match [source=" + source + ", target=" + target + ", places="
      + places + ", transitions=" + transitions + ", preArcs=" + preArcs
      + ", postArcs=" + postArcs + "]";
  }

}
