package org.pikater.web.vaadin.gui.welcometour;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.pikater.shared.TopologyModel;
import org.pikater.shared.XStreamHelper;
import org.pikater.web.AppHelper;
import org.pikater.web.AppLogger;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class Step2 extends WelcomeTourWizardStep
{
	private final Component content;
	
	private final Step2UI ui;
	
	public Step2(WelcomeTourWizard parentWizard)
	{
		super(parentWizard);
		
		File webInfConfDirectory = new File(AppHelper.webInfConfPath);
		if(!webInfConfDirectory.isDirectory())
		{
			AppLogger.log(Level.SEVERE, String.format("The following path leads to a resource that is not a directory even though it should be:\n '%s'", AppHelper.webInfConfPath));
			
			content = createErrorLabel("There was an internal error in the application and it can not proceed. Please, refer to the server logs or contact the administrator.");
			ui = null;
		}
		else
		{
			// first get all the topology files
			File[] jadeTopologyFiles = webInfConfDirectory.listFiles(new FilenameFilter()
			{
				@Override
				public boolean accept(File dir, String name)
				{
					return name.matches("jadeTopology\\d*\\.xml");
				}
			});
			if(jadeTopologyFiles.length == 0)
			{
				content = createErrorLabel("No jade topology XML files were found. Please, contact the administrator.");
				ui = null;
			}
			else
			{
				// parse all topologies
				List<String> omittedModels = new ArrayList<String>();
				for(File topologyFile : jadeTopologyFiles)
				{
					TopologyModel model = null;
					try
					{
						// parse the model
						model = XStreamHelper.deserializeFromPath(
								TopologyModel.class,
								AppHelper.joinPathComponents(AppHelper.webInfConfPath, topologyFile.getName()),
								XStreamHelper.topologySerializer
						);
						if(model == null)
						{
							throw new NullPointerException(String.format("parsed into a null object", topologyFile.getName()));
						}
						else if (!model.isWellFormed())
						{
							throw new IllegalStateException(String.format("not well formed", topologyFile.getName()));
						}
						else
						{
							parentWizard.addParsedModel(topologyFile.getName(), model);
						}
					}
					catch (Throwable t)
					{
						AppLogger.logThrowable(String.format("A problem was encountered while parsing topology '%s': ", topologyFile.getName()) + t.getMessage(), t);
						omittedModels.add(topologyFile.getName());
					}
				}
				
				parentWizard.allModelsParsedCallback(); // this is needed for the following to work:
				
				ui = new Step2UI(omittedModels, parentWizard.getParsedModels().keySet(), parentWizard.getWrappedModels());
				content = ui;
			}
		}
	}
	
	@Override
	public void refresh()
	{
		ui.refresh();
	}

	@Override
	public String getCaption()
	{
		return "Check and fill out any needed and missing information";
	}

	@Override
	public Component getContent()
	{
		return content;
	}

	@Override
	public boolean onAdvance()
	{
		return ui != null;
	}

	@Override
	public boolean onBack()
	{
		return false;
	}
	
	private Label createErrorLabel(String message)
	{
		Label result = new Label(message);
		result.addStyleName("errorLabel");
		return result;
	}
}