package ru.rusak.fedor.explorer.service;

import ru.rusak.fedor.explorer.data.VacancyDto;

import org.springframework.stereotype.Component;

@Component
public class SearchService {

	public void index(int key, VacancyDto vacancyDto) {
		//modify internal index to include new vacancy
	}

	public void unindex(int key) {
		//modify internal index to exclude indexed vacancy
	}

}
