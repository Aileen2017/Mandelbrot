package com.projects.mandelbrotproject;
public class Complex {

	double rea;
	double imag;
	
	public Complex(double rea, double imag)
	{
		this.rea = rea;
		this.imag = imag;
	}
	
	public Complex complexMultiply(Complex c)
	{
		return new Complex(this.rea*c.rea-this.imag*c.imag, this.rea*c.imag+this.imag*c.rea);
	}
	
	public Complex complexAdd(Complex c)
	{
		double reamA=this.rea+c.rea;
		double imagA=this.imag+c.imag;
		return new Complex(reamA, imagA);
		
	}
	
	public void complexAddinPlace(Complex c)
	{
		this.rea=this.rea+c.rea;
		this.imag=this.imag+c.imag;
		
	}
	
	public void complexSquare()
	{
		double tr=this.rea;
		this.rea=this.rea*this.rea-this.imag*this.imag;
		this.imag=tr*this.imag+this.imag*tr;
	}
	
	
	public Complex complexSubtract(Complex c)
	{
		double reamA=this.rea-c.rea;
		double imagA=this.imag-c.imag;
		return new Complex(reamA, imagA);
	}
	
    public int hashCode()
    {
    	return new Double(rea).hashCode() ^ new Double(imag).hashCode();
    }
    
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Complex other = (Complex)obj;
        return(rea == other.rea && imag == other.imag);
    }

    public String toString()
    {
    	return Double.toString(rea) + " + " + Double.toString(imag) + "i";
    }
    
    public double getNorm()
    {
    	double s=Math.pow(rea,2);
    	double s1=Math.pow(imag,2);
    	double sqr=Math.sqrt(s+s1);
    	sqr=Math.sqrt(Math.pow(rea,2)+Math.pow(imag,2));
    	return sqr;
    }
    
    public double getNormSquare()
    {
    	return Math.pow(rea, 2)+Math.pow(imag, 2);
    }
    
}
