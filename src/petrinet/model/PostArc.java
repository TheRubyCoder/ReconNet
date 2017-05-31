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

package petrinet.model;

/**
 * The arc of a petrinet. Holding information about its marking as well as
 * references to its source and target nodes.
 */
public final class PostArc
  implements IArc {

  /**
   * Maximal weight
   */
  private int weight;

  /**
   * Name of the arc
   */
  private String name;

  /**
   * target transition
   */
  private Transition source;

  /**
   * source place
   */
  private Place target;

  /**
   * Unique id
   */
  private final int id;

  /**
   * Creates a new arc
   * 
   * @param id
   *        Unique id
   * @param source
   *        source node of arc
   * @param target
   *        target node of arc
   * @param name
   *        name of arc
   * @param weight
   *        arc weight
   */
  public PostArc(int id, Transition source, Place target, String name,
    int weight) {

    this.id = id;
    this.source = source;
    this.target = target;
    this.name = name;
    this.weight = weight;
  }

  /*
   * (non-Javadoc)
   * @see petrinet.model.IArc#getName()
   */
  @Override
  public String getName() {

    return this.name;
  }

  /*
   * (non-Javadoc)
   * @see petrinet.model.IArc#getId()
   */
  @Override
  public int getId() {

    return this.id;
  }

  /*
   * (non-Javadoc)
   * @see petrinet.model.IArc#setName(java.lang.String)
   */
  @Override
  public void setName(String name) {

    this.name = name;
  }

  /*
   * (non-Javadoc)
   * @see petrinet.model.IArc#getWeight()
   */
  @Override
  public int getWeight() {

    return weight;
  }

  /*
   * (non-Javadoc)
   * @see petrinet.model.IArc#setWeight(int)
   */
  @Override
  public void setWeight(int weight) {

    if (weight <= 0) {
      throw new IllegalArgumentException();
    }

    this.weight = weight;
  }

  /*
   * (non-Javadoc)
   * @see petrinet.model.IArc#getSource()
   */
  @Override
  public Transition getSource() {

    return source;
  }

  public Transition getTransition() {

    return source;
  }

  /*
   * (non-Javadoc)
   * @see petrinet.model.IArc#getTarget()
   */
  @Override
  public Place getTarget() {

    return target;
  }

  public Place getPlace() {

    return target;
  }

  @Override
  public String toString() {

    return "PostArc [weight=" + weight + ", name=" + name + ", transition="
      + source + ", place=" + target + ", id=" + id + "]";
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object object) {

    if (this == object) {
      return true;
    }

    if (!(object instanceof PostArc)) {
      return false;
    }

    PostArc arc = (PostArc) object;

    return id == arc.getId();
  }
}
