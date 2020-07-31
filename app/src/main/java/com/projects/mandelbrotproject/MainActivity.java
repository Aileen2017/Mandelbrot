package com.projects.mandelbrotproject;

import java.util.Timer;
import java.util.TimerTask;

import com.projects.mandelbrotproject.R;

import android.util.Log;
import android.view.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.Color;


public class MainActivity extends Activity {

	View theInflatedView;
	public boolean menuOpen = false;
	private Menu menu = null;
	  private ProgressDialog pd = null;
	    private Object data = null;
    @TargetApi(11)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
   //  View iv = (View) findViewById(R.id.imageV);
       
    setContentView(R.layout.activity_main);
      // LayoutInflater inflater = LayoutInflater.from(MainActivity.this); // 1
     //  theInflatedView = inflater.inflate(R.layout.activity_main, null);
     //  setContentView(theInflatedView);
       mandView mv=(mandView)findViewById(R.id.mv);
     
       TextView tv=(TextView)this.findViewById(R.id.textView1);
       ProgressBar progressBar=(ProgressBar)this.findViewById(R.id.progressBar);
       mv.setTextBox(tv);
       mv.setProgressBar(progressBar);
       mv.setMenu(menu);
       
       this.pd = ProgressDialog.show(this, "Working..", "Downloading Data...", true, false);

       // Start a new thread that will download all the data
       new DownloadTask().execute("Any parameters my download task needs here");
  
       
     //  getActionBar().hide();
    //   setProgressBarIndeterminate(true);
      // setProgressBarIndeterminateVisibility(true);
      // MenuItem mv3 = menu.findItem(R.id.item3);
       
    //   mv3.setVisible(false);
     //  MenuItem mv4 = menu.findItem(R.id.item4);
      // mv4.setVisible(false);
      // invalidateOptionsMenu();
     //  closeOptionsMenu();
       
      //tv.append(Double.toString( mv.frs.peek().centreX));
      // mv.paint.setColor(Color.CYAN);
     //  mandView mv = new mandView(this);
      // setContentView(mv);
       /*
       mandView mv = new mandView(this);
       mv.setBackgroundColor(Color.WHITE);
       setContentView(mv);*/
    }
    
    private class DownloadTask extends AsyncTask<String, Void, Object> {
       
    	protected Object doInBackground(String... args) {
            Log.i("MyApp", "Background thread starting");

            // This is where you would do all the work of downloading your data
           /* mandView mv=(mandView)findViewById(R.id.mv);
            
            TextView tv=(TextView)this.findViewById(R.id.textView1);
            ProgressBar progressBar=(ProgressBar)this.findViewById(R.id.progressBar);
            mv.setTextBox(tv);
            mv.setProgressBar(progressBar);
            mv.setMenu(menu);*/
            return "data";
        }

        protected void onPostExecute(Object result) {
            // Pass the result data back to the main activity
            MainActivity.this.data = result;

            if (MainActivity.this.pd != null) {
                MainActivity.this.pd.dismiss();
            }
        }
   }    
    

    @TargetApi(11)
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        
    //   MenuItem mv3 = menu.findItem(R.id.item3);
      //  mv3.setVisible(false);
       /* MenuItem mv4 = menu.findItem(R.id.item4);
        mv4.setVisible(false);*/
        //invalidateOptionsMenu();
      //  closeOptionsMenu();
        return true;
       
    }
    @Override
   /* public void onAttachedToWindow() {
        super.onAttachedToWindow();
      //  openOptionsMenu();
      //  closeOptionsMenu();
    }*/
    
   /* public boolean onPrepareOptionsMenu(Menu menu) {
    	Timer timing = new Timer();
    	        timing.schedule(new TimerTask() {
    		 
    		          
    		            @Override
    		            public void run() {
    		                closeOptionsMenu();
    		            }
    		        }, 10000);
    		        return super.onPrepareOptionsMenu(menu);
    	
    }
    */
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        case R.id.item1:
        	{	
        		
        			//TextView tv=(TextView)this.findViewById(R.id.textView1);
        			//tv.append("clicked");
        			mandView mv=(mandView)this.findViewById(R.id.mv);
        		//	mv.setTextBox(tv);
        			//tv.append(Double.toString(mv.frs.peek().centreX));
        			mv.back();
        			return true;
        		
        	}
        case R.id.item2:
        	{
        		mandView mv=(mandView)this.findViewById(R.id.mv);
        		
        		mv.save();
        		

        		Toast msg = Toast.makeText(this, "This image has been saved.", Toast.LENGTH_SHORT);

        		//msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);

        		msg.show();
        		return true;
        	}
        case R.id.item3:
    		{
    			mandView mv=(mandView)this.findViewById(R.id.mv);
    			mv.toggleDisplay();
    		return true;
    		}	
        }
        return false;
    }
    
    
//}

@Override
protected Dialog onCreateDialog(int id) {
    switch (id) {
   
        case 1: {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait while loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            return dialog;
        }
    }
    return null;
	}
}
