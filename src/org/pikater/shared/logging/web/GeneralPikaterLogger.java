package org.pikater.shared.logging.web;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class GeneralPikaterLogger implements IPikaterLogger
{
	protected static String throwableToStackTrace(Throwable t)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		Throwable tt = t;
		while (tt != null)
		{
			tt.printStackTrace(pw);
			tt = tt.getCause();
			if(tt != null)
			{
				pw.print("caused by: ");
			}
		}
		return sw.toString();
	}
}