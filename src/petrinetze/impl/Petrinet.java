package petrinetze.impl;

import java.util.List;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import petrinetze.IPlace;
import petrinetze.IPost;
import petrinetze.IPre;
import petrinetze.ITransition;

public class Petrinet implements IPetrinet {

	@Override
	public IPlace createPlace(String name) {
		return new Place();
	}

	@Override
	public void deletePlaceById(int id) {

	}

	@Override
	public ITransition createTransition(String name) {
		return null;

	}

	@Override
	public void deleteTransitionByID(String id) {
	}

	@Override
	public IArc createArc() {
		return null;
	}

	@Override
	public void deleteArcByID(int id) {

	}

	@Override
	public List<ITransition> getActivatedTransitions() {
		return null;
	}

	@Override
	public List<INode> fire(int id) {
		return null;
	}

	@Override
	public List<INode> fire() {
		return null;
	}

	@Override
	public IPre getPre() {
		return null;
	}

	@Override
	public IPost getPost() {
		return null;
	}

	@Override
	public int getId() {
		return 0;
	}

}
