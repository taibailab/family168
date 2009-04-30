package com.family168.impl;

import com.family168.Helloworld;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class DemoActivator
    implements BundleActivator
{
    private ServiceRegistration registration = null;

    public void start( BundleContext context )
               throws Exception
    {
        System.out.println( "start" );
        registration =
            context.registerService( Helloworld.class.getName(  ),
                                     new HelloworldImpl(  ),
                                     null );
    }

    public void stop( BundleContext context )
              throws Exception
    {
        System.out.println( "end" );

        if ( registration != null )
        {
            registration.unregister(  );
        }
    }
}
