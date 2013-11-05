package net.grosinger.bookmetasearch.amazoninterface;

import com.ECS.client.jax.Item;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Tony Grosinger
 */
public class SearchHandler extends AbstractHandler {
    private AmazonAffiliatesSearch searcher;

    public SearchHandler(AmazonAffiliatesSearch searcher) {
        this.searcher = searcher;
    }

    @Override
    public void handle(String target,Request baseRequest,HttpServletRequest request,HttpServletResponse response)
           throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");


        //List<Item> results = search.performItemSearch("Name of the wind");
        //System.out.println(results.get(0).getItemAttributes().getTitle());

        List<Item> results2 = searcher.performItemLookupASIN("B0010SKUYM");
        System.out.println(results2.get(0).getItemAttributes().getTitle());

        List<Item> results3 = searcher.performItemLookupISBN("9780756404079");
        System.out.println(results3.get(0).getItemAttributes().getTitle());
    }
}
