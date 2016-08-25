package de.repictures.diewitzeapp.dws.Blobs;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlobUpload extends HttpServlet {

    BlobstoreService bServ = BlobstoreServiceFactory.getBlobstoreService();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<BlobKey> blobs = bServ.getUploads(req).get("bitmap");
            BlobKey blobKey = blobs.get(0);

            ImagesService imagesService = ImagesServiceFactory.getImagesService();
            ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);

            String servingUrl = imagesService.getServingUrl(servingOptions);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");

            JSONObject json = new JSONObject();
            json.put("servingUrl", servingUrl);
            json.put("blobKey", blobKey.getKeyString());

            PrintWriter out = resp.getWriter();
            out.print(json.toString());
            out.flush();
            out.close();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
