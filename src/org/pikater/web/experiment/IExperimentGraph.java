package org.pikater.web.experiment;

public interface IExperimentGraph<I extends Object, B extends IBoxInfoCommon<I>> extends Iterable<B>
{
	boolean containsBox(I boxID);
	B getBox(I boxID);
	B addBox(B box);
	void clear();
	boolean isEmpty();
	boolean hasOutputEdges(I boxID);
	void connect(I fromBoxID, I toBoxID);
	void disconnect(I fromBoxID, I toBoxID);
}