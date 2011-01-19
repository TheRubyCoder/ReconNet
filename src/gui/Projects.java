/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import engine.EditMode;
import gui.PetrinetTreeModel.PetrinetNode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import transformation.IRule;
import transformation.Rule;

/**
 *
 * @author steffen
 */
public class Projects {

    private List<Project> projects;
    private JTree tree;
    private JDesktopPane desktop;

    public Projects(JTree petrinetTree,JDesktopPane desktop){
        projects = new ArrayList<Project>();
        tree = petrinetTree;
        this.desktop = desktop;
    }

    public void updateTree(){
        tree.setModel(this.getPetrinetTreeModel());
    }

    public int getSize(){
        return projects.size();
    }

    public Project getProject(JInternalFrame frame){
        for(Project pro : projects){
            if(pro.getPetrinetFrame().equals(frame)){
                return pro;
            }
        }
        return null;
    }

    public void addRuleToProject(Project pro, Rule rule){
        //JInternalFrame frame = project.addRule(name, rule);
       // jDesktopPane1.add(frame);
        //frame.setBounds(40, 20, 360, 250);
        //frame.setVisible(true);
    }

    public void setCreateMode(EditMode mode){
        for(Project pro : projects){
            pro.getEngine().getGraphEditor().setEditMode(mode);
        }
    }

    public Project getProject(String name){
        for(Project pro : projects){
            if(pro.getName().equals(name)){
                return pro;
            }
        }
        return null;
    }

    public void createProject(){
        String name = JOptionPane.showInputDialog(null,"Geben Sie Ihren Namen ein","Neues Projekt",JOptionPane.PLAIN_MESSAGE);
        Project project = new Project(name,null);
        desktop.add(project.getPetrinetFrame());
        project.getPetrinetFrame().setBounds(40, 20, 360, 250);
        project.getPetrinetFrame().setVisible(true);
    }

    

    public void addProject(Project pro){
        projects.add(pro);
        this.updateTree();
    }

    public void addRuleToProject(Project pro, String name, IRule rule){
        if(projects.contains(pro)){
            pro.addRule(name, rule);
        }
        this.updateTree();
    }

    public PetrinetTreeModel getPetrinetTreeModel(){
        PetrinetTreeModel model = new PetrinetTreeModel();
        for(Project pro : projects){
            PetrinetNode n = model.addPetrinet(pro.getName(), pro.getEngine());
            for(String name :pro.getRules().keySet()){
                n.addRule(name, pro.getRules().get(name).getRule());
            }
        }
        return model;
    }
}
