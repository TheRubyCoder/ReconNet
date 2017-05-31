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

package petrinetze.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;

import petrinet.model.IRenew;

/**
 * Testing example with coffee machine and tea. Custom renew function which
 * changes the label depending on time of day.
 */
public final class CoffeeNetTest {

  // public final Petrinet COFFEE_NET;

  private CoffeeNetTest() {

    // not called
  }

  class CoffeeTypeRenew
    implements IRenew {

    @Override
    // CHECKSTYLE:OFF - No need to check for MagicNumbers
    public String
      renew(String tlb) {

      int hour = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);
      if (hour < 6) {
        return "doppelter Espresso";
      } else if (hour >= 6 && hour < 9) {
        return "Espresso (einfach)";
      } else if (hour >= 9 && hour < 11) {
        return "Caramell Macciato";
      } else if (hour >= 11 && hour < 16) {
        return "Filterkaffee";
      } else if (hour >= 16 && hour < 18) {
        return "schwarzer Tee";
      } else if (hour >= 18 && hour < 24) {
        return "Kräutertee";
      }
      return "error";
    }

    // CHECKSTYLE:ON

    @Override
    public boolean isTlbValid(String tlb) {

      return true;
    }

    @Override
    public String toGUIString() {

      return "coffee";
    }

  }

  // public CoffeeNetTest() {

  // TODO: Use engine!!!

  // COFFEE_NET = PetrinetComponent.getPetrinet().createPetrinet();
  // Place ready = COFFEE_NET.createPlace("ready");
  //
  // Transition cookCoffee = COFFEE_NET.createTransition("Coffee", new
  // CoffeeTypeRenew());
  // COFFEE_NET.createArc("", ready, cookCoffee);
  //
  // Transition cookTea = COFFEE_NET.createTransition("Tea");
  // COFFEE_NET.createArc("", ready, cookTea);
  //
  // Place availableCoffees = COFFEE_NET.createPlace("Available Coffees");
  // COFFEE_NET.createArc("", availableCoffees, cookCoffee);
  //
  // Place availableTeas = COFFEE_NET.createPlace("Available Teas");
  // COFFEE_NET.createArc("", availableTeas, cookTea);
  //
  // Place coffeesCooked = COFFEE_NET.createPlace("Coffees Cooked");
  // COFFEE_NET.createArc("", cookCoffee, coffeesCooked);
  //
  // Transition orderCoffee = COFFEE_NET.createTransition("Order Coffee");
  // COFFEE_NET.createArc("", coffeesCooked, orderCoffee).setMark(50);
  //
  // Place blinkBlink = COFFEE_NET.createPlace("* Blink * Blink *");
  // COFFEE_NET.createArc("", orderCoffee, blinkBlink);
  //
  // Transition refillCoffee = COFFEE_NET.createTransition("Refill Coffee");
  // COFFEE_NET.createArc("", blinkBlink, refillCoffee);
  // COFFEE_NET.createArc("", refillCoffee, availableCoffees).setMark(50);
  //
  // Place coffeeReady = COFFEE_NET.createPlace("Coffee Ready");
  // COFFEE_NET.createArc("", cookCoffee, coffeeReady);
  //
  // Place teaReady = COFFEE_NET.createPlace("Tea Ready");
  // COFFEE_NET.createArc("", cookTea, teaReady);
  //
  //
  // SimulationHandler sim =
  // engine.handler.simulation.SimulationHandler.getInstance();
  // sim.
  //
  // // set start tokens
  // availableCoffees.setMark(1);
  // coffeesCooked.setMark(49);
  // ready.setMark(1);
  //
  // // first test
  // assert(cookCoffee.isActivated());
  // sim.fire(COFFEE_NET.getId(), 1);
  // assert(!cookCoffee.isActivated());
  // assert(ready.getMark() == 0);
  // assert(coffeeReady.getMark() == 1);
  // assert(coffeesCooked.getMark() == 50);
  // System.out.printf("Cooked %s!\n", cookCoffee.getTlb());
  //
  // // second test
  // assert(orderCoffee.isActivated());
  // sim.fire(orderCoffee);
  // assert(!orderCoffee.isActivated());
  // assert(blinkBlink.getMark() == 1);
  // assert(coffeesCooked.getMark() == 0);
  // }

  public static void main(String[] foo) {

    new CoffeeNetTest();
  }
}
