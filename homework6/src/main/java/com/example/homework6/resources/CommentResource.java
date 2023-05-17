package com.example.homework6.resources;

import com.example.homework6.entities.Comment;
import com.example.homework6.servise.CommentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/comments")
public class CommentResource {

    @Inject
    private CommentService commentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getAll(){ return commentService.getAll(); }

    @GET
    @Path("/byPostId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getByPostId(@PathParam("id") int id) { return commentService.getAllByPostId(id); }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Comment create(@Valid Comment post){ return commentService.add(post); }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Comment getById(@PathParam("id") int id) {return commentService.findById(id); }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") int id) { commentService.delete(id); }
}
