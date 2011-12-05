package engine.util;

import java.util.HashMap;
import java.util.Map;

import petrinet.INode;
import engine.attribute.NodeLayoutAttribute;
import engine.data.JungData;

public class Utility {

	//+(jung : Engine.Engine.JungData) : Map<INode, NodeLayoutAttribute>
	
	/**
	 * Gets the LayoutAttributes of all Nodes.
	 * 
	 * @param jung JungData
	 * @return Map<INode, NodeLayoutAttribute>  map with INode as Key and his LayoutAttribute as Value
	 */
	public Map<INode, NodeLayoutAttribute> getNodeLayoutAttributes(JungData jung){
		return new HashMap<INode, NodeLayoutAttribute>();
	}
	
}
