package com.example.homework6.repository.impl.inMemory;

import com.example.homework6.entities.Post;
import com.example.homework6.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryPostRepository implements PostRepository {

    private static List<Post> posts = new CopyOnWriteArrayList<>();
    private static int postId = 1;

    @Override
    public synchronized Post insert(Post post) {
        post.setId(postId++);
        posts.add(post);
        return post;
    }

    @Override
    public List<Post> getAll() {
        return new ArrayList<>(posts);
    }

    @Override
    public Post findById(int id) {
        for(Post post : posts){
            if(post.getId() == id)
                return post;
        }
        return null;
    }

    @Override
    public void delete(int id) {
        for(int i = 0 ; i < posts.size() ; i++){
            Post post = posts.get(i);
            if(post.getId() == id){
                posts.remove(post);
                break;
            }
        }
    }
}
