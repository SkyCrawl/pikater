package org.pikater.web.vaadin.gui.server.ui_visualization.components;

import java.util.Collection;

import org.pikater.web.vaadin.gui.server.components.matrixview.IMatrixDataSource;
import org.pikater.web.vaadin.gui.server.components.matrixview.MatrixView;
import org.pikater.web.vaadin.gui.server.ui_visualization.VisualizationUI.DSVisOneUIArgs;
import org.pikater.web.visualisation.definition.result.AbstractDSVisSubresult;
import org.pikater.web.visualisation.definition.result.DSVisOneSubresult;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class SingleDatasetVisualizer extends Panel
{
	private static final long serialVersionUID = 8665905099485047156L;
	
	public SingleDatasetVisualizer(final DSVisOneUIArgs arguments)
	{
		super();
		
		final IMatrixDataSource<String, DSVisOneSubresult> resultMatrixView = arguments.getGeneratedResult().toMatrixView();
		MatrixView<String> matrixView = new MatrixView<String>(new IMatrixDataSource<String, ChartThumbnail>()
		{
			@Override
			public Collection<String> getLeftIndexSet()
			{
				return resultMatrixView.getLeftIndexSet();
			}

			@Override
			public Collection<String> getTopIndexSet()
			{
				return resultMatrixView.getTopIndexSet();
			}

			@Override
			public ChartThumbnail getElement(String leftIndex, String topIndex)
			{
				return new ChartThumbnail(
						resultMatrixView.getElement(leftIndex, topIndex),
						arguments.getGeneratedResult().getImageWidth(),
						arguments.getGeneratedResult().getImageHeight()
				);
			}
		});
		matrixView.setSizeFull();

		// and display it
		setContent(matrixView);
	}
	
	/*
	private void createViewer()
	{
		ImageDownloadResource resource = new ImageDownloadResource(
				singleImageResult.getFile(),
				ResourceExpiration.ON_SESSION_END,
				singleImageResult.getImageType().toMimeType(),
				chartResults.getImageWidth(),
				chartResults.getImageHeight()
				);
		ImageViewer viewer = new ImageViewer(
				ResourceRegistrar.getDownloadURL(ResourceRegistrar.registerResource(VaadinSession.getCurrent(), resource)),
				resource.getImageWidth(),
				resource.getImageHeight()
				);
		viewer.setWidth("800px");
		viewer.setHeight("800px");
	}
	*/
	
	//---------------------------------------------------------------
	// SPECIAL TYPES
	
	private class ChartThumbnail extends VerticalLayout
	{
		public ChartThumbnail(AbstractDSVisSubresult imageResult, int imageWidth, int imageHeight)
		{
			super();
			setSizeUndefined();
			
			
			// TODO Auto-generated constructor stub
		}
	}
}