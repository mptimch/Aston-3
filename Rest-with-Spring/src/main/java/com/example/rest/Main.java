package com.example.rest;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
        public static void main(String[] args) throws LifecycleException {
            Tomcat tomcat = new Tomcat();
            tomcat.setSilent(true);
            tomcat.getConnector().setPort(8080);

            Context tomcatContext = tomcat.addContext("", null);

            AnnotationConfigWebApplicationContext applicationContext =
                    new AnnotationConfigWebApplicationContext();
            applicationContext.scan("com.example.rest");
            applicationContext.setServletContext(tomcatContext.getServletContext());
            applicationContext.refresh();

            DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
            Wrapper dispatcherWrapper =
                    Tomcat.addServlet(tomcatContext, "dispatcher", dispatcherServlet);
            dispatcherWrapper.addMapping("/");
            dispatcherWrapper.setLoadOnStartup(1);

            tomcat.start();
        }




    public static void executeScript(String filename, Statement statement) throws IOException, SQLException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder script = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                script.append(line).append("\n");
                if (line.endsWith(";")) {
                    script.setLength(script.length() - 1);
                    statement.execute(script.toString());
                    script.setLength(0);
                }
            }
        }
    }
}
