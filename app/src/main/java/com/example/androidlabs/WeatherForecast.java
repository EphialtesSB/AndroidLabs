package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery req = new ForecastQuery();
        req.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metrichttp://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");


        ProgressBar loadingimage  = (ProgressBar) findViewById(R.id.progressBar);
        loadingimage.setVisibility(View.VISIBLE);

        TextView Current  = findViewById(R.id.Current);
        TextView Min = findViewById(R.id.Min);
        TextView Max = findViewById(R.id.Max);
        TextView Uv = findViewById(R.id.Uv);


    }


    //Type1     Type2   Type3
    private class ForecastQuery extends AsyncTask< String, Integer, String>
    {
        String Current;
        String Min;
        String Max;
        String Icon;
        String uvURL = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
        Float uvRating;
        String Weather = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";




        //Type3                Type1
        public String doInBackground(String ... args) {



            try {


                // UV INFORMATION
                //create a URL object of what server to contact:
                URL url = new URL(Weather);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();


                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                //From part 3, slide 20
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        if (xpp.getName().equals("temperature")) {
                            //If you get here, then you are pointing to a <Temperature> start tag
                            Current = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            Min = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            Max = xpp.getAttributeValue(null, "max");
                            publishProgress(75);


                        } else if (xpp.getName().equals("weather")){
                            Icon = xpp.getAttributeValue(null, "icon"); //this should get the icon
                        }

                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable

                }

                // UV INFORMATION
                //create a URL object of what server to contact:
                url = new URL(uvURL);

                //open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                response = urlConnection.getInputStream();


                //JSON reading:
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string


                // convert string to JSON:
                JSONObject uvReport = new JSONObject(result);

                //get the double associated with "value"
                 double uvRatingd = uvReport.getDouble("value");
                uvRating = (float) uvRatingd;

                Log.i("weatherforcast", "The uv is now: " + uvRating);
            String iconUrl = "http://openweathermap.org/img/w/"+ Icon +".png";

            if (!fileExistance(Icon + ".png")){
                Bitmap image = null;
                url = new URL(iconUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    InputStream in = new URL(iconUrl).openStream();
                    image = BitmapFactory.decodeStream(in);
                }
                publishProgress(100);

                FileOutputStream outputStream = openFileOutput(Icon + ".png", Context.MODE_PRIVATE);
                image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
                Log.i ("imagefile", "Image downloaded");
            } else {
                Log.i ("imagefile", "Image found");
            }

                FileInputStream fis = null;
                try {    fis = openFileInput(Icon + ".png");   }
                catch (FileNotFoundException e) {    e.printStackTrace();  }
                Bitmap bm = BitmapFactory.decodeStream(fis);
                fileExistance(Icon + ".png");



            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }





          /*  catch(Exception e)
            {

            }*/


            return "Done";
        }


        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        //Type 2
        public void onProgressUpdate(Integer ... args)
        {
         // set progress bar to args (25%)
           // ProgressBar.setProgress(args[0]);




        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            Log.i("HTTP", fromDoInBackground);

            TextView uvtxt = findViewById(R.id.Uv);
            uvtxt.setText(uvRating.toString());

            TextView currenttxt = findViewById(R.id.Current);
            currenttxt.setText(Current);

            TextView mintxt = findViewById(R.id.Min);
            mintxt.setText(Min);

            TextView maxtxt = findViewById(R.id.Max);
            maxtxt.setText(Max);


            File file = getBaseContext().getFileStreamPath(Icon+ ".png");

            ImageView Img;
            Img = findViewById(R.id.ImageView);

            String filePath = file.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            Img.setImageBitmap(bitmap);

        }
    }

}
