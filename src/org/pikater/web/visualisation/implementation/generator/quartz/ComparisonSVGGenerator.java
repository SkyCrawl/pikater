package org.pikater.web.visualisation.implementation.generator.quartz;

import java.io.IOException;
import java.io.PrintStream;

import org.pikater.shared.database.jpa.JPADataSetLO;
import org.pikater.web.visualisation.definition.result.AbstractDSVisResult;
import org.pikater.web.visualisation.implementation.charts.exception.ChartException;
import org.pikater.web.visualisation.implementation.generator.ChartGenerator;
import org.pikater.web.visualisation.implementation.renderer.SVGRenderer;

public class ComparisonSVGGenerator extends ComparisonGenerator {

	public ComparisonSVGGenerator(AbstractDSVisResult progressListener,PrintStream output,JPADataSetLO dslo1,JPADataSetLO dslo2,int XIndex1, int XIndex2, int YIndex1, int YIndex2,int ColorIndex1,int ColorIndex2){
		super(progressListener, output, dslo1, dslo2, XIndex1, XIndex2, YIndex1, YIndex2, ColorIndex1, ColorIndex2);
		initRenderer();
	}
	
	public ComparisonSVGGenerator(AbstractDSVisResult progressLstener,PrintStream output,JPADataSetLO dslo1,JPADataSetLO dslo2,String XName1, String XName2, String YName1, String YName2,String ColorName1,String ColorName2){
		super(progressLstener, output, dslo1, dslo2, XName1, XName2, YName1, YName2, ColorName1, ColorName2);
		initRenderer();
	}
	
	private void initRenderer(){
		renderer=new SVGRenderer(output, ChartGenerator.SINGLE_CHART_SIZE, ChartGenerator.SINGLE_CHART_SIZE);
	}
	
	
	@Override
	public void create() throws IOException, ChartException {
		try{
			super.create();
		}finally{
			output.close();
		}
	}

}