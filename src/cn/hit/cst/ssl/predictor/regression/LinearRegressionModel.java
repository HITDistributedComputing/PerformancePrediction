package cn.hit.cst.ssl.predictor.regression;

public class LinearRegressionModel {
	private double beta0;
	private double beta1;
	
	public LinearRegressionModel(){
		
	}
	
	public LinearRegressionModel(double[] x, double[] y){
		this.trainModel(x, y);
	}
	
	//by default, the input array should have same number of elements
	public void trainModel(double[] x, double[] y){
		//
		int count = 0;
		if (x.length == y.length) {
			count = x.length;
		}
		else{
			System.out.println("element counts in x and y array are different!");
		}
		//sum of x, x2, y
		double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
		//average of x and y
		double xbar, ybar;
		for(int i = 0; i < count; i++){
			sumx += x[i];
			sumx2 += x[i] * x[i];
			sumy  += y[i];
		}
		xbar = sumx / count;
		ybar = sumy / count;
		
		// second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < count; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        this.beta1 = xybar / xxbar;
        this.beta0 = ybar - this.beta1 * xbar;
        
        // print results
        System.out.println("y   = " + this.beta1 + " * x + " + this.beta0);
        
        // analyze results
        int df = count - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < count; i++) {
            double fit = this.beta1*x[i] + this.beta0;
            rss += (fit - y[i]) * (fit - y[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }
        double R2    = ssr / yybar;
        double svar  = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar / count + xbar*xbar*svar1;
        System.out.println("R^2                 = " + R2);
        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
        svar0 = svar * sumx2 / (count * xxbar);
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));

        System.out.println("SSTO = " + yybar);
        System.out.println("SSE  = " + rss);
        System.out.println("SSR  = " + ssr);
	}
	
	public double predictY(double x){
		return (this.beta1 * x + this.beta0);
	}
}
