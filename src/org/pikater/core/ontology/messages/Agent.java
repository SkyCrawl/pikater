package org.pikater.core.ontology.messages;

import org.pikater.core.ontology.messages.option.Option;

import jade.content.Concept;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;

public class Agent implements Concept, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6257129995443147585L;
	private String _name;
	private String _type;
	private List _options;
	private String _gui_id;
	private byte [] object;
	
	// Methods required to use this class to represent the OPTIONS role
	public void setOptions(List options) {
		_options = options;
	}

	public List getOptions() {
		return _options;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getType() {
		return _type;
	}

	public void setGui_id(String gui_id) {
		_gui_id = gui_id;
	}

	public String getGui_id() {
		return _gui_id;
	}
	
	public void setObject(byte [] object) {
		this.object = object;
	}
	public byte [] getObject() {
		return object;
	}
	// -----------------------------

	public List stringToOptions(String optString) {
		String[] optArray = optString.split("[ ]+");
		List optList = new ArrayList();
		for (int i = 0; i < optArray.length; i++) {
			if (optArray[i].startsWith("-")) {
				String name = optArray[i].replaceFirst("-", "");
				// if the next array element is again an option name,
				// (or it is the last element)
				// => it's a boolean parameter

				Option opt = new Option();
				String value;
				if (i == optArray.length - 1) {
					value = "True";
					opt.setData_type("BOOLEAN");
				} else {
					// if (optArray[i+1].startsWith("-")){
					if (optArray[i + 1].matches("\\-[A-Z]")) {
						value = "True";
						opt.setData_type("BOOLEAN");
					} else {
						value = optArray[i + 1];
						opt.setData_type("FLOAT");
					}
				}

				opt.setName(name);
				opt.setValue(value);

				if (opt.getUser_value() == null) {
					// first string -> options, parsing the string from user
					if (value.contains("?")) {
						opt.setMutable(true);
					}
					opt.setUser_value(value);

				}
				optList.add(opt);
			}
		}
		return optList;
	}

	public String optionsToString() {
		String str = "";
		if (_options == null) {
			return str;
		}
		Iterator itr = _options.iterator();
		while (itr.hasNext()) {
			org.pikater.core.ontology.messages.option.Option next_opt =
					(org.pikater.core.ontology.messages.option.Option) itr.next();
			if (next_opt.getValue() == null || next_opt.getValue().equals("null")){
				// don't include this option to the string
			}
			else{
				if (next_opt.getData_type().equals("BOOLEAN")) {
					if (next_opt.getValue().equals("True")) {
						str += "-" + next_opt.getName() + " ";
					}
				} else {
					str += "-" + next_opt.getName() + " " + next_opt.getValue()
							+ " ";
				}
			}
		}
		return str;
	}

        public Option getOptionByName(String name) {

            for (int i = 0; i < getOptions().size(); i++) {
                Option o = (Option)_options.get(i);
                if (o.getName().equals(name)) {
                    return o;
                }
            }

            return null;
        }

        public String toGuiString() {
		if (_options == null) {
			return "";
		}
                String str = "";
		Iterator itr = _options.iterator();
		while (itr.hasNext()) {
			org.pikater.core.ontology.messages.option.Option next_opt =
					(org.pikater.core.ontology.messages.option.Option) itr.next();
                        if (next_opt.getData_type().equals("BOOLEAN")) {
                        	if (next_opt.getValue().equals("True")) {
                                str += "-" + next_opt.getName() + " ";
                            }
                            if (next_opt.getMutable()) {
                                str += "-" + next_opt.getName() + " ? ";
                            }
                        } else
 
                        if (!next_opt.getMutable())
                            str += "-" + next_opt.getName() + " " + next_opt.getValue() + " ";
                        else {
                            str += "-" + next_opt.getName() + " " + next_opt.getValue() + "/";
                            if (!next_opt.getIs_a_set())
                                str += "<" + next_opt.getRange().getMin() + "," + next_opt.getRange().getMax() + ">";
                            else {
                                String set = "";
                                for (int i = 0; i < next_opt.getSet().size(); i++) {
                                    set += next_opt.getSet().get(i);
                                    if (i != next_opt.getSet().size()-1) {
                                        set += " ";
                                    }
                                }
                                str += "{" + set + "}";
                            }
                            str += "/" + next_opt.getNumber_of_values_to_try() + " ";
                        }
		}
		return str;
        }
        
        public Object clone() {
            
            Agent agent = new Agent();
            agent.setGui_id(_gui_id);
            agent.setName(_name);
            agent.setObject(object);
            agent.setOptions(_options);
            agent.setType(_type);
            
            return agent;
        }

}