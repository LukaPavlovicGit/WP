package com.example.homework5.repository;

import com.example.homework5.entities.Post;

import java.util.List;

public interface PostRepository {
    public Post insert(Post post);
    public List<Post> getAll();
    public Post findById(int id);
    public void delete(int id);
}
