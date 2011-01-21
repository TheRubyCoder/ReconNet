package gui;

import engine.Engine;
import engine.EngineFactory;
import petrinetze.IPetrinet;
import petrinetze.impl.Petrinet;
import transformation.Rule;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 08.01.11
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public class PetrinetTreeDemo extends JScrollPane {

    private JTree tree;

    private PetrinetTreeModel model;

    private JPopupMenu popup;

    private final Action showPetrinetAction =
        new AbstractAction("Anzeigen") { // TODO i18n
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not implemented");
            }
        };

    private final Action addRuleAction =
        new AbstractAction("Hinzufügen") { // TODO i18n
            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not implemented");
            }
        };

    private final Action editRuleAction = new AbstractAction("Bearbeiten") { // TODO i18n
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not implemented");
        }
    };

    private final Action applyRuleAction = new AbstractAction("Anwenden") { // TODO i18n
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not implemented");
        }
    };

    private final List<JMenuItem> petrinetItems;

    private final List<JMenuItem> rulesItems;

    private final List<JMenuItem> ruleItems;

    static enum PopupType {
        PETRINET, RULES, RULE
    }

    public PetrinetTreeDemo() {
        this(new JTree(new PetrinetTreeModel()));
    }

    private PetrinetTreeDemo(JTree tree) {
        super(tree);
        this.tree = tree;
        this.model = (PetrinetTreeModel)tree.getModel();

        this.tree.addMouseListener(treeMouseListener);
        this.tree.setExpandsSelectedPaths(true);

        // popupMenu
        this.popup = new JPopupMenu();

        this.petrinetItems = Arrays.asList(
            popup.add(showPetrinetAction)
        );

        this.rulesItems = Arrays.asList(
            popup.add(addRuleAction)
        );

        this.ruleItems = Arrays.asList(
            popup.add(editRuleAction),
            popup.add(applyRuleAction)
        );
    }

    private void setVisible(final Iterable<? extends JMenuItem> items, final boolean flag) {
        for (JMenuItem item : items) item.setVisible(flag);
    }

    private void showPopup(int x, int y, PopupType type) {
        setVisible(petrinetItems, type == PopupType.PETRINET);
        setVisible(rulesItems, type == PopupType.RULES);
        setVisible(ruleItems, type == PopupType.RULE);
        popup.show(tree, x, y);
    }

    public PetrinetTreeModel.PetrinetNode addPetrinet(String name, Engine engine) {
        final PetrinetTreeModel.PetrinetNode node = ((PetrinetTreeModel)tree.getModel()).addPetrinet(name, engine);
        tree.setLeadSelectionPath(new TreePath(node.getPath()));
        return node;
    }

    private transient final MouseListener treeMouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger() && tree.getSelectionPath() != null) {
                final TreeNode node = (TreeNode) tree.getSelectionPath().getLastPathComponent();

                if (node instanceof PetrinetTreeModel.PetrinetNode) {
                    showPopup(e.getX(), e.getY(), PopupType.PETRINET);
                }
                else if (node instanceof PetrinetTreeModel.RulesNode) {
                    showPopup(e.getX(), e.getY(), PopupType.RULES);
                }
                else if (node instanceof PetrinetTreeModel.RuleNode) {
                    showPopup(e.getX(), e.getY(), PopupType.RULE);
                }
                else {
                    System.out.println(node);
                }
            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                final JFrame frame = new JFrame("PetrinetTreeDemo");
                final PetrinetTreeDemo panel = new PetrinetTreeDemo();


                final EngineFactory factory = EngineFactory.newFactory();

                PetrinetTreeModel.PetrinetNode node = panel.addPetrinet("Wecker", factory.createEngine(new Petrinet()));
                // node.addRule("zerstören", new Rule());

                panel.setBorder(BorderFactory.createEmptyBorder()); // nur falls direkt im JFrame!
                frame.setContentPane(panel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
