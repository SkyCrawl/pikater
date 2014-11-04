package org.pikater.core.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.pikater.shared.logging.core.ConsoleLogger;

/**
 * Created with IntelliJ IDEA. User: Kuba Date: 9.8.13 Time: 0:04 Provides
 * ability to get the configuration from the properties file
 */
public class PropertiesConfigurationProvider implements ConfigurationProvider {
	private Properties configuration;

	public PropertiesConfigurationProvider(String propertiesPath) {
		configuration = new Properties();
		try (FileInputStream stream = new FileInputStream(propertiesPath)) {
			configuration.load(stream);
		} catch (IOException e) {
			ConsoleLogger.logThrowable(e.getMessage(), e);
		}
	}

	@Override
	public Configuration getConfiguration() {
		int agentNumber = 1;
		List<AgentConfiguration> agentConfigurations = new ArrayList<AgentConfiguration>();
		AgentConfiguration agentConfig = getAgentConfiguration(agentNumber);
		while (agentConfig != null) {
			agentConfigurations.add(agentConfig);
			agentNumber++;
			agentConfig = getAgentConfiguration(agentNumber);
		}
		return new ConfigurationImpl(agentConfigurations);
	}

	private AgentConfiguration getAgentConfiguration(int agentNumber) {
		String agentKey = "agent" + agentNumber;
		String agentNameKey = agentKey + ".name";
		String agentTypeKey = agentKey + ".type";
		String agentName = configuration.getProperty(agentNameKey);
		if (agentName == null) {
			return null;
		}
		String agentType = configuration.getProperty(agentTypeKey);
		List<Argument> arguments = new ArrayList<Argument>();
		int argNumber = 1;
		String argKey = configuration.getProperty(getArgumentKeyKey(
				agentNumber, argNumber));
		while (argKey != null) {
			String argValue = configuration.getProperty(getArgumentValueKey(
					agentNumber, argNumber));
			Argument argument = new Argument(agentKey, argValue);
			arguments.add(argument);
			argNumber++;
			argKey = configuration.getProperty(getArgumentKeyKey(agentNumber,
					argNumber));
		}
		return new AgentConfigurationImpl(agentName, agentType, arguments);
	}

	private String getArgumentKeyKey(int agentNumber, int agrNumber) {
		return "agent" + agentNumber + ".arg" + agrNumber + ".key";
	}

	private String getArgumentValueKey(int agentNumber, int agrNumber) {
		return "agent" + agentNumber + ".arg" + agrNumber + ".value";
	}
}
