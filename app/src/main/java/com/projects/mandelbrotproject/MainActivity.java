package com.projects.mandelbrotproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.projects.mandelbrotproject.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import static android.support.v4.content.FileProvider.getUriForFile;


public class MainActivity extends Activity {

	View theInflatedView;
	public boolean menuOpen = false;
	private Menu menu = null;
	private ProgressDialog pd = null;
	private Object data = null;
    private mandView mv;

    @TargetApi(11)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mv=(mandView)findViewById(R.id.mv);
     
       TextView tv=(TextView)this.findViewById(R.id.textView1);
       ProgressBar progressBar=(ProgressBar)this.findViewById(R.id.progressBar);
       mv.setTextBox(tv);
       mv.setProgressBar(progressBar);
       mv.setMenu(menu);

       this.pd = ProgressDialog.show(this, "Working..", "Downloading Data...", true, false);

       // Start a new thread that will download all the data
       new DownloadTask().execute("Any parameters my download task needs here");
  
       
    }
    
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
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
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
       
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        case R.id.item1:
        	{	
        		

        			mandView mv=(mandView)this.findViewById(R.id.mv);

        			mv.back();
        			return true;
        		
        	}
        case R.id.item2:
        	{
        		
        		save();

        		Toast msg = Toast.makeText(this, "This image has been saved.", Toast.LENGTH_SHORT);

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


    public void save()
    {
        mv=(mandView)findViewById(R.id.mv);
        if(!mv.clicked)
        {

            int REQUEST_CODE = 1;
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                exportToFile(mv);
            } //else if (shouldShowRequestPermissionRationale()) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            //showInContextUI(...);
            //}
            else {
                // You can directly ask for the permission.
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        REQUEST_CODE);


            }

        }
    }

    private boolean exportToFile(mandView mv) {
        try
        {

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            //get current date time with Date()
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            //Random randomGenerator = new Random();
            //int randomInt = randomGenerator.nextInt(100);
            String fileName="mandel"+dateFormat.format(date)+".png";
            //String filename=Integer.toString(randomInt)+ "mandel.png";

                String path = Environment.getExternalStorageDirectory().toString();
                OutputStream fOut = null;
                //   String fp=path+"/Pictures/";
                File file = new File(path, fileName);
                fOut = new FileOutputStream(file);

                mv.frs.peek().bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);

                MyMediaConnectorClient client = new MyMediaConnectorClient(file.getAbsolutePath());
                MediaScannerConnection scanner = new MediaScannerConnection(mv.getContext(), client);
                client.setScanner(scanner);
                scanner.connect();

                fOut.flush();

                fOut.close();


            Uri U = getUriForFile(getApplicationContext(), "com.projects.mandelbrotproject.fileprovider", file);

            Intent i = new Intent(Intent.ACTION_SEND);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.setType("image/jpeg");

            i.putExtra(Intent.EXTRA_STREAM, U);
            startActivity(i);


        }
        catch(Exception e)
        {
            Log.i("ExceptionMenu", e.toString());

        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            exportToFile((mandView)findViewById(R.id.mv));
        }  else {
            // Explain to the user that the feature is unavailable because
            // the features requires a permission that the user has denied.
            // At the same time, respect the user's decision. Don't link to
            // system settings in an effort to convince the user to change
            // their decision.
        }

    }


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
