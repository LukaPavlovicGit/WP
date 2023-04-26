package com.example.homework5.repository;

import com.example.homework5.entities.Comment;
import com.example.homework5.entities.Post;

import java.util.List;

public interface CommentRepository {
    public Comment insert(Comment comment);
    public List<Comment> getAll();
    public List<Comment> getAllByPostId(int postId);
    public Comment findById(int id);
    public void delete(int id);
}
