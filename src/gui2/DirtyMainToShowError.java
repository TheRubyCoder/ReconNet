package gui2;

import engine.handler.RuleNet;
import exceptions.EngineException;

import java.awt.Point;

public class DirtyMainToShowError {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int ruleId = EngineAdapter.getRuleManipulation().createRule();
		try{
			EngineAdapter.getRuleManipulation().createPlace(ruleId, RuleNet.L, new Point(10,10));
		}catch(EngineException e){
			PopUp.popError(e);
			e.printStackTrace();
		}
		System.out.println("done");
	}

}
