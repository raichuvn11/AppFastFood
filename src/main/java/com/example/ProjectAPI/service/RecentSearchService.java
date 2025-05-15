package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.RecentSearch;
import com.example.ProjectAPI.repository.RecentSearchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecentSearchService implements IRecentSearchService {

    @Autowired
    private RecentSearchRepository repository;

    @Override
    public void saveSearch( String keyword, Long userId) {
        // Kiểm tra đã có keyword này chưa
        Optional<RecentSearch> existing = repository.findByKeywordAndUserId(keyword, userId);
        if (existing.isPresent()) {
            // Nếu đã có, cập nhật thời gian
            RecentSearch recent = existing.get();
            recent.setCreatedAt(LocalDateTime.now());
            repository.save(recent);
        } else {
            // Nếu chưa có, tạo mới
            RecentSearch newSearch = new RecentSearch();
            newSearch.setUserId(userId);
            newSearch.setKeyword(keyword);
            newSearch.setCreatedAt(LocalDateTime.now());
            repository.save(newSearch);
        }
    }

    @Override
    public List<RecentSearch> getRecentSearches(Long userId) {
        return repository.findTop5ByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional
    public void deleteAllByUser(Long userId) {
        repository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteSearchByKeywordAndUser(String keyword, Long userId) {
        repository.deleteByKeywordAndUserId(keyword, userId);
    }
}