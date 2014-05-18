package org.pikater.web.vaadin.gui.client.gwtmanagers;

import org.pikater.web.vaadin.gui.client.mainuiextension.MainUIExtensionServerRpc;

public class GWTLogger
{
	private static MainUIExtensionServerRpc serverRPC = null;
	
	public static void setRemoteLogger(MainUIExtensionServerRpc serverRPC)
	{
		GWTLogger.serverRPC = serverRPC;
	}
	
	// ----------------------------------------------------------
	// LOGGING METHODS
	
	/**
	 * Sends a message to the server where it will be logged as a warning.
	 * @param message
	 */
	public static void logWarning(String message)
	{
		if(checkLogger())
		{
			serverRPC.logWarning(message);
		}
	}

	/**
	 * Sends a message and a throwable to the server where they will be logged as an error. Only use this
	 * with your own Java exceptions in GWT code. Native exceptions are useless for a GWT developer and
	 * {@link #logUncaughtNativeClientException} should be used instead. 
	 * @param properJavaThrowable
	 * @return
	 */
	public static void logThrowable(String message, Throwable t)
	{
		if(checkLogger())
		{
			serverRPC.logThrowable(message, printThrowable(t));
		}
	}
	
	/**
	 * Sends a notification to the server that a native and unpredicted exception occured in the client code.
	 */
	public static void logUncaughtNativeClientException()
	{
		if(checkLogger())
		{
			serverRPC.logUncaughtNativeClientException();
		}
	}
	
	// ----------------------------------------------------------
	// PRIVATE METHODS
	
	private static boolean checkLogger()
	{
		if(serverRPC == null)
		{
			throw new NullPointerException("Remote client logger was needed but had not been set. Ensure that your main UI is extended"
					+ "with a remote logger. Everything else should be taken case of automatically.");
		}
		else
		{
			return true;
		}
	}
	
	private static String printThrowable(Throwable properJavaThrowable)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(properJavaThrowable.toString());
		sb.append('\n');
		for(StackTraceElement element : properJavaThrowable.getStackTrace())
		{
			sb.append("\tat " + element.toString());
			sb.append('\n');
		}
		if(properJavaThrowable.getCause() != null)
		{
			sb.append("caused by: ");
			sb.append(printThrowable(properJavaThrowable.getCause()));
		}
		return sb.toString();
	}
}