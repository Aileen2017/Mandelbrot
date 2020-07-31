package com.projects.mandelbrotproject;


public class Sequence {
	
	private int maxIteration;
	private Complex z=new Complex(0,0);
	public Complex c=new Complex(0,0); 
	
	public Sequence(int maxIteration)
	{
		this.maxIteration = maxIteration;
	}

	public void getC(double rea, double imag)
	{
		c.rea=rea;
		c.imag=imag;
	}
	
	public double getSpeed(Complex c)
	{
		double x4 = c.rea - 1.0/4;
		double q = x4 * x4 + c.imag * c.imag;
		if(q * (q + x4) < c.imag * c.imag / 4)
			return -1;
		
		if((c.rea + 1) * (c.rea + 1) + c.imag * c.imag < 1.0 / 16)
			return -1;
		
		int iterations=0;
		z.rea=c.rea;
		z.imag=c.imag;
		
		
		while(z.getNormSquare()<4&&iterations<maxIteration)
		{
			iterations++;
			z.complexSquare();
			z.complexAddinPlace(c);
			
			
		}
		
		if(iterations == maxIteration)
		{
			return -1;
		}
		else
		{
			return iterations - Math.log(Math.log(z.getNorm()) / Math.log(4))/ Math.log(2);
		}
	}

}
