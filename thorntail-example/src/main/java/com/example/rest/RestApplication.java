package com.example.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Response;

@ApplicationPath("/")
@Path("")
public class RestApplication extends Application {

    private String indexPage = "<html><body>\n" +
            "Try the <a href=\"hello\">example service</a> " +
            "or take a look at the <a href=\"metrics\">metrics</a>" +
            "</body></html>";

    @GET
    @Produces("text/html")
    public Response doGet() {
        return Response.ok(indexPage).build();

    }
}