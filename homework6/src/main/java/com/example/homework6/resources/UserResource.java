package com.example.homework6.resources;

import com.example.homework6.requests.LoginRequest;
import com.example.homework6.requests.RegisterRequest;
import com.example.homework6.servise.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (jwt == null) {
            response.put("error", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@Valid RegisterRequest registerRequest)
    {

        Map<String, String> response = new HashMap<>();
        String username = registerRequest.getUsername();
        String firstname = registerRequest.getFirstname();
        String lastname = registerRequest.getLastname();
        String password = registerRequest.getPassword();
        if(!userService.register(username, firstname, lastname, password)){
            response.put("error", "A username is already used...");
            return Response.status(406, "Not acceptable").entity(response).build();
        }
        response.put("message", "Registration Successful");
        return Response.ok(response).build();
    }
}
