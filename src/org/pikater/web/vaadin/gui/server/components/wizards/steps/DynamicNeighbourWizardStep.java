package org.pikater.web.vaadin.gui.server.components.wizards.steps;

import org.pikater.web.vaadin.gui.server.components.wizards.IWizardCommon;
import org.pikater.web.vaadin.gui.server.components.wizards.WizardWithDynamicSteps;

/**
 * Wizard step to be exclusively used by {@link WizardWithDynamicSteps}.
 * 
 * @author SkyCrawl
 *
 * @param <OUTPUT>
 * @param <WIZARD>
 */
public abstract class DynamicNeighbourWizardStep<OUTPUT extends IWizardCommon, WIZARD extends WizardWithDynamicSteps<OUTPUT>> extends ParentAwareWizardStep<OUTPUT, WIZARD>
{
	private final boolean isLeaf;
	
	public DynamicNeighbourWizardStep(WIZARD parentWizard, boolean isLeaf)
	{
		super(parentWizard);
		
		this.isLeaf = isLeaf;
	}
	
	//----------------------------------------------------
	// INHERITED INTERFACE
	
	@Override
	public boolean onBack()
	{
		return true;
	}
	
	/**
	 * <font color="red">RED ALERT:</font> this is a "system" method, called in ActiveStepChanged event etc.</br>
	 * Unless you want to suffer from heap out of memory error, don't add or activate steps in here.
	 */
	@Override
	public boolean onAdvance()
	{
		return true;
	}
	
	//----------------------------------------------------
	// OTHER PUBLIC INTERFACE
	
	/**
	 * Whether this step doesn't construct any further steps.
	 * @return
	 */
	public boolean isLeaf()
	{
		return isLeaf;
	}
	
	//----------------------------------------------------
	// ABSTRACT INTERFACE
	
	public abstract DynamicNeighbourWizardStep<OUTPUT, WIZARD> constructNextStep();
}