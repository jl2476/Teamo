package com.example.Teamo.DAO;

import com.example.Teamo.Model.User;
import com.example.Teamo.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository                                                                                       
public interface UserDAO extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    List<User> findByFirstNameIgnoreCase(String firstName);

    List<User> findByLastNameIgnoreCase(String lastName);

    List<User> findByIsActiveTrue();

    @Query("SELECT u FROM User u JOIN u.tags s WHERE s = :tag")
    List<User> findByTag(@Param("tag") Tag tag);

    @Query("SELECT u FROM User u JOIN u.tags s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :tagName, '%'))")
    List<User> findByTagNameContaining(@Param("tagName") String tagName);

    @Query("""
    SELECT u, COUNT(s) AS matches
    FROM User u
    JOIN u.tags s
    WHERE s IN :tags
    GROUP BY u
    ORDER BY matches DESC
    """)
    List<User> findByTagsIn(@Param("tags") Set<Tag> tags);

    @Query("SELECT u FROM User u JOIN u.tags s WHERE LOWER(s.category) IN :categories")
    List<User> findByTagCategoriesIn(@Param("categories") Set<String> categories);

    @Query("SELECT u FROM User u WHERE u.id != :userId AND u.isActive = true")
    List<User> findOtherActiveUsers(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.isActive = true " +
           "AND (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(u.bio) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);

    //@Query("SELECT u FROM User u WHERE u.isActive = true ORDER BY u.updatedAt DESC")
    //List<User> findRecentlyActiveUsers(@Param("limit") int limit);

    //@Query("SELECT COUNT(m) FROM Match m WHERE (m.user1.id = :userId OR m.user2.id = :userId) AND m.isActive = true")
    //long countMatchesForUser(@Param("userId") Long userId);
}
