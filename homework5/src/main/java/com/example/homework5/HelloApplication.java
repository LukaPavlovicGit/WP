package com.example.homework5;

import com.example.homework5.repository.CommentRepository;
import com.example.homework5.repository.PostRepository;
import com.example.homework5.repository.impl.inMemory.InMemoryCommentRepository;
import com.example.homework5.repository.impl.inMemory.InMemoryPostRepository;
import com.example.homework5.repository.impl.sqlite.SqliteCommentRepository;
import com.example.homework5.repository.impl.sqlite.SqlitePostRepository;
import com.example.homework5.servise.CommentService;
import com.example.homework5.servise.PostService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication(){
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {

                // InMemory
//                this.bind(InMemoryPostRepository.class).to(PostRepository.class).in(Singleton.class);
//                this.bind(InMemoryCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                // SQLite
                this.bind(SqlitePostRepository.class).to(PostRepository.class).in(Singleton.class);
                this.bind(SqliteCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                this.bindAsContract(PostService.class);

                this.bindAsContract(CommentService.class);
            }
        };
        register(binder);

        // Ucitavamo resurse
        packages("com.example.homework5.resources");
    }
}