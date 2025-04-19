package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.request.RecentSearchRequest;
import com.example.ProjectAPI.model.RecentSearch;
import com.example.ProjectAPI.service.IRecentSearchService;
import com.example.ProjectAPI.service.RecentSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recent-searches")
public class RecentSearchController {

    @Autowired
    private IRecentSearchService searchService;

    @PostMapping("/save")
    public ResponseEntity<?> saveSearch(@RequestBody RecentSearchRequest request) {
        searchService.saveSearch(request.getKeyword(), request.getUserId());
        return ResponseEntity.ok("Saved recent search");
    }

    @PostMapping("/list")
    public ResponseEntity<List<RecentSearch>> getRecentSearches(@RequestBody RecentSearchRequest request) {
        List<RecentSearch> searches = searchService.getRecentSearches(request.getUserId());
        return ResponseEntity.ok(searches);
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clearRecentSearches(@RequestBody RecentSearchRequest request) {
        searchService.deleteAllByUser(request.getUserId());
        return ResponseEntity.ok("Cleared recent searches");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteRecentSearch(@RequestBody RecentSearchRequest request) {
        searchService.deleteSearchByKeywordAndUser(request.getKeyword(), request.getUserId());
        return ResponseEntity.ok("Deleted recent search: " + request.getKeyword());
    }
}
