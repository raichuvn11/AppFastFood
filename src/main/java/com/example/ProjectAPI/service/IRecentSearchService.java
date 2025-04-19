package com.example.ProjectAPI.service;

import com.example.ProjectAPI.model.RecentSearch;

import java.util.List;

public interface IRecentSearchService {

    void saveSearch(String keyword, Long userId);

    List<RecentSearch> getRecentSearches(Long userId);

    void deleteAllByUser(Long userId);

    void deleteSearchByKeywordAndUser(String keyword, Long userId);

}