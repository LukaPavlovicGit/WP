package com.example.homework6.servise;

import com.example.homework6.entities.Post;
import com.example.homework6.repository.PostRepository;

import javax.inject.Inject;
import java.util.List;

public class PostService {

    @Inject
    private PostRepository postRepository;

    public Post add(Post post){ return postRepository.insert(post); }

    public List<Post> getAll(){ return postRepository.getAll(); }

    public Post findById(int id){
        return postRepository.findById(id);
    }

    public void delete(int id){
        postRepository.delete(id);
    }
}
