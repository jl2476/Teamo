package com.example.Teamo.Service;

import com.example.Teamo.DAO.UserDAO;
import com.example.Teamo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for User-related business logic.
 * Handles user operations, search, and matching functionality.
 *
 * @author Teamo Development Team
 */
@Service
@Transactional
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Find a user by their ID
     * @param userId the user ID
     * @return Optional containing the user if found
     */
    public Optional<User> findById(Long userId) {
        return userDAO.findById(userId);
    }

    /**
     * Update an existing user
     * @param userId the user ID to update
     * @param updatedUser the updated user data
     * @return the updated user
     * @throws RuntimeException if user not found
     */
    public User updateUser(Long userId, User updatedUser) {
        return userDAO.findById(userId)
                .map(user -> {
                    // Update only non-null fields from updatedUser
                    if (updatedUser.getFirstName() != null) {
                        user.setFirstName(updatedUser.getFirstName());
                    }
                    if (updatedUser.getLastName() != null) {
                        user.setLastName(updatedUser.getLastName());
                    }
                    if (updatedUser.getBio() != null) {
                        user.setBio(updatedUser.getBio());
                    }
                    if (updatedUser.getProfilePictureUrl() != null) {
                        user.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
                    }
                    if (updatedUser.getLocation() != null) {
                        user.setLocation(updatedUser.getLocation());
                    }
                    if (updatedUser.getWebsite() != null) {
                        user.setWebsite(updatedUser.getWebsite());
                    }
                    if (updatedUser.getLinkedin() != null) {
                        user.setLinkedin(updatedUser.getLinkedin());
                    }
                    if (updatedUser.getGithub() != null) {
                        user.setGithub(updatedUser.getGithub());
                    }
                    return userDAO.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    /**
     * Search users by name, bio, or location
     * @param query the search query
     * @return list of matching users
     */
    public List<User> searchUsers(String query) {
        return userDAO.searchUsers(query);
    }

    /**
     * Find users with complementary skills to the specified user
     * @param userId the user ID to find complements for
     * @return list of users with complementary skills
     */
    public List<User> findUsersWithComplementarySkills(Long userId) {
        return userDAO.findUsersWithComplementarySkills(userId);
    }

    /**
     * Find users by skill category
     * @param category the skill category
     * @return list of users with that skill category
     */
    public List<User> findUsersBySkillCategory(String category) {
        return userDAO.findBySkillCategoriesIn(Set.of(category.toLowerCase()));
    }

    /**
     * Find users by location
     * @param location the location
     * @return list of users in that location
     */
    public List<User> findUsersByLocation(String location) {
        return userDAO.findByLocationIgnoreCase(location);
    }

    /**
     * Find recently active users
     * @param limit maximum number of users to return
     * @return list of recently active users
     */
    public List<User> findRecentlyActiveUsers(int limit) {
        List<User> users = userDAO.findRecentlyActiveUsers(limit);
        return users.stream().limit(limit).collect(Collectors.toList());
    }

    /**
     * Find users who haven't been swiped on by the current user
     * @param userId the current user ID
     * @return list of unswiped users
     */
    public List<User> findUnswipedUsers(Long userId) {
        return userDAO.findUnswipedUsersForUser(userId);
    }

    /**
     * Deactivate a user account
     * @param userId the user ID to deactivate
     */
    public void deactivateUser(Long userId) {
        userDAO.findById(userId).ifPresent(user -> {
            user.setActive(false);
            userDAO.save(user);
        });
    }

    /**
     * Find users by multiple skill names
     * @param skillNames array of skill names
     * @return list of users with those skills
     */
    public List<User> findUsersBySkills(String[] skillNames) {
        Set<String> skills = Set.of(skillNames);
        return userDAO.findBySkillCategoriesIn(skills);
    }

    /**
     * Find all active users
     * @return list of all active users
     */
    public List<User> findAllActiveUsers() {
        return userDAO.findByIsActiveTrue();
    }

    /**
     * Get user statistics
     * @param userId the user ID
     * @return UserStats object with user statistics
     */
    public UserStats getUserStats(Long userId) {
        UserStats stats = new UserStats();
        Optional<User> userOpt = userDAO.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            stats.setTotalPortfolioItems(user.getPortfolioItems() != null ? user.getPortfolioItems().size() : 0);
            // Count matches for this user (either as user1 or user2 in Match entity)
            stats.setTotalMatches((int) userDAO.countMatchesForUser(userId));
            // TODO: Implement likes received count when SwipeAction entity is enhanced
            stats.setTotalLikesReceived(0);
        }

        return stats;
    }

    /**
     * Simple DTO for user statistics
     */
    public static class UserStats {
        private int totalPortfolioItems;
        private int totalMatches;
        private int totalLikesReceived;

        public int getTotalPortfolioItems() { return totalPortfolioItems; }
        public void setTotalPortfolioItems(int totalPortfolioItems) { this.totalPortfolioItems = totalPortfolioItems; }

        public int getTotalMatches() { return totalMatches; }
        public void setTotalMatches(int totalMatches) { this.totalMatches = totalMatches; }

        public int getTotalLikesReceived() { return totalLikesReceived; }
        public void setTotalLikesReceived(int totalLikesReceived) { this.totalLikesReceived = totalLikesReceived; }
    }
}