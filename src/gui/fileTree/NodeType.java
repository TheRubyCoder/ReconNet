package gui.fileTree;

/**
 *Enumeration of the different kinds of {@link PetriTreeNode}.
 */
public enum NodeType {
    
    /**
     * Node is tree root. 
     */
    ROOT, 
    
    /**
     * Node is root for all rule nodes.
     */
    RULE_ROOT,
    
    /**
     * Node is a rule.
     */
    RULE, 
    
    /**
     * Node is root for all net nodes.
     */
    NET_ROOT, 
    
    /**
     * Node is a net.
     */
    NET, 
    
    /**
     * Node is a NAC.
     */
    NAC
}