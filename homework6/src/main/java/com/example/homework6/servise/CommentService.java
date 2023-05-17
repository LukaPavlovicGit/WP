package com.example.homework6.servise;

import com.example.homework6.entities.Comment;
import com.example.homework6.repository.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepository commentRepository;


    public Comment add(Comment comment){ return commentRepository.insert(comment); }

    public List<Comment> getAll(){
        return commentRepository.getAll();
    }

    public List<Comment> getAllByPostId(int postId){
        return commentRepository.getAllByPostId(postId);
    }

    public Comment findById(int id){
        return commentRepository.findById(id);
    }

    public void delete(int id){
        commentRepository.delete(id);
    }

}
