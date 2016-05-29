package cn.hit.cst.ssl.visualization;

import java.util.ArrayList;

import cn.hit.cst.ssl.exception.XYArraySizeException;
import flanagan.analysis.Regression;
import flanagan.plot.PlotGraph;

public class ScatterPlotter {
	public static void scatterPlot(ArrayList<Double> xArrayList, 
			ArrayList<Double> yArrayList, String gTitle,
			String xAxisLegend, String yAxisLegend) throws XYArraySizeException{
		double[] x, y;
		if (xArrayList.size() != yArrayList.size()) {
			//TODO: throw Exception and end the function
			throw new XYArraySizeException("Scatter plotting");
		}
		x = new double[xArrayList.size()];
		y = new double[xArrayList.size()];
		for (int i = 0; i < xArrayList.size(); i++){
			x[i] = xArrayList.get(i);
			y[i] = yArrayList.get(i);
		}
		
		PlotGraph graphPlotter = new PlotGraph(x, y);
		graphPlotter.setGraphTitle(gTitle);
		graphPlotter.setXaxisLegend(xAxisLegend);
		graphPlotter.setYaxisLegend(yAxisLegend);
		graphPlotter.setLine(0);
		graphPlotter.plot();
	}
	
	public static void scatterRegressionPlot(ArrayList<Double> xArrayList, 
			ArrayList<Double> yArrayList, String xAxisLegend, String yAxisLegend) throws XYArraySizeException{
		double[] x, y;
		if (xArrayList.size() != yArrayList.size()) {
			//TODO: throw Exception and end the function
			throw new XYArraySizeException("Scatter plotting");
		}
		x = new double[xArrayList.size()];
		y = new double[xArrayList.size()];
		for (int i = 0; i < xArrayList.size(); i++){
			x[i] = xArrayList.get(i);
			y[i] = yArrayList.get(i);
		}
		
		Regression reg = new Regression(x, y);
		reg.linearPlot(xAxisLegend, yAxisLegend);
	}
}
