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

import java.util.List;
import java.util.Map;
import java.util.UUID;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.Rule.Net;

/**
 * Interface for accessing transformation component so other components do not
 * need to directly access classes within the component
 */
public interface ITransformation {

  /**
   * Creates a new empty rule
   *
   * @return
   */
  Rule createRule();

  /**
   * Returns the respective representations of a node
   *
   * @param rule
   *        The rule in which the node lies
   * @param node
   *        The node that has representations
   * @return A List with always 3 Elements:
   *         <ul>
   *         <li>Index 0: The Element of L-Net</li>
   *         <li>Index 1: The Element of K-Net</li>
   *         <li>Index 2: The Element if R-Net</li>
   *         </ul>
   *         Where each Element is <tt>null</tt> if there was no
   *         representation. <br>
   *         Returns <tt>null</tt> if the <tt>node</tt> is not in
   *         <tt>rule</tt> <h4>example 1</h4> <tt>rule</tt> L, K, and R has
   *         each only one place and the parameter <tt>node</tt> refers to the
   *         node in L. The return would be List(node of L, node of K, node of
   *         R) <h4>example 2</h4> <tt>rule</tt> has one node in each L and K,
   *         but R is empty (deletes one node)<br>
   *         <tt>node</tt> refers to the node in K<br>
   *         the return would be List(node of L, node of K, <tt>null</tt>)
   */
  List<INode> getMappings(Rule rule, INode node);

  /**
   * Very similar to {@link ITransformation#getMappings(Rule, INode)} but with
   * the <code>ruleId</code> instead of <tt>rule</tt>.
   *
   * @see ITransformation#storeSessionId(int, Rule)
   * @param ruleId
   * @param node
   * @return
   */
  List<INode> getMappings(int ruleId, INode node);

  /**
   * Very similar to {@link ITransformation#getMappings(Rule, INode)} but with
   * Arc instead of INode
   */
  List<IArc> getMappings(Rule rule, IArc arc);

  /**
   * For a given node, this method returns all corresponding nodes of all nacs
   * of the rule
   *
   * @param rule
   *        The rule
   * @param node
   *        The origin node
   * @return
   */
  Map<UUID, INode> getCorrespondingNodesOfAllNacs(Rule rule, INode node);

  /**
   * Transformations the petrinet like defined in rule with random match
   *
   * @param petrinet
   *        Petrinet to transform
   * @param rule
   *        Rule to apply to petrinet
   * @return the transformation that was used for transforming (containing
   *         rule, nNet and match)
   */
  Transformation transform(Petrinet net, Rule rule);

  /**
   * Stores the session id of a rule so it can be used in
   * {@link ITransformation#getMappings(int, INode)}
   *
   * @param id
   * @param rule
   */
  void storeSessionId(int id, Rule rule);

  Net getNet(Rule rule, Place place);

  Net getNet(Rule rule, Transition transition);

  Net getNet(Rule rule, PreArc preArc);

  Net getNet(Rule rule, PostArc postArc);
}
