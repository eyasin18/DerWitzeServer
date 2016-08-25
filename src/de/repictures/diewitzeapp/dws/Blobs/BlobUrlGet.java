package de.repictures.diewitzeapp.dws.Blobs;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlobUrlGet extends HttpServlet {

    BlobstoreService bServ = BlobstoreServiceFactory.getBlobstoreService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String blobUploadUrl = bServ.createUploadUrl("/blob/upload");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");

        resp.getWriter().print(blobUploadUrl);

    }
}
