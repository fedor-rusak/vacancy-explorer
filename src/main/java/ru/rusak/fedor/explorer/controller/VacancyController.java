package ru.rusak.fedor.explorer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<VacancyDto> getById(@PathVariable int id) {
		if (internalStorage.containsKey(id)) {
			return new ResponseEntity(internalStorage.get(id), HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/vacancies", consumes = "application/json")
	public int add(@RequestBody VacancyDto vacancyDto) {
		int savedId = idGenerator.addAndGet(1);
		vacancyDto.setId(savedId);

		internalStorage.put(savedId, vacancyDto);

		return savedId;
	}

	@DeleteMapping(path = "/vacancies/{id}")
	public ResponseEntity deleteById(@PathVariable int id) {
		if (internalStorage.containsKey(id)) {
			internalStorage.remove(id);

			return new ResponseEntity(HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

}
