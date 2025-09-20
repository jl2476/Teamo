package com.example.Teamo.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * SwipeAction entity representing a user's swipe action on another user's profile.
 * This tracks both likes and passes to determine potential matches.
 *
 * @author Teamo Development Team
 */
@Entity
@Table(name = "swipe_actions")
public class SwipeAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user performing the swipe

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id", nullable = false)
    private User targetUser; // The user being swiped on

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SwipeType swipeType; // LIKE or PASS

    @Column(nullable = false)
    private LocalDateTime swipedAt;

    // Constructors
    public SwipeAction() {}

    public SwipeAction(User user, User targetUser, SwipeType swipeType) {
        this.user = user;
        this.targetUser = targetUser;
        this.swipeType = swipeType;
        this.swipedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public SwipeType getSwipeType() {
        return swipeType;
    }

    public void setSwipeType(SwipeType swipeType) {
        this.swipeType = swipeType;
    }

    public LocalDateTime getSwipedAt() {
        return swipedAt;
    }

    public void setSwipedAt(LocalDateTime swipedAt) {
        this.swipedAt = swipedAt;
    }

    /**
     * Enum representing the type of swipe action
     */
    public enum SwipeType {
        LIKE("like"),
        PASS("pass");

        private final String value;

        SwipeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static SwipeType fromValue(String value) {
            for (SwipeType type : SwipeType.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown swipe type: " + value);
        }
    }

    /**
     * Check if this is a like action
     * @return true if this swipe is a like
     */
    public boolean isLike() {
        return swipeType == SwipeType.LIKE;
    }

    /**
     * Check if this is a pass action
     * @return true if this swipe is a pass
     */
    public boolean isPass() {
        return swipeType == SwipeType.PASS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwipeAction that = (SwipeAction) o;
        return user.equals(that.user) && targetUser.equals(that.targetUser);
    }

    @Override
    public int hashCode() {
        return user.hashCode() + targetUser.hashCode();
    }

    @Override
    public String toString() {
        return "SwipeAction{" +
                "id=" + id +
                ", user=" + user.getFullName() +
                ", targetUser=" + targetUser.getFullName() +
                ", swipeType=" + swipeType +
                ", swipedAt=" + swipedAt +
                '}';
    }
}
