package io.egen.api;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
* Since there is no MAIN method in this application ,
* We have to extend AbstractAnnotationConfigDispatcherServletInitializer which
* then be used to configure the 'Configuration' file
*/


public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {

        /*
        * Specifies the location where the root application configuration are stored.
        * There can be multiple configuration files.
        */

        return new Class<?>[]{WebConfig.class,JPAConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {
        /*
        * To inform the application that all of the request will have prefix as "/api/*"
        * This prefix could be different as well
        * */
        return new String[]{"/api/*"};
    }
}
