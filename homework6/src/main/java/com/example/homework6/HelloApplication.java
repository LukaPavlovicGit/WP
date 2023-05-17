package com.example.homework6;

import com.example.homework6.repository.CommentRepository;
import com.example.homework6.repository.PostRepository;
import com.example.homework6.repository.UserRepository;
import com.example.homework6.repository.impl.mysql.MySqlCommentRepository;
import com.example.homework6.repository.impl.mysql.MySqlPostRepository;
import com.example.homework6.repository.impl.mysql.MySqlUserRepository;
import com.example.homework6.servise.CommentService;
import com.example.homework6.servise.PostService;
import com.example.homework6.servise.UserService;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

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
//                this.bind(SqlitePostRepository.class).to(PostRepository.class).in(Singleton.class);
//                this.bind(SqliteCommentRepository.class).to(CommentRepository.class).in(Singleton.class);

                // MySql
                this.bind(MySqlPostRepository.class).to(PostRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(MySqlUserRepository.class).to(UserRepository.class).in(Singleton.class);

                this.bindAsContract(PostService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(UserService.class);

            }
        };
        register(binder);

        // Ucitavamo resurse
        packages("com.example.homework6.resources");
    }
}