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

package data;

import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.Rule;
import transformation.TransformationComponent;

public final class ScenarioRuleChangingData {

  private ScenarioRuleChangingData() {

    // not called
  }

  public static Rule getRuleScenario1() {

    return emptyRule;
  }

  public static Rule getRuleScenario2() {

    return emptyRule;
  }

  public static Rule getRuleScenario3() {

    return emptyRule;
  }

  public static Rule getRuleScenario4() {

    return ruleWithOnePlaceInLKR;
  }

  public static Rule getRuleScenario5() {

    return ruleWithOnePlaceInLKR;
  }

  public static Rule getRuleScenario6() {

    return ruleWithOnePlaceInLKR;
  }

  public static Rule getRuleScenario7() {

    return ruleWithOnePlaceAndOneTransition;
  }

  public static Rule getRuleScenario8() {

    return ruleWithOnePlaceAndOneTransition;
  }

  public static Rule getRuleScenario9() {

    return ruleWithOnePlaceAndOneTransition;
  }

  public static Rule getRuleScenario13() {

    return ruleWithOnePlaceInLKR;
  }

  public static Rule getRuleScenario14() {

    return ruleWithOnePlaceInLKR;
  }

  public static Rule getRuleScenario15() {

    return ruleWithOnePlaceInLKR;
  }

  public static Rule getRuleScenario16() {

    return ruleWithOnePlaceInLKR;
  }

  public static Rule getRuleScenario17() {

    return ruleWithOnePlaceInLKR;
  }

  public static Rule getRuleScenario18() {

    return ruleWithOnePlaceInLKR;
  }

  private static Rule emptyRule;

  private static Rule ruleWithOnePlaceInLKR;

  private static Rule ruleWithOnePlaceAndOneTransition;

  static {
    clearAllRuleChanges();
  }

  /**
   * Initiating all scenario rules like specified in
   * /../additional/images/rule_changing_scenarios.png
   */
  public static void clearAllRuleChanges() {

    emptyRule = TransformationComponent.getTransformation().createRule();

    ruleWithOnePlaceInLKR =
      TransformationComponent.getTransformation().createRule();
    ruleWithOnePlaceInLKR.addPlaceToK("P1");

    ruleWithOnePlaceAndOneTransition =
      TransformationComponent.getTransformation().createRule();
    Place placeInK = ruleWithOnePlaceAndOneTransition.addPlaceToK("P1");
    Transition transitionInK =
      ruleWithOnePlaceAndOneTransition.addTransitionToK("A");
    ruleWithOnePlaceAndOneTransition.addPreArcToK("", placeInK, transitionInK);
  }

}
