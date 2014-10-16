package org.pikater.core;

import org.pikater.core.agents.system.computation.graph.GUIDGenerator;
import org.pikater.shared.database.connection.PostgreSQLConnectionProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * Configurable informations for Pikater Core
 *
 */
public class CoreConfiguration {
	/**
	 * Private constructors hide the public ones.
	 */
	private CoreConfiguration()
	{
	}
	
	/*
	 * Spring configuration convenience interface
	 */
	private static final ApplicationContext APPLICATION_CONTEXT =
			new ClassPathXmlApplicationContext(getConfigurationFileName());
	
	@SuppressWarnings("unchecked")
	private static <T extends Object> T getBean(String id) {
		return (T) APPLICATION_CONTEXT.getBean(id);
	}
	
	/**
	 * 
	 * Get Bean configuration file
	 */
	public static String getConfigurationFileName() {
		return "Beans.xml";
	}
	
	/**
	 * 
	 * Get PostgreSQL database connection provider
	 */
	public static PostgreSQLConnectionProvider getPGSQLConnProvider() {
		return getBean("defaultConnection");
	}
	
	/**
	 * 
	 * Get generator
	 */
	public static GUIDGenerator getGUIDGenerator() {
		return getBean("guidGenerator");
	}
	
	/*
	 * Other configuration interface
	 */
	
	/**
	 * 
	 * Get a path for the Master server
	 */
	public static String getCoreMasterConfigurationFilepath() {
		
		return "core" +
				System.getProperty("file.separator") +
				"configurationMaster.xml";
	}
	
	/**
	 * 
	 * Get a path to input files for {@link Agent_GUIKlara}
	 */
	public static String getKlarasInputsPath() {
		
		return getCorePath("inputs") +
				"inputsKlara" +
				System.getProperty("file.separator");
	}
	
	/**
	 * 
	 * Get a path to data files
	 */
	public static String getDataFilesPath() {
		
		return getCorePath("data") +
				"files" +
				System.getProperty("file.separator");
	}

	/**
	 * 
	 * Get a path to external agent JAR files
	 */
	public static String getExtAgentsPath() {
		
		return getCorePath("ext_agents");
	}
	
	/**
	 * 
	 * Get a path to the folder with results
	 */
	public static String getSavedResultsPath() {
		
		return getCorePath("saved");
	}
	
	/**
	 * 
	 * Get a path to the folder for template files generated
	 * by {@link Agent_MetadataQueen}
	 */
	public static String getMetadataPath() {
		
		return getCorePath("metadata");
	}
	
	private static String getCorePath(String nextFolder) {
		
		return "core" +
				System.getProperty("file.separator") +
				nextFolder +
				System.getProperty("file.separator");
	}
}