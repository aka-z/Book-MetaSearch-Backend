package net.grosinger.bookmetasearch.amazoninterface;

import com.ECS.client.jax.AWSECommerceService;
import com.ECS.client.jax.AWSECommerceServicePortType;
import com.ECS.client.jax.Item;
import com.ECS.client.jax.ItemLookup;
import com.ECS.client.jax.ItemLookupRequest;
import com.ECS.client.jax.ItemSearch;
import com.ECS.client.jax.ItemSearchRequest;
import com.ECS.client.jax.Items;
import com.ECS.client.jax.OperationRequest;

import java.util.List;

import javax.xml.ws.Holder;

public class AmazonAffiliatesSearch {
    private AWSECommerceServicePortType port;

    private String apiKey;
    private String affiliateId;

    public AmazonAffiliatesSearch(String apiKey, String apiSecret, String affiliateId) {
        this.apiKey = apiKey;
        this.affiliateId = affiliateId;

        AWSECommerceService service = new AWSECommerceService();
        service.setHandlerResolver(new AwsHandlerResolver(apiSecret));
        port = service.getAWSECommerceServicePort();
    }

    public List<Item> performItemSearch(String itemName) {
        ItemSearchRequest itemSearch = new ItemSearchRequest();
        itemSearch.setTitle(itemName);

        return itemSearchHelper(itemSearch);
    }

    public List<Item> performItemLookupISBN(String isbn) {
        ItemLookupRequest itemLookup = new ItemLookupRequest();
        itemLookup.setIdType("ISBN");
        itemLookup.setSearchIndex("Books");
        itemLookup.getItemId().add(isbn);

        return itemLookupHelper(itemLookup);
    }

    public List<Item> performItemLookupASIN(String asin) {
        ItemLookupRequest itemLookup = new ItemLookupRequest();
        itemLookup.setIdType("ASIN");
        itemLookup.getItemId().add(asin);

        return itemLookupHelper(itemLookup);
    }

    private List<Item> itemSearchHelper(ItemSearchRequest itemSearch) {
        ItemSearch search = new ItemSearch();
        search.setAssociateTag(affiliateId);
        search.setAWSAccessKeyId(apiKey);
        search.getRequest().add(itemSearch);

        Holder<OperationRequest> operationRequest = null;
        Holder<List<Items>> items = new Holder<List<Items>>();
        port.itemSearch(search.getMarketplaceDomain(),
                search.getAWSAccessKeyId(), search.getAssociateTag(),
                search.getXMLEscaping(), search.getValidate(),
                search.getShared(), search.getRequest(), operationRequest,
                items);

        Items retval = items.value.get(0); // first and only Items element
        return retval.getItem();
    }

    private List<Item> itemLookupHelper(ItemLookupRequest itemLookup) {
        ItemLookup lookup = new ItemLookup();
        lookup.setAssociateTag(affiliateId);
        lookup.setAWSAccessKeyId(apiKey);
        lookup.getRequest().add(itemLookup);

        Holder<OperationRequest> operationRequest = null;
        Holder<List<Items>> items = new Holder<List<Items>>();
        port.itemLookup(lookup.getMarketplaceDomain(),
                lookup.getAWSAccessKeyId(), lookup.getAssociateTag(),
                lookup.getXMLEscaping(), lookup.getValidate(),
                lookup.getShared(), lookup.getRequest(), operationRequest,
                items);

        Items retval = items.value.get(0); // first and only Items element
        return retval.getItem();
    }
}
