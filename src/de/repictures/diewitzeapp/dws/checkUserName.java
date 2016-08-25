package de.repictures.diewitzeapp.dws;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class checkUserName {

	public checkUserName(DatastoreService datastore, String username, HttpServletResponse resp) throws IOException {
		try{
			username = URLDecoder.decode(username, "UTF-8");
			Filter filter = new FilterPredicate("Visible Name", FilterOperator.EQUAL, username);
			Query query = new Query("profile").setFilter(filter);
			List<Entity> allUsers = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
			if(allUsers.size() > 0){
				resp.getWriter().println(false);
			} else {
				resp.getWriter().println(true);
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
