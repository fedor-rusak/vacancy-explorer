package ru.rusak.fedor.explorer.controller;

import ru.rusak.fedor.explorer.data.VacancyDto;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.Collections;

@Component
public class VacancyRepository {

	private AtomicInteger idGenerator = new AtomicInteger(0);
	private Map<Integer, VacancyDto> internalStorage = Collections.synchronizedMap(new HashMap<>());

	private final SimpleDateFormat sdf;

	public VacancyRepository() {
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public boolean contains(int key) {
		return internalStorage.containsKey(key);
	}

	public VacancyDto get(int key) {
		return internalStorage.get(key);
	}

	public int add(VacancyDto newVacancy) {
		int savedId = idGenerator.addAndGet(1);
		newVacancy.setId(savedId);
		newVacancy.setCreationTimestamp(sdf.format(new Date()));

		internalStorage.put(savedId, newVacancy);

		return savedId;
	}

	public void remove(int key) {
		internalStorage.remove(key);
	}

}