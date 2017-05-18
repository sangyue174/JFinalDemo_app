package com.myapp.jfinaltest.core;

import com.jfinal.config.*;
import com.jfinal.plugin.IPlugin;
import com.myapp.jfinaltest.annotation.JFinalTestConfig;

import org.junit.Before;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: liujunjie
 * Date: 16/3/17
 * Time: 下午7:51
 */
public class JFinalTest {
    Logger logger = Logger.getLogger("JFinalTest");

    @Before
    public void setup() throws IllegalAccessException, InstantiationException {
        JFinalTestConfig annotation = this.getClass().getAnnotation(JFinalTestConfig.class);
        if(null != annotation){
            JFinalConfig config = annotation.value().newInstance();
            Constants constants = new Constants();
            Interceptors interceptors = new Interceptors();
            Handlers handlers = new Handlers();
            Plugins plugins = new Plugins();
            Routes routes = new Routes() {
                @Override
                public void config() {
                    logger.log(Level.INFO, "routes config success!");
                }
            };
            configRoutes(routes);
            config.configConstant(constants);
            config.configPlugin(plugins);
            config.configInterceptor(interceptors);
            config.configHandler(handlers);
            for(IPlugin plugin : plugins.getPluginList()){
                plugin.start();
                logger.log(Level.INFO, plugin.getClass().getSimpleName() + " has stared!");
            }
        }else{
            logger.log(Level.WARNING, "you properly miss annotation  JFinalTestConfig");
        }

    }

    protected void configRoutes(Routes routes){
    }
}
