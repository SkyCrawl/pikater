package org.pikater.core.utilities.evolution.operators;

import org.pikater.core.utilities.evolution.Population;
import org.pikater.core.utilities.evolution.RandomNumberGenerator;
import org.pikater.core.utilities.evolution.individuals.SearchItemIndividual;
import org.pikater.core.ontology.search.searchItems.FloatSItem;
import org.pikater.core.ontology.search.searchItems.SetSItem;
import org.pikater.core.ontology.search.searchItems.IntSItem;

/**
 *
 * @author Martin Pilat
 */
public class SearchItemIndividualMutation implements Operator {

    double mutationProbability;
    double geneChangeProbability;
    double changeWidth;
    RandomNumberGenerator rng = RandomNumberGenerator.getInstance();

    /**
     * 
     * @param mutationProbability Probability of mutation for a given individual
     * @param geneChangeProbability Probability of gene change in a mutated individual
     * @param changeWidth How much integer and float parameters should be changed (as a ratio of the width of their interval)
     */
    public SearchItemIndividualMutation(double mutationProbability, double geneChangeProbability, double changeWidth) {
        this.mutationProbability = mutationProbability;
        this.geneChangeProbability = geneChangeProbability;
        this.changeWidth = changeWidth;
    }
    
    @Override
    public void operate(Population parents, Population offspring) {
        
        int size = parents.getPopulationSize();

        for (int i = 0; i < size; i++) {

             SearchItemIndividual p1 = (SearchItemIndividual) parents.get(i);
             SearchItemIndividual o1 = (SearchItemIndividual) p1.clone();

             if (rng.nextDouble() < mutationProbability) {
                 for (int j = 0; j < o1.length(); j++) {
                     if (rng.nextDouble() < geneChangeProbability) {
                         if (o1.getSchema(j) instanceof SetSItem) {
                            o1.set(j, p1.getSchema(j).randomValue(rng.getRandom()));
                         }
                         if (o1.getSchema(j) instanceof FloatSItem) {
                             FloatSItem fs = (FloatSItem)o1.getSchema(j);
                             float val = Float.parseFloat(o1.get(j));
                             val += changeWidth*(fs.getMax()-fs.getMin())*rng.nextGaussian();
                             val = Math.min(val, fs.getMax());
                             val = Math.max(val, fs.getMin());
                             o1.set(j, String.valueOf(val));
                         }
                         if (o1.getSchema(j) instanceof IntSItem) {
                             IntSItem fs = (IntSItem)o1.getSchema(j);
                             int val = Integer.parseInt(o1.get(j));
                             val += changeWidth*(fs.getMax()-fs.getMin())*rng.nextGaussian();
                             val = Math.min(val, fs.getMax());
                             val = Math.max(val, fs.getMin());
                             o1.set(j, String.valueOf(val));
                         }
                     }
                 }
             }

             offspring.add(o1);
        }
        
        
        
    }
    
}
