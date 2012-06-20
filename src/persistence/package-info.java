/**
 * <p>
 * The persistence component bundles all functionalities around saving and loading petrinets into local files. 
 * It uses {@link http://de.wikipedia.org/wiki/Java_Architecture_for_XML_Binding Jaxb} which enables typesafe accessing of XML-nodes.
 * Each Type of XML node is represented by a class.
 * The process if marshalling is reading an tree of objects, which are instances of the classes that represent the XML nodes, and 
 * writing its structure and content into the specified XML file.
 * The process of unmarshalling is reading the file, parsing it, finding syntax 
 * errors and also generating a tree of objects, which are instances of the classes that represent the XML nodes.
 * </p>
 * <p>
 *  The most important class in this package is the {@link persistence.Converter Converter}. It takes logical petrinet and rule 
 *  objects and turns them XML node classes and vice versa.
 *  Also quite important is the {@link persistence.Persistence Persistence class}, which makes all the custom node classes 
 *  known to the jaxb framework. Also offering the ultimate safing and loading methods that are used by the engine.
 * </p>
 *  */
@javax.xml.bind.annotation.XmlSchema(namespace = "http://www.pnml.org/version-2009/grammar/pnml", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED, attributeFormDefault = javax.xml.bind.annotation.XmlNsForm.UNQUALIFIED)
package persistence;

