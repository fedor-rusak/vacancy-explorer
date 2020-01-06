package ru.rusak.fedor.explorer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.Collections;

@RestController
public class VacancyController {

	private AtomicInteger idGenerator = new AtomicInteger(0);
	private Map<Integer, VacancyDto> internalStorage = Collections.synchronizedMap(new HashMap<>());

	private final SimpleDateFormat sdf;

	public VacancyController() {
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

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
		vacancyDto.setCreationTimestamp(sdf.format(new Date()));

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
