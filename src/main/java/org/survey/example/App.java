package org.survey.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import org.survey.example.controller.SurveyController;

/**
 * Hello world!
 *
 */
public class App extends Application<AppConfiguration>
{
    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new SurveyController());
    }
}
