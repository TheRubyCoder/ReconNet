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

package engine.ihandler;

import java.util.Map;

import exceptions.EngineException;

public interface ITransformationUnitManipulation {

  /**
   * Creates a TransformationUnit
   *
   * @return Session Id of the transformation unit
   */
  int createTransformationUnit(String fileName, String filePath);

  /**
   * Removes the transformation unit from the session data
   *
   * @param transformationUnitId
   *        Id of the transformation unit
   */
  void removeTransformationUnit(int transformationUnitId);

  /**
   * Gets the fileName of transformation unit for the given id
   *
   * @param id
   *        Id of the transformation unit data
   * @return FileName of the transformation unit
   */
  String getFileName(int transformationUnitId);

  /**
   * Sets the control expression of a transformation unit for the given id
   *
   * @param id
   *        Id of the transformation unit
   * @param controlExpression
   *        the control expression to set
   */
  void
  setControlExpression(int transformationUnitId, String controlExpression);

  /**
   * Gets the control expression of a transformation unit for the given id
   *
   * @param id
   *        Id of the transformation unit
   * @return the control expression to get
   */
  String getControlExpression(int transformationUnitId);

  /**
   * Executes the transformation unit
   *
   * @param transformationUnitId
   *        Id of the transformation unit to execute
   * @param petrinetId
   *        Id of the petrinet on which it should be executed
   * @param ruleNameToId
   *        A map which maps ruleNames to their Session Ids
   */
  void executeTransformationUnit(int transformationUnitId, int petrinetId,
    Map<String, Integer> ruleNameToId)
    throws EngineException;

  /**
   * Sets the maximum number of executions a controlexpression is executed
   * when the asLongAsPossible operator is used
   *
   * @param transformationUnitId
   *        Id of the transformation unit
   * @param executionLimit
   *        maximum number of executions
   */
  void setAsLongAsPossibleExecutionLimit(int transformationUnitId,
    int executionLimit);

  /**
   * Gets the execution limit for asLongAsPossible
   *
   * @param transformationUnitId
   *        Id of the transformation unit
   * @return execution limit for asLongAsPossible
   */
  int getAsLongAsPossibleExecutionLimit(int transformationUnitId);

  /**
   * Sets the upper range of the randomNumberOfTimes operator
   *
   * @param transformationUnitId
   *        Id of the transformation unit
   * @param randomNumberOfTimesUpperRange
   *        upper range of the randomNumberOfTimes operator
   */
  void setRandomNumberOfTimesUpperRange(int transformationUnitId,
    int randomNumberOfTimesUpperRange);

  /**
   * Gets the upper range of the randomNumberOfTimes operator
   *
   * @param transformationUnitId
   *        Id of the transformation unit
   * @return upper range of the randomNumberOfTimes operator
   */
  int getRandomNumberOfTimesUpperRange(int transformationUnitId);

  /**
   * Saves the transformation unit to the file system
   *
   * @param transformationUnitId
   *        Id of the transformation
   */
  void saveToFileSystem(int transformationUnitId)
    throws EngineException;

  /**
   * Loads a transformation unit from the file system
   *
   * @param displayName
   *        displayName under which the loaded transformation unit is created
   * @param filePath
   *        path to the file where the transformation unit is stored
   */
  int loadFromFileSystem(String displayName, String filePath)
    throws EngineException;
}
