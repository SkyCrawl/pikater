package pikater.agents.computing;

import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import pikater.gui.java.MyWekaOption;
import pikater.ontology.messages.*;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Option;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

public class Agent_WekaCA extends Agent_ComputingAgent {
	private static final long serialVersionUID = -3594051562022044000L;
	private Classifier cls = null;//TODO: constructors
	private String agentType = null;
	private String wekaClassName = null;
	
	private String DurationServiceRegression_output_prefix = "  --d-- ";
	
	protected Classifier getModelObject(){
		return cls;
	}

	protected boolean setModelObject(Classifier _cls){
		cls = _cls;
		agentType = null;
		setWekaClassName(cls.getClass().getName());
		return true;

	}
	@Override
	public String getAgentType() {
		return agentType;	
	}
	
	public void setWekaClassName(String _className){
		wekaClassName = _className;
		String[] namelst = wekaClassName.split("\\.");
		if(namelst.length>0)
			agentType = namelst[namelst.length-1];
	}
	
	public void createClassifierClass(){
		//TODO: Create cls according to agentType!!!
		if(wekaClassName == null || wekaClassName.length()==0)
		    return;

		try {
//:TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! wekaClassName has static value
		    //TODO: May take options as a second parameter:
		    wekaClassName = "weka.classifiers.functions.MultilayerPerceptron";
		    System.out.println(" "+wekaClassName);
		    cls = Classifier.forName(wekaClassName,null);

		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}

	@Override
	protected Date train(pikater.ontology.messages.Evaluation evaluation) throws Exception {
		working = true;
						
		if (getLocalName().equals("DurationServiceRegression")){
				log(DurationServiceRegression_output_prefix, 2);
		}
		log("Training...", 2);
				
		cls=null;
		createClassifierClass();//new cls
		if(cls==null)
			throw new Exception(getLocalName() + ": Weka classifier class hasn't been created (Wrong type?).");
		if (OPTIONS.length > 0) {
			cls.setOptions(OPTIONS);
		}
		
		long start = System.currentTimeMillis();
		cls.buildClassifier(train);
		long end = System.currentTimeMillis();
		long duration = end - start;
		if (duration < 1) { duration = 1; } 
		
		List evals = new ArrayList();
		
		Eval s = new Eval();
		s.setName("start");
		s.setValue(start);
		evals.add(s);
		
		Eval d = new Eval();
		d.setName("duration");
		d.setValue(duration);
		evals.add(d);

		if (getLocalName().equals("DurationServiceRegression")){
			log(DurationServiceRegression_output_prefix, 2);
		}
		log("start: " + new Date(start) + " : duration: " + duration, 2);
		
		state = states.TRAINED; // change agent state
		OPTIONS = cls.getOptions();

		// write out net parameters
		if (getLocalName().equals("DurationServiceRegression")){
                log(DurationServiceRegression_output_prefix+getOptions(),2);
		}
		else{
			log(getOptions(), 1);
		}
		
		working = false;
		
		// add evals to Evaluation
		List evaluations_new = evaluation.getEvaluations();
		
		Iterator itr = evals.iterator();
		while (itr.hasNext()) {
			Eval eval = (Eval) itr.next();
			evaluations_new.add(eval);
		}
		
		evaluation.setEvaluations(evaluations_new);
		
		return new Date(start);
	}

	protected String getOptFileName(){
		return "/options/"+getAgentType() +".opt";
	}

	protected Evaluation test(EvaluationMethod evaluation_method) throws Exception{
		working = true;
		log("Testing...", 2);

		// evaluate classifier and print some statistics
		Evaluation eval = null;				
		eval = new Evaluation(train);
		// if (train == null){ System.out.log("bacha, train je null"); }
		// if (eval == null){ System.out.log("bacha, eval je null"); }
		// if (cls == null){ System.out.log("bacha, cls je null"); }
		// if (test == null){ System.out.log("bacha, test je null"); }
		// doWait(10);
		
		log("Evaluation method: \t", 2);
		
		if (evaluation_method.getName().equals("CrossValidation") ){
			int folds = -1; 
			Iterator itr = evaluation_method.getOptions().iterator();
			while (itr.hasNext()) {
				pikater.ontology.messages.Option next = (pikater.ontology.messages.Option) itr.next();
				if (next.getName().equals("F")){
					folds = Integer.parseInt( (String)next.getValue() );
				}
			}
			if (folds == -1){
					folds = 5;
				  // TODO read default value from file (if necessary)
			}
			log(folds + "-fold cross validation.", 2);
			eval.crossValidateModel(
					cls,
					test,
					folds, new Random(1));
		}
		else{ // name = Standard
			log("Standard weka evaluation.", 2);
			eval.evaluateModel(cls, test);
		}
				
		log("Error rate: " + eval.errorRate()+" ", 1);
		log(eval.toSummaryString(getLocalName() + " agent: "
				+ "\nResults\n=======\n", false), 2);

		working = false;
		return eval;
	}

	@Override
	protected void evaluateCA(EvaluationMethod evaluation_method,
			pikater.ontology.messages.Evaluation evaluation) throws Exception{
		
		float default_value = Float.MAX_VALUE;
		Evaluation eval = test(evaluation_method);
		
		List evaluations = new ArrayList();
		Eval ev = new Eval();
		ev.setName("error_rate");
		ev.setValue((float) eval.errorRate());
		evaluations.add(ev);
		
		ev = new Eval();
		ev.setName("kappa_statistic");
		try {
			ev.setValue((float) eval.kappa());
		} catch (Exception e) {
			ev.setValue(default_value);
		}
		evaluations.add(ev);

		ev = new Eval();
		ev.setName("mean_absolute_error");
		try {
			ev.setValue((float) eval.meanAbsoluteError());
		} catch (Exception e) {
			ev.setValue(default_value);
		}
		evaluations.add(ev);

		ev = new Eval();
		ev.setName("relative_absolute_error");
		try {
			ev.setValue((float) eval.relativeAbsoluteError());
		} catch (Exception e) {
			ev.setValue(default_value);
		}
		evaluations.add(ev);
		
		ev = new Eval();
		ev.setName("root_mean_squared_error");
		try {
			ev.setValue((float) eval.rootMeanSquaredError());
		} catch (Exception e) {
			ev.setValue(default_value);
		}
		evaluations.add(ev);

		ev = new Eval();
		ev.setName("root_relative_squared_error");
		try {
			ev.setValue((float) eval.rootRelativeSquaredError());
		} catch (Exception e) {
			ev.setValue(default_value);
		}
		evaluations.add(ev);
		
		
		List evaluations_new = evaluation.getEvaluations();
		
		Iterator itr = evaluations.iterator();
		while (itr.hasNext()) {
			Eval _ev = (Eval) itr.next();
			evaluations_new.add(_ev);
		}
		
		evaluation.setEvaluations(evaluations_new);		
	}

	@Override
	protected DataInstances getPredictions(Instances test,
			DataInstances onto_test) {

		//Evaluation eval = test();
		double pre[] = new double[test.numInstances()];
		for (int i = 0; i < test.numInstances(); i++) {
			try {
				pre[i] = getModelObject().classifyInstance(test.instance(i));
			} catch (Exception e) {
				pre[i] = Integer.MAX_VALUE;
			}
		}

		// copy results to the DataInstancs
		int i = 0;
		Iterator itr = onto_test.getInstances().iterator();
		while (itr.hasNext()) {
			Instance next_instance = (Instance) itr.next();
			next_instance.setPrediction(pre[i]);
			i++;
		}

		return onto_test;
	}

	private pikater.ontology.messages.Option convertOption(
			MyWekaOption _weka_opt) {
		pikater.ontology.messages.Option opt = new pikater.ontology.messages.Option();
		Interval interval = null;
		opt.setMutable(_weka_opt.mutable);

		interval = new Interval();
		interval.setMin(_weka_opt.lower);
		interval.setMax(_weka_opt.upper);
		opt.setRange(interval);

		if (_weka_opt.set != null) {
			// copy array to List
			List set = new ArrayList();
			for (int i = 0; i < _weka_opt.set.length; i++) {
				set.add(_weka_opt.set[i]);
			}
			opt.setSet(set);
		}

		opt.setIs_a_set(_weka_opt.isASet);

		interval = new Interval();
		interval.setMin(_weka_opt.numArgsMin);
		interval.setMax(_weka_opt.numArgsMax);
		opt.setNumber_of_args(interval);

		opt.setData_type(_weka_opt.type.toString());
		opt.setDescription(_weka_opt.description);
		opt.setName(_weka_opt.name);
		opt.setSynopsis(_weka_opt.synopsis);
		opt.setDefault_value(_weka_opt.default_value);
		opt.setValue(_weka_opt.default_value);
		return opt;
	}

	@Override
	protected void getParameters() {
		//set the Agent type according to the arguments

		if(className==null){
			logError("Wrong arguments of WekaCA");
			return;//TODO: error
		}
		setWekaClassName(className);
		
		createClassifierClass();//in order not to have cls==null
		 
		// fills the global Options vector

		// System.out.log(getLocalName() + ": The options are: ");

		String optPath = System.getProperty("user.dir") + getOptFileName();

		agent_options = new pikater.ontology.messages.Agent();
		agent_options.setName(getLocalName());
		agent_options.setType(getAgentType());
		// read options from file
		try {
			/* Sets up a file reader to read the options file */
			FileReader input = new FileReader(optPath);
			/*
			 * Filter FileReader through a Buffered read to read a line at a
			 * time
			 */
			BufferedReader bufRead = new BufferedReader(input);

			String line; // String that holds current file line
			int count = 0; // Line number of count
			// Read first line
			line = bufRead.readLine();
			count++;

			// list of ontology.messages.Option
			List _options = new ArrayList();

			// Read through file one line at time. Print line # and line
			while (line != null) {
				// System.out.log("    " + count + ": " + line);

				// parse the line
				String delims = "[ ]+";
				String[] params = line.split(delims, 7);

				if (params[0].equals("$")) {

					MyWekaOption.dataType dt = MyWekaOption.dataType.BOOLEAN;

					if (params[2].equals("boolean")) {
						dt = MyWekaOption.dataType.BOOLEAN;
					}
					if (params[2].equals("float")) {
						dt = MyWekaOption.dataType.FLOAT;
					}
					if (params[2].equals("int")) {
						dt = MyWekaOption.dataType.INT;
					}
					if (params[2].equals("mixed")) {
						dt = MyWekaOption.dataType.MIXED;
					}

					String[] default_options = ((Classifier)getModelObject()).getOptions();					 
					// System.out.log("Default options: "+Arrays.deepToString(default_options));
										
					Enumeration en = ((Classifier)getModelObject()).listOptions();
					while (en.hasMoreElements()) {

						Option next = (weka.core.Option) en.nextElement();
						String default_value = null; 
						for (int i = 0; i < default_options.length; i++) {
							if (default_options[i].equals("-" + next.name())) {
								if (default_options[i].startsWith("-")) {
									// if the next array element is again an
									// option name,
									// (or it is the last element)
									// => it's a boolean parameter
									if (i == default_options.length - 1) {
										default_value = "True";
									} else {
										// if
										// (default_options[i+1].startsWith("-")){
										if (default_options[i + 1]
												.matches("\\-[A-Z]")) {
											default_value = "True";
										} else {
											default_value = default_options[i + 1];
										}
									}
								}
							}
						}

						if ((next.name()).equals(params[1])) {
							MyWekaOption o;
							if (params.length > 4) {

								o = new MyWekaOption(next.description(), next
										.name(), next.numArguments(), next
										.synopsis(), dt, new Integer(params[3])
										.intValue(), new Integer(params[4])
										.intValue(), params[5], default_value,
										params[6]);

							} else {
								o = new MyWekaOption(next.description(), next
										.name(), next.numArguments(), next
										.synopsis(), dt, 0, 0, "",
										default_value, "");
							}

							// convert&save o to options vector
							_options.add(convertOption(o));
						}
					}

				}

				line = bufRead.readLine();

				count++;
			}
			agent_options.setOptions(_options);
			bufRead.close();

		} catch (ArrayIndexOutOfBoundsException e) {
			/*
			 * If no file was passed on the command line, this exception is
			 * generated. A message indicating how to the class should be called
			 * is displayed
			 */
			logError("No file specified.");
		} catch (Exception e) {
			e.printStackTrace();
			logError("Reading options from .opt file failed.");
		}
	} // end getParameters
}