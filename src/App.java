import com.ECS.client.jax.Item;
import net.grosinger.bookmetasearch.amazon.AmazonAffiliatesSearch;

import java.util.List;

public class App {
    private static final String ACCESS_KEY_ID = "<YOUR ACCESS KEY>";
    private static final String ASSOCIATES_ID = "<YOUR ASSOCIATE ID>";
    private static final String SECRET_KEY = "YOUR SECRET KEY";

    public static void main(String[] args) {
        AmazonAffiliatesSearch search = new AmazonAffiliatesSearch(ACCESS_KEY_ID, SECRET_KEY, ASSOCIATES_ID);

        //List<Item> results = search.performItemSearch("Name of the wind");
        //System.out.println(results.get(0).getItemAttributes().getTitle());

        List<Item> results2 = search.performItemLookupASIN("B0010SKUYM");
        System.out.println(results2.get(0).getItemAttributes().getTitle());

        List<Item> results3 = search.performItemLookupISBN("9780756404079");
        System.out.println(results3.get(0).getItemAttributes().getTitle());
    }
}
