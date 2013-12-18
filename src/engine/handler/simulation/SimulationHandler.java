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

package engine.handler.simulation;

import static exceptions.Exceptions.exception;
import static exceptions.Exceptions.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.ITransformation;
import transformation.Rule;
import transformation.Transformation;
import transformation.TransformationComponent;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.ihandler.ISimulation;
import engine.session.SessionManager;
import exceptions.EngineException;
import exceptions.ShowAsInfoException;

public class SimulationHandler implements ISimulation {

    /** Session manager holding session data */
    private final SessionManager sessionManager;

    /** The transformation component (singleton instance) */
    private final ITransformation transformationComponent;

    /** Singleton instance of this class */
    private static SimulationHandler simulation;

    /**
     * Random number generator to choose a random rule to be applied or to
     * choose whether to fire or to transform
     */
    private Random random = new Random();

    /** The distance for nodes that are added through application of a rule */
    public static final int DISTANCE_WHEN_ADDED = 100;

    private SimulationHandler() {
        sessionManager = SessionManager.getInstance();
        transformationComponent = TransformationComponent.getTransformation();
    }

    /** Returns the singleton instance */
    public static SimulationHandler getInstance() {
        if (simulation == null)
            simulation = new SimulationHandler();

        return simulation;
    }

    @Override
    public int createSimulationSession(int id) {
        return 0;
    }

    @Override
    public void fire(int id, int n) throws EngineException {

        PetrinetData petrinetData = sessionManager.getPetrinetData(id);

        // Test: is id valid
        if (petrinetData == null) {
            exception("SimulationHandler - id of the Petrinet is wrong");
            return;
        }

        Petrinet petrinet = petrinetData.getPetrinet();
        for (int i = 0; i < n; i++) {

            // if there are no more activated transitions the
            // petrinet.fire()method will throw a IllegalState Exception
            // this method catches the IllegalState Exception and throws a
            // EngineException so the GUI only will get EngineExceptions
            try {
                petrinet.fire();
            } catch (IllegalStateException e) {
                exception(e.getMessage());
            }
        }
    }

    @Override
    public void save(int id, String path, String filename, String format) throws EngineException {
        throw new UnsupportedOperationException("Simulation data are not implemented");
    }

    @Override
    public void transform(int id, Collection<Integer> ruleIDs, int n) throws EngineException {

        if (ruleIDs.isEmpty()) {
            info("Es sind keine Regeln ausgewählt");
            
            PetrinetData petrinetData = sessionManager.getPetrinetData(id);

            // Test: is id valid
            if (petrinetData == null) {
                exception("SimulationHandler - id of the Petrinet is wrong");
                return;
            }

            Petrinet petrinet = petrinetData.getPetrinet();
            JungData jungData = petrinetData.getJungData();
            List<Rule> sortedRules = new ArrayList<Rule>();
            for (Integer ruleId : ruleIDs) {
                sortedRules.add(sessionManager.getRuleData(ruleId).getRule());
            }
            sortedRules = Collections.unmodifiableList(sortedRules);

            for (int i = 0; i < n; i++) {
                // Make sure a random rule is selected each time
                List<Rule> shuffledRules = new ArrayList<Rule>(sortedRules);
                Collections.shuffle(shuffledRules);
                // Find matching rule and apply it
                System.out.println("matchingrule start");
                Transformation transformation = findMatchingRule(shuffledRules, petrinet);
                System.out.println("matchingrule end");
                // Remove deleted elements from display
                jungData.deleteDataOfMissingElements(petrinet);
                // Add new elements to display
                fillJungDataWithNewElements(jungData, transformation.getAddedPlaces(), transformation.getAddedTransitions(), transformation.getAddedPreArcs(),
                        transformation.getAddedPostArcs());
            }

        }

        PetrinetData petrinetData = sessionManager.getPetrinetData(id);

        // Test: is id valid
        if (petrinetData == null) {
            exception("SimulationHandler - id of the Petrinet is wrong");
            return;
        }

        Petrinet petrinet = petrinetData.getPetrinet();
        JungData jungData = petrinetData.getJungData();
        List<Rule> sortedRules = new ArrayList<Rule>();
        for (Integer ruleId : ruleIDs) {
            sortedRules.add(sessionManager.getRuleData(ruleId).getRule());
        }
        sortedRules = Collections.unmodifiableList(sortedRules);

        for (int i = 0; i < n; i++) {
            // Make sure a random rule is selected each time
            List<Rule> shuffledRules = new ArrayList<Rule>(sortedRules);
            Collections.shuffle(shuffledRules);
            // Find matching rule and apply it
            Transformation transformation = findMatchingRule(shuffledRules, petrinet);
            if (!(transformation == null)) {// p2 nach präsentation
                // Remove deleted elements from display
                jungData.deleteDataOfMissingElements(petrinet);
                // Add new elements to display
                fillJungDataWithNewElements(jungData, transformation.getAddedPlaces(), transformation.getAddedTransitions(), transformation.getAddedPreArcs(),
                        transformation.getAddedPostArcs());
            } else {// p2 nach präsentation
                info("Keine der Regeln passt auf das Petrinetz");// p2 nach
                                                                 // präsentation
            }// p2 nach präsentation
        }
        
        // Restart the layouting process
        // this gets done here because we know no better place to do it in
        
        FRLayout<INode, IArc> frLayout = (FRLayout<INode, IArc>) jungData.getJungLayout();
        frLayout.initialize();
        for(int i = 0; i < 700; i++){
        	frLayout.step();
        }
    }

    /**
     * After a rule has been applied on a petrinet, the added nodes are not part
     * of the display (<code>jungData</code>). They must be added.
     * 
     * @param jungData
     * @param addedNodes
     * @param addedArcs
     */
    private void fillJungDataWithNewElements(JungData jungData, Set<Place> addedPlaces, Set<Transition> addedTransitions, Set<PreArc> addedPreArcs,
            Set<PostArc> addedPostArcs) {

        // Add places at "random" position
        for (Place place : addedPlaces) {
            jungData.createPlace(place);
        }

        // Add transitions at "random" position
        for (Transition transition : addedTransitions) {
            jungData.createTransition(transition);
        }

        // Add arcs
        for (PreArc arc : addedPreArcs) {
            // Find out which method to call
            jungData.createArc(arc, arc.getSource(), arc.getTarget());
        }

        for (PostArc arc : addedPostArcs) {
            // Find out which method to call
            jungData.createArc(arc, arc.getSource(), arc.getTarget());
        }
    }

    /**
     * Finds a rule out of <code>shuffledRules</code> that can be applied on
     * <code>petrinet</code>
     * 
     * @param shuffledRules
     * @param petrinet
     * @return Transformation that was used for altering the petrinet
     * @throws ShowAsInfoException
     *             if none of the rules matches with the petrinet (user friendly
     *             message)
     */
    private Transformation findMatchingRule(List<Rule> shuffledRules, Petrinet petrinet) {
        Iterator<Rule> ruleIterator = shuffledRules.iterator();
        while (ruleIterator.hasNext()) {
            Rule rule = (Rule) ruleIterator.next();
            Transformation transformation = transformationComponent.transform(petrinet, rule);
            if (transformation == null) {
                // go on with iteration
            } else {
                return transformation;
            }
        }
        // info("Keine der Regeln passt auf das Petrinetz"); p2 nach
        // präsentation
        return null;
    }

    @Override
    public void fireOrTransform(int id, Collection<Integer> ruleIDs, int n) throws EngineException {
        for (int i = 0; i < n; i++) {
            // try fire first? 50 : 50 chance
            if (random.nextFloat() < 0.5d) {
                try {
                    fire(id, 1);
                } catch (EngineException ex) {
                    // fire failed? OK, try transform
                    transform(id, ruleIDs, 1);
                }
            } else {
                // try transform first? 50 : 50 chance
                try {
                    transform(id, ruleIDs, 1);
                } catch (EngineException ex) {
                    // transform failed? OK, try fire
                    fire(id, 1);
                } catch (ShowAsInfoException ex) {
                    // transform failed? OK, try fire
                    fire(id, 1);

                }
            }
        }
    }

}
