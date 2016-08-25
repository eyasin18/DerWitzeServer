package de.repictures.diewitzeapp.dws;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class WitzeNachKategoNew {
    public WitzeNachKategoNew(int count, DatastoreService datastore, Key postKey,
                              String profileKey, HttpServletResponse resp) throws IOException, EntityNotFoundException {

        Query query;
        query = new Query("posting", postKey);
        query.addSort("datum", Query.SortDirection.DESCENDING);

        List<Entity> posts = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        List<Entity> outputlist;
        try {
            outputlist = posts.subList(count, count + 15);
        } catch (IndexOutOfBoundsException e){
            outputlist = posts.subList(count, posts.size());
        }
        String output = "";
        for(Entity e : outputlist) {
            String ratingKeyStr = (String) e.getProperty("VotingKey");
            Entity rating = datastore.get(KeyFactory.stringToKey(ratingKeyStr));
            String profileKeyStr = (String) e.getProperty("user");
            Entity user = datastore.get(KeyFactory.stringToKey(profileKeyStr));
            Boolean voted = false, reported = false;
            ArrayList<String> voters = (ArrayList<String>) rating.getProperty("Voters");
            if (voters.contains(profileKey)) voted = true;
            ArrayList<String> reporters = (ArrayList<String>) rating.getProperty("Reporters");
            if (reporters.contains(profileKey)) reported = true;
            output += false + "~" + user.getProperty("Visible Name") + "~" + e.getProperty("inhalt")+ "~"
                    + rating.getProperty("Votes") + "~" + rating.getProperty("Reports") + "~"
                    + e.getProperty("key") + "~" + user.getProperty("photoUrl") + "~" + user.getProperty("devise") + "~"
                    + voted + "~" + reported + "~" + e.getProperty("VotingKey") + "~" + e.getProperty("user") + "</we>";
        }
        output += true + "</we>";
        resp.getWriter().println(URLEncoder.encode(output.replace("\n", "<br />"), "UTF-8"));
    }
}