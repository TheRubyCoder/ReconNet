package gui;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import petrinet.ActionType;
import petrinet.Arc;
import petrinet.INode;
import petrinet.IPetrinetListener;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.Renews;
import transformation.Rule;
import engine.EditMode;
import engine.Engine;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 *
 * @todo Listenerbenachrichtigung muss erweitert werden
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

    class RootNode extends DefaultMutableTreeNode {

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

    class RulesNode extends DefaultMutableTreeNode {

        RulesNode() {
            super("Rules", true);
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

        public List<Rule> getRules() {
            final List<Rule> rules = new ArrayList<Rule>(children.size());

            for (Object c : children) {
                rules.add(((RuleNode)c).getRule());
            }

            return rules;
        }
    }

    public class PetrinetNode extends DefaultMutableTreeNode implements IPetrinetListener {

        private final DefaultMutableTreeNode rulesNode;

        private volatile PetrinetFrame frame;

        PetrinetNode(String name, Engine engine) {
            super(new Named<Engine>(name, engine), true);
            rulesNode = new RulesNode();
            add(rulesNode);

            engine.getNet().addPetrinetListener(this);
        }

        public void addRule(String name, RuleWrapper rule) {
            final RuleNode node = new RuleNode(name, rule);
            rulesNode.add(node);

            fireTreeNodesInserted(
                PetrinetTreeModel.this,
                rulesNode.getPath(),
                new int[] { rulesNode.getIndex(node) },
                new Object[] { node }
            );
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

        public Petrinet getPetrinet() {
            return getEngine().getNet();
        }

        public PetrinetFrame getFrame() {
            assert SwingUtilities.isEventDispatchThread();

            if (frame == null) {
                frame = new PetrinetFrame(this);
            }

            return frame;
        }

        @Override
        public void changed(Petrinet petrinet, INode element, ActionType actionType) {
            if (frame != null) frame.repaint();
        }

        @Override
        public void changed(Petrinet petrinet, Arc element, ActionType actionType) {
            if (frame != null) frame.repaint();
        }
    }

    public static abstract class NamedNode<T> extends DefaultMutableTreeNode {

        protected NamedNode(Named<T> content) {
            super(content, true);
        }

        public String getName() {
            return ((NamedNode<T>)userObject).getName();
        }

        public void setName(String name) {
            // TODO Listener notification?
            ((NamedNode<T>)userObject).setName(name);
        }

        public T getValue() {
            return ((NamedNode<T>)userObject).getValue();
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

        public Rule getRule() {
            return rule.getRule();
        }

        @Override
        public boolean isLeaf() {
            return true;
        }
    }


    public static class RenewsNode extends DefaultMutableTreeNode {

        RenewsNode() {
            super("Renews");
            add("id", Renews.IDENTITY);
            add("count", Renews.COUNT);
        }

        public final void add(String name, IRenew renew) {
            add(new RenewNode(name, renew));
        }

        public Map<IRenew,String> getRenews() {
            final Map<IRenew,String> renews = new IdentityHashMap<IRenew, String>();

            for (Object c : children) {
                renews.put(((RenewNode)c).getValue(), ((RenewNode)c).getName());
            }

            return renews;
        }
    }

    public static class RenewNode extends NamedNode<IRenew> {
        RenewNode(String name, IRenew renew) {
            super(new Named<IRenew>(name, renew));
        }
    }

    public PetrinetTreeModel() {
        super(null, false);
        this.root = new RootNode();
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
