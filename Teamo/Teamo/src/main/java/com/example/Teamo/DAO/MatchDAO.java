<<<<<<< HEAD
package com.example.Teamo.dao;
=======
package com.example.Teamo.DAO;
>>>>>>> 408160eb0091f7af97f6b7e05d7cd54e70da892c

import com.example.Teamo.Model.Match;
import com.example.Teamo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for Match entities.
 * Provides methods for querying and managing matches between users in the database.
 *
 * @author Teamo Development Team
 */
@Repository
public interface MatchDAO extends JpaRepository<Match, Long> {

    /**
     * Find a match between two specific users
     * @param user1 first user
     * @param user2 second user
     * @return Optional containing the match if found
     */
    @Query("SELECT m FROM Match m WHERE (m.user1 = :user1 AND m.user2 = :user2) OR (m.user1 = :user2 AND m.user2 = :user1)")
    Optional<Match> findMatchBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);

    /**
     * Find a match between two users by their IDs
     * @param userId1 first user ID
     * @param userId2 second user ID
     * @return Optional containing the match if found
     */
    @Query("SELECT m FROM Match m WHERE (m.user1.id = :userId1 AND m.user2.id = :userId2) OR (m.user1.id = :userId2 AND m.user2.id = :userId1)")
    Optional<Match> findMatchBetweenUserIds(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    /**
     * Find all matches for a specific user
     * @param user the user
     * @return list of matches involving the user
     */
    @Query("SELECT m FROM Match m WHERE m.user1 = :user OR m.user2 = :user")
    List<Match> findMatchesForUser(@Param("user") User user);

    /**
     * Find all matches for a specific user ID
     * @param userId the user ID
     * @return list of matches involving the user
     */
    @Query("SELECT m FROM Match m WHERE m.user1.id = :userId OR m.user2.id = :userId")
    List<Match> findMatchesForUserId(@Param("userId") Long userId);

    /**
     * Find active matches for a user
     * @param user the user
     * @return list of active matches
     */
    @Query("SELECT m FROM Match m WHERE (m.user1 = :user OR m.user2 = :user) AND m.isActive = true")
    List<Match> findActiveMatchesForUser(@Param("user") User user);

    /**
     * Find active matches for a user ID
     * @param userId the user ID
     * @return list of active matches
     */
    @Query("SELECT m FROM Match m WHERE (m.user1.id = :userId OR m.user2.id = :userId) AND m.isActive = true")
    List<Match> findActiveMatchesForUserId(@Param("userId") Long userId);

    /**
     * Find recent matches for a user (ordered by matchedAt descending)
     * @param user the user
     * @param limit maximum number of matches to return
     * @return list of recent matches
     */
    @Query("SELECT m FROM Match m WHERE (m.user1 = :user OR m.user2 = :user) AND m.isActive = true ORDER BY m.matchedAt DESC")
    List<Match> findRecentMatchesForUser(@Param("user") User user, @Param("limit") int limit);

    /**
     * Find recent matches for a user ID
     * @param userId the user ID
     * @param limit maximum number of matches to return
     * @return list of recent matches
     */
    @Query("SELECT m FROM Match m WHERE (m.user1.id = :userId OR m.user2.id = :userId) AND m.isActive = true ORDER BY m.matchedAt DESC")
    List<Match> findRecentMatchesForUserId(@Param("userId") Long userId, @Param("limit") int limit);

    /**
     * Check if two users have an active match
     * @param user1 first user
     * @param user2 second user
     * @return true if they have an active match
     */
    @Query("SELECT COUNT(m) > 0 FROM Match m WHERE ((m.user1 = :user1 AND m.user2 = :user2) OR (m.user1 = :user2 AND m.user2 = :user1)) AND m.isActive = true")
    boolean haveActiveMatch(@Param("user1") User user1, @Param("user2") User user2);

    /**
     * Check if two users have an active match by IDs
     * @param userId1 first user ID
     * @param userId2 second user ID
     * @return true if they have an active match
     */
    @Query("SELECT COUNT(m) > 0 FROM Match m WHERE ((m.user1.id = :userId1 AND m.user2.id = :userId2) OR (m.user1.id = :userId2 AND m.user2.id = :userId1)) AND m.isActive = true")
    boolean haveActiveMatchByIds(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    /**
     * Find all active matches in the system
     * @return list of all active matches
     */
    @Query("SELECT m FROM Match m WHERE m.isActive = true ORDER BY m.matchedAt DESC")
    List<Match> findAllActiveMatches();

    /**
     * Find matches ordered by last interaction (most recent first)
     * @return list of matches ordered by last interaction
     */
    @Query("SELECT m FROM Match m WHERE m.isActive = true ORDER BY m.lastInteraction DESC")
    List<Match> findMatchesByLastInteraction();

    /**
     * Count total active matches for a user
     * @param userId the user ID
     * @return count of active matches
     */
    @Query("SELECT COUNT(m) FROM Match m WHERE (m.user1.id = :userId OR m.user2.id = :userId) AND m.isActive = true")
    long countActiveMatchesForUser(@Param("userId") Long userId);

    /**
     * Count total matches in the system
     * @return total number of matches
     */
    long count();

    /**
     * Count active matches in the system
     * @return number of active matches
     */
    @Query("SELECT COUNT(m) FROM Match m WHERE m.isActive = true")
    long countActiveMatches();

    /**
     * Find matches that haven't had interaction in a while (for cleanup)
     * @param cutoffDate the date before which matches are considered inactive
     * @return list of old inactive matches
     */
    @Query("SELECT m FROM Match m WHERE m.lastInteraction < :cutoffDate")
    List<Match> findMatchesInactiveSince(@Param("cutoffDate") java.time.LocalDateTime cutoffDate);

    /**
     * Delete all matches for a user
     * @param user the user
     */
    @Query("DELETE FROM Match m WHERE m.user1 = :user OR m.user2 = :user")
    void deleteMatchesForUser(@Param("user") User user);

    /**
     * Delete all matches for a user by ID
     * @param userId the user ID
     */
    @Query("DELETE FROM Match m WHERE m.user1.id = :userId OR m.user2.id = :userId")
    void deleteMatchesForUserId(@Param("userId") Long userId);
}