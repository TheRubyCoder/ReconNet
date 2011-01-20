package gui;

import com.sun.java.swing.SwingUtilities3;
import engine.EditMode;
import engine.Engine;
import petrinetze.IPetrinet;
import transformation.IRule;

import javax.swing.tree.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 07.01.11
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public class PetrinetTreeModel extends DefaultTreeModel {

    public static class Named<T> {

        private String name;

        private T value;

        public Named(String name, T value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Named named = (Named) o;

            if (name != null ? !name.equals(named.name) : named.name != null) return false;
            if (value != null ? !value.equals(named.value) : named.value != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

    static class RootNode extends DefaultMutableTreeNode {

        RootNode() {
            super("Petrinetze", true);
        }

        public PetrinetNode addPetrinet(String name, Engine engine) {
            final PetrinetNode node = new PetrinetNode(name, engine);
            add(node);
            return node;
        }

        public List<Engine> getChildren() {
            final List<Engine> res = new ArrayList<Engine>(children.size());
            for (Object node : children) {
                if (node instanceof PetrinetNode) {
                    res.add(((PetrinetNode)node).getEngine());
                }
            }

            return res;
        }
        
        public void setEditMode(EditMode mode){
            for(Object node : children){
                if(node instanceof PetrinetNode){
                    ((PetrinetNode)node).setEditMode(mode);
                }
            }
        }

        @Override
        public boolean isRoot() {
            return true;
        }
    }

    static class RulesNode extends DefaultMutableTreeNode {

        RulesNode() {
            super("Rules");
        }

        @Override
        public boolean isLeaf() {
            return false;
        }
        
        public void setEditMode(EditMode mode){
            if(children != null){
                for (Object c : children) {
                    ((RuleNode)c).setEditMode(mode);
                }
            }
            
        }

        @Override
        public PetrinetNode getParent() {
            return (PetrinetNode)super.getParent();
        }

        public List<IRule> getRules() {
            final List<IRule> rules = new ArrayList<IRule>(children.size());

            for (Object c : children) {
                rules.add(((RuleNode)c).getRule());
            }

            return rules;
        }
    }

    public static class PetrinetNode extends DefaultMutableTreeNode {

        private final DefaultMutableTreeNode rulesNode;

        private PetrinetFrame frame;

        PetrinetNode(String name, Engine engine) {
            super(new Named<Engine>(name, engine), true);
            rulesNode = new RulesNode();
            add(rulesNode);
        }

        public void addRule(String name, RuleWrapper rule) {
            rulesNode.add(new RuleNode(name, rule));
        }

        @Override
        public boolean isLeaf() {
            return false;
        }
        
        public void setEditMode(EditMode mode){
            ((Named<Engine>)getUserObject()).getValue().getGraphEditor().setEditMode(mode);
            ((RulesNode)rulesNode).setEditMode(mode);
        }

        public Engine getEngine() {
            Named<Engine> object = (Named<Engine>) getUserObject();
            return object.getValue();
        }

        public IPetrinet getPetrinet() {
            return getEngine().getNet();
        }

        public PetrinetFrame getFrame() {
            assert SwingUtilities.isEventDispatchThread();

            if (frame == null) {
                frame = new PetrinetFrame(this);
            }

            return frame;
        }
    }

    public static class RuleNode extends DefaultMutableTreeNode {

        private String name;

        private final RuleWrapper rule;

        RuleNode(String name, RuleWrapper rule) {
            super(new Named<RuleWrapper>(name,rule), true);
            this.name = name;
            this.rule = rule;
        }
        
        public void setEditMode(EditMode mode){
            rule.setEditMode(mode);
        }

        @Override
        public RulesNode getParent() {
            return (RulesNode) super.getParent();
        }

        public String getName() {
            return ((Named<?>)getUserObject()).getName();
        }
        
        public void setName(String name){
            ((Named<?>)getUserObject()).setName(name);
        }
        
        public RuleWrapper getWrapper(){
            return rule;
        }

        public IRule getRule() {
            return rule.getRule();
        }

        @Override
        public boolean isLeaf() {
            return true;
        }
    }

    public PetrinetTreeModel(RootNode root) {
        super(root, false);
    }

    public PetrinetTreeModel() {
        this(new RootNode());
    }

    public PetrinetNode addPetrinet(String name, Engine engine) {
        PetrinetNode node = getRoot().addPetrinet(name, engine);
        nodeChanged(root);
        return node;
    }
    
    public void setEditMode(EditMode mode){
        getRoot().setEditMode(mode);
    }

    public List<Engine> getPetrinets() {
        return getRoot().getChildren();
    }

    @Override
    public RootNode getRoot() {
        return (RootNode)root;
    }
}
