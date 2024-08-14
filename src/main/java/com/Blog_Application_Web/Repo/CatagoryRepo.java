package com.Blog_Application_Web.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Blog_Application_Web.entity.Catagory;
import com.Blog_Application_Web.entity.Post;

@Repository
public interface CatagoryRepo extends JpaRepository<Catagory, Integer> {

	@Query("select s from Post s where s.catagory.id = :catagoryId")
	public List<Post> findPostByCatagoryId(@Param(value = "catagoryId") int catagoryId);
}
