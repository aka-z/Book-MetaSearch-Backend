package net.grosinger.bookmetasearch.amazoninterface.http;

import com.ECS.client.jax.Item;
import com.google.gson.Gson;
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
    private Gson gson;

    public SearchHandler(AmazonAffiliatesSearch searcher) {
        this.searcher = searcher;
        gson = new Gson();
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("Incoming connection: " + request.getPathInfo());

        response.setContentType("text/html;charset=utf-8");

        // Check the path
        String[] requestPath = request.getPathInfo().substring(1).split("/");
        if (requestPath.length != 1 || !requestPath[0].equals("search") || request.getQueryString() == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            baseRequest.setHandled(true);
            return;
        }

        // Check the parameters
        Map<String, String> parameters = getQueryMap(request);
        if (!parameters.containsKey("method")
                || !parameters.containsKey("q")
                || (!parameters.get("method").equals("asin") && !parameters.get("method").equals("isbn"))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters or search method");
            baseRequest.setHandled(true);
            return;
        }

        // Perform search
        List<Item> results;
        if (parameters.get("method").equals("asin")) {
            results = searcher.performItemLookupASIN(parameters.get("q"));
        } else {
            results = searcher.performItemLookupISBN(parameters.get("q"));
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().print(gson.toJson(results));
        baseRequest.setHandled(true);
    }

    private Map<String, String> getQueryMap(HttpServletRequest request) {
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
