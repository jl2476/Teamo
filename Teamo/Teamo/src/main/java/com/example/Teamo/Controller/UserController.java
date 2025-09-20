package com.example.Teamo.controller;

import com.example.Teamo.model.User;
import com.example.Teamo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for User-related operations.
 * Handles user profile management, search, and discovery features.
 *
 * @author Teamo Development Team
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get user profile by ID
     * @param userId the user ID
     * @return user profile
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        return user.map(u -> ResponseEntity.ok(u))
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get current authenticated user's profile
     * @return current user's profile
     */
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        // TODO: Implement authentication and get current user
        return ResponseEntity.ok(new User());
    }

    /**
     * Update user profile
     * @param userId the user ID
     * @param user the updated user data
     * @return updated user
     */
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Search users by name, bio, or location
     * @param query the search query
     * @return list of matching users
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users with complementary skills
     * @param userId the user ID to find complements for
     * @return list of users with complementary skills
     */
    @GetMapping("/{userId}/complementary")
    public ResponseEntity<List<User>> getComplementaryUsers(@PathVariable Long userId) {
        List<User> users = userService.findUsersWithComplementarySkills(userId);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by skill category
     * @param category the skill category
     * @return list of users with that skill category
     */
    @GetMapping("/skills/category/{category}")
    public ResponseEntity<List<User>> getUsersBySkillCategory(@PathVariable String category) {
        List<User> users = userService.findUsersBySkillCategory(category);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users by location
     * @param location the location
     * @return list of users in that location
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<List<User>> getUsersByLocation(@PathVariable String location) {
        List<User> users = userService.findUsersByLocation(location);
        return ResponseEntity.ok(users);
    }

    /**
     * Get recently active users
     * @param limit maximum number of users to return
     * @return list of recently active users
     */
    @GetMapping("/recent")
    public ResponseEntity<List<User>> getRecentlyActiveUsers(@RequestParam(defaultValue = "20") int limit) {
        List<User> users = userService.findRecentlyActiveUsers(limit);
        return ResponseEntity.ok(users);
    }

    /**
     * Get users who haven't been swiped on by the current user
     * @param userId the current user ID
     * @return list of unswiped users
     */
    @GetMapping("/{userId}/discover")
    public ResponseEntity<List<User>> getDiscoverableUsers(@PathVariable Long userId) {
        List<User> users = userService.findUnswipedUsers(userId);
        return ResponseEntity.ok(users);
    }

    /**
     * Deactivate user account
     * @param userId the user ID
     * @return success response
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        try {
            userService.deactivateUser(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get user statistics
     * @param userId the user ID
     * @return user statistics
     */
    @GetMapping("/{userId}/stats")
    public ResponseEntity<UserStats> getUserStats(@PathVariable Long userId) {
        // TODO: Implement user statistics
        UserStats stats = new UserStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get users by multiple skill names
     * @param skillNames comma-separated list of skill names
     * @return list of users with those skills
     */
    @GetMapping("/skills")
    public ResponseEntity<List<User>> getUsersBySkills(@RequestParam String skillNames) {
        String[] skills = skillNames.split(",");
        List<User> users = userService.findUsersBySkills(skills);
        return ResponseEntity.ok(users);
    }

    /**
     * Simple DTO for user statistics
     */
    public static class UserStats {
        private int totalPortfolioItems;
        private int totalMatches;
        private int totalLikesReceived;

        // Getters and setters
        public int getTotalPortfolioItems() { return totalPortfolioItems; }
        public void setTotalPortfolioItems(int totalPortfolioItems) { this.totalPortfolioItems = totalPortfolioItems; }

        public int getTotalMatches() { return totalMatches; }
        public void setTotalMatches(int totalMatches) { this.totalMatches = totalMatches; }

        public int getTotalLikesReceived() { return totalLikesReceived; }
        public void setTotalLikesReceived(int totalLikesReceived) { this.totalLikesReceived = totalLikesReceived; }
    }
}