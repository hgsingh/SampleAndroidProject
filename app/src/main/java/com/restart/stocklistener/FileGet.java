package com.restart.stocklistener;

/**
 * Created by harsukh singh on 12/16/15.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class FileGet {

    public FileGet()
    {
        //empty constructor for the time beings
    }

    //this object only contains one method to get the file
    //this will create a temporary file for the StockReader to read
    public static void get_file(String fileURL, File directory)
    {
        URL u = null;
        try{
            u = new URL(fileURL); //convert into URL object
            URLConnection conn = u.openConnection(); //open a connection object
            HttpURLConnection httpConn  =  (HttpURLConnection) conn;
            int response = httpConn.getResponseCode(); //get conn status
            if(response == HttpURLConnection.HTTP_OK)
            {
                FileOutputStream f = new FileOutputStream(directory);
                InputStream in = conn.getInputStream(); //get contents
                //would a json object need to be called here to read the file?
                byte[] buffer = new byte[1024];
                int offset = in.read(buffer);
                while(offset > 0)
                {
                    f.write(buffer, 0, offset);
                    offset = in.read(buffer);
                }
                f.close();
            }
        }
        catch(Exception down)
        {
            down.printStackTrace();
        }
    }

    public static boolean remove_file(String path_to_remove)
    {
        File file = new File(path_to_remove);
        return file.delete();
    }
}
