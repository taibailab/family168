package com.family168.impl;

import com.family168.Helloworld;

public class HelloworldImpl
    implements Helloworld
{
    public String hello( String name )
    {
        System.out.println( "This is bundle 2: " + name );

        return "[Bundle2] : Hello World: " + name;
    }
}
