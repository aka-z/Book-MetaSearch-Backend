package net.grosinger.bookmetasearch.amazoninterface;

import net.grosinger.bookmetasearch.amazoninterface.http.SearchHandler;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HttpListener {
    private static Logger LOG = Logger.getLogger(HttpListener.class);

    private AmazonAffiliatesSearch searcher;

    public static void main(String[] args) {
        InputStream propertiesFileStream = HttpListener.class.getResourceAsStream("/api-keys.properties");

        HttpListener webServer = new HttpListener(propertiesFileStream);
        webServer.start();
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
