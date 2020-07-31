package com.projects.mandelbrotproject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Stack;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;

public class mandView extends View 

{
	MediaScannerConnection conn;
	Paint paint = new Paint();
	
	Stack<Frame> frs = new Stack<Frame>();
	
	private TextView textBox;
	private ProgressBar progressBar = null;
	private boolean displayText = true;
	private Menu menu;
	private boolean clicked=false;
	
	
	public mandView(Context context) 
	{
		super(context);
	}

	public mandView(Context context, AttributeSet attrs)
	{
		super(context,attrs);
	}
	
	public void setTextBox(TextView textBox)
	{
		this.textBox = textBox;
	}
	
	public void setMenu(Menu menu)
	{
		this.menu=menu;
	}
	
	public void setProgressBar(ProgressBar progressBar)
	{
		this.progressBar = progressBar;
	}

	protected void onSizeChanged(final int w, final int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		
		final Handler mHandler = new Handler();
		if(!clicked)
		{
			clicked=true;
			new Thread(new Runnable() 
			{
	             public void run() 
	             {
	            	 final Frame ff=new Frame(w,h,0,0,4,mHandler,progressBar);
	
	                 // Update the progress bar
	                 mHandler.post(new Runnable() {
	                     public void run() {
	                    	 frs.push(ff);
	                    	 display();
	                    	 clicked=false;
	                     }
	                     
	                 });
	
	             }
			}).start();		
		}
	}
	
	public void back()
	{
		if(!clicked)
		{	if(frs.size()>1)
			{
				frs.pop();
				display();

			}
	
		}	
	}
	
	public void save()
	{
		if(!clicked)
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			   //get current date time with Date()
			   Date date = new Date();
			   System.out.println(dateFormat.format(date));
			   //Random randomGenerator = new Random();
			//int randomInt = randomGenerator.nextInt(100);
			 String filename="mandel"+dateFormat.format(date)+".png";
			   //String filename=Integer.toString(randomInt)+ "mandel.png";
	          try{
	
	        	   String path = Environment.getExternalStorageDirectory().toString();
	        	   
	        	   
	        	   OutputStream fOut = null;
	        	//   String fp=path+"/Pictures/";
	        	   File file = new File(path, filename);
	        	   fOut = new FileOutputStream(file);
	
	        	   frs.peek().bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
	        	   
	        	   MyMediaConnectorClient client = new MyMediaConnectorClient(file.getAbsolutePath());
	   			MediaScannerConnection scanner = new MediaScannerConnection(this.getContext(), client);
	   			client.setScanner(scanner);
	   			scanner.connect();
	        	   
	        	  // MediaStore.Images.Media.insertImage(this.getContext().getContentResolver(), frs.peek().bm, filename , "");
	        	//	   this.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
	        	   //	this.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
	        	   	fOut.flush();
	        	   	fOut.close();
	           }catch(Exception e)
	           {
	        	   e.printStackTrace();
	           }
		}
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{	
		if(!clicked)
		{
			clicked=true;
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				int i = (int) event.getX();
				int j = (int) event.getY();
				
				
				final Frame f=frs.peek();
				
				double screenRatio = (double)f.h / (double)f.w; 
				
				double max_x=f.centreX + f.windowSize / 2;
				double min_x=f.centreX - f.windowSize / 2;
				double max_y=f.centreY + f.windowSize * screenRatio / 2;
				double min_y=f.centreY - f.windowSize * screenRatio / 2;	
				double x=(i*(max_x-min_x))/f.w+min_x;
				double y=(j*(max_y-min_y))/f.h+min_y;
				final double centreX=x;
				final double centreY=y; 
				final double windowSize = f.windowSize / 2;
				
				final Handler mHandler = new Handler();
				
				new Thread(new Runnable() 
				{
		             public void run() 
		             {
		            	 final Frame ff=new Frame(f.w,f.h,centreX,centreY, windowSize, mHandler, progressBar);
	
	                     // Update the progress bar
	                     mHandler.post(new Runnable() {
	                         public void run() {
	                        	 frs.push(ff);
	                        	 display();
	                        	 clicked=false;
	                         }
	                     });
	
		             }
				}).start();
			}
		//	MenuItem mv3 = menu.findItem(R.id.item3);
	       // mv3.setVisible(true);
			
		}
		
		return true;
	}


	
	
	
	
	
	
	public void onBackPressed()
	{
	    	frs.pop();
	    	display();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(!frs.empty())
		{
			Frame fr=frs.peek();
			canvas.drawBitmap(fr.bm,0,0,null);
		}
	    
	}
	
	private void display()
	{
		assert(frs.size()>0);
		
		invalidate();
		if(textBox != null)
		{
			textBox.setText("");
			
			if(displayText)
			{
				textBox.append("x = "+Double.toString(frs.peek().centreX)+"\n");
				textBox.append("y = "+Double.toString(frs.peek().centreY)+"\n");
				textBox.append("zoom = "+Double.toString(frs.peek().windowSize)+"\n");
				textBox.append("runtime = "+Long.toString(frs.peek().interval)+" ms"+"\n");
			}
		}
		//TextView tv=(TextView)this.getRootView().findViewById(R.id.textView1);
		//tv.append("example");
	}
	
	public void toggleDisplay()
	{
		displayText = !displayText;
		display();
	}
	
	
}
