/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import engine.EditMode;
import gui.PetrinetTreeModel.PetrinetNode;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame;
import transformation.IRule;

/**
 *
 * @author steffen
 */
public class Projects {

    private List<Project> projects;

    public Projects(){
        projects = new ArrayList<Project>();
    }

    public Project getProject(JInternalFrame frame){
        for(Project pro : projects){
            if(pro.getPetrinetFrame().equals(frame)){
                return pro;
            }
        }
        return null;
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

    public PetrinetTreeModel addProject(Project pro){
        projects.add(pro);
        return this.getPetrinetTreeModel();
    }

    public PetrinetTreeModel addRuleToProject(Project pro, String name, IRule rule){
        if(projects.contains(pro)){
            pro.addRule(name, rule);
        }
        return this.getPetrinetTreeModel();
    }

    public PetrinetTreeModel getPetrinetTreeModel(){
        PetrinetTreeModel model = new PetrinetTreeModel();
        for(Project pro : projects){
            PetrinetNode n = model.addPetrinet(pro.getName(), pro.getEngine());
            for(String name :pro.getRules().keySet()){
                n.addRule(name, pro.getRules().get(name));
            }
        }
        return model;
    }

}
