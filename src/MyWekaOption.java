import java.io.*;
// import weka.core.Option;


public class MyWekaOption implements Serializable{
	public enum dataType {
		    INT, FLOAT, BOOLEAN, MIXED 
	}
	// list - pocet polozek, range
	
	public boolean mutable = false;
	public float lower;
	public float upper;
	public float numArgsMin;
	public float numArgsMax;
	public dataType type;
	
	public String description;
	public String name;
	public int numArguments;
	public String synopsis;
	public String default_value;
	
	
	public String[] set;
	public boolean isASet = false;
	
	public MyWekaOption(String arg0, String arg1, int arg2, String arg3, dataType _type,
			int _numArgsMin, int _numArgsMax, String range, String _default_value, String rest) {
		// super(arg0, arg1, arg2, arg3);
		
		description = arg0;
		name = arg1;
		numArguments = arg2;
		synopsis = arg3;
		
        numArgsMin = _numArgsMin;
        numArgsMax = _numArgsMax;
        
        type = _type;
        
        default_value = _default_value;
        
		String delims = "[, ]+";
        String[] params = rest.split(delims);
        
        if (range.equals("r")){
 		    int maxValue;
	    	if (params[1].equals("MAXINT")){
	    	   maxValue = Integer.MAX_VALUE;
	    	}
	    	else {
	    	   maxValue = new Integer(params[1]).intValue();
	    	}
        	
        	lower = new Float(params[0]);
            upper = new Float(maxValue);
		}
        if (range.equals("s")){
        	isASet = true;
        	set = new String[params.length];
			System.arraycopy(params, 0, set, 0, params.length);
        }
        	
	}  // end constructor
	
}
