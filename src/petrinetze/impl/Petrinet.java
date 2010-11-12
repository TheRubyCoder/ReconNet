package haw.wp.rcpn.impl;

import haw.wp.rcpn.IArc;
import haw.wp.rcpn.INode;
import haw.wp.rcpn.IPetrinet;
import haw.wp.rcpn.IPlace;
import haw.wp.rcpn.IPost;
import haw.wp.rcpn.IPre;
import haw.wp.rcpn.ITransition;

import java.util.List;

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
