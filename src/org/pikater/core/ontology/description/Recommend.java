package org.pikater.core.ontology.description;

import org.pikater.core.ontology.messages.option.Option;
import org.pikater.shared.experiment.universalformat.UniversalComputationDescription;
import org.pikater.shared.experiment.universalformat.UniversalElement;
import org.pikater.shared.experiment.universalformat.UniversalOntology;

import jade.util.leap.ArrayList;


/**
 * Created by Martin Pilat on 28.12.13.
 */
public class Recommend extends AbstractDataProcessing {

	private static final long serialVersionUID = -1204258141585020540L;

	private String recommenderClass;
    private ArrayList options;

    public String getRecommenderClass() {
        return recommenderClass;
    }

    public void setRecommenderClass(String recommenderClass) {
        this.recommenderClass = recommenderClass;
    }

    public ArrayList getOptions() {
    	if (this.options == null) {
    		return new ArrayList();
    	}
        return options;
    }
    public void setOptions(ArrayList options) {
        this.options = options;
    }
    public void addOption(Option option) {
    	if (this.options == null) {
    		this.options = new ArrayList();
    	}
        this.options.add(option);
    }

	@Override
	UniversalElement exportUniversalElement(
			UniversalComputationDescription uModel) {
		
		Option recommenderClassOption = new Option();
		recommenderClassOption.setName("recommenderClass");
		recommenderClassOption.setValue(recommenderClass);
		
		ArrayList options = new ArrayList();
		options.add(recommenderClassOption);
		
		UniversalOntology element = new UniversalOntology();
		element.setType(this.getClass());
		element.setOptions(options);
		
		UniversalElement wrapper =
				new UniversalElement(uModel);
		wrapper.setElement(element);
		
		return wrapper;
	}
}
