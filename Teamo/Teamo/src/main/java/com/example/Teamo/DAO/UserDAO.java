<<<<<<< HEAD
package com.example.Teamo.dao;

import com.example.Teamo.Model.User;
import com.example.Teamo.Model.Skill;
=======
package com.example.Teamo.DAO;

import com.example.Teamo.Model.User;
import com.example.Teamo.Model.Tag;
>>>>>>> 408160eb0091f7af97f6b7e05d7cd54e70da892c
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

<<<<<<< HEAD
/**
 * Data Access Object for User entities.
 * Provides methods for querying and managing user data in the database.
 *
 * @author Teamo Development Team
 */
@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    /**
     * Find a user by their email address
     * @param email the user's email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by their email address (case insensitive)
     * @param email the user's email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmailIgnoreCase(String email);

    /**
     * Check if a user exists with the given email
     * @param email the email to check
     * @return true if user exists
     */
    boolean existsByEmail(String email);

    /**
     * Find users by their first and last name
     * @param firstName the first name
     * @param lastName the last name
     * @return list of matching users
     */
    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Find users by first name (case insensitive)
     * @param firstName the first name
     * @return list of matching users
     */
    List<User> findByFirstNameIgnoreCase(String firstName);

    /**
     * Find users by last name (case insensitive)
     * @param lastName the last name
     * @return list of matching users
     */
    List<User> findByLastNameIgnoreCase(String lastName);

    /**
     * Find users by location
     * @param location the location
     * @return list of users in that location
     */
    List<User> findByLocationIgnoreCase(String location);

    /**
     * Find active users only
     * @return list of active users
     */
    List<User> findByIsActiveTrue();

    /**
     * Find users by skill
     * @param skill the skill to search for
     * @return list of users with that skill
     */
    @Query("SELECT u FROM User u JOIN u.skills s WHERE s = :skill")
    List<User> findBySkill(@Param("skill") Skill skill);

    /**
     * Find users by skill name
     * @param skillName the skill name to search for
     * @return list of users with that skill
     */
    @Query("SELECT u FROM User u JOIN u.skills s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :skillName, '%'))")
    List<User> findBySkillNameContaining(@Param("skillName") String skillName);

    /**
     * Find users by multiple skills
     * @param skills the set of skills
     * @return list of users with any of the specified skills
     */
    @Query("SELECT u FROM User u JOIN u.skills s WHERE s IN :skills")
    List<User> findBySkillsIn(@Param("skills") Set<Skill> skills);

    /**
     * Find users with specific skill categories
     * @param categories the categories to search for
     * @return list of users with skills in those categories
     */
    @Query("SELECT u FROM User u JOIN u.skills s WHERE LOWER(s.category) IN :categories")
    List<User> findBySkillCategoriesIn(@Param("categories") Set<String> categories);

    /**
     * Find users excluding the specified user (for matching purposes)
     * @param userId the user ID to exclude
     * @return list of other active users
     */
    @Query("SELECT u FROM User u WHERE u.id != :userId AND u.isActive = true")
    List<User> findOtherActiveUsers(@Param("userId") Long userId);

    /**
     * Find users who haven't been swiped on by the current user
     * @param userId the current user's ID
     * @return list of users not yet swiped on
     */
    @Query("SELECT u FROM User u WHERE u.id != :userId AND u.isActive = true " +
           "AND u.id NOT IN (SELECT sa.targetUser.id FROM SwipeAction sa WHERE sa.user.id = :userId)")
    List<User> findUnswipedUsersForUser(@Param("userId") Long userId);

    /**
     * Find users with skills matching the current user's interests
     * @param userId the current user's ID
     * @return list of users with complementary skills
     */
    @Query("SELECT DISTINCT u FROM User u JOIN u.skills s " +
           "WHERE u.id != :userId AND u.isActive = true " +
           "AND s.category NOT IN (SELECT s2.category FROM User u2 JOIN u2.skills s2 WHERE u2.id = :userId)")
    List<User> findUsersWithComplementarySkills(@Param("userId") Long userId);

    /**
     * Search users by name, bio, or location
     * @param searchTerm the search term
     * @return list of matching users
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true " +
           "AND (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(u.bio) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(u.location) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);

    /**
     * Find recently active users (based on updatedAt timestamp)
     * @param limit the maximum number of users to return
     * @return list of recently active users
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true ORDER BY u.updatedAt DESC")
    List<User> findRecentlyActiveUsers(@Param("limit") int limit);

    /**
     * Count total number of active users
     * @return count of active users
     */
    long countByIsActiveTrue();
=======

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

    @Query("SELECT u FROM User u JOIN u.tags s WHERE s IN :tags")
    List<User> findByTagsIn(@Param("tags") Set<Tag> tags);

    @Query("SELECT u FROM User u JOIN u.tags s WHERE LOWER(s.category) IN :categories")
    List<User> findByTagCategoriesIn(@Param("categories") Set<String> categories);

    @Query("SELECT u FROM User u WHERE u.id != :userId AND u.isActive = true")
    List<User> findOtherActiveUsers(@Param("userId") Long userId);

    @Query("SELECT DISTINCT u FROM User u JOIN u.tags s " +
           "WHERE u.id != :userId AND u.isActive = true " +
           "AND s.category NOT IN (SELECT s2.category FROM User u2 JOIN u2.tags s2 WHERE u2.id = :userId)")
    List<User> findUsersWithComplementaryTags(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.isActive = true " +
           "AND (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(u.bio) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);

    @Query("SELECT u FROM User u WHERE u.isActive = true ORDER BY u.updatedAt DESC")
    List<User> findRecentlyActiveUsers(@Param("limit") int limit);

    //@Query("SELECT COUNT(m) FROM Match m WHERE (m.user1.id = :userId OR m.user2.id = :userId) AND m.isActive = true")
    //long countMatchesForUser(@Param("userId") Long userId);
>>>>>>> 408160eb0091f7af97f6b7e05d7cd54e70da892c
}
