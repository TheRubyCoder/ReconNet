package engine.attribute;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The ColorGenerator generates colors for Places. First it generates a certain
 * sequence of colors, afterwards it generates pseudo-random colors. No color is
 * generated twice. A ColorGenerator always generates the same sequence if
 * colors. (Fixed seed)
 */
public class ColorGenerator implements Iterator<Color>{
	
	/** Holding the already used colors so no color is used twice */
	private List<Color> usedColors = new LinkedList<Color>();
	
	/** Holding the colors that are used before starting generating pseudo-random colors*/
	private List<Color> fixedColors = new LinkedList<Color>();
	
	/** Holding the descriptions for {@link ColorGenerator#fixedColors}*/
	private Map<Color,String> colorDescriptions = new HashMap<Color,String>();
	
	private Random random;
	
	public ColorGenerator() {
		random = new Random(0);
		fixedColors.add(Color.RED);
		colorDescriptions.put(Color.RED, "Rot");
		fixedColors.add(Color.BLUE);
		colorDescriptions.put(Color.BLUE, "Blau");
		fixedColors.add(Color.CYAN);
		colorDescriptions.put(Color.CYAN, "Türkis");
		fixedColors.add(Color.GREEN);
		colorDescriptions.put(Color.GREEN, "Grün");
		fixedColors.add(Color.MAGENTA);
		colorDescriptions.put(Color.MAGENTA, "Violett");
		fixedColors.add(Color.ORANGE);
		colorDescriptions.put(Color.ORANGE, "Orange");
		fixedColors.add(Color.YELLOW);
		colorDescriptions.put(Color.YELLOW, "Gelb");
		
		fixedColors.add(Color.RED.darker().darker());
		colorDescriptions.put(Color.RED.darker().darker(), "Dunkelrot");
		fixedColors.add(Color.BLUE.darker().darker());
		colorDescriptions.put(Color.BLUE.darker().darker(), "Dunkelblau");
		fixedColors.add(Color.CYAN.darker().darker());
		colorDescriptions.put(Color.CYAN.darker().darker(), "Dunkeltürkis");
		fixedColors.add(Color.GREEN.darker().darker());
		colorDescriptions.put(Color.GREEN.darker().darker(), "Dunkelgrün");
		fixedColors.add(Color.MAGENTA.darker().darker());
		colorDescriptions.put(Color.MAGENTA.darker().darker(), "Dunkelviolett");
		fixedColors.add(Color.ORANGE.darker().darker());
		colorDescriptions.put(Color.ORANGE.darker().darker(), "Dunkelorange");
		fixedColors.add(Color.YELLOW.darker().darker());
		colorDescriptions.put(Color.YELLOW.darker().darker(), "Dunkelgelb");
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Color next() {
		Color next = null;
		if (usedColors.size() < fixedColors.size()) {
			next = fixedColors.get(usedColors.size());
		} else {
			next = randomColor();
			while(usedColors.contains(next)){
				next = randomColor();
			}
		}
		usedColors.add(next);
		return next;
	}
	
	private Color randomColor(){
		return new Color(random.nextFloat(),random.nextFloat(), random.nextFloat());
	}
	
	public List<Color> getFixedColors() {
		return fixedColors;
	}
	
	public String getDescription(Color color) {
		String description = "Zufällige Farbe";
		if(fixedColors.contains(color)){
			description = colorDescriptions.get(color);
		}
		return description;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("You do not need to remove Colors");
	}

}
