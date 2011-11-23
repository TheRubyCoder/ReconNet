package gui;

import engine.*;
import petrinet.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class GraphDemo extends JFrame {
	
	final JToggleButton simButton;
	
	final DefaultTableModel model;

    private transient final Petrinet petrinet = PetrinetComponent.getPetrinet().createPetrinet();

    private final Engine engine;

    private final JPanel graphPanel;

    private final JTable labelTable;

    private JToolBar toolBar;

    private Icon icon(String name) {
        return new ImageIcon(GraphDemo.class.getResource(String.format("resource/%s.png", name)));
    }

    public GraphDemo() {
        super("Graph Demo");
        getRootPane().putClientProperty("apple.awt.brushMetalLook", Boolean.TRUE);
        Place a = petrinet.createPlace("A");
        a.setMark(8);
        Place b = petrinet.createPlace("B");
        b.setMark(12);
        Place c = petrinet.createPlace("C");
        c.setMark(1);

        Transition r = petrinet.createTransition("r", Renews.fromMap(new HashMap<String,String>(){{
            put("hund", "katze");
            put("maus", "hund");
            put("katze", "hamster");
            put("hamster", "maus");
        }}));
        r.setTlb("hund");

        Transition s = petrinet.createTransition("s", Renews.COUNT);
        s.setTlb(String.valueOf(0));

        Transition t = petrinet.createTransition("t", Renews.COUNT);
        t.setTlb(String.valueOf(0));

        // Arc j =
        petrinet.createArc("j", a, r);
        // Arc k =
        petrinet.createArc("k", r, b);
        Arc l = petrinet.createArc("l", b, s);
        l.setMark(16);
        Arc m = petrinet.createArc("m", s, c);
        m.setMark(10);
        Arc n = petrinet.createArc("n", c, t);
        n.setMark(4);
        Arc o = petrinet.createArc("o", t, a);
        o.setMark(7);
        Arc p = petrinet.createArc("p", t, c);
        p.setMark(2);

        /*
        for (char id = 'D'; id <= 'Z'; id++) {
            petrinet.createPlace(String.valueOf(id));
        }*/

        engine = EngineFactory.newFactory().createEngine(petrinet);
        // engine.getLayoutEditor().setLayout(Layout.KKLayout);

        engine.getGraphEditor().setEditMode(EditMode.PICK);

        graphPanel = engine.getGraphEditor().getGraphPanel();

        model = new DefaultTableModel();

        model.setColumnCount(2);
        model.setColumnIdentifiers(new Object[] { "Transition", "Label" });

        for (Transition transition : petrinet.getAllTransitions()) {
            model.addRow(new Object[] { transition.getName(), transition.getTlb() });
        }

        labelTable = new JTable(model);


        final ButtonGroup modeGroup = new ButtonGroup();

        final JToggleButton pickButton = new JToggleButton("Auswahl", icon("16-em-pencil"));
        modeGroup.add(pickButton);
        final JToggleButton addPlaceButton = new JToggleButton("Stelle", icon("16-circle-blue-add"));
        modeGroup.add(addPlaceButton);
        final JToggleButton addTransitionButton = new JToggleButton("Transition", icon("16-square-blue-add"));
        modeGroup.add(addTransitionButton);
        final JToggleButton translateButton = new JToggleButton("Verschieben", icon("16-tool-b"));
        modeGroup.add(translateButton);

        modeGroup.setSelected(pickButton.getModel(), true);

        final ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == pickButton) {
                    engine.getGraphEditor().setEditMode(EditMode.PICK);
                }
                else if (e.getSource() == addPlaceButton) {
                    engine.getGraphEditor().setEditMode(EditMode.ADD_PLACE);
                }
                else if (e.getSource() == addTransitionButton) {
                    engine.getGraphEditor().setEditMode(EditMode.ADD_TRANSITION);
                }
                else if (e.getSource() == translateButton) {
                    engine.getGraphEditor().setEditMode(EditMode.TRANSLATE);
                }
            }
        };

        pickButton.addActionListener(buttonListener);
        addPlaceButton.addActionListener(buttonListener);
        addTransitionButton.addActionListener(buttonListener);
        translateButton.addActionListener(buttonListener);

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(pickButton);
        toolBar.add(addPlaceButton);
        toolBar.add(addTransitionButton);
        toolBar.add(translateButton);
        toolBar.add(new JSeparator(JSeparator.VERTICAL));

        final Icon playIcon = icon("24-arrow-next");
        final Icon pauseIcon = icon("24-control-pause");

        final Action simulationAction = new AbstractAction("Simulation", playIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (engine.getSimulation().isRunning()) {
                    engine.getSimulation().stop();
                }
                else {
                    engine.getSimulation().start(1000);
                }
            }
        };

        simButton = new JToggleButton(simulationAction);
        simButton.setSelectedIcon(pauseIcon);
        toolBar.add(simButton);

        final JLabel statusLabel = new JLabel("Petrinetz geladen");

        petrinet.addPetrinetListener(new TestPetriListener(this));

        engine.getSimulation().addStepListener(new StepListener() {
                    @Override
                    public void stepped(Simulation s) {
                        updateTable();
                        /* only if no PetrinetListener is attached!
                        getContentPane().repaint();
                        */
                    }

                    @Override
                    public void started(Simulation s) {
                        simButton.setSelected(true);
                        statusLabel.setText("Simulation gestartet");
                    }

                    @Override
                    public void stopped(Simulation s) {
                        statusLabel.setText("Simulation gestoppt");
                        simButton.setSelected(false);
                    }
                });

        engine.getNet().addPetrinetListener(new TestPetriListener2(this));

        final JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphPanel, new JScrollPane(labelTable));
        split.setContinuousLayout(true);
        split.setResizeWeight(1.0);
        split.setDividerLocation(split.getWidth() - 200);

        final JSplitPane outerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(new JTree()), split);
        outerSplit.setContinuousLayout(true);
        outerSplit.setResizeWeight(0.0);
        outerSplit.setDividerLocation(200);

        getContentPane().add(outerSplit, BorderLayout.CENTER);
        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(statusLabel, BorderLayout.SOUTH);
    }

    private List<Transition> getTransitions() {
        final List<Transition> ts = new ArrayList<Transition>(petrinet.getAllTransitions());
        Collections.sort(ts, new Comparator<Transition>() {
            @Override
            public int compare(Transition o1, Transition o2) {
                return (o1.getId() > o2.getId()) ? 1 :
                       (o1.getId() == o2.getId()) ? 0 :
                       -1;
            }
        });
        return ts;
    }

    private void updateTable() {
        int i = 0;
        for (Transition t : getTransitions()) {
            labelTable.setValueAt(t.getTlb(), i, 1);
            i++;
        }
    }

    private static Timer timer = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final GraphDemo d = new GraphDemo();

                d.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        d.engine.getSimulation().stop();
                    }
                });

                d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                d.pack();
                d.setVisible(true);
            }
        });

    }
    
    private static class TestPetriListener2 implements IPetrinetListener{
    	
    	private GraphDemo graphDemo;
    	
    	TestPetriListener2(GraphDemo graphDemo){
    		this.graphDemo = graphDemo;
    	}
    	 @Override
         public void changed(Petrinet petrinet, INode element, ActionType actionType) {
             netChanged(petrinet);
         }

         @Override
         public void changed(Petrinet petrinet, Arc element, ActionType actionType) {
             netChanged(petrinet);
         }

         private void netChanged(Petrinet p) {
        	 graphDemo.simButton.setEnabled(!p.getActivatedTransitions().isEmpty());
        	 graphDemo.graphPanel.repaint();
         }
    }
    
    private static class TestPetriListener implements IPetrinetListener{
    	
    	private GraphDemo graphDemo;
    	
    	TestPetriListener(GraphDemo graphDemo){
    		this.graphDemo = graphDemo;
    	}
    	
    	@Override
        public void changed(Petrinet petrinet, INode element, ActionType actionType) {
            if (element instanceof Transition) {
                switch (actionType) {
                    case added:
                    case deleted:
                        final List<Transition> transitions = graphDemo.getTransitions();

                        while (graphDemo.model.getRowCount() > 0) graphDemo.model.removeRow(0);

                        for (Transition transition : transitions) {
                        	graphDemo.model.addRow(new Object[] { transition.getName(), transition.getTlb() });
                        }
                        graphDemo.labelTable.setModel(graphDemo.model);
                }
            }
            graphDemo.repaint();
        }

        @Override
        public void changed(Petrinet petrinet, Arc element, ActionType actionType) {
        	graphDemo.repaint();
        }
    }
}
