package net.grosinger.bookmetasearch.api;

import net.grosinger.bookmetasearch.api.http.SearchHandler;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HttpListener {
    private static Logger LOG = Logger.getLogger(HttpListener.class);

    private AmazonAffiliatesSearch searcher;

    public static void main(String[] args) {
        if(args.length != 1) {
            LOG.error("Must pass parameter path of api-keys.properties");
            System.exit(1);
        }

        try {
            LOG.debug("Loading properties from " + args[0]);

            InputStream propertiesFileStream = new FileInputStream(args[0]);
            HttpListener webServer = new HttpListener(propertiesFileStream);
            webServer.start();
        } catch (FileNotFoundException e) {
            LOG.error("Invalid properties file location", e);
            System.exit(1);
        }
    }

    public HttpListener(InputStream propertiesFileStream) {
        Properties prop = new Properties();
        try {
            prop.load(propertiesFileStream);
            propertiesFileStream.close();
        } catch (IOException e) {
            LOG.error("Unable to load properties file", e);
        }

        String awsAccessId = prop.getProperty("aws-access-id");
        String awsSecretKey = prop.getProperty("aws-secret-key");
        String awsAffiliateId = prop.getProperty("amazon-affiliate-id");

        searcher = new AmazonAffiliatesSearch(awsAccessId, awsSecretKey, awsAffiliateId);
    }

    public void start() {
        Server server = new Server(8080);
        server.setHandler(new SearchHandler(searcher));

        try {
            server.start();
            server.join();
        } catch (InterruptedException e) {
            LOG.error("Could not start web server", e);
        } catch (Exception e) {
            LOG.error("Could not start web server", e);
        }
    }
}
