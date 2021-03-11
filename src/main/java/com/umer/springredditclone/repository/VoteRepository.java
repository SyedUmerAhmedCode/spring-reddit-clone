package com.umer.springredditclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umer.springredditclone.model.Post;
import com.umer.springredditclone.model.User;
import com.umer.springredditclone.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long>{
	
//	Optional<Vote> FindTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
