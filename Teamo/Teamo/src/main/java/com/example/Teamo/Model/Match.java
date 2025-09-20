package com.example.Teamo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.Teamo.Model.User;

/**
 * Match entity representing a successful connection between two users who have both
 * expressed interest in collaborating with each other.
 *
 */
@Entity
@Table(name = "matches")
public class Match {

    // Auto incrementing id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User can have multiple matches, Lazy loading so data is loaded only when accessed
    // User1 is the user who created the match
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    // User2 is the user who received the match
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    // The time the match was created
    @Column(nullable = false)
    private LocalDateTime matchedAt;

    // The time the match was last interacted with
    @Column
    private LocalDateTime lastInteraction;

    // Whether the match is active or not
    @Column(nullable = false)
    private boolean isActive = true;

    // Constructors
    public Match() {}

    public Match(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.matchedAt = LocalDateTime.now();
        this.lastInteraction = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public LocalDateTime getMatchedAt() {
        return matchedAt;
    }

    public void setMatchedAt(LocalDateTime matchedAt) {
        this.matchedAt = matchedAt;
    }

    public LocalDateTime getLastInteraction() {
        return lastInteraction;
    }

    public void setLastInteraction(LocalDateTime lastInteraction) {
        this.lastInteraction = lastInteraction;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Get the other user in this match (not the provided user)
     * @param currentUser the current user
     * @return the other user in the match
     */
    public User getOtherUser(User currentUser) {
        if (user1.equals(currentUser)) {
            return user2;
        } else if (user2.equals(currentUser)) {
            return user1;
        }
        throw new IllegalArgumentException("User is not part of this match");
    }

    /**
     * Check if this match involves the given user
     * @param user the user to check
     * @return true if the user is part of this match
     */
    public boolean involvesUser(User user) {
        return user1.equals(user) || user2.equals(user);
    }

    /**
     * Update the last interaction timestamp
     */
    public void updateLastInteraction() {
        this.lastInteraction = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        // Two matches are equal if they involve the same two users (regardless of order)
        return (user1.equals(match.user1) && user2.equals(match.user2)) ||
               (user1.equals(match.user2) && user2.equals(match.user1));
    }

    @Override
    public int hashCode() {
        // Hash code should be the same regardless of user order
        String hashString = user1.getId() < user2.getId()
            ? user1.getId() + "_" + user2.getId()
            : user2.getId() + "_" + user1.getId();
        return hashString.hashCode();
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", user1=" + user1.getFullName() +
                ", user2=" + user2.getFullName() +
                ", matchedAt=" + matchedAt +
                '}';
    }
}
