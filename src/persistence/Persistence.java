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

package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import petrinet.model.INode;
import petrinet.model.Petrinet;
import transformation.Rule;
import transformation.TransformationUnit;
import engine.attribute.NodeLayoutAttribute;
import engine.ihandler.IPetrinetPersistence;
import engine.ihandler.IRulePersistence;
import gui.PopUp;

/**
 * The Persistence class is the interface between persistence component and
 * engine component. It has high-level methods for saving and loading
 * petrinets and rules
 */
public final class Persistence {

  private Persistence() {

    // not called
  }

  static {
    try {
      context =
        JAXBContext.newInstance(persistence.Pnml.class, Arc.class,
          Converter.class, Graphics.class, InitialMarking.class, Name.class,
          Net.class, Page.class, Place.class, PlaceName.class,
          Position.class, Transition.class, TransitionLabel.class,
          TransitionName.class, TransitionRenew.class, Color.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static JAXBContext context;

  /**
   * Saves a petrinet into a file
   *
   * @param pathAndFilename
   *        String that identifies the file
   * @param petrinet
   *        logical petrinet to be safed
   * @param nodeMap
   *        layout information: color and position of nodes
   * @param nodeSize
   *        size of nodes. This is depending on "zoom" and is important when
   *        loading the file
   * @return <code>true</code> when successful, <code>false</code> when any
   *         exception was thrown
   */
  public static boolean savePetrinet(String pathAndFilename,
    Petrinet petrinet, Map<INode, NodeLayoutAttribute> nodeMap,
    double nodeSize) {

    Map<String, String[]> coordinates = new HashMap<String, String[]>();

    for (Entry<INode, NodeLayoutAttribute> e : nodeMap.entrySet()) {
      String[] coords =
        {String.valueOf(e.getValue().getCoordinate().getX()),
          String.valueOf(e.getValue().getCoordinate().getY()),
          String.valueOf(e.getValue().getColor().getRed()),
          String.valueOf(e.getValue().getColor().getGreen()),
          String.valueOf(e.getValue().getColor().getBlue())};
      coordinates.put(String.valueOf(e.getKey().getId()), coords);
    }

    Pnml pnml =
      Converter.convertPetrinetToPnml(petrinet, coordinates, nodeSize);
    File file = new File(pathAndFilename);

    try {

      Marshaller m = context.createMarshaller();

      DefaultValidationEventHandler eventHandler =
        new javax.xml.bind.helpers.DefaultValidationEventHandler();
      m.setEventHandler(eventHandler);
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      FileWriter fw = new FileWriter(file);
      m.marshal(pnml, fw);
      fw.flush();
      fw.close();

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Loads a petrinet from a file
   *
   * @param pathAndFilename
   *        String that identifies the file
   * @param handler
   *        The engine handler to create and modify the petrinet
   * @return the id of the created petrinet. <code>-1</code> if any exception
   *         was thrown
   */
  public static int loadPetrinet(String pathAndFilename,
    IPetrinetPersistence handler) {

    Pnml pnml = new Pnml();
    try {
      Unmarshaller m = context.createUnmarshaller();

      DefaultValidationEventHandler eventHandler =
        new javax.xml.bind.helpers.DefaultValidationEventHandler();
      m.setEventHandler(eventHandler);

      pnml = (Pnml) m.unmarshal(new File(pathAndFilename));

      return Converter.convertPnmlToPetrinet(pnml, handler);
    } catch (JAXBException e) {
      e.printStackTrace();
      PopUp.popError(e);
    }

    return -1;
  }

  public static boolean saveRuleWithNacs(String pathAndFilename, Rule rule,
    Map<INode, NodeLayoutAttribute> nodeMapL,
    Map<INode, NodeLayoutAttribute> nodeMapK,
    Map<INode, NodeLayoutAttribute> nodeMapR,
    ArrayList<Map<INode, NodeLayoutAttribute>> nodeMapNacs, double kNodeSize) {

    boolean success = false;

    try {

      Map<INode, NodeLayoutAttribute> nodeMapMerged =
        new HashMap<INode, NodeLayoutAttribute>();
      nodeMapMerged.putAll(nodeMapL);
      nodeMapMerged.putAll(nodeMapK);
      nodeMapMerged.putAll(nodeMapR);

      for (Map<INode, NodeLayoutAttribute> nodeMapNac : nodeMapNacs) {

        nodeMapMerged.putAll(nodeMapNac);
      }

      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      Pnml pnml =
        Converter.convertRuleWithNacsToPnml(rule, nodeMapMerged, kNodeSize);

      DefaultValidationEventHandler eventHandler =
        new javax.xml.bind.helpers.DefaultValidationEventHandler();
      m.setEventHandler(eventHandler);

      FileWriter fw = new FileWriter(pathAndFilename);
      m.marshal(pnml, fw);
      fw.flush();
      fw.close();
      success = true;
    } catch (IOException e) {
      PopUp.popError(e);
    } catch (JAXBException e) {
      PopUp.popError(e);
    }

    return success;
  }

  /**
   * Loads a {@linkplain Rule} with nacs from a file
   *
   * @param pathAndFilename
   *        String that identifies the file
   * @param handler
   *        The engine handler to create and modify the rule
   * @return the id of the created rule. <code>-1</code> if any exception was
   *         thrown
   */
  public static int loadRuleWithNacs(String pathAndFilename,
    IRulePersistence handler) {

    Pnml pnml;
    try {
      Unmarshaller m = context.createUnmarshaller();

      DefaultValidationEventHandler eventHandler =
        new javax.xml.bind.helpers.DefaultValidationEventHandler();
      m.setEventHandler(eventHandler);

      pnml = (Pnml) m.unmarshal(new File(pathAndFilename));

      return Converter.convertPnmlToRuleWithNacs(pnml, handler);
    } catch (JAXBException e) {
      e.printStackTrace();
      PopUp.popError(e);
    }

    return 0;
  }

  public static void saveTransformationUnit(
    TransformationUnit transformationUnit, String filePath)
    throws Exception {

    Gson gson = new Gson();
    String jsonString = gson.toJson(transformationUnit);

    FileOutputStream fos = null;

    try {
      fos = new FileOutputStream(filePath);
      fos.write(jsonString.getBytes());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new Exception("Die Datei konnte nicht geöffnet werden.");
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception("Die Datei konnte nicht geöffnet werden.");
    } finally {
      try {
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
        throw new Exception("Fehler beim Schließen der Datei.");
      }
    }

  }

  public static TransformationUnit loadTransformationUnit(String filePath)
    throws Exception {

    Gson gson = new Gson();

    FileInputStream fis = null;

    try {
      fis = new FileInputStream(filePath);
      TransformationUnit transformationUnit =
        gson.fromJson(new InputStreamReader(fis), TransformationUnit.class);
      return transformationUnit;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new Exception("Die Datei konnte nicht geöffnet werden.");
    } catch (JsonIOException e) {
      e.printStackTrace();
      throw new Exception(
        "Die Transformationseinheit konnte nicht geladen werden.");
    } catch (JsonSyntaxException e) {
      e.printStackTrace();
      throw new Exception(
        "Die Transformationseinheit konnte nicht geladen werden.");
    } finally {
      try {
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
        throw new Exception("Fehler beim Schließen der Datei.");
      }
    }
  }

}
