/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veraenderung, sind
 * unter den folgenden Bedingungen zulaessig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare muessen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare muessen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden duerfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFUEGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FUER
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FUER IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFAELLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHAEDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGUETERN ODER -DIENSTLEISTUNGEN; EINSCHRAENKUNG DER NUTZUNGSFAEHIGKEIT;
 * VERLUST VON NUTZUNGSFAEHIGKEIT; DATEN; PROFIT ODER GESCHAEFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLAESSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
 * BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE
 * MOEGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
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
 * AS IS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FUER ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package engine.attribute;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The ColorGenerator generates colors for Places. First it generates a
 * certain sequence of colors, afterwards it generates pseudo-random colors.
 * No color is generated twice. A ColorGenerator always generates the same
 * sequence of colors. (Fixed seed)
 */
public class ColorGenerator
  implements Iterator<Color> {

  /** Holding the already used colors so no color is used twice. */
  private List<Color> usedColors = new LinkedList<Color>();

  /**
   * Holding the colors that are used before starting generating pseudo-random
   * colors
   */
  private List<Color> fixedColors = new LinkedList<Color>();

  /** Holding the descriptions for {@link ColorGenerator#fixedColors}. */
  private Map<Color, String> colorDescriptions = new HashMap<Color, String>();

  private Random random;

  public ColorGenerator() {

    random = new Random(0);
    fixedColors.add(Color.RED);
    colorDescriptions.put(Color.RED, "Rot");
    fixedColors.add(Color.BLUE);
    colorDescriptions.put(Color.BLUE, "Blau");
    fixedColors.add(Color.CYAN);
    colorDescriptions.put(Color.CYAN, "Tuerkis");
    fixedColors.add(Color.GREEN);
    colorDescriptions.put(Color.GREEN, "Gruen");
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
    colorDescriptions.put(Color.CYAN.darker().darker(), "Dunkeltuerkis");
    fixedColors.add(Color.GREEN.darker().darker());
    colorDescriptions.put(Color.GREEN.darker().darker(), "Dunkelgruen");
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
      while (usedColors.contains(next)) {
        next = randomColor();
      }
    }
    usedColors.add(next);
    return next;
  }

  private Color randomColor() {

    Color c =
      new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());

    return c;
  }

  public List<Color> getFixedColors() {

    return fixedColors;
  }

  public String getDescription(Color color) {

    String description = "Zufaellige Farbe";
    if (fixedColors.contains(color)) {
      description = colorDescriptions.get(color);
    }
    return description;
  }

  @Override
  public void remove() {

    throw new UnsupportedOperationException(
      "You do not need to remove Colors");
  }

}
