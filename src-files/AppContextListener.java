package br.com.protut;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	for (int i = 0; i < 3; i++) {
            try {
                PlcSimulator.init();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (PlcSimulator.isRunning())
                break;
        }
        
        System.out.println("Protut Modbus PLC simulator started");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	PlcSimulator.terminate();
        System.out.println("Protut Modbus PLC simulator stopped");
    }
	
}