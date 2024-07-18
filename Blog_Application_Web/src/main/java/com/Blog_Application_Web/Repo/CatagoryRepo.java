package com.Blog_Application_Web.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Blog_Application_Web.entity.Catagory;

@Repository
public interface CatagoryRepo extends JpaRepository<Catagory, Integer>{

}
