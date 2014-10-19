package org.pikater.core.utilities.evolution.operators;

import org.pikater.core.ontology.subtrees.newoption.values.DoubleValue;
import org.pikater.core.utilities.evolution.individuals.ArrayIndividual;
import org.pikater.core.utilities.evolution.Population;
import org.pikater.core.utilities.evolution.RandomNumberGenerator;

/**
 * Performs a one point crossover. 
 * 
 * A single point is selected randomly in the indivdiual and the tails of the parents (following the
 * selected point) are swapped between them.
 * 
 * @author Martin Pilat
 */
public class OnePtXOver implements Operator {

    double xOverProb = 0;

    RandomNumberGenerator rng = RandomNumberGenerator.getInstance();

    /**
     * Constructor, sets the probability of crossover
     * 
     * @param prob the probability of crossover
     */
    
    public OnePtXOver(double prob) {
        xOverProb = prob;
    }

    public void operate(Population parents, Population offspring) {

        int size = parents.getPopulationSize();

        for (int i = 0; i < size/2; i++) {
            ArrayIndividual p1 = (ArrayIndividual) parents.get(i);
            ArrayIndividual p2 = (ArrayIndividual) parents.get(i+1);

            ArrayIndividual o1 = (ArrayIndividual) p1.clone();
            ArrayIndividual o2 = (ArrayIndividual) p2.clone();

            if (rng.nextDouble() < xOverProb) {

                int point = rng.nextInt(p1.length());

                for (int j = point; j < p1.length(); j++) {
                    Object tmp = o1.get(j);
                    o1.set(j, new DoubleValue((double)o2.get(j)) );
                    o2.set(j, new DoubleValue((double)tmp));
                }

            }

            offspring.add(o1);
            offspring.add(o2);
        }

    }
    


}
