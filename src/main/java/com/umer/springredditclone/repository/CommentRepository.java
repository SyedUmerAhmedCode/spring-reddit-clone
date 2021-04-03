package com.umer.springredditclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umer.springredditclone.model.Comment;
import com.umer.springredditclone.model.Post;
import com.umer.springredditclone.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByPost(Post post); 
	
	List<Comment> findByUser(User user);

}
