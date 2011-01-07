package gui;

import petrinetze.IPetrinet;
import petrinetze.impl.Petrinet;
import transformation.IRule;
import transformation.Rule;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.util.List;

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

        public PetrinetNode addPetrinet(String name, IPetrinet petrinet) {
            final PetrinetNode node = new PetrinetNode(name, petrinet);
            add(node);
            return node;
        }
    }

    static class PetrinetNode extends DefaultMutableTreeNode {

        private final DefaultMutableTreeNode rulesNode;

        PetrinetNode(String name, IPetrinet petrinet) {
            super(new Named<IPetrinet>(name, petrinet), true);
            rulesNode = new DefaultMutableTreeNode("Regeln") {
                @Override
                public boolean isLeaf() {
                    return false;
                }
            };
            add(rulesNode);
        }

        public void addRule(String name, IRule rule) {
            rulesNode.add(new RuleNode(name, rule));
        }

        @Override
        public boolean isLeaf() {
            return false;
        }
    }

    static class RuleNode extends DefaultMutableTreeNode {

        private final String name;

        private final IRule rule;

        RuleNode(String name, IRule rule) {
            super(new Named<IRule>(name,rule), false);
            this.name = name;
            this.rule = rule;
        }

        public String getName() {
            return name;
        }

        public IRule getRule() {
            return rule;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                final PetrinetTreeModel model = new PetrinetTreeModel();

                final PetrinetNode n1 = model.addPetrinet("Untitled 1", new Petrinet());
                final PetrinetNode n2 = model.addPetrinet("Wecker", new Petrinet());
                final PetrinetNode n3 = model.addPetrinet("TV", new Petrinet());

                n1.addRule("Regel 1", new Rule());

                final JTree tree = new JTree(model);
                final JFrame f = new JFrame();

                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setContentPane(new JScrollPane(tree));
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);

                tree.addTreeSelectionListener(new TreeSelectionListener() {
                    @Override
                    public void valueChanged(TreeSelectionEvent e) {
                        System.out.println(e.getNewLeadSelectionPath().getLastPathComponent());
                    }
                });
            }
        });
    }

    public PetrinetTreeModel(RootNode root) {
        super(root);
    }

    public PetrinetTreeModel() {
        this(new RootNode());
    }

    public PetrinetNode addPetrinet(String name, IPetrinet petrinet) {
        return getRoot().addPetrinet(name, petrinet);
    }

    @Override
    public RootNode getRoot() {
        return (RootNode)super.getRoot();
    }
}
