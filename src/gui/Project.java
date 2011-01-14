/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import engine.Engine;
import engine.EngineFactory;
import engine.Simulation;
import engine.StepListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
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
    private Map<String,IRule> rules;
    private Map<IRule,JInternalFrame> ruleFrames;

    public Project(String name){
        this(name,new Petrinet());
    }

    public Project(String name,IPetrinet net){
        this(name,net,new HashMap<String,IRule>());
    }

    public Project(String name,IPetrinet net, Map<String,IRule> rules){
        this.locked = false;
        this.name = name;
        this.petrinet = net;
        this.rules = rules;
        this.ruleFrames = new HashMap<IRule,JInternalFrame>();
        this.engine = EngineFactory.newFactory().createEngine(petrinet);
        engine.getSimulation().addStepListener(this);
        this.petrinetFrame = new JInternalFrame();
        petrinetFrame.setContentPane(new JScrollPane(engine.getGraphEditor().getGraphPanel()));
        petrinetFrame.setClosable(true);
        petrinetFrame.setIconifiable(true);
        petrinetFrame.setMaximizable(true);
        petrinetFrame.setResizable(true);
    }

    public JInternalFrame getPetrinetFrame(){
        return petrinetFrame;
    }

    public void addRule(String name,IRule r){
        rules.put(name, r);
    }

    public String getName(){
        return name;
    }

    public Engine getEngine(){
        return engine;
    }

    public IPetrinet getPetrinet(){
        return petrinet;
    }

    public Map<String,IRule> getRules(){
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
