package com.restart.stocklistener;

import android.util.JsonReader;
import android.content.Intent;
/**
 * Created by harsukh singh on 12/16/15.
 */
public class StockReader extends Object {
    //data retrieved from http://www.bloomberg.com/markets/chart/data/1D/"SYMBOL":US
    //note that the user should be able to receive the basic data at any time of day...
    //basic data inculdes, current ticker symbol, current equity value, change and percent change
    public StockReader() //default constructor
    {

    }

    public StockReader(String toRead) //non default constructor reads the string to open
    {
        //try opening the link here
        //Intent urlOpenIntent = new Intent(Intent.ACTION)
    }

    private boolean GetQuote() //reads in the data from the public site
    {
        return false;
    }

}
