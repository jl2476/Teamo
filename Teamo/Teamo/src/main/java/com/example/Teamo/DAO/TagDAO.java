package com.example.Teamo.DAO;

import com.example.Teamo.Model.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagDAO extends JpaRepository<Tag, Long>{

    Optional<Tag> findByName(String name);

    List<Tag> findByCategory(String category);

    List<Tag> findByNameContainingIgnoreCase(String query);

    
}
