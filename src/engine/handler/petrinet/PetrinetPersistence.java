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

package engine.handler.petrinet;

import java.awt.Color;
import java.awt.geom.Point2D;

import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import engine.ihandler.IPetrinetPersistence;
import exceptions.EngineException;

/**
 * <p>
 * This Class implements engine.ihandler.IPetrinetPersistence, and it is the
 * interface for the Persistence-Component.
 * </p>
 * <p>
 * We wrap the implementation in: PetriManipulationBackend
 * </p>
 * <p>
 * It is a Singleton. (PetrinetPersistence.getInstance)
 * </p>
 * <p>
 * It can be use for all manipulations for a Petrinet.
 * <ul>
 * <li>create[Petrinet|PreArc|PostArc|Place|Transition](..)</li>
 * <li>set[Marking|Pname|Tlb|Tname|Weight](..)</li>
 * </ul>
 * </p>
 * 
 * @author alex (aas772)
 * 
 */
public class PetrinetPersistence implements IPetrinetPersistence {

	private static PetrinetPersistence petrinetPersistence;
	private PetrinetHandler petrinetManipulationBackend;

	private PetrinetPersistence() {
		this.petrinetManipulationBackend = PetrinetHandler.getInstance();
	}

	public static PetrinetPersistence getInstance() {
		if (petrinetPersistence == null) {
			petrinetPersistence = new PetrinetPersistence();
		}

		return petrinetPersistence;
	}

	@Override
	public PreArc createPreArc(int id, Place place, Transition transition) throws EngineException {
		return petrinetManipulationBackend.createPreArc(id, place, transition);
	}

	@Override
	public PostArc createPostArc(int id, Transition transition, Place place) throws EngineException {
		return petrinetManipulationBackend.createPostArc(id, transition, place);
	}

	@Override
	public Place createPlace(int id, Point2D coordinate) throws EngineException {
		return petrinetManipulationBackend.createPlace(id, coordinate);
	}

	@Override
	public int createPetrinet() {
		int petrinetId = petrinetManipulationBackend.createPetrinet();

		return petrinetId;
	}

	@Override
	public Transition createTransition(int id, Point2D coordinate)
			throws EngineException {

		return petrinetManipulationBackend.createTransition(id,
				coordinate);
	}


	@Override
	public void setMarking(int id, Place place, int marking)
			throws EngineException {

		petrinetManipulationBackend.setMarking(id, place, marking);
	}

	@Override
	public void setPname(int id, Place place, String pname)
			throws EngineException {

		petrinetManipulationBackend.setPname(id, place, pname);
	}

	@Override
	public void setTlb(int id, Transition transition, String tlb)
			throws EngineException {

		petrinetManipulationBackend.setTlb(id, transition, tlb);
	}

	@Override
	public void setTname(int id, Transition transition, String tname)
			throws EngineException {

		petrinetManipulationBackend.setTname(id, transition, tname);
	}

	@Override
	public void setWeight(int id, PreArc preArc, int weight) throws EngineException {
		petrinetManipulationBackend.setWeight(id, preArc, weight);
	}

	@Override
	public void setWeight(int id, PostArc postArc, int weight) throws EngineException {
		petrinetManipulationBackend.setWeight(id, postArc, weight);
	}

	@Override
	public void setRnw(int id, Transition transition, IRenew renews)
			throws EngineException {

		petrinetManipulationBackend.setRnw(id, transition, renews);
	}

	@Override
	public void setPlaceColor(int id, Place place, Color color)
			throws EngineException {

		petrinetManipulationBackend.setPlaceColor(id, place, color);
	}

	@Override
	public void setNodeSize(int id, double nodeSize) {
		petrinetManipulationBackend.setNodeSize(id, nodeSize);
	}
}
