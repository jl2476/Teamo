package com.example.Teamo.Controller;

import com.example.Teamo.Model.User;
import com.example.Teamo.Model.Tag;
import com.example.Teamo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        return user.map(u -> ResponseEntity.ok(u))
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        // TODO: Implement authentication and get current user
        // For now, return a placeholder - this should be replaced with proper authentication
        User placeholderUser = new User();
        placeholderUser.setId(1L);
        placeholderUser.setEmail("placeholder@example.com");
        placeholderUser.setFirstName("Placeholder");
        placeholderUser.setLastName("User");
        placeholderUser.setBio("This is a placeholder user");
        return ResponseEntity.ok(placeholderUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}/complementary")
    public ResponseEntity<List<User>> getComplementaryUsers(@PathVariable Long userId) {
        List<User> users = userService.findUsersWithComplementaryTags(userId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/tags/category/{category}")
    public ResponseEntity<List<User>> getUsersByTagCategory(@PathVariable String category) {
        List<User> users = userService.findUsersByTagCategory(category);
        return ResponseEntity.ok(users);
    }

    // @GetMapping("/recent")
    // public ResponseEntity<List<User>> getRecentlyActiveUsers(@RequestParam(defaultValue = "20") int limit) {
    //     List<User> users = userService.findRecentlyActiveUsers(limit);
    //     return ResponseEntity.ok(users);
    // }                                                                                                                           

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        try {
            userService.deactivateUser(userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/stats")
    public ResponseEntity<UserStats> getUserStats(@PathVariable Long userId) {
        // TODO: Implement user statistics
        UserStats stats = new UserStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<User>> getUsersByTags(@RequestParam String tagNames) {
        String[] tags = tagNames.split(",");
        List<User> users = userService.findUsersByTags(tags);
        return ResponseEntity.ok(users);
    }

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