package com.Blog_Application_Web.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Blog_Application_Web.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	@Query("select e from User e where e.email = :email")
	User findByEmail(@Param("email") String  email);

}
