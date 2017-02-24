package com.hackaton.hevre.clientapplication.Model.WikiDataService;

import com.hackaton.hevre.clientapplication.Model.Common.GetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avichay on 09/11/2016.
 */

public class WikiDataApiWrapper extends GetRequest implements IWikiDataApi {

    /* static */
    static WikiDataApiWrapper instance = null;

    /* data members */
    // currently not!

    /**
     * @return WikiDataWrapper object
     */
    public static WikiDataApiWrapper getInstance() {
        if (instance == null) {
            instance = new WikiDataApiWrapper();
        }
        return instance;
    }

    /* C-tors */

    /**
     * Default private C-tor
     */
    private WikiDataApiWrapper() {

    }

    private List<String> getAllCategories(String response) {
        List<String> categories = new ArrayList<String>();
        final JSONObject obj;
        try {
            obj = new JSONObject(response);
            final JSONObject result = obj.getJSONObject("results");
            final JSONArray bindings = result.getJSONArray("bindings");
            final int n = bindings.length();
            for (int i = 0; i < n; ++i) {
                final JSONObject category = bindings.getJSONObject(i);
                final JSONObject categoryValue =  category.getJSONObject("category");
                final String value = categoryValue.getString("value");
                categories.add(value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public List<String> getAllTags(String product)
    {
        product = product.trim();
        final String productName  = product.replaceAll(" ", "%20");

        final String restUrlInstanceOf = "https://query.wikidata.org/sparql?query=SELECT%20%3Fcategory%0AWHERE%0A%7B%0A%20%20%3Fitem%20wdt%3AP31%20%3FitemInstance%20.%20%23%20instance%20of%20%0A%20%20%23%3Fitem%20wdt%3AP279%20%3FitemSubclass%20.%20%23%20subclass%20of%0A%20%20%3Fitem%20rdfs%3Alabel%20%22" + productName + "%22%40he%20.%0A%20%20%3FitemInstance%20rdfs%3Alabel%20%3Fcategory%20filter%20%28lang%28%3Fcategory%29%20%3D%20%22en%22%29.%0A%7D&format=json";
        final String restUrlSubclassOf = "https://query.wikidata.org/sparql?query=SELECT%20%3Fcategory%0AWHERE%0A%7B%0A%20%20%23%3Fitem%20wdt%3AP31%20%3FitemInstance%20.%20%23%20instance%20of%20%0A%20%20%3Fitem%20wdt%3AP279%20%3FitemSubclass%20.%20%23%20subclass%20of%0A%20%20%3Fitem%20rdfs%3Alabel%20%22" + productName + "%22%40he%20.%0A%20%20%3FitemSubclass%20rdfs%3Alabel%20%3Fcategory%20filter%20%28lang%28%3Fcategory%29%20%3D%20%22en%22%29.%0A%7D&format=json";
        List<String> categories = new ArrayList<String>();

        String instanceOfRequest = get(restUrlInstanceOf);
        String subclassOfRequest = get(restUrlSubclassOf);

        categories.addAll(getAllCategories(instanceOfRequest));
        categories.addAll(getAllCategories(subclassOfRequest));

        return categories;

    }
}
