/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pikater.core.utilities.evolution.surrogate;

import org.pikater.core.ontology.messages.IntSItem;
import org.pikater.core.ontology.messages.searchItems.FloatSItem;

/**
 *
 * @author Martin Pilat
 */
public class LogarithmicNormalizer extends ModelInputNormalizer{

    @Override
    public double normalizeFloat(String dbl, FloatSItem schema) {
        double val = Double.parseDouble(dbl);
        val -= schema.getMin();
        val += 1.0;
        return Math.log(val);
    }

    @Override
    public double normalizeInt(String n, IntSItem schema) {
        double val = Integer.parseInt(n);
        val -= schema.getMin();
        val += 1.0;
        return Math.log(val);
    }
    
}
