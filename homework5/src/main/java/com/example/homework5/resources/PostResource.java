package com.example.homework5.resources;

import com.example.homework5.entities.Post;
import com.example.homework5.servise.PostService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/posts")
public class PostResource {

    @Inject
    private PostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){ return Response.ok(postService.getAll()).build(); }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Post create(@Valid Post post){ return postService.add(post); }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getById(@PathParam("id") int id) {return postService.findById(id); }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") int id) { postService.delete(id); }
}
