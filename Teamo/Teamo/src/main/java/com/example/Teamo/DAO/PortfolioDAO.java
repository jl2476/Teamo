package com.example.Teamo.DAO;

import com.example.Teamo.Model.PortfolioItem;
import com.example.Teamo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioDAO extends JpaRepository<PortfolioItem, Long> {

    List<PortfolioItem> findByUser(User user);

    List<PortfolioItem> findByUserId(Long userId);

    List<PortfolioItem> findByUserAndIsPublicTrue(User user);

    List<PortfolioItem> findByUserIdAndIsPublicTrue(Long userId);

    List<PortfolioItem> findByMediaType(String mediaType);

    List<PortfolioItem> findByUserAndMediaType(User user, String mediaType);

    @Query("SELECT p FROM PortfolioItem p WHERE LOWER(p.tags) LIKE LOWER(CONCAT('%', :tag, '%'))")
    List<PortfolioItem> findByTagsContaining(@Param("tag") String tag);

    @Query("SELECT p FROM PortfolioItem p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :titleTerm, '%'))")
    List<PortfolioItem> findByTitleContaining(@Param("titleTerm") String titleTerm);

    @Query("SELECT p FROM PortfolioItem p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', :descriptionTerm, '%'))")
    List<PortfolioItem> findByDescriptionContaining(@Param("descriptionTerm") String descriptionTerm);

    @Query("SELECT p FROM PortfolioItem p WHERE p.isPublic = true AND LOWER(p.title) LIKE LOWER(CONCAT('%', :titleTerm, '%'))")
    List<PortfolioItem> findPublicByTitleContaining(@Param("titleTerm") String titleTerm);

    List<PortfolioItem> findByMediaTypeAndIsPublicTrue(String mediaType);

    @Query("SELECT p FROM PortfolioItem p ORDER BY p.createdAt DESC")
    List<PortfolioItem> findAllOrderByCreatedAtDesc();

    @Query("SELECT p FROM PortfolioItem p WHERE p.isPublic = true ORDER BY p.createdAt DESC")
    List<PortfolioItem> findPublicOrderByCreatedAtDesc();

    @Query("SELECT p FROM PortfolioItem p WHERE p.user.id = :userId ORDER BY p.orderIndex ASC, p.createdAt DESC")
    List<PortfolioItem> findByUserIdOrderByOrderIndex(@Param("userId") Long userId);

    @Query("SELECT p FROM PortfolioItem p WHERE p.isPublic = true ORDER BY p.createdAt DESC")
    List<PortfolioItem> findRecentPublicPortfolioItems(@Param("limit") int limit);

    @Query("SELECT p FROM PortfolioItem p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<PortfolioItem> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);

    long countByUserId(Long userId);

    long countByUserIdAndIsPublicTrue(Long userId);

    void deleteByUser(User user);

    void deleteByUserId(Long userId);
}
