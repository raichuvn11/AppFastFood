package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.RecentSearch;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {

    List<RecentSearch> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);

    @Modifying
    @Transactional
    void deleteByUserId(Long userId);

    void deleteByKeywordAndUserId(String keyword, Long userId);

    Optional<RecentSearch> findByKeywordAndUserId(String keyword, Long userId);
}