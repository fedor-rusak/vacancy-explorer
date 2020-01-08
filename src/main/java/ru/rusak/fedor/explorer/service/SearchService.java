package ru.rusak.fedor.explorer.service;

import ru.rusak.fedor.explorer.data.VacancyDto;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class SearchService {

	private Map<String, Set<Integer>> wordToVacancyIndex = new ConcurrentHashMap<>();

	//practically cache for removal (unindex) operation
	private Map<Integer, Set<String>> vacancyToWordIndex = new ConcurrentHashMap<>();

	public void index(int key, VacancyDto vacancyDto) {
		String textForIndex = vacancyDto.getVacancyName() + " " + vacancyDto.getDescription();

		String[] words = prepareWords(textForIndex);

		vacancyToWordIndex.put(key, Collections.synchronizedSet(new HashSet<String>()));

		for (String word: words) {
			Set<Integer> vacancySet = wordToVacancyIndex.get(word);

			if (vacancySet == null) {
				vacancySet = Collections.synchronizedSet(new HashSet<Integer>());
				wordToVacancyIndex.put(word, vacancySet);
			}

			vacancySet.add(key);


			vacancyToWordIndex.get(key).add(word);
		}

		//modify internal index to include new vacancy
	}

	public void unindex(int key) {
		Set<String> wordsForVacancy = vacancyToWordIndex.get(key);

		for (String word: wordsForVacancy) {
			wordToVacancyIndex.get(word).remove(key);
		}
	}

	public Set<Integer> search(String query) {
		String[] queryWords = prepareWords(query);

		if (queryWords[0].equals("")) {
			//no actual words in query to look for
			return new HashSet<Integer>();
		}

		for (String queryWord: queryWords) {
			if (wordToVacancyIndex.get(queryWord) == null) {
				//current approach is AND logic for ALL words
				//so if some word is missing in our index. Empty result.
				return new HashSet<Integer>();
			}
		}

		Set<Integer> result = new HashSet<>(wordToVacancyIndex.get(queryWords[0]));

		for (int i = 1; i < queryWords.length; i++) {
			Set<Integer> setForIntersection = wordToVacancyIndex.get(queryWords[i]);

			result.retainAll(setForIntersection);
		}

		return result;
	}

	private static String[] prepareWords(String input) {
		return input
				.toLowerCase()
				.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", " ")
				.replaceAll(" +", " ")
				.trim()
				.split(" ");
	}

}
