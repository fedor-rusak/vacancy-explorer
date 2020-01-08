package ru.rusak.fedor.explorer.controller;

import ru.rusak.fedor.explorer.service.SearchService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SearchController {

	private SearchService searchService;

	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@GetMapping(path = "/search")
	public ResponseEntity<Set<Integer>> getById(@RequestParam String query) {
		return new ResponseEntity(searchService.search(query), HttpStatus.OK);
	}

}
