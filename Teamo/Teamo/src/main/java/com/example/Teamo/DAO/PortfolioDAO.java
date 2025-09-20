package com.example.Teamo.DAO;

import com.example.Teamo.Model.PortfolioItem;
import com.example.Teamo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for PortfolioItem entities.
 * Provides methods for querying and managing portfolio items in the database.
 *
 * @author Teamo Development Team
 */
@Repository
public interface PortfolioDAO extends JpaRepository<PortfolioItem, Long> {

    /**
     * Find all portfolio items for a specific user
     * @param user the user
     * @return list of portfolio items
     */
    List<PortfolioItem> findByUser(User user);

    /**
     * Find all portfolio items for a specific user ID
     * @param userId the user ID
     * @return list of portfolio items
     */
    List<PortfolioItem> findByUserId(Long userId);

    /**
     * Find all public portfolio items for a specific user
     * @param user the user
     * @return list of public portfolio items
     */
    List<PortfolioItem> findByUserAndIsPublicTrue(User user);

    /**
     * Find all public portfolio items for a specific user ID
     * @param userId the user ID
     * @return list of public portfolio items
     */
    List<PortfolioItem> findByUserIdAndIsPublicTrue(Long userId);

    /**
     * Find portfolio items by media type
     * @param mediaType the media type
     * @return list of portfolio items with that media type
     */
    List<PortfolioItem> findByMediaType(String mediaType);

    /**
     * Find portfolio items by media type for a specific user
     * @param user the user
     * @param mediaType the media type
     * @return list of portfolio items
     */
    List<PortfolioItem> findByUserAndMediaType(User user, String mediaType);

    /**
     * Find portfolio items by tags containing a specific term
     * @param tag the tag to search for
     * @return list of portfolio items containing the tag
     */
    @Query("SELECT p FROM PortfolioItem p WHERE LOWER(p.tags) LIKE LOWER(CONCAT('%', :tag, '%'))")
    List<PortfolioItem> findByTagsContaining(@Param("tag") String tag);

    /**
     * Find portfolio items with titles containing a search term
     * @param titleTerm the search term for titles
     * @return list of matching portfolio items
     */
    @Query("SELECT p FROM PortfolioItem p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :titleTerm, '%'))")
    List<PortfolioItem> findByTitleContaining(@Param("titleTerm") String titleTerm);

    /**
     * Find portfolio items with descriptions containing a search term
     * @param descriptionTerm the search term for descriptions
     * @return list of matching portfolio items
     */
    @Query("SELECT p FROM PortfolioItem p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :descriptionTerm, '%'))")
    List<PortfolioItem> findByDescriptionContaining(@Param("descriptionTerm") String descriptionTerm);

    /**
     * Find public portfolio items with titles containing a search term
     * @param titleTerm the search term for titles
     * @return list of matching public portfolio items
     */
    @Query("SELECT p FROM PortfolioItem p WHERE p.isPublic = true AND LOWER(p.title) LIKE LOWER(CONCAT('%', :titleTerm, '%'))")
    List<PortfolioItem> findPublicByTitleContaining(@Param("titleTerm") String titleTerm);

    /**
     * Find public portfolio items by media type
     * @param mediaType the media type
     * @return list of public portfolio items with that media type
     */
    List<PortfolioItem> findByMediaTypeAndIsPublicTrue(String mediaType);

    /**
     * Find portfolio items ordered by creation date (newest first)
     * @return list of portfolio items ordered by creation date
     */
    @Query("SELECT p FROM PortfolioItem p ORDER BY p.createdAt DESC")
    List<PortfolioItem> findAllOrderByCreatedAtDesc();

    /**
     * Find public portfolio items ordered by creation date (newest first)
     * @return list of public portfolio items ordered by creation date
     */
    @Query("SELECT p FROM PortfolioItem p WHERE p.isPublic = true ORDER BY p.createdAt DESC")
    List<PortfolioItem> findPublicOrderByCreatedAtDesc();

    /**
     * Find portfolio items for a user ordered by custom order index
     * @param userId the user ID
     * @return list of portfolio items ordered by order index
     */
    @Query("SELECT p FROM PortfolioItem p WHERE p.user.id = :userId ORDER BY p.orderIndex ASC, p.createdAt DESC")
    List<PortfolioItem> findByUserIdOrderByOrderIndex(@Param("userId") Long userId);

    /**
     * Find recent portfolio items from all users (for discovery feed)
     * @param limit the maximum number of items to return
     * @return list of recent public portfolio items
     */
    @Query("SELECT p FROM PortfolioItem p WHERE p.isPublic = true ORDER BY p.createdAt DESC")
    List<PortfolioItem> findRecentPublicPortfolioItems(@Param("limit") int limit);

    /**
     * Find portfolio items by user and order them by creation date
     * @param userId the user ID
     * @return list of portfolio items ordered by creation date
     */
    @Query("SELECT p FROM PortfolioItem p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<PortfolioItem> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);

    /**
     * Count total portfolio items for a user
     * @param userId the user ID
     * @return count of portfolio items
     */
    long countByUserId(Long userId);

    /**
     * Count public portfolio items for a user
     * @param userId the user ID
     * @return count of public portfolio items
     */
    long countByUserIdAndIsPublicTrue(Long userId);

    /**
     * Delete all portfolio items for a user
     * @param user the user
     */
    void deleteByUser(User user);

    /**
     * Delete all portfolio items for a user by ID
     * @param userId the user ID
     */
    void deleteByUserId(Long userId);
}
