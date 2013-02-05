/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pikater.evolution;

/**
 *
 * @author Martin Pilat
 */
public class MergingReplacement implements Replacement {

    @Override
    public Population replace(Population parents, Population offspring) {
        Population replaced = new Population();
        replaced.addAll((Population)parents.clone());
        replaced.addAll((Population)offspring.clone());
        return replaced;
    }
    
    
    
}
