package com.dragansah.gsoc2011.demoapp.pages;

import org.apache.tapestry5.annotations.Log;

public class AjaxExamples
{
    public String getTest1() throws InterruptedException
    {
        Thread.sleep(1000);
        return "test1";
    }

    public String getTest2() throws InterruptedException
    {
        Thread.sleep(1000);
        return "test2";
    }

    @Log
    void onContextMenu()
    {

    }
}
