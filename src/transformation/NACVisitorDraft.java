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

import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.matcher.PNVF2;
import transformation.matcher.PNVF2.MatchException;
import transformation.matcher.PNVF2.MatchVisitor;

public class NACVisitorDraft
  implements MatchVisitor {

  /** The rule whose applicability for a particular match is to be checked */
  private Rule rule = null;

  /** The match to check the NACs against */
  private Match match = null;

  /**
   * Checks if the negative application conditions (NACs) are fulfilled
   * <p>
   * If the given rule doesn't contain NACs, this acts like a trivial
   * "accept first" visitor
   * 
   * @param rule
   *        The rule whose applicability for a particular match is to be
   *        checked
   */
  NACVisitorDraft(Rule rule) {

    this.rule = rule;
  }

  @Override
  public boolean visit(Match match) {

    this.match = match;

    // catch trivial case (rule has no NACs)
    if (rule.getNACs() == null) {
      return true;
    }

    // Check each NAC. Abort loop and return false if the first applicable
    // morphism one is found
    for (NAC nac : rule.getNACs()) {

      Match partialMatch = createPartialMatch(nac);

      // try to find a complete match Nac->G(target) based on the partial
      // match
      PNVF2 vf2 =
        PNVF2.getInstance(partialMatch.getSource(), match.getTarget());

      // TODO isStrictMatch sollte von irgendwo uebernommen werden??
      try {
        vf2.getMatch(false, partialMatch);
      } catch (MatchException e) {
        continue;
      }

      // if a match has been found the rule is not applicable, thus:
      return false;
    }

    // If no match has been found for any NAC we can confidently:
    return true;
  }

  /**
   * @param nac
   *        NAC to be checked for
   * @return partialMatch Mapping from Nac to the target net, following the
   *         given Match
   */
  private Match createPartialMatch(NAC nac) {

    Map<Place, Place> places = new HashMap<Place, Place>();
    Map<Transition, Transition> transitions =
      new HashMap<Transition, Transition>();
    Map<PreArc, PreArc> preArcs = new HashMap<PreArc, PreArc>();
    Map<PostArc, PostArc> postArcs = new HashMap<PostArc, PostArc>();

    // for each element in L (rule) obtain the mapped elements in N and
    // target and successively create a new mapping N->target
    // using each pair of obtained elements
    for (Place p : rule.getL().getPlaces()) {
      places.put(nac.fromLtoNac(p), match.getPlaces().get(p));
    }

    for (Transition t : rule.getL().getTransitions()) {
      transitions.put(nac.fromLtoNac(t), match.getTransitions().get(t));
    }

    for (PreArc preA : rule.getL().getPreArcs()) {
      preArcs.put(nac.fromLtoNac(preA), match.getPreArcs().get(preA));
    }

    for (PostArc postA : rule.getL().getPostArcs()) {
      postArcs.put(nac.fromLtoNac(postA), match.getPostArcs().get(postA));
    }

    return new Match(nac.getNac(), this.match.getTarget(), places,
      transitions, preArcs, postArcs);
  }
}
