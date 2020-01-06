package ru.rusak.fedor.explorer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VacancyController {

	private VacancyRepository vacancyRepository;

	public VacancyController(VacancyRepository vacancyRepository) {
		this.vacancyRepository = vacancyRepository;
	}

	@GetMapping(path = "/vacancies/{id}", produces = "application/json")
	public ResponseEntity<VacancyDto> getById(@PathVariable int id) {
		if (vacancyRepository.contains(id)) {
			return new ResponseEntity(vacancyRepository.get(id), HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/vacancies", consumes = "application/json")
	public ResponseEntity add(@RequestBody VacancyDto vacancyDto) {
		if (isEmpty(vacancyDto.getVacancyName())
		    || isEmpty(vacancyDto.getDescription())
		    || isEmpty(vacancyDto.getSourceName())
		    || isEmpty(vacancyDto.getCorrespondingId())) {
			return new ResponseEntity("Fields vacancyName, description, sourceName and correspondingId are mandatory.", HttpStatus.BAD_REQUEST);
		}

		int savedId = vacancyRepository.add(vacancyDto);

		return new ResponseEntity(savedId, HttpStatus.OK);
	}

	@DeleteMapping(path = "/vacancies/{id}")
	public ResponseEntity deleteById(@PathVariable int id) {
		if (vacancyRepository.contains(id)) {
			vacancyRepository.remove(id);

			return new ResponseEntity(HttpStatus.OK);
		}

		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	public static boolean isEmpty(String value) {
		return value == null || value.trim().equals("");
	}

}
