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

package gui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;

import exceptions.ShowAsInfoException;
import exceptions.ShowAsWarningException;

/**
 * Main Class of GUI. It simply opens and configures the main window
 */
public final class Main {

  private Main() {

    // not called
  }

  /**
   * With this EventQueue all uncaught exceptions are printed in the console
   * and displayed to the user
   */
  private static class EventQueueProxy
    extends EventQueue {

    protected void dispatchEvent(AWTEvent newEvent) {

      try {
        super.dispatchEvent(newEvent);
      } catch (ShowAsWarningException warning) {
        PopUp.popWarning(warning);
      } catch (ShowAsInfoException info) {
        PopUp.popInfo(info);
      } catch (Throwable t) {
        t.printStackTrace();
        PopUp.popError(t);
      }
    }
  }

  public static void main(String[] args) {

    // Make SWING use the petrinet event queue
    EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
    queue.push(new EventQueueProxy());

    // invokeLater makes all UI-Updates thread safe
    SwingUtilities.invokeLater(new Runnable() {

      public void run() {

        MainWindow.getInstance();
      }
    });
  }
}
