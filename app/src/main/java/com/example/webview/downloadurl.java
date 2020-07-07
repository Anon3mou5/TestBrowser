package com.example.webview;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class downloadurl extends Thread{

       String webpage;

    public downloadurl(String webpage) {
        this.webpage = webpage;
    }

    @Override
    public void run() {
        super.run();
        try {

            // Create URL object
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download
            File root = new File(Environment.getExternalStorageDirectory(), "Pages");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "webpage.html");

            if(gpxfile.exists())
            {
                gpxfile.delete();
            }
            gpxfile.createNewFile();
            FileWriter fwriter = new FileWriter(gpxfile);
            BufferedWriter writer =
                    new BufferedWriter(fwriter);

            // read each line from stream till end
            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }

            readr.close();
            writer.close();
            System.out.println("Successfully Downloaded.");
        }

        // Exceptions
        catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
        }
        catch (IOException ie) {
            System.out.println("IOException raised");
        }
    }
}
