package org.pikater.web.vaadin.gui.server.webui.indexpage.content;

import org.pikater.web.vaadin.gui.server.AuthHandler;
import org.pikater.web.vaadin.gui.server.webui.indexpage.content.user.ProfileView;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;

public class ContentProvider
{
	public interface IWebFeatureSet
	{
		boolean accessAllowed(VaadinSession session);
		String toMenuCaption();
		String toNavigatorName();
		IContentComponent toComponent();
	}
	
	public interface IContentComponent extends View
	{
		boolean hasUnsavedProgress();
		String getCloseDialogMessage();
	}
	
	public enum DefaultFeature implements IWebFeatureSet
	{
		WELCOME;
		
		@Override
		public boolean accessAllowed(VaadinSession session)
		{
			return true; // this feature set is for everyone
		}

		@Override
		public String toMenuCaption()
		{
			return null;
		}
		
		@Override
		public String toNavigatorName()
		{
			return "welcome";
		}

		@Override
		public IContentComponent toComponent()
		{
			return new WelcomeContent();
		}
	}
	
	public enum AdminFeature implements IWebFeatureSet
	{
		VIEW_USERS,
		VIEW_SCHEDULED_EXPERIMENTS,
		VIEW_DATASETS_METHODS,
		VIEW_SYSTEM_STATUS;
		
		@Override
		public boolean accessAllowed(VaadinSession session)
		{
			return AuthHandler.getUserEntity(session).isAdmin(); // only allowed for admins
		}
		
		@Override
		public String toMenuCaption()
		{
			switch(this)
			{
				case VIEW_DATASETS_METHODS:
					return "New datasets & methods";
				case VIEW_SCHEDULED_EXPERIMENTS:
					return "Scheduled experiments";
				case VIEW_SYSTEM_STATUS:
					return "System status";
				case VIEW_USERS:
					return "Users";
				default:
					throw new IllegalStateException("Unknown state: " + name());
			}
		}
		
		@Override
		public String toNavigatorName()
		{
			switch(this)
			{
				case VIEW_DATASETS_METHODS:
					return "admin-ToApprove";
				case VIEW_SCHEDULED_EXPERIMENTS:
					return "adminDisplaySchedule";
				case VIEW_SYSTEM_STATUS:
					return "adminSystemStatus";
				case VIEW_USERS:
					return "adminDisplayUsers";
				default:
					throw new IllegalStateException("Unknown state: " + name());
			}
		}

		@Override
		public IContentComponent toComponent()
		{
			switch(this)
			{
				default:
					return new UnimplementedContent();
			}
		}
	}
	
	public enum UserFeature implements IWebFeatureSet
	{
		VIEW_PROFILE,
		VIEW_DATASETS,
		VIEW_METHODS,
		EXPERIMENT_EDITOR,
		VIEW_EXPERIMENT_RESULTS;
		
		@Override
		public boolean accessAllowed(VaadinSession session)
		{
			return AuthHandler.isUserAuthenticated(session); // only allowed for authenticated users
		}
		
		@Override
		public String toMenuCaption()
		{
			switch(this)
			{
				case VIEW_PROFILE:
					return "View & edit profile";
				case EXPERIMENT_EDITOR:
					return "Experiment editor";
				case VIEW_DATASETS:
					return "Available datasets";
				case VIEW_EXPERIMENT_RESULTS:
					return "Experiment results";
				case VIEW_METHODS:
					return "Available methods";
				default:
					throw new IllegalStateException("Unknown state: " + name());
			}
		}
		
		@Override
		public String toNavigatorName()
		{
			switch(this)
			{
				case VIEW_PROFILE:
					return "userProfile";
				case EXPERIMENT_EDITOR:
					return "experimentEditor";
				case VIEW_DATASETS:
					return "displayDatasets";
				case VIEW_EXPERIMENT_RESULTS:
					return "displayResults";
				case VIEW_METHODS:
					return "displayMethods";
				default:
					throw new IllegalStateException("Unknown state: " + name());
			}
		}

		@Override
		public IContentComponent toComponent()
		{
			switch(this)
			{
				case VIEW_PROFILE:
					return new ProfileView();
				default:
					return new UnimplementedContent();
			}
		}
	}
}
