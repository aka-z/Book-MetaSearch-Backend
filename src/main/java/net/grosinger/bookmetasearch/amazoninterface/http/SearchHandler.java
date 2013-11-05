package net.grosinger.bookmetasearch.amazoninterface.http;

import com.ECS.client.jax.Item;
import net.grosinger.bookmetasearch.amazoninterface.AmazonAffiliatesSearch;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tony Grosinger
 */
public class SearchHandler extends AbstractHandler {
    private static Logger LOG = Logger.getLogger(SearchHandler.class);
    private AmazonAffiliatesSearch searcher;

    public SearchHandler(AmazonAffiliatesSearch searcher) {
        this.searcher = searcher;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("Incoming connection: " + request.getPathInfo());

        response.setContentType("text/html;charset=utf-8");

        String[] requestPath = request.getPathInfo().substring(1).split("/");
        if (requestPath.length == 2 && requestPath[0].equals("search")) {
            LOG.debug("Performing search: " + request.getQueryString());

            if (request.getQueryString() != null) {
                Map<String, String> parameters = getQueryMap(request);

                if (requestPath[1].equals("asin") && parameters.containsKey("asin")) {
                        List<Item> results = searcher.performItemLookupASIN(parameters.get("asin"));

                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().print(results.get(0).getItemAttributes().getTitle());
                        baseRequest.setHandled(true);
                } else if(requestPath[1].equals("isbn") && parameters.containsKey("isbn")) {
                        List<Item> results = searcher.performItemLookupISBN(parameters.get("isbn"));

                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getOutputStream().print(results.get(0).getItemAttributes().getTitle());
                        baseRequest.setHandled(true);
                        return;
                }
            }

            // If we haven't returned yet...
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid search request");
            baseRequest.setHandled(true);
            return;
        }

        LOG.error("Unable to handle request");
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        baseRequest.setHandled(false);
    }

    public static Map<String, String> getQueryMap(HttpServletRequest request) {
        String[] params = request.getQueryString().split("&");
        Map<String, String> parameters = new HashMap<String, String>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            parameters.put(name, value);
        }
        return parameters;
    }
}
