package com.example.homework6.repository;

import com.example.homework6.entities.Post;

import java.util.List;

public interface PostRepository {
    public Post insert(Post post);
    public List<Post> getAll();
    public Post findById(int id);
    public void delete(int id);
}
