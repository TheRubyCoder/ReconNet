/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import engine.Engine;
import engine.EngineFactory;
import engine.Simulation;
import engine.StepListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.impl.Petrinet;
import transformation.IRule;

/**
 *
 * @author steffen
 */
public class Project implements StepListener{

    private String name;
    private Engine engine;
    private IPetrinet petrinet;
    private boolean locked;
    private JInternalFrame petrinetFrame;
    private JTable table;
    private Map<String,RuleWrapper> rules;

    public Project(String name,JTable table){
        this(name,new Petrinet(),table);
    }

    public Project(String name,IPetrinet net,JTable table){
        this(name,net,new HashMap<String,RuleWrapper>(),table);
    }

    public Project(String name,IPetrinet net, Map<String,RuleWrapper> rules,JTable table){
        this.locked = false;
        this.name = name;
        this.petrinet = net;
        this.rules = rules;
        this.table = table;
        this.engine = EngineFactory.newFactory().createEngine(petrinet);
        engine.getSimulation().addStepListener(this);
        this.petrinetFrame = new JInternalFrame();
        petrinetFrame.setContentPane(new JScrollPane(engine.getGraphEditor().getGraphPanel()));
        petrinetFrame.setClosable(true);
        petrinetFrame.setIconifiable(true);
        petrinetFrame.setMaximizable(true);
        petrinetFrame.setResizable(true);
        engine.getGraphEditor().getGraphPanel().addPropertyChangeListener("pickedNodes", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                Set<INode> nodes = (Set<INode>) evt.getNewValue();
            }
        });
    }

    public JInternalFrame getPetrinetFrame(){
        return petrinetFrame;
    }

    public JInternalFrame addRule(String name,IRule r){
        RuleWrapper wrapper = new RuleWrapper(name,r);
        rules.put(name, wrapper);
        return wrapper.createFrame();
    }

   

    public String getName(){ return name;}
    public void setName(String name){this.name = name;}

    public Engine getEngine(){
        return engine;
    }

    public IPetrinet getPetrinet(){
        return petrinet;
    }

    public Map<String,RuleWrapper> getRules(){
        return rules;
    }

    public void stepped(Simulation s) {
        engine.getGraphEditor().getGraphPanel().repaint();
    }

    public void started(Simulation s) {
        locked = true;
        petrinetFrame.setEnabled(false);
    }

    public void stopped(Simulation s) {
        petrinetFrame.setEnabled(true);
        locked = false;
    }

}
