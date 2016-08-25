package de.repictures.diewitzeapp.dws;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class checkProfile {

	public checkProfile(String email, DatastoreService datastore, HttpServletResponse resp) throws IOException {

        Key personKey = KeyFactory.createKey("email", email);
        Query query = new Query("profile", personKey);
        List<Entity> profiles = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
        try {
            if (profiles.size() > 0){
                Entity e = profiles.get(0);
                resp.getWriter().println(e.getProperty("email") + "</we>" + e.getProperty("password") + 
                		"</we>" + e.getProperty("key"));
            } else {
                resp.getWriter().println(email);
            }
        } catch (UnsupportedClassVersionError e){
            resp.getWriter().println(email);
        }
	}

}
