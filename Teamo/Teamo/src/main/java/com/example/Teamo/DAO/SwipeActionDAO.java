package com.example.Teamo.dao;

import com.example.Teamo.Model.SwipeAction;
import com.example.Teamo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Access Object for SwipeAction entities.
 * Provides methods for querying and managing swipe actions in the database.
 *
 * @author Teamo Development Team
 */
@Repository
public interface SwipeActionDAO extends JpaRepository<SwipeAction, Long> {

    /**
     * Find all swipe actions by a specific user
     * @param user the user who performed the swipes
     * @return list of swipe actions
     */
    List<SwipeAction> findByUser(User user);

    /**
     * Find all swipe actions by a specific user ID
     * @param userId the user ID who performed the swipes
     * @return list of swipe actions
     */
    List<SwipeAction> findByUserId(Long userId);

    /**
     * Find all swipe actions on a specific target user
     * @param targetUser the target user who was swiped on
     * @return list of swipe actions
     */
    List<SwipeAction> findByTargetUser(User targetUser);

    /**
     * Find all swipe actions on a specific target user ID
     * @param targetUserId the target user ID who was swiped on
     * @return list of swipe actions
     */
    List<SwipeAction> findByTargetUserId(Long targetUserId);

    /**
     * Find a specific swipe action between two users
     * @param user the user who swiped
     * @param targetUser the user who was swiped on
     * @return Optional containing the swipe action if found
     */
    @Query("SELECT sa FROM SwipeAction sa WHERE sa.user = :user AND sa.targetUser = :targetUser")
    SwipeAction findSwipeActionBetweenUsers(@Param("user") User user, @Param("targetUser") User targetUser);

    /**
     * Find a specific swipe action between two users by IDs
     * @param userId the user ID who swiped
     * @param targetUserId the target user ID who was swiped on
     * @return Optional containing the swipe action if found
     */
    @Query("SELECT sa FROM SwipeAction sa WHERE sa.user.id = :userId AND sa.targetUser.id = :targetUserId")
    SwipeAction findSwipeActionBetweenUserIds(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);

    /**
     * Find all like actions by a user
     * @param user the user who performed the likes
     * @return list of like actions
     */
    @Query("SELECT sa FROM SwipeAction sa WHERE sa.user = :user AND sa.swipeType = 'LIKE'")
    List<SwipeAction> findLikesByUser(@Param("user") User user);

    /**
     * Find all like actions by a user ID
     * @param userId the user ID who performed the likes
     * @return list of like actions
     */
    @Query("SELECT sa FROM SwipeAction sa WHERE sa.user.id = :userId AND sa.swipeType = 'LIKE'")
    List<SwipeAction> findLikesByUserId(@Param("userId") Long userId);

    /**
     * Find all pass actions by a user
     * @param user the user who performed the passes
     * @return list of pass actions
     */
    @Query("SELECT sa FROM SwipeAction sa WHERE sa.user = :user AND sa.swipeType = 'PASS'")
    List<SwipeAction> findPassesByUser(@Param("user") User user);

    /**
     * Find all pass actions by a user ID
     * @param userId the user ID who performed the passes
     * @return list of pass actions
     */
    @Query("SELECT sa FROM SwipeAction sa WHERE sa.user.id = :userId AND sa.swipeType = 'PASS'")
    List<SwipeAction> findPassesByUserId(@Param("userId") Long userId);

    /**
     * Find users liked by a specific user
     * @param user the user who performed the likes
     * @return list of users who were liked
     */
    @Query("SELECT sa.targetUser FROM SwipeAction sa WHERE sa.user = :user AND sa.swipeType = 'LIKE'")
    List<User> findLikedUsersByUser(@Param("user") User user);

    /**
     * Find users liked by a specific user ID
     * @param userId the user ID who performed the likes
     * @return list of users who were liked
     */
    @Query("SELECT sa.targetUser FROM SwipeAction sa WHERE sa.user.id = :userId AND sa.swipeType = 'LIKE'")
    List<User> findLikedUsersByUserId(@Param("userId") Long userId);

    /**
     * Find users who liked a specific user
     * @param targetUser the user who was liked
     * @return list of users who liked this user
     */
    @Query("SELECT sa.user FROM SwipeAction sa WHERE sa.targetUser = :targetUser AND sa.swipeType = 'LIKE'")
    List<User> findUsersWhoLikedUser(@Param("targetUser") User targetUser);

    /**
     * Find users who liked a specific user ID
     * @param targetUserId the user ID who was liked
     * @return list of users who liked this user
     */
    @Query("SELECT sa.user FROM SwipeAction sa WHERE sa.targetUser.id = :targetUserId AND sa.swipeType = 'LIKE'")
    List<User> findUsersWhoLikedUserId(@Param("targetUserId") Long targetUserId);

    /**
     * Count total likes received by a user
     * @param targetUserId the user ID who received likes
     * @return count of likes received
     */
    @Query("SELECT COUNT(sa) FROM SwipeAction sa WHERE sa.targetUser.id = :targetUserId AND sa.swipeType = 'LIKE'")
    long countLikesReceivedByUserId(@Param("targetUserId") Long targetUserId);

    /**
     * Count total likes given by a user
     * @param userId the user ID who gave likes
     * @return count of likes given
     */
    @Query("SELECT COUNT(sa) FROM SwipeAction sa WHERE sa.user.id = :userId AND sa.swipeType = 'LIKE'")
    long countLikesGivenByUserId(@Param("userId") Long userId);

    /**
     * Find recent swipe actions by a user
     * @param userId the user ID
     * @param limit maximum number of actions to return
     * @return list of recent swipe actions
     */
    @Query("SELECT sa FROM SwipeAction sa WHERE sa.user.id = :userId ORDER BY sa.swipedAt DESC")
    List<SwipeAction> findRecentSwipeActionsByUserId(@Param("userId") Long userId, @Param("limit") int limit);

    /**
     * Find swipe actions within a date range
     * @param startDate the start date
     * @param endDate the end date
     * @return list of swipe actions in that range
     */
    @Query("SELECT sa FROM SwipeAction sa WHERE sa.swipedAt BETWEEN :startDate AND :endDate")
    List<SwipeAction> findSwipeActionsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Check if a user has already swiped on a target user
     * @param userId the user ID
     * @param targetUserId the target user ID
     * @return true if already swiped
     */
    @Query("SELECT COUNT(sa) > 0 FROM SwipeAction sa WHERE sa.user.id = :userId AND sa.targetUser.id = :targetUserId")
    boolean hasUserSwipedOnTarget(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);

    /**
     * Delete all swipe actions for a user
     * @param user the user
     */
    void deleteByUser(User user);

    /**
     * Delete all swipe actions for a user by ID
     * @param userId the user ID
     */
    void deleteByUserId(Long userId);

    /**
     * Delete all swipe actions on a target user
     * @param targetUser the target user
     */
    void deleteByTargetUser(User targetUser);

    /**
     * Delete all swipe actions on a target user by ID
     * @param targetUserId the target user ID
     */
    void deleteByTargetUserId(Long targetUserId);
}
