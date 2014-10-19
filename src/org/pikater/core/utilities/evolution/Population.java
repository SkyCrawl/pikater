package org.pikater.core.utilities.evolution;

import org.pikater.core.utilities.evolution.individuals.Individual;
import org.pikater.shared.logging.core.ConsoleLogger;
import org.pikater.shared.util.ICloneable;
import org.pikater.shared.util.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** A container which contains the individuals of a population.
 *
 * @author Martin Pilat
 */
public class Population implements ICloneable {
    int size = 0;
    Individual sampleIndividual;
    List<Individual> individuals;
    
    /**
     * Creates a new empty population.
     */

    public Population() {
        individuals = new ArrayList<Individual>();
    }

    /**
     * Makes a deep copy of the population.
     *
     * @return A deep copy of the population. All individuals are cloned using
     * their clone methods.
     */
    @Override
    public Population clone() {
        try {
            Population result = (Population) super.clone();
            result.individuals = CollectionUtils.deepCopy(individuals);
            return result;
        } catch (Exception e) {
            ConsoleLogger.logThrowable("Unexpected error occured:", e);
            throw new RuntimeException("failed to clone");
        }
    }

    /**
     * Shuffles the order of the individuals in the population randomly.
     */
    public void shuffle() {
        Collections.shuffle(individuals, RandomNumberGenerator.getInstance().getRandom());
    }

    /**
     * Sets the individual which is cloned to create the random initial population.
     *
     * @param sample The sample individual.
     */

    public void setSampleIndividual(Individual sample) {
        this.sampleIndividual = sample;
    }

    /**
     * Sets the population size, used only during the random initialization.
     * @param size The population size.
     */

    public void setPopulationSize(int size) {
        this.size = size;
    }

    /**
     * Returns the actual size of the population.
     * @return The number of individuals in the population.
     */

    public int getPopulationSize() {
        return individuals.size();
    }

    /**
     * Gets the ith individual in the population.
     * @param i The index of the individual which shall be returned.
     * @return The indivudal at index i.
     */

    public Individual get(int i) {
        return individuals.get(i);
    }

    /**
     * Adds an individual to the population.
     * @param ind The individual which shall be addded.
     */

    public void add(Individual ind) {
        individuals.add(ind);
    }

    /**
     * Adds all the individuals fromt he population p to the population. Does not
     * clone them.
     * @param p The population from which the individuals shall be added.
     */

    public void addAll(Population p) {
        individuals.addAll(p.individuals);
    }

    /**
     * Creates random initial population of the specified size. Calls the clone
     * method on the sample individual and the randomInitialization method
     * on the clones created from it.
     */

    public void createRandomInitialPopulation() {

        individuals = new ArrayList<Individual>(size);

        for (int i = 0; i < size; i++) {

            Individual n = (Individual)sampleIndividual.clone();
            n.randomInitialization();
            individuals.add(n);

        }
    }

    /**
     * Removes all the individuals from the population.
     */
    public void clear() {
        individuals.clear();
    }

    /**
     * Returns all the individuals in the population as a list sorted in descending
     * order og their fitness.
     * 
     * @return The sorted list of individuals.
     */

    public List<Individual> getSortedIndividuals() {
        
        List<Individual> sorted = new ArrayList<Individual>();
        sorted.addAll(individuals);

        Collections.sort(sorted, new FitnessFunctionComparator());

        return sorted;

    }

    static class FitnessFunctionComparator implements Comparator<Individual>, Serializable {

        private static final long serialVersionUID = 1L;

        public int compare(Individual o1, Individual o2) {
            if (o1.getFitnessValue() > o2.getFitnessValue()) {
                return -1;
            }
            if (o1.getFitnessValue() == o2.getFitnessValue()) {
                return 0;
            }
            return 1;
        }

    }
    
}
