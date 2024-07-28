package com.Blog_Application_Web.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Blog_Application_Web.entity.Comments;
import com.Blog_Application_Web.entity.Post;
import com.Blog_Application_Web.entity.User;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Integer> {

	List<Comments> findAllCommentByPost(Post post);

	List<Comments> findAllCommentByUser(User user);
}
