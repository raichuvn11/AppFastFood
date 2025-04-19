package com.example.ProjectAPI.repository;

import com.example.ProjectAPI.model.RecentSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecentSearchRepository extends JpaRepository<RecentSearch, Long> {

    List<RecentSearch> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);

    void deleteByUserId(Long userId);

    void deleteByKeywordAndUserId(String keyword, Long userId);

    Optional<RecentSearch> findByKeywordAndUserId(String keyword, Long userId);
}