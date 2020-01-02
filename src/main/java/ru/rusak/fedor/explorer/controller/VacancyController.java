package ru.rusak.fedor.explorer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

@RestController
public class VacancyController {

	private AtomicInteger idGenerator = new AtomicInteger(0);
	private Map<Integer, VacancyDto> internalStorage = Collections.synchronizedMap(new HashMap<>());

	@GetMapping(path = "/vacancies/{id}", produces = "application/json")
	public VacancyDto getById(@PathVariable int id) {
		return internalStorage.get(id);
	}

	@PostMapping(path = "/vacancies", consumes = "application/json")
	public int add(@RequestBody VacancyDto vacancyDto) {
		int savedId = idGenerator.addAndGet(1);
		vacancyDto.setId(savedId);

		internalStorage.put(savedId, vacancyDto);

		return savedId;
	}

}
