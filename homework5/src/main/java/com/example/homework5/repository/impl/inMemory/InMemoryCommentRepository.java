package com.example.homework5.repository.impl.inMemory;

import com.example.homework5.entities.Comment;
import com.example.homework5.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryCommentRepository implements CommentRepository {
    private static List<Comment> comments = new CopyOnWriteArrayList<>();
    private static int id = 1;
    @Override
    public synchronized Comment insert(Comment comment) {
        comment.setId(id++);
        comments.add(comment);
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        return new ArrayList<>(comments);
    }

    @Override
    public List<Comment> getAllByPostId(int postId) {
        List<Comment> list = new ArrayList<>();
        for(Comment comment : comments){
            if(comment.getPostId() == postId)
                list.add(comment);
        }
        return list;
    }

    @Override
    public Comment findById(int id) {
        for(Comment comment : comments){
            if(comment.getId() == id)
                return comment;
        }
        return null;
    }

    @Override
    public void delete(int id) {
        for(Comment comment : comments){
            if(comment.getId() == id){
                comments.remove(comment);
                break;
            }
        }
    }
}
