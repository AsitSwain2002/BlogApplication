package com.Blog_Application_Web.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Blog_Application_Web.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

	@Query("select p from Post p where p.title like :key")
	List<Post> searchBytitle(@Param("key") String keyword);
	
	@Query("select p from Post p where p.user.id = :userId")
	List<Post> allPostByUser(@Param("userId") int userId);

}
