package petrinetze.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import petrinetze.IArc;
import petrinetze.IPlace;
import petrinetze.IRenew;
import petrinetze.ITransition;

import engine.Engine;
import engine.Simulation;
import engine.impl.*;

/**
 * Testing example with coffee machine and tea. Custom renew function which changes the label depending on time of day.
 */
public class CoffeeNetTest {
	public final Petrinet COFFEE_NET;
	
	class CoffeeTypeRenew implements IRenew {
		@Override
		public String renew(String tlb) {
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

		@Override
		public boolean isTlbValid(String tlb) {
			return true;
		}
		
	}
	
	public CoffeeNetTest() {
		 COFFEE_NET = new Petrinet();
		 IPlace ready = COFFEE_NET.createPlace("ready");
		 
		 ITransition cookCoffee = COFFEE_NET.createTransition("Coffee", new CoffeeTypeRenew());
		 COFFEE_NET.createArc("", ready, cookCoffee);
		 
		 ITransition cookTea = COFFEE_NET.createTransition("Tea");
		 COFFEE_NET.createArc("", ready, cookTea);
		 
		 IPlace availableCoffees = COFFEE_NET.createPlace("Available Coffees");
		 COFFEE_NET.createArc("", availableCoffees, cookCoffee);
		 
		 IPlace availableTeas = COFFEE_NET.createPlace("Available Teas");
		 COFFEE_NET.createArc("", availableTeas, cookTea);
		 
		 IPlace coffeesCooked = COFFEE_NET.createPlace("Coffees Cooked");
		 COFFEE_NET.createArc("", cookCoffee, coffeesCooked);
		 
		 ITransition orderCoffee = COFFEE_NET.createTransition("Order Coffee");
		 COFFEE_NET.createArc("", coffeesCooked, orderCoffee).setMark(50);
		 
		 IPlace blinkBlink = COFFEE_NET.createPlace("* Blink * Blink *");
		 COFFEE_NET.createArc("", orderCoffee, blinkBlink);
		 
		 ITransition refillCoffee = COFFEE_NET.createTransition("Refill Coffee");
		 COFFEE_NET.createArc("", blinkBlink, refillCoffee);
		 COFFEE_NET.createArc("", refillCoffee, availableCoffees).setMark(50);
		 
		 IPlace coffeeReady = COFFEE_NET.createPlace("Coffee Ready");
		 COFFEE_NET.createArc("", cookCoffee, coffeeReady);
		 
		 IPlace teaReady = COFFEE_NET.createPlace("Tea Ready");
		 COFFEE_NET.createArc("", cookTea, teaReady);
		 
		 Engine engine = new EngineFactoryImpl().createEngine(COFFEE_NET);
		 Simulation sim =  engine.getSimulation();
		 
		 // set start tokens
		 availableCoffees.setMark(1);
		 coffeesCooked.setMark(49);
		 ready.setMark(1);
		 
		 // first test
		 assert(cookCoffee.isActivated());
		 sim.step(cookCoffee);
		 assert(!cookCoffee.isActivated());
		 assert(ready.getMark() == 0);
		 assert(coffeeReady.getMark() == 1);
		 assert(coffeesCooked.getMark() == 50);
		 System.out.printf("Cooked %s!\n", cookCoffee.getTlb());
		 
		 // second test
		 assert(orderCoffee.isActivated());
		 sim.step(orderCoffee);
		 assert(!orderCoffee.isActivated());
		 assert(blinkBlink.getMark() == 1);
		 assert(coffeesCooked.getMark() == 0);
	}
	
	public static void main(String[] foo) {
		new CoffeeNetTest();
	}
}
