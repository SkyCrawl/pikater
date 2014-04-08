package org.pikater.core.ontology.messages;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;
import org.pikater.core.ontology.description.ComputationDescription;
import org.pikater.core.ontology.description.ComputingAgent;
import org.pikater.core.ontology.description.DataSourceDescription;
import org.pikater.core.ontology.description.FileVisualizer;
import org.pikater.core.ontology.messages.metadata.*;

public class MessagesOntology extends Ontology {

	// A symbolic constant, containing the name of this ontology.

	private static final long serialVersionUID = 1634256259949979087L;

	public static final String NAME = "messages-ontology";

	// VOCABULARY
	// Concepts

	public static final String TASK = "TASK";
	public static final String TASK_ID = "id";
	public static final String TASK_PROBLEM_ID = "problem_id";
	public static final String TASK_AGENT = "agent";
	public static final String TASK_DATA = "data";
	public static final String TASK_RESULT = "result";
	public static final String TASK_SAVE_MODE = "save_mode";
	public static final String TASK_USERID = "userID";
	public static final String TASK_START = "start";
	public static final String TASK_FINISH = "finish";
	public static final String TASK_GET_RESULTS = "get_results";
	public static final String TASK_SAVE_RESULTS = "save_results";
	public static final String TASK_GUI_AGENT = "gui_agent";
	public static final String TASK_PROBLEM_NAME = "problem_name";
	public static final String TASK_NOTE = "note";
	public static final String TASK_EVALUATION_METHOD = "evaluation_method";

	public static final String ID = "ID";
	public static final String ID_IDENTIFICATOR = "identificator";
	public static final String ID_SUBID = "subid";

	public static final String DATA = "data";
	public static final String DATA_TRAIN_FILE_NAME = "train_file_name";
	public static final String DATA_TEST_FILE_NAME = "test_file_name";
	public static final String DATA_LABEL_FILE_NAME = "label_file_name";
	public static final String DATA_EXTERNAL_TRAIN_FILE_NAME = "external_train_file_name";
	public static final String DATA_EXTERNAL_TEST_FILE_NAME = "external_test_file_name";
	public static final String DATA_EXTERNAL_LABEL_FILE_NAME = "external_label_file_name";
	public static final String DATA_METADATA = "metadata";
	public static final String DATA_OUTPUT = "output";
	public static final String DATA_MODE = "mode";

	public static final String PROBLEM = "PROBLEM";
	public static final String PROBLEM_ID = "id";
	public static final String PROBLEM_GUI_ID = "gui_id";
	public static final String PROBLEM_STATUS = "status";
	public static final String PROBLEM_AGENTS = "agents";
	public static final String PROBLEM_DATA = "data";
	public static final String PROBLEM_TIMEOUT = "timeout";
	public static final String PROBLEM_METHOD = "method";
	public static final String PROBLEM_START = "start";
	public static final String PROBLEM_GET_RESULTS = "get_results";
	public static final String PROBLEM_SAVE_RESULTS = "save_results";
	public static final String PROBLEM_GUI_AGENT = "gui_agent";
	public static final String PROBLEM_NAME = "name";
	public static final String PROBLEM_EVALUATION_METHOD = "evaluation_method";
	public static final String PROBLEM_RECOMMENDER = "recommender";

	public static final String METHOD = "METHOD";
	public static final String METHOD_OPTIONS = "options";
	public static final String METHOD_NAME = "name";
	public static final String METHOD_ERROR_RATE = "error_rate";
	public static final String METHOD_MAXIMUM_TRIES = "maximum_tries";

	public static final String EVALUATION_METHOD = "METHOD";
	public static final String EVALUATION_METHOD_OPTIONS = "options";
	public static final String EVALUATION_METHOD_NAME = "name";

	public static final String EVALUATION = "EVALUATION";
	public static final String EVALUATION_EVALUATIONS = "evaluations";
	public static final String EVALUATION_LABELED_DATA = "labeled_data";
	public static final String EVALUATION_STATUS = "status";
	public static final String EVALUATION_OBJECT_FILENAME = "object_filename";
	public static final String EVALUATION_OBJECT = "object";
	public static final String EVALUATION_DATA_TABLE = "data_table";
	public static final String EVALUATION_START = "start";

	public static final String EVAL = "EVAL";
	public static final String EVAL_NAME = "name";
	public static final String EVAL_VALUE = "value";

	public static final String RESULTS = "RESULTS";
	public static final String RESULTS_PROBLEM_ID = "problem_id";
	public static final String RESULTS_RESULTS = "results";
	public static final String RESULTS_AVG_ERROR_RATE = "avg_error_rate";
	public static final String RESULTS_AVG_KAPPA_STATISTIC = "avg_kappa_statistic";
	public static final String RESULTS_AVG_MEAN_ABSOLUTE_ERROR = "avg_mean_absolute_error";
	public static final String RESULTS_AVG_MEAN_SQUARED_ERROR = "avg_root_mean_squared_error";
	public static final String RESULTS_AVG_RELATIVE_ABSOLUTE_ERROR = "avg_relative_absolute_error";
	public static final String RESULTS_AVG_RELATIVE_SQUARED_ERROR = "avg_root_relative_squared_error";

	public static final String OPTION = "OPTION";
	public static final String OPTION_MUTABLE = "mutable";
	public static final String OPTION_RANGE = "range";
	public static final String OPTION_SET = "set";
	public static final String OPTION_IS_A_SET = "is_a_set";
	public static final String OPTIONS_NUM_ARGS = "number_of_args";
	public static final String OPTION_DATA_TYPE = "data_type";
	public static final String OPTION_WEKA_DESTRIPTION = "description";
	public static final String OPTION_WEKA_NAME = "name";
	public static final String OPTION_WEKA_SYNOPSIS = "synopsis";
	public static final String OPTION_VALUE = "value";
	public static final String OPTION_DEFAULT_VALUE = "default_value";
	public static final String OPTION_USER_VALUE = "user_value";
	public static final String OPTION_NUMBER_OF_VALUES_TO_TRY = "number_of_values_to_try";

	public static final String SEARCHITEM = "SEARCH-ITEM";
	public static final String SEARCHITEM_NUMBER_OF_VALUES_TO_TRY = "number_of_values_to_try";

	public static final String BOOLSITEM = "BOOL-SEARCH-ITEM";

	public static final String INTSITEM = "INT-SEARCH-ITEM";
	public static final String INTSITEM_MIN = "min";
	public static final String INTSITEM_MAX = "max";

	public static final String FLOATSITEM = "FLOAT-SEARCH-ITEM";
	public static final String FLOATSITEM_MIN = "min";
	public static final String FLOATSITEM_MAX = "max";

	public static final String SETSITEM = "SET-SEARCH-ITEM";
	public static final String SETSITEM_SET = "set";

	public static final String SEARCHSOLUTION = "SEARCH-SOLUTION";
	public static final String SEARCHSOLUTION_VALUES = "values";

	public static final String AGENT = "AGENT";
	public static final String AGENT_NAME = "name";
	public static final String AGENT_TYPE = "type";
	public static final String AGENT_GUI_ID = "gui_id";
	public static final String AGENT_OPTIONS = "options";
	public static final String AGENT_OBJECT = "object";

	public static final String INTERVAL = "INTERVAL";
	public static final String INTERVAL_MIN = "min";
	public static final String INTERVAL_MAX = "max";

	public static final String DATA_INSTANCES = "DATA-INSTANCES";
	public static final String DATA_INSTANCES_ATTRIBUTES = "attributes";
	public static final String DATA_INSTANCES_INSTANCES = "instances";
	public static final String DATA_INSTANCES_NAME = "name";
	public static final String DATA_INSTANCES_CLASS_INDEX = "class_index";

	public static final String ATTRIBUTE = "ATTRIBUTE";
	public static final String ATTRIBUTE_NAME = "name";
	public static final String ATTRIBUTE_TYPE = "type";
	public static final String ATTRIBUTE_VALUES = "values";
	public static final String ATTRIBUTE_DATE_FORMAT = "date_format";

	public static final String INSTANCE = "INSTANCE";
	public static final String INSTANCE_VALUES = "values";
	public static final String INSTANCE_MISSING = "missing";

	public static final String METADATA = "METADATA";
	public static final String METADATA_INTERNAL_NAME = "internal_name";
	public static final String METADATA_EXTERNAL_NAME = "external_name";
	public static final String METADATA_NUMBER_OF_INSTANCES = "number_of_instances";
	public static final String METADATA_NUMBER_OF_ATTRIBUTES = "number_of_attributes";
	public static final String METADATA_MISSING_VALUES = "missing_values";
	public static final String METADATA_DEFAULT_TASK = "default_task";
	public static final String METADATA_ATTRIBUTE_TYPE = "attribute_type";
	public static final String METADATA_LINEAR_REGRESSION_DURATION = "linear_regression_duration";
	public static final String METADATA_ATTRIBUTE_METADATA_LIST = "attribute_metadata_list";
	
	public static final String ATTRIBUTE_METADATA = "ATTRIBUTE_METADATA";
	public static final String ATTRIBUTE_METADATA_RATIO_OF_MISSING_VALUES = "ratio_of_missing_values";
	public static final String ATTRIBUTE_METADATA_ORDER = "order";
	public static final String ATTRIBUTE_METADATA_ENTROPY = "entropy";
	public static final String ATTRIBUTE_METADATA_ATTRIBUTE_CLASS_ENTROPY = "attribute_class_entropy";
	
	public static final String OPTIONS = "OPTIONS";
	public static final String OPTIONS_LIST = "list";

	public static final String FITNESS = "FITNESS";
	public static final String FITNESS_SOLUTION = "solution";
	public static final String FITNESS_FITNESS_VALUES = "fitnessValues";

	public static final String DURATION = "DURATION";
	public static final String DURATION_START = "start";
	public static final String DURATION_DURATION = "duration";
	public static final String DURATION_LR_DURATION = "LR_duration";

	// Predicates
	public static final String PARTIALRESULTS = "PARTIALRESULTS";
	public static final String PARTIALRESULTS_TASK = "task";
	public static final String PARTIALRESULTS_TASK_ID = "task_id";
	public static final String PARTIALRESULTS_RESULTS = "results";

	// Actions

	public static final String EXECUTE = "EXECUTE";
	public static final String EXECUTE_TASK = "task";
	public static final String EXECUTE_METHOD = "method";

	public static final String IMPORT_FILE = "IMPORT_FILE";
	public static final String IMPORT_USER = "userID";
	public static final String IMPORT_FILENAME = "externalFilename";
	public static final String IMPORT_FILECONTENT = "fileContent";
	public static final String IMPORT_FILE_TEMP = "tempFile";

	public static final String TRANSLATE = "TRANSLATE";
	public static final String TRANSLATE_USER = "userID";
	public static final String TRANSLATE_EXTERNAL_FILENAME = "externalFilename";
	public static final String TRANSLATE_INTERNAL_FILENAME = "internalFilename";

	public static final String SOLVE = "SOLVE";
	public static final String SOLVE_PROBLEM = "problem";

	public static final String GET_OPTIONS = "GET-OPTIONS";

	public static final String SAVE_RESULTS = "SAVE-RESULTS";
	public static final String SAVE_RESULTS_TASK = "task";

	public static final String GET_DATA = "GET-DATA";
	public static final String GET_DATA_FILE_NAME = "file_name";
	public static final String GET_DATA_O2A_AGENT = "o2a_agent";

	public static final String SAVE_METADATA = "SAVE-METADATA";
	public static final String SAVE_METADATA_METADATA = "metadata";

	public static final String GET_ALL_METADATA = "GET-ALL-METADATA";
	public static final String GET_ALL_METADATA_EXCEPTIONS = "exceptions";
	public static final String GET_ALL_METADATA_RESULTS_REQUIRED = "results_required";

	public static final String GET_THE_BEST_AGENT = "GET-THE-BEST-AGENT";
	public static final String GET_THE_BEST_AGENT_NEAREST_FILE_NAME = "nearest_file_name";
        public static final String GET_THE_BEST_AGENT_NUMBER_OF_AGENTS = "numberOfAgents";

	public static final String GET_FILE_INFO = "GET-FILE-INFO";
	public static final String GET_FILE_INFO_USERID = "userID";
	public static final String GET_FILE_INFO_ATTR_LO = "attrLower";
	public static final String GET_FILE_INFO_ATTR_HI = "attrUpper";
	public static final String GET_FILE_INFO_INST_LO = "instLower";
	public static final String GET_FILE_INFO_INST_HI = "instUpper";
	public static final String GET_FILE_INFO_FILENAME = "filename";
	public static final String GET_FILE_INFO_MISSING_VALUES = "missingValues";
	public static final String GET_FILE_INFO_DEFAULT_TASK = "defaultTask";
	public static final String GET_FILE_INFO_ATTRIBUTE_TYPE = "attributeType";

	public static final String UPDATE_METADATA = "UPDATE-METADATA";
	public static final String UPDATE_METADATA_METADATA = "metadata";

	public static final String GET_FILES = "GET-FILES";
	public static final String GET_FILES_USERID = "userID";

	public static final String LOAD_AGENT = "LOAD-AGENT";
	public static final String LOAD_AGENT_FILENAME = "filename";
	public static final String LOAD_AGENT_FIRST_ACTION = "first_action";
	public static final String LOAD_AGENT_OBJECT = "object";

	public static final String SAVE_AGENT = "SAVE-AGENT";
	public static final String SAVE_AGENT_USERID = "userID";
	public static final String SAVE_AGENT_AGENT = "agent";

	public static final String GET_SAVED_AGENTS = "GET-SAVED-AGENTS";
	public static final String GET_SAVED_AGENTS_USERID = "userID";

	public static final String SAVED_RESULT = "SAVED-RESULT";
	public static final String SAVED_RESULT_ERR = "errorRate";
	public static final String SAVED_RESULT_KAPPA = "kappaStatistic";
	public static final String SAVED_RESULT_RMSE = "RMSE";
	public static final String SAVED_RESULT_RAE = "relativeAbsoluteError";
	public static final String SAVED_RESULT_MAE = "meanAbsError";
	public static final String SAVED_RESULT_RRSE = "rootRelativeSquaredError";
	public static final String SAVED_RESULT_TRAIN = "trainFile";
	public static final String SAVED_RESULT_TEST = "testFile";
	public static final String SAVED_RESULT_AGENT_TYPE = "agentType";
	public static final String SAVED_RESULT_DATE = "date";
	public static final String SAVED_RESULT_USER_ID = "userID";
	public static final String SAVED_RESULT_OPTIONS = "agentOptions";

	public static final String LOAD_RESULTS = "LOAD-RESULTS";
	public static final String LOAD_RESULTS_ERR_LOW = "errorLower";
	public static final String LOAD_RESULTS_ERR_HI = "errorUpper";
	public static final String LOAD_RESULTS_KAPPA_HI = "kappaUpper";
	public static final String LOAD_RESULTS_KAPPA_LOW = "kappaLower";
	public static final String LOAD_RESULTS_RMSE_HI = "mseUpper";
	public static final String LOAD_RESULTS_RMSE_LOW = "mseLower";
	public static final String LOAD_RESULTS_RAE_HI = "raeUpper";
	public static final String LOAD_RESULTS_RAE_LOW = "raeLower";
	public static final String LOAD_RESULTS_MAE_HI = "maeUpper";
	public static final String LOAD_RESULTS_MAE_LOW = "maeLower";
	public static final String LOAD_RESULTS_RRSE_HI = "rrseUpper";
	public static final String LOAD_RESULTS_RRSE_LOW = "rrseLower";
	public static final String LOAD_RESULTS_TRAIN = "dataFile";
	public static final String LOAD_RESULTS_TEST = "testFile";
	public static final String LOAD_RESULTS_AGENT_TYPE = "agentType";
	public static final String LOAD_RESULTS_AFTER_DATE = "startDate";
	public static final String LOAD_RESULTS_BEFORE_DATE = "endDate";
	public static final String LOAD_RESULTS_USER_ID = "userID";

	public static final String DELETE_TEMP_FILES = "DELETE-TEMP-FILES";

	public static final String GET_NEXT_PARAMETERS = "GET_NEXT_PARAMETERS";
	public static final String GET_NEXT_PARAMETERS_SCHEMA = "schema";
	public static final String GET_NEXT_PARAMETERS_SEARCH_OPTIONS = "search_options";

	public static final String CREATE_AGENT = "CREATE_AGENT";
	public static final String CREATE_AGENT_TYPE = "type";
	public static final String CREATE_AGENT_NAME = "name";
	public static final String CREATE_AGENT_ARGUMENTS = "arguments";

	public static final String EXECUTE_PARAMETERS = "EXECUTE_PARAMETERS";
	public static final String EXECUTE_PARAMETERS_SOLUTIONS = "solutions";

	public static final String GET_AGENTS = "GET_AGENTS";
	public static final String GET_AGENTS_AGENT = "agent";
	public static final String GET_AGENTS_NUMBER = "number";
	public static final String GET_AGENTS_TASK_ID = "task_id";

	public static final String GET_DURATION = "GET_DURATION";
	public static final String GET_DURATION_DURATION = "duration";

	public static final String GET_METADATA = "GET_METADATA";
	public static final String GET_METADATA_INTERNAL_FILENAME = "internal_filename";
	public static final String GET_METADATA_EXTERNAL_FILENAME = "external_filename";

	public static final String SHUTDOWN_DATABASE = "SHUTDOWN-DATABASE";

	public static final String RECOMMEND = "RECOMMEND";
	public static final String RECOMMEND_DATA = "data";
	public static final String RECOMMEND_RECOMMENDER = "recommender";
	
	public static final String SEND_EMAIL = "SEND_EMAIL";
	public static final String SEND_EMAIL_TO_ADDRESS = "to_address";
	public static final String SEND_EMAIL_TYPE = "email_type";
	
	public static final String EXPERIMENT = "EXPERIMENT";
	public static final String EXPERIMENT_DESCRIPTION = "description";
	
	public static final String COMP_DESCRIPTION = "ComputationDescription";
	public static final String COMP_DESCRIPTION_ROOT = "rootElement";
	
	public static final String FILEVISUALIZER = "FileVisualizer"; 
	public static final String FILEVISUALIZER_DATASOURCE = "dataSource";

	public static final String DATAPROVIDER = "DataSourceDescription";
	public static final String DATAPROVIDER_DATAPROVIDER = "dataProvider";
	
	public static final String COMPUTINGAGENT = "ComputingAgent";
	public static final String COMPUTINGAGENT_MODEL = "modelClass";


	// public static final String SEND_OPTIONS = "SEND-OPTIONS";
	// public static final String SEND_OPTIONS_OPTIONS = "options";

	private static Ontology theInstance = new MessagesOntology();

	/**
	 * This method grants access to the unique instance of the ontology.
	 * 
	 * @return An <code>Ontology</code> object, containing the concepts of the
	 *         ontology.
	 */
	public static Ontology getInstance() {
		return theInstance;
	}

	/**
	 * Constructor
	 */
	private MessagesOntology() {
		// __CLDC_UNSUPPORTED__BEGIN
		super(NAME, BasicOntology.getInstance());

		try {
			add(new ConceptSchema(TASK), Task.class);
			add(new ConceptSchema(DATA), Data.class);
			add(new ConceptSchema(OPTION), Option.class);

			add(new ConceptSchema(SEARCHITEM), SearchItem.class);
			add(new ConceptSchema(BOOLSITEM), BoolSItem.class);
			add(new ConceptSchema(INTSITEM), IntSItem.class);
			add(new ConceptSchema(FLOATSITEM), FloatSItem.class);
			add(new ConceptSchema(SETSITEM), SetSItem.class);

			add(new ConceptSchema(SEARCHSOLUTION), SearchSolution.class);

			add(new ConceptSchema(INTERVAL), Interval.class);
			add(new ConceptSchema(AGENT), Agent.class);
			add(new ConceptSchema(PROBLEM), Problem.class);
			add(new ConceptSchema(METHOD), Method.class);
			add(new ConceptSchema(EVALUATION_METHOD), EvaluationMethod.class);
			add(new ConceptSchema(EVALUATION), Evaluation.class);
			add(new ConceptSchema(EVAL), Eval.class);
			add(new ConceptSchema(RESULTS), Results.class);
			add(new ConceptSchema(DATA_INSTANCES), DataInstances.class);
			add(new ConceptSchema(ATTRIBUTE), Attribute.class);
			add(new ConceptSchema(INSTANCE), Instance.class);
			add(new ConceptSchema(METADATA), Metadata.class);
			add(new ConceptSchema(ATTRIBUTE_METADATA), AttributeMetadata.class);
			add(new ConceptSchema(SAVED_RESULT), SavedResult.class);
			add(new ConceptSchema(OPTIONS), Options.class);
			add(new ConceptSchema(FITNESS), Fitness.class);
			add(new ConceptSchema(ID), Id.class);
			add(new ConceptSchema(DURATION), Duration.class);

			add(new PredicateSchema(PARTIALRESULTS), PartialResults.class);

			add(new AgentActionSchema(GET_OPTIONS), GetOptions.class);
			add(new AgentActionSchema(EXECUTE), Execute.class);
			add(new AgentActionSchema(SOLVE), Solve.class);
			add(new AgentActionSchema(IMPORT_FILE), ImportFile.class);
			add(new AgentActionSchema(TRANSLATE), TranslateFilename.class);
			add(new AgentActionSchema(SAVE_RESULTS), SaveResults.class);
			add(new AgentActionSchema(SAVE_METADATA), SaveMetadata.class);
			add(new AgentActionSchema(GET_DATA), GetData.class);
			add(new AgentActionSchema(GET_ALL_METADATA), GetAllMetadata.class);
			add(new AgentActionSchema(GET_THE_BEST_AGENT), GetTheBestAgent.class);
			add(new AgentActionSchema(GET_FILE_INFO), GetFileInfo.class);
			add(new AgentActionSchema(UPDATE_METADATA), UpdateMetadata.class);
			add(new AgentActionSchema(GET_FILES), GetFiles.class);
			add(new AgentActionSchema(LOAD_RESULTS), LoadResults.class);
			add(new AgentActionSchema(LOAD_AGENT), LoadAgent.class);
			add(new AgentActionSchema(SAVE_AGENT), SaveAgent.class);
			add(new AgentActionSchema(GET_SAVED_AGENTS), GetSavedAgents.class);
			add(new AgentActionSchema(DELETE_TEMP_FILES), DeleteTempFiles.class);
			add(new AgentActionSchema(GET_NEXT_PARAMETERS), GetParameters.class);
			add(new AgentActionSchema(CREATE_AGENT), CreateAgent.class);
			add(new AgentActionSchema(EXECUTE_PARAMETERS),
					ExecuteParameters.class);
			add(new AgentActionSchema(GET_AGENTS), GetAgents.class);
			add(new AgentActionSchema(GET_DURATION), GetDuration.class);
			add(new AgentActionSchema(GET_METADATA), GetMetadata.class);
			add(new AgentActionSchema(SHUTDOWN_DATABASE),
					ShutdownDatabase.class);
			add(new AgentActionSchema(RECOMMEND), Recommend.class);
			add(new AgentActionSchema(SEND_EMAIL), SendEmail.class);
			
			
			add(new AgentActionSchema(EXPERIMENT), ExecuteExperiment.class);
			
			add(new ConceptSchema(COMP_DESCRIPTION), ComputationDescription.class);
			add(new ConceptSchema(FILEVISUALIZER), FileVisualizer.class);
			add(new ConceptSchema(DATAPROVIDER), DataSourceDescription.class);
			add(new ConceptSchema(COMPUTINGAGENT), ComputingAgent.class);

	
			ConceptSchema cs = (ConceptSchema) getSchema(ID);
			cs.add(ID_IDENTIFICATOR,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(ID_SUBID, (ConceptSchema) getSchema(ID),
					ObjectSchema.OPTIONAL);

			cs = (ConceptSchema) getSchema(PROBLEM);
			cs.add(PROBLEM_ID, (ConceptSchema) getSchema(ID),
					ObjectSchema.OPTIONAL);
			cs.add(PROBLEM_GUI_ID,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(PROBLEM_STATUS,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(PROBLEM_AGENTS, (ConceptSchema) getSchema(AGENT), 1,
					ObjectSchema.UNLIMITED);
			cs.add(PROBLEM_DATA, (ConceptSchema) getSchema(DATA), 1,
					ObjectSchema.UNLIMITED);
			cs.add(PROBLEM_TIMEOUT,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(PROBLEM_METHOD, (ConceptSchema) getSchema(AGENT));
			cs.add(PROBLEM_START,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(PROBLEM_GET_RESULTS,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(PROBLEM_SAVE_RESULTS,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			cs.add(PROBLEM_GUI_AGENT,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(PROBLEM_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(PROBLEM_EVALUATION_METHOD,
					(ConceptSchema) getSchema(EVALUATION_METHOD));
			cs.add(PROBLEM_RECOMMENDER, (ConceptSchema) getSchema(AGENT), ObjectSchema.OPTIONAL);

			cs = (ConceptSchema) getSchema(METHOD);
			cs.add(METHOD_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(METHOD_OPTIONS, (ConceptSchema) getSchema(OPTION), 0,
					ObjectSchema.UNLIMITED);

			cs = (ConceptSchema) getSchema(TASK);
			cs.add(TASK_ID, (ConceptSchema) getSchema(ID));
			cs.add(TASK_PROBLEM_ID, (ConceptSchema) getSchema(ID),
					ObjectSchema.OPTIONAL);
			cs.add(TASK_AGENT, (ConceptSchema) getSchema(AGENT));
			cs.add(TASK_DATA, (ConceptSchema) getSchema(DATA));
			cs.add(TASK_RESULT, (ConceptSchema) getSchema(EVALUATION),
					ObjectSchema.OPTIONAL);
			cs.add(TASK_SAVE_MODE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(TASK_GET_RESULTS,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(TASK_SAVE_RESULTS,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			cs.add(TASK_GUI_AGENT,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);

			cs.add(TASK_USERID,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(TASK_START,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(TASK_FINISH,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(TASK_PROBLEM_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(TASK_NOTE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(TASK_EVALUATION_METHOD,
					(ConceptSchema) getSchema(EVALUATION_METHOD));

			cs = (ConceptSchema) getSchema(DATA);
			cs.add(DATA_TRAIN_FILE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(DATA_TEST_FILE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(DATA_LABEL_FILE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(DATA_EXTERNAL_LABEL_FILE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(DATA_EXTERNAL_TEST_FILE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(DATA_EXTERNAL_TRAIN_FILE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(DATA_METADATA, (ConceptSchema) getSchema(METADATA),
					ObjectSchema.OPTIONAL);
			cs.add(DATA_OUTPUT,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(DATA_MODE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);

			cs = (ConceptSchema) getSchema(INTERVAL);
			cs.add(INTERVAL_MIN,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(INTERVAL_MAX,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));

			cs = (ConceptSchema) getSchema(OPTION);
			cs.add(OPTION_MUTABLE,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			cs.add(OPTION_RANGE, (ConceptSchema) getSchema(INTERVAL),
					ObjectSchema.OPTIONAL);
			cs.add(OPTION_SET,
					(PrimitiveSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);
			cs.add(OPTION_IS_A_SET,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN),
					ObjectSchema.OPTIONAL);
			cs.add(OPTIONS_NUM_ARGS, (ConceptSchema) getSchema(INTERVAL),
					ObjectSchema.OPTIONAL);
			cs.add(OPTION_DATA_TYPE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(OPTION_WEKA_DESTRIPTION,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(OPTION_WEKA_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(OPTION_WEKA_SYNOPSIS,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(OPTION_VALUE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(OPTION_DEFAULT_VALUE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(OPTION_USER_VALUE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(OPTION_NUMBER_OF_VALUES_TO_TRY,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));

			ConceptSchema si_schema = (ConceptSchema) getSchema(SEARCHITEM);
			si_schema.add(SEARCHITEM_NUMBER_OF_VALUES_TO_TRY,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));

			cs = (ConceptSchema) getSchema(BOOLSITEM);
			cs.addSuperSchema(si_schema);

			cs = (ConceptSchema) getSchema(INTSITEM);
			cs.addSuperSchema(si_schema);
			cs.add(INTSITEM_MIN,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(INTSITEM_MAX,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));

			cs = (ConceptSchema) getSchema(FLOATSITEM);
			cs.addSuperSchema(si_schema);
			cs.add(FLOATSITEM_MIN,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(FLOATSITEM_MAX,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));

			cs = (ConceptSchema) getSchema(SETSITEM);
			cs.addSuperSchema(si_schema);
			cs.add(SETSITEM_SET,
					(PrimitiveSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);

			cs = (ConceptSchema) getSchema(SEARCHSOLUTION);
			cs.add(SEARCHSOLUTION_VALUES,
					(PrimitiveSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);

			cs = (ConceptSchema) getSchema(EVAL);
			cs.add(EVAL_NAME, (PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(EVAL_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT));

			cs = (ConceptSchema) getSchema(EVALUATION);
			cs.add(EVALUATION_EVALUATIONS, (ConceptSchema) getSchema(EVAL), 1,
					ObjectSchema.UNLIMITED);
			cs.add(EVALUATION_LABELED_DATA,
					(ConceptSchema) getSchema(DATA_INSTANCES), 0,
					ObjectSchema.UNLIMITED);
			cs.add(EVALUATION_STATUS,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(EVALUATION_OBJECT_FILENAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(EVALUATION_OBJECT,
					(PrimitiveSchema) getSchema(BasicOntology.BYTE_SEQUENCE),
					ObjectSchema.OPTIONAL);
			cs.add(EVALUATION_DATA_TABLE,
					(ConceptSchema) getSchema(DATA_INSTANCES),
					ObjectSchema.OPTIONAL);
			cs.add(EVALUATION_START,
					(PrimitiveSchema) getSchema(BasicOntology.DATE),
					ObjectSchema.OPTIONAL);

			cs = (ConceptSchema) getSchema(RESULTS);
			cs.add(RESULTS_PROBLEM_ID,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(RESULTS_AVG_ERROR_RATE,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(RESULTS_AVG_KAPPA_STATISTIC,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			cs.add(RESULTS_AVG_MEAN_ABSOLUTE_ERROR,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			cs.add(RESULTS_AVG_MEAN_SQUARED_ERROR,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			cs.add(RESULTS_AVG_RELATIVE_ABSOLUTE_ERROR,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			cs.add(RESULTS_AVG_RELATIVE_SQUARED_ERROR,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			cs.add(RESULTS_RESULTS, (ConceptSchema) getSchema(TASK), 0,
					ObjectSchema.UNLIMITED);

			cs = (ConceptSchema) getSchema(AGENT);
			cs.add(AGENT_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(AGENT_TYPE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(AGENT_GUI_ID,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(AGENT_OPTIONS, (ConceptSchema) getSchema(OPTION), 0,
					ObjectSchema.UNLIMITED);
			cs.add(AGENT_OBJECT,
					(PrimitiveSchema) getSchema(BasicOntology.BYTE_SEQUENCE),
					ObjectSchema.OPTIONAL);

			cs = (ConceptSchema) getSchema(DATA_INSTANCES);
			cs.add(DATA_INSTANCES_ATTRIBUTES,
					(ConceptSchema) getSchema(ATTRIBUTE), 0,
					ObjectSchema.UNLIMITED);
			cs.add(DATA_INSTANCES_INSTANCES,
					(ConceptSchema) getSchema(INSTANCE), 0,
					ObjectSchema.UNLIMITED);
			cs.add(DATA_INSTANCES_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(DATA_INSTANCES_CLASS_INDEX,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));

			cs = (ConceptSchema) getSchema(ATTRIBUTE);
			cs.add(ATTRIBUTE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(ATTRIBUTE_TYPE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(ATTRIBUTE_VALUES,
					(PrimitiveSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);
			cs.add(ATTRIBUTE_DATE_FORMAT,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);

			cs = (ConceptSchema) getSchema(INSTANCE);
			cs.add(INSTANCE_VALUES,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT), 0,
					ObjectSchema.UNLIMITED);
			cs.add(INSTANCE_MISSING,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN), 0,
					ObjectSchema.UNLIMITED);

			cs = (ConceptSchema) getSchema(METADATA);
			cs.add(METADATA_INTERNAL_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(METADATA_EXTERNAL_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(METADATA_NUMBER_OF_INSTANCES,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(METADATA_NUMBER_OF_ATTRIBUTES,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(METADATA_MISSING_VALUES,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN),
					ObjectSchema.OPTIONAL);
			cs.add(METADATA_DEFAULT_TASK,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(METADATA_ATTRIBUTE_TYPE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			cs.add(METADATA_LINEAR_REGRESSION_DURATION,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(METADATA_ATTRIBUTE_METADATA_LIST, (ConceptSchema) getSchema(ATTRIBUTE_METADATA),
					ObjectSchema.OPTIONAL);							

			cs = (ConceptSchema) getSchema(ATTRIBUTE_METADATA);
			cs.add(ATTRIBUTE_METADATA_RATIO_OF_MISSING_VALUES,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			cs.add(ATTRIBUTE_METADATA_ORDER,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			cs.add(ATTRIBUTE_METADATA_ENTROPY,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(ATTRIBUTE_METADATA_ATTRIBUTE_CLASS_ENTROPY,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			
			cs = (ConceptSchema) getSchema(SAVED_RESULT);
			cs.add(SAVED_RESULT_ERR,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(SAVED_RESULT_KAPPA,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(SAVED_RESULT_RMSE,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(SAVED_RESULT_RRSE,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(SAVED_RESULT_MAE,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(SAVED_RESULT_RAE,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT));
			cs.add(SAVED_RESULT_USER_ID,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			cs.add(SAVED_RESULT_AGENT_TYPE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(SAVED_RESULT_TRAIN,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(SAVED_RESULT_TEST,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(SAVED_RESULT_OPTIONS,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			cs.add(SAVED_RESULT_DATE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));

			cs = (ConceptSchema) getSchema(OPTIONS);
			cs.add(OPTIONS_LIST, (ConceptSchema) getSchema(OPTION), 0,
					ObjectSchema.UNLIMITED);

			cs = (ConceptSchema) getSchema(FITNESS);
			cs.add(FITNESS_SOLUTION, (ConceptSchema) getSchema(SEARCHSOLUTION),
					ObjectSchema.OPTIONAL);
			cs.add(FITNESS_FITNESS_VALUES,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT), 1,
					ObjectSchema.UNLIMITED);

			cs = (ConceptSchema) getSchema(DURATION);
			cs.add(DURATION_START,
					(PrimitiveSchema) getSchema(BasicOntology.DATE), 1);
			cs.add(DURATION_DURATION,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER), 1);
			cs.add(DURATION_LR_DURATION,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.MANDATORY);

			PredicateSchema ps = (PredicateSchema) getSchema(PARTIALRESULTS);
			ps.add(PARTIALRESULTS_TASK, (ConceptSchema) getSchema(TASK),
					ObjectSchema.OPTIONAL);
			ps.add(PARTIALRESULTS_TASK_ID,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			ps.add(PARTIALRESULTS_RESULTS,
					(ConceptSchema) getSchema(EVALUATION), 0,
					ObjectSchema.UNLIMITED);

			AgentActionSchema as = (AgentActionSchema) getSchema(SOLVE);
			as.add(SOLVE_PROBLEM, (ConceptSchema) getSchema(PROBLEM));

			as = (AgentActionSchema) getSchema(GET_OPTIONS);

			as = (AgentActionSchema) getSchema(EXECUTE);
			as.add(EXECUTE_TASK, (ConceptSchema) getSchema(TASK),
					ObjectSchema.OPTIONAL);
			as.add(EXECUTE_METHOD, (ConceptSchema) getSchema(AGENT),
					ObjectSchema.OPTIONAL);

			as = (AgentActionSchema) getSchema(IMPORT_FILE);
			as.add(IMPORT_USER,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(IMPORT_FILENAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			as.add(IMPORT_FILECONTENT,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(IMPORT_FILE_TEMP,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN));
			as.setResult((PrimitiveSchema) getSchema(BasicOntology.STRING));
			// internal
			// filename
			as = (AgentActionSchema) getSchema(TRANSLATE);
			as.add(TRANSLATE_USER,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(TRANSLATE_EXTERNAL_FILENAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(TRANSLATE_INTERNAL_FILENAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.setResult((PrimitiveSchema) getSchema(BasicOntology.STRING)); // the
																				// internal
																				// filename

			as = (AgentActionSchema) getSchema(SAVE_RESULTS);
			as.add(SAVE_RESULTS_TASK, (ConceptSchema) getSchema(TASK));

			as = (AgentActionSchema) getSchema(SAVE_METADATA);
			as.add(SAVE_METADATA_METADATA, (ConceptSchema) getSchema(METADATA));

			as = (AgentActionSchema) getSchema(GET_DATA);
			as.add(GET_DATA_FILE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(GET_DATA_O2A_AGENT,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);

			as = (AgentActionSchema) getSchema(GET_ALL_METADATA);
			as.add(GET_ALL_METADATA_EXCEPTIONS,
					(ConceptSchema) getSchema(METADATA), 0,
					ObjectSchema.UNLIMITED);
			as.add(GET_ALL_METADATA_RESULTS_REQUIRED,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN),
					ObjectSchema.OPTIONAL);

			as = (AgentActionSchema) getSchema(GET_THE_BEST_AGENT);
			as.add(GET_THE_BEST_AGENT_NEAREST_FILE_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
                        as.add(GET_THE_BEST_AGENT_NUMBER_OF_AGENTS, (PrimitiveSchema) getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);

			as = (AgentActionSchema) getSchema(GET_FILE_INFO);
			as.add(GET_FILE_INFO_USERID,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(GET_FILE_INFO_ATTR_HI,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			as.add(GET_FILE_INFO_ATTR_LO,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			as.add(GET_FILE_INFO_INST_HI,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			as.add(GET_FILE_INFO_INST_LO,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);
			as.add(GET_FILE_INFO_ATTRIBUTE_TYPE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(GET_FILE_INFO_MISSING_VALUES,
					(PrimitiveSchema) getSchema(BasicOntology.BOOLEAN),
					ObjectSchema.OPTIONAL);
			as.add(GET_FILE_INFO_DEFAULT_TASK,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(GET_FILE_INFO_FILENAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.setResult((ConceptSchema) getSchema(METADATA), 0,
					ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(UPDATE_METADATA);
			as.add(UPDATE_METADATA_METADATA,
					(ConceptSchema) getSchema(Metadata.class));

			as = (AgentActionSchema) getSchema(GET_FILES);
			as.add(GET_FILES_USERID,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.setResult((PrimitiveSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(LOAD_AGENT);
			as.add(LOAD_AGENT_FILENAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			as.add(LOAD_AGENT_FIRST_ACTION, (ConceptSchema) getSchema(EXECUTE),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_AGENT_OBJECT,
					(PrimitiveSchema) getSchema(BasicOntology.BYTE_SEQUENCE),
					ObjectSchema.OPTIONAL);

			as = (AgentActionSchema) getSchema(SAVE_AGENT);
			as.add(SAVE_AGENT_USERID,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(SAVE_AGENT_AGENT, (ConceptSchema) getSchema(AGENT));

			as = (AgentActionSchema) getSchema(GET_SAVED_AGENTS);
			as.add(GET_SAVED_AGENTS_USERID,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));

			as = (AgentActionSchema) getSchema(LOAD_RESULTS);
			as.add(LOAD_RESULTS_ERR_HI,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_ERR_LOW,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_KAPPA_HI,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_KAPPA_LOW,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_RMSE_HI,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_RMSE_LOW,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_MAE_HI,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_MAE_LOW,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_RAE_HI,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_RAE_LOW,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_RRSE_HI,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_RRSE_LOW,
					(PrimitiveSchema) getSchema(BasicOntology.FLOAT),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_AFTER_DATE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_BEFORE_DATE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_AGENT_TYPE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_TRAIN,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_TEST,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(LOAD_RESULTS_USER_ID,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER),
					ObjectSchema.OPTIONAL);

			as.setResult((ConceptSchema) getSchema(SAVED_RESULT), 0,
					ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(GET_AGENTS);
			as.add(GET_AGENTS_AGENT, (ConceptSchema) getSchema(AGENT));
			as.add(GET_AGENTS_NUMBER,
					(PrimitiveSchema) getSchema(BasicOntology.INTEGER));
			as.add(GET_AGENTS_TASK_ID, (ConceptSchema) getSchema(ID));

			as = (AgentActionSchema) getSchema(DELETE_TEMP_FILES);

			as = (AgentActionSchema) getSchema(GET_NEXT_PARAMETERS);
			as.add(GET_NEXT_PARAMETERS_SCHEMA,
					(ConceptSchema) getSchema(SEARCHITEM), 0,
					ObjectSchema.UNLIMITED);
			as.add(GET_NEXT_PARAMETERS_SEARCH_OPTIONS,
					(ConceptSchema) getSchema(OPTION), 0,
					ObjectSchema.UNLIMITED);
			as = (AgentActionSchema) getSchema(CREATE_AGENT);
			as.add(CREATE_AGENT_TYPE,
					(PrimitiveSchema) getSchema(BasicOntology.STRING));
			as.add(CREATE_AGENT_NAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(CREATE_AGENT_ARGUMENTS,
					(PrimitiveSchema) getSchema(BasicOntology.STRING), 0,
					ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(EXECUTE_PARAMETERS);
			as.add(EXECUTE_PARAMETERS_SOLUTIONS,
					(ConceptSchema) getSchema(SEARCHSOLUTION), 0,
					ObjectSchema.UNLIMITED);

			as = (AgentActionSchema) getSchema(GET_DURATION);
			as.add(GET_DURATION_DURATION, (ConceptSchema) getSchema(DURATION));

			as = (AgentActionSchema) getSchema(GET_METADATA);
			as.add(GET_METADATA_EXTERNAL_FILENAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);
			as.add(GET_METADATA_INTERNAL_FILENAME,
					(PrimitiveSchema) getSchema(BasicOntology.STRING),
					ObjectSchema.OPTIONAL);

			as = (AgentActionSchema) getSchema(SHUTDOWN_DATABASE);

			as = (AgentActionSchema) getSchema(RECOMMEND);
			as.add(RECOMMEND_DATA, (ConceptSchema) getSchema(DATA));
			as.add(RECOMMEND_RECOMMENDER, (ConceptSchema) getSchema(AGENT));
			
			as = (AgentActionSchema) getSchema(SEND_EMAIL);
            as.add(SEND_EMAIL_TO_ADDRESS, (PrimitiveSchema) getSchema(BasicOntology.STRING),
                    ObjectSchema.MANDATORY);
            as.add(SEND_EMAIL_TYPE, (PrimitiveSchema) getSchema(BasicOntology.STRING),
                    ObjectSchema.MANDATORY);
            
            as = (AgentActionSchema) getSchema(EXPERIMENT);
            as.add(EXPERIMENT_DESCRIPTION, (ConceptSchema) getSchema(COMP_DESCRIPTION),
            		ObjectSchema.MANDATORY);

            cs = (ConceptSchema) getSchema(COMP_DESCRIPTION);
            cs.add(COMP_DESCRIPTION_ROOT, (ConceptSchema) getSchema(FILEVISUALIZER),
            		ObjectSchema.OPTIONAL);

            cs = (ConceptSchema) getSchema(FILEVISUALIZER);
            cs.add(FILEVISUALIZER_DATASOURCE, (ConceptSchema) getSchema(DATAPROVIDER));
            
            cs = (ConceptSchema) getSchema(DATAPROVIDER);
            cs.add(DATAPROVIDER_DATAPROVIDER, (ConceptSchema) getSchema(COMPUTINGAGENT));
            
            cs = (ConceptSchema) getSchema(COMPUTINGAGENT);
            cs.add(COMPUTINGAGENT_MODEL, (PrimitiveSchema) getSchema(BasicOntology.STRING));
            
		} catch (OntologyException oe) {
			oe.printStackTrace();
		}
	}

}