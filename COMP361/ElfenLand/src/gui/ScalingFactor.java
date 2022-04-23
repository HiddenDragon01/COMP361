package gui;

import java.awt.Polygon;

public class ScalingFactor 
{
	public double factorX;
	public double factorY;
	
	public ScalingFactor(double factorX, double factorY)
	{
		this.factorX = factorX;
		this.factorY = factorY;
	}
	
	public ScalingFactor(int initWidth, int initHeight, int targetWidth, int targetHeight)
	{
		this.factorX = (double) 1 / ((double) initWidth / targetWidth ) ;
		this.factorY = (double) 1 / ((double) initHeight / targetHeight) ;
	}
	
	public ScalingFactor(int initWidth, int initHeight, Polygon p)
	{
		int targetWidth = p.xpoints[1] - p.xpoints[0];
		int targetHeight = p.ypoints[3] - p.ypoints[0];
		this.factorX = (double) 1 / ((double) initWidth / targetWidth ) ;
		this.factorY = (double) 1 / ((double) initHeight / targetHeight) ;
	}
}
