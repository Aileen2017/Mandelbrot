package com.projects.mandelbrotproject;

import android.graphics.Color;

public class HslColor 
{
	private double hue;
	private double saturation;
	private double luminance;
	
	public HslColor(double hue, double saturation, double luminance)
	{
		this.setHue(hue);
		this.setSaturation(saturation);
		this.setLuminance(luminance);
	}

	public int getRgb()
	{
		double c = (1 - Math.abs(2 * luminance - 1)) * saturation;
		double h = hue / 60;
		double x = c * (1 - Math.abs((h % 2) - 1));
		double r1 = 0;
		double g1 = 0;
		double b1 = 0;
		if(h >= 0 && h < 1)
		{
			r1 = c;
			g1 = x;
		}
		else if(h >= 1 && h < 2)
		{
			r1 = x;
			g1 = c;			
		}
		else if(h >= 2 && h < 3)
		{
			g1 = c;
			b1 = x;			
		}
		else if(h >= 3 && h < 4)
		{
			g1 = x;
			b1 = c;			
		}
		else if(h >= 4 && h < 5)
		{
			r1 = x;
			b1 = c;			
		}
		else if(h >= 5 && h < 6)
		{
			r1 = c;
			b1 = x;			
		}
		double m = luminance - 0.5 * c;
		double r = r1 + m;
		double g = g1 + m;
		double b = b1 + m;
		return Color.argb(255, (int)(r * 255), (int)(g * 255), (int)(b * 255));
	}
	
	public double getHue() 
	{
		return hue;
	}

	public void setHue(double hue) 
	{
		this.hue = hue;
	}

	public double getSaturation() 
	{
		return saturation;
	}

	public void setSaturation(double saturation) 
	{
		this.saturation = saturation;
	}

	public double getLuminance() 
	{
		return luminance;
	}

	public void setLuminance(double luminance) 
	{
		this.luminance = luminance;
	}
}
