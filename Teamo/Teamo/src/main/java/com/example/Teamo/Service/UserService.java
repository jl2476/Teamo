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

@Service
@Transactional
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> findById(Long userId) {
        return userDAO.findById(userId);
    }

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
                    return userDAO.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public List<User> searchUsers(String query) {
        return userDAO.searchUsers(query);
    }

    public List<User> findUsersWithComplementaryTags(Long userId) {
        return userDAO.findUsersWithComplementaryTags(userId);
    }

    public List<User> findUsersByTagCategory(String category) {
        return userDAO.findByTagCategoriesIn(Set.of(category.toLowerCase()));
    }

    // public List<User> findRecentlyActiveUsers(int limit) {
    //     List<User> users = userDAO.findRecentlyActiveUsers(limit);
    //     return users.stream().limit(limit).collect(Collectors.toList());
    // }

    public void deactivateUser(Long userId) {
        userDAO.findById(userId).ifPresent(user -> {
            user.setActive(false);
            userDAO.save(user);
        });
    }

    public List<User> findUsersByTags(String[] tagNames) {
        Set<String> tags = Set.of(tagNames);
        return userDAO.findByTagCategoriesIn(tags);
    }

    public List<User> findAllActiveUsers() {
        return userDAO.findByIsActiveTrue();
    }

    public UserStats getUserStats(Long userId) {
        UserStats stats = new UserStats();
        Optional<User> userOpt = userDAO.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            stats.setTotalPortfolioItems(user.getPortfolioItems() != null ? user.getPortfolioItems().size() : 0);
            // Count matches for this user (either as user1 or user2 in Match entity)
            //stats.setTotalMatches((int) userDAO.countMatchesForUser(userId));
            // TODO: Implement matches/likes feature on users on an ___ action
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