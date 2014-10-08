package org.pikater.web.unused.ssh;

import com.jcraft.jsch.JSchException;

public interface ISSHSyncCommandExec
{
	/** 
	 * Multiple commands may be specified in this string, e.g. "cd /etc; pwd"
	 * @return The response to your commands.
	 * @throws JSchException
	 * @throws InterruptedException
	 */
	String execSync(String command) throws JSchException, InterruptedException;
}
