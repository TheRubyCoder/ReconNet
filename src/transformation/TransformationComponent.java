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

import java.util.ArrayList;
import java.util.HashMap;
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
import exceptions.EngineException;

/**
 * Singleton that represents the transformation component<br/>
 * Other components refer to this object to delegate to the transformation
 * component instead of directly refering to the classes within the component
 */
public final class TransformationComponent
  implements ITransformation {

  static final int ARRAYLIST_CAPACITY = 3;

  /** To Store mappings of ids of {@link SessionManager} */
  private Map<Integer, Rule> rules = new HashMap<Integer, Rule>();

  // #################### singleton ##################
  private static TransformationComponent instance;

  private TransformationComponent() {

  }

  static {
    instance = new TransformationComponent();
  }

  public static ITransformation getTransformation() {

    return instance;
  }

  // #################################################

  // ### instance methods (UML-Interface) ###########
  @Override
  public Rule createRule() {

    return new Rule();
  }

  @Override
  public List<INode> getMappings(Rule rule, INode node) {

    List<INode> mappings = new ArrayList<INode>(ARRAYLIST_CAPACITY);

    mappings.add(null);
    mappings.add(null);
    mappings.add(null);
    mappings.add(null);

    if (node instanceof Place) {
      Net net = getNet(rule, (Place) node);

      switch (net) {
      case L:
        mappings.set(0, node);
        mappings.set(1, rule.fromLtoK((Place) node));
        mappings.set(2, rule.fromLtoR((Place) node));
        break;

      case K:
        mappings.set(0, rule.fromKtoL((Place) node));
        mappings.set(1, node);
        mappings.set(2, rule.fromKtoR((Place) node));
        break;

      case R:
        mappings.set(0, rule.fromRtoL((Place) node));
        mappings.set(1, rule.fromRtoK((Place) node));
        mappings.set(2, node);
        break;
      default:
        break;
      }
    } else if (node instanceof Transition) {
      Net net = getNet(rule, (Transition) node);

      switch (net) {
      case L:
        mappings.set(0, node);
        mappings.set(1, rule.fromLtoK((Transition) node));
        mappings.set(2, rule.fromLtoR((Transition) node));
        break;

      case K:
        mappings.set(0, rule.fromKtoL((Transition) node));
        mappings.set(1, node);
        mappings.set(2, rule.fromKtoR((Transition) node));
        break;

      case R:
        mappings.set(0, rule.fromRtoL((Transition) node));
        mappings.set(1, rule.fromRtoK((Transition) node));
        mappings.set(2, node);
        break;
      default:
        break;
      }
    }

    return mappings;
  }

  @Override
  public List<IArc> getMappings(Rule rule, IArc arc) {

    List<IArc> mappings = new ArrayList<IArc>(ARRAYLIST_CAPACITY);

    mappings.add(null);
    mappings.add(null);
    mappings.add(null);
    mappings.add(null);

    if (arc instanceof PreArc) {
      Net net = getNet(rule, (PreArc) arc);

      switch (net) {
      case L:
        mappings.set(0, arc);
        mappings.set(1, rule.fromLtoK((PreArc) arc));
        mappings.set(2, rule.fromLtoR((PreArc) arc));
        break;

      case K:
        mappings.set(0, rule.fromKtoL((PreArc) arc));
        mappings.set(1, arc);
        mappings.set(2, rule.fromKtoR((PreArc) arc));
        break;

      case R:
        mappings.set(0, rule.fromRtoL((PreArc) arc));
        mappings.set(1, rule.fromRtoK((PreArc) arc));
        mappings.set(2, arc);
        break;
      default:
        break;
      }
    } else if (arc instanceof PostArc) {
      Net net = getNet(rule, (PostArc) arc);

      switch (net) {
      case L:
        mappings.set(0, arc);
        mappings.set(1, rule.fromLtoK((PostArc) arc));
        mappings.set(2, rule.fromLtoR((PostArc) arc));
        break;

      case K:
        mappings.set(0, rule.fromKtoL((PostArc) arc));
        mappings.set(1, arc);
        mappings.set(2, rule.fromKtoR((PostArc) arc));
        break;

      case R:
        mappings.set(0, rule.fromRtoL((PostArc) arc));
        mappings.set(1, rule.fromRtoK((PostArc) arc));
        mappings.set(2, arc);
        break;
      default:
        break;
      }
    }

    return mappings;
  }

  @Override
  public List<INode> getMappings(int ruleId, INode node) {

    return getMappings(rules.get(ruleId), node);
  }

  @Override
  public List<INode> getAllNodeRepresentations(int ruleId, INode node) {

    List<INode> result = new ArrayList<INode>();

    if (node instanceof Place) {

      result.addAll(this.getAllPlaceRepresentations(ruleId, (Place) node));

    } else if (node instanceof Transition) {

      result.addAll(this.getAllTransitionRepresentations(ruleId,
        (Transition) node));

    }

    return result;
  }

  private List<INode> getAllPlaceRepresentations(int ruleId, Place place) {

    Rule rule = rules.get(ruleId);

    List<INode> nodes = new ArrayList<INode>();

    // add place itself
    nodes.add(place);

    if (rule.getL().containsPlace(place)) {
      Place kPlace = rule.fromLtoK(place);
      Place rPlace = rule.fromLtoR(place);
      if (kPlace != null) {
        nodes.add(kPlace);
      }
      if (rPlace != null) {
        nodes.add(rPlace);
      }
      nodes.addAll(rule.fromLtoNAC(place));

    } else if (rule.getK().containsPlace(place)) {
      Place lPlace = rule.fromKtoL(place);
      Place rPlace = rule.fromKtoR(place);
      if (lPlace != null) {
        nodes.add(lPlace);
        nodes.addAll(rule.fromLtoNAC(lPlace));
      }
      if (rPlace != null) {
        nodes.add(rPlace);
      }

    } else if (rule.getR().containsPlace(place)) {
      Place lPlace = rule.fromRtoL(place);
      Place kPlace = rule.fromRtoK(place);
      if (lPlace != null) {
        nodes.add(lPlace);
        nodes.addAll(rule.fromLtoNAC(lPlace));
      }
      if (kPlace != null) {
        nodes.add(kPlace);
      }

    } else {
      // place has origin in NAC
      for (NAC nac : rule.getNACs()) {

        if (nac.getNac().containsPlace(place)) {
          Place lPlace = nac.fromNacToL(place);
          Place kPlace = rule.fromLtoK(lPlace);
          Place rPlace = rule.fromKtoR(kPlace);

          if (lPlace != null) {
            nodes.add(lPlace);
          }
          if (kPlace != null) {
            nodes.add(kPlace);
          }
          if (rPlace != null) {
            nodes.add(rPlace);
          }

        }

      }

    }

    return nodes;
  }

  private List<INode> getAllTransitionRepresentations(int ruleId,
    Transition transition) {

    Rule rule = rules.get(ruleId);

    List<INode> nodes = new ArrayList<INode>();

    // add place itself
    nodes.add(transition);

    if (rule.getL().containsTransition(transition)) {
      Transition kTransition = rule.fromLtoK(transition);
      Transition rTransition = rule.fromLtoR(transition);
      if (kTransition != null) {
        nodes.add(kTransition);
      }
      if (rTransition != null) {
        nodes.add(rTransition);
      }
      nodes.addAll(rule.fromLtoNAC(transition));

    } else if (rule.getK().containsTransition(transition)) {
      Transition lTransition = rule.fromKtoL(transition);
      Transition rTransition = rule.fromKtoR(transition);
      if (lTransition != null) {
        nodes.add(lTransition);
        nodes.addAll(rule.fromLtoNAC(lTransition));
      }
      if (rTransition != null) {
        nodes.add(rTransition);
      }

    } else if (rule.getR().containsTransition(transition)) {
      Transition lTransition = rule.fromRtoL(transition);
      Transition kTransition = rule.fromRtoK(transition);
      if (lTransition != null) {
        nodes.add(lTransition);
        nodes.addAll(rule.fromLtoNAC(lTransition));
      }
      if (kTransition != null) {
        nodes.add(kTransition);
      }

    } else {
      // transition has origin in NAC
      for (NAC nac : rule.getNACs()) {

        if (nac.getNac().containsTransition(transition)) {
          Transition lTransition = nac.fromNacToL(transition);
          Transition kTransition = rule.fromLtoK(lTransition);
          Transition rTransition = rule.fromKtoR(kTransition);

          if (lTransition != null) {
            nodes.add(lTransition);
          }
          if (kTransition != null) {
            nodes.add(kTransition);
          }
          if (rTransition != null) {
            nodes.add(rTransition);
          }

        }

      }

    }

    return nodes;
  }

  @Override
  public Map<UUID, INode>
    getCorrespondingNodesOfAllNacs(Rule rule, INode node) {

    Map<UUID, INode> nodes = new HashMap<UUID, INode>();

    if (node instanceof Place) {
      Net net = getNet(rule, (Place) node);

      INode lNode = null;

      switch (net) {
      case L:
        lNode = node;
        break;

      case K:
        lNode = rule.fromKtoL((Place) node);
        break;

      case R:
        lNode = rule.fromRtoL((Place) node);
        break;

      default:
        break;
      }

      for (NAC nac : rule.getNACs()) {

        INode nacNode = nac.fromLtoNac((Place) lNode);

        if (nacNode != null) {
          nodes.put(nac.getId(), nacNode);
        }
      }
    }

    if (node instanceof Transition) {
      Net net = getNet(rule, (Transition) node);

      INode lNode = null;

      switch (net) {
      case L:
        lNode = node;
        break;

      case K:
        lNode = rule.fromKtoL((Transition) node);
        break;

      case R:
        lNode = rule.fromRtoL((Transition) node);
        break;

      default:
        break;
      }

      for (NAC nac : rule.getNACs()) {

        INode nacNode = nac.fromLtoNac((Transition) lNode);

        if (nacNode != null) {
          nodes.put(nac.getId(), nacNode);
        }
      }
    }

    return nodes;
  }

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
  @Override
  public Transformation transform(Petrinet net, Rule rule) {

    Transformation transformation;
    // WLAD Aenderung 1, match mit oder ohne nac
    if (rule.getNACs().isEmpty()) {
      transformation =
        Transformation.createTransformationWithAnyMatch(net, rule);
    } else {
      transformation = Transformation.createTransformationWithNAC(net, rule);
    }
    if (transformation == null) {
      return null;
    }

    try {
      return transformation.transform();
    } catch (EngineException contact) {
      contact.printStackTrace();
      System.out.println("Contact condition has been broken");
      return null;
    }
  }

  @Override
  public void storeSessionId(int id, Rule rule) {

    rules.put(id, rule);
  }

  public Net getNet(Rule rule, Place place) {

    if (rule.getL().containsPlace(place)) {
      return Net.L;

    } else if (rule.getK().containsPlace(place)) {
      return Net.K;

    } else if (rule.getR().containsPlace(place)) {
      return Net.R;
    }

    for (NAC nac : rule.getNACs()) {
      if (nac.getNac().containsPlace(place)) {
        return Net.NAC;
      }
    }

    System.out.println("unknown place");
    return null;
  }

  public Net getNet(Rule rule, Transition transition) {

    if (rule.getL().containsTransition(transition)) {
      return Net.L;

    } else if (rule.getK().containsTransition(transition)) {
      return Net.K;

    } else if (rule.getR().containsTransition(transition)) {
      return Net.R;
    }

    System.out.println("unknown place");
    return null;
  }

  public Net getNet(Rule rule, PreArc preArc) {

    if (rule.getL().containsPreArc(preArc)) {
      return Net.L;

    } else if (rule.getK().containsPreArc(preArc)) {
      return Net.K;

    } else if (rule.getR().containsPreArc(preArc)) {
      return Net.R;
    }

    System.out.println("unknown place");
    return null;
  }

  public Net getNet(Rule rule, PostArc postArc) {

    if (rule.getL().containsPostArc(postArc)) {
      return Net.L;

    } else if (rule.getK().containsPostArc(postArc)) {
      return Net.K;

    } else if (rule.getR().containsPostArc(postArc)) {
      return Net.R;
    }

    System.out.println("unknown place");
    return null;
  }

}
