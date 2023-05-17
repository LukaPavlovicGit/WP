package com.example.homework6.repository;

import com.example.homework6.entities.Comment;

import java.util.List;

public interface CommentRepository {
    public Comment insert(Comment comment);
    public List<Comment> getAll();
    public List<Comment> getAllByPostId(int postId);
    public Comment findById(int id);
    public void delete(int id);
}
