package com.projects.mandelbrotproject;


import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ProgressBar;

public class Frame {
	
	
	public double windowSize;

	public Bitmap bm;
	public double	centreX = 0;
	public double centreY = 0;
	public int w;
	public int h;
	public long interval=0;
	public int progress;

	public Frame(final int w, final int h, double centreX, double centreY, double windowSize, final Handler handler, final ProgressBar progressBar)
	{
		bm=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		this.windowSize=windowSize;
		this.centreX=centreX;
		this.centreY=centreY;
		this.w=w;
		this.h=h;
		long start = System.currentTimeMillis() ;
		double screenRatio = (double)h / (double)w; 
		
		final double max_x=this.centreX + this.windowSize / 2.0;
		final double min_x=this.centreX - this.windowSize / 2.0;
		final double max_y=this.centreY + this.windowSize * screenRatio / 2.0;
		final double min_y=this.centreY - this.windowSize * screenRatio / 2.0;	
		
		final HslColor color = new HslColor(0.0, 1.0, 0.5);
		
	    int numberOfCores = Runtime.getRuntime().availableProcessors();
	    ThreadPoolExecutor executor = new ThreadPoolExecutor(numberOfCores,
	    		numberOfCores, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	    
	    for(int j=0;j<h;j++)
	    {
	    	final int local_j = j;
	    	executor.execute(new Runnable()
	    	{
	    		int[] colors=new int[w];
	    		
                public void run() 
                {
                	Sequence s=new Sequence(255);
                	
        	    	for(int i=0;i<w;i++)
        			{
        				double x=(i*(max_x-min_x))/w+min_x;
        				double y=(local_j*(max_y-min_y))/h+min_y;
        				s.getC(x, y);
        				double sp = s.getSpeed(s.c);
        				if(sp < 0)
        				{
        					color.setLuminance(0);
        				}
        				else
        				{
        					color.setLuminance(0.5);
        					color.setHue((sp * 10) % 360);
        				}
        				
        				colors[i] = color.getRgb();
        			}
        				
    		    	final int progress =  local_j * 100 / h;
    		    	handler.post(new Runnable() {
    	                public void run() {
    	                    progressBar.setProgress(progress);
    	                    bm.setPixels(colors, 0, w, 0, local_j, w, 1);
    	                }
    	            });
               }
	    	});
	    }
	    
	    executor.shutdown();
	    try
	    {
	    	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
	    }
	    catch(InterruptedException e)
	    {
	    }
	    
	    long end = System.currentTimeMillis();
		interval = end - start;
	}
}
