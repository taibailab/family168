package com.family168.impl;

import com.family168.Helloworld;

public class HelloworldImpl
    implements Helloworld
{
    public String hello( String name )
    {
        System.out.println( "Hello World: " + name );

        return "[Bundle1] : Hello World: " + name;
    }
}
