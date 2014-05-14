package org.pikater.core.ontology.metadata;

import jade.content.Concept;
import jade.util.leap.ArrayList;
import jade.util.leap.List;
import org.pikater.core.ontology.messages.metadata.*;

public class Metadata implements Concept {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4618372245480479979L;
	private String _internal_name;
	private String _external_name;
	private int _number_of_instances = -1;
	private int _number_of_attributes = -1;
	private boolean _missing_values;
	private String _default_task; // Classification, Regression, Clustering
	private String _attribute_type; // Categorical, Numerical, Mixed
	private int _linear_regression_duration; // ms
	// private List<AttributeMetadata> _attribute_metadata_list = new ArrayList<>();
	private List _attribute_metadata_list = new ArrayList(); 
	
        public String getTargetClassType()
        {
            for (int i=getAttribute_metadata_list().size()-1;i>=0;i--)
            {
                AttributeMetadata att= (AttributeMetadata)getAttribute_metadata_list().get(i);
                if (att.isIsTarget()) return att.getType();
            }
            return "No target class";
        }
        
        public double getCategoricalRatio()
        {
            double n=getNumber_of_attributes()-1;
            return getNumberOfCategorical()/n;
        }
        
        public double getIntegerRatio()
        {
            double n=getNumber_of_attributes()-1;
            return getNumberOfInteger()/n;
        }
        
        public double getRealRatio()
        {
            double n=getNumber_of_attributes()-1;
            return getNumberOfReal()/n;
        }
        
        public int getNumberOfCategorical()
        {
            int count=0;
           for (int i=getAttribute_metadata_list().size()-1;i>=0;i--)
            {
                AttributeMetadata att= (AttributeMetadata)getAttribute_metadata_list().get(i);
                if (!att.isIsTarget())
                {
                    if (att instanceof CategoricalAttributeMetadata) count++;
                }
            }
            return count;
        }
        
        public int getNumberOfInteger()
        {
            int count=0;
           for (int i=getAttribute_metadata_list().size()-1;i>=0;i--)
            {
                AttributeMetadata att= (AttributeMetadata)getAttribute_metadata_list().get(i);
                if (!att.isIsTarget())
                {
                    if (att instanceof IntegerAttributeMetadata) count++;
                }
            }
            return count;
        }
        
        public int getNumberOfReal()
        {
           int count=0;
           for (int i=getAttribute_metadata_list().size()-1;i>=0;i--)
            {
                AttributeMetadata att= (AttributeMetadata)getAttribute_metadata_list().get(i);
                if (!att.isIsTarget())
                {
                    if (att instanceof RealAttributeMetadata) count++;
                }
            }
            return count;
        }
        
        public List getAttribute_metadata_list() {
            return _attribute_metadata_list;
        }

        public void setAttribute_metadata_list(List _attribute_metadata_list) {
            this._attribute_metadata_list = _attribute_metadata_list;
        }

	public int getNumber_of_instances() {
		return _number_of_instances;
	}

	public void setNumber_of_instances(int _number_of_instances) {
		this._number_of_instances = _number_of_instances;
	}

	public int getNumber_of_attributes() {
		return _number_of_attributes;
	}

	public void setNumber_of_attributes(int _number_of_attributes) {
		this._number_of_attributes = _number_of_attributes;
	}

	public boolean getMissing_values() {
		return _missing_values;
	}

	public void setMissing_values(boolean _missing_values) {
		this._missing_values = _missing_values;
	}

	public String getDefault_task() {
		return _default_task;
	}

	public void setDefault_task(String _default_task) {
		this._default_task = _default_task;
	}

	//Deprecated
	public String getAttribute_type() {
		return _attribute_type;
	}

	//Deprecated
	public void setAttribute_type(String _attribute_type) {
		this._attribute_type = _attribute_type;
	}

	//Deprecated
	public void setInternal_name(String _internal_name) {
		this._internal_name = _internal_name;
	}

	//Deprecated
	public String getInternal_name() {
		return _internal_name;
	}

	//Deprecated
	public void setExternal_name(String _external_name) {
		this._external_name = _external_name;
	}

	//Deprecated
	public String getExternal_name() {
		return _external_name;
	}

	public int getLinear_regression_duration() {
		return _linear_regression_duration;
	}

	public void setLinear_regression_duration(int _linear_regression_duration) {
		this._linear_regression_duration = _linear_regression_duration;
	}


}