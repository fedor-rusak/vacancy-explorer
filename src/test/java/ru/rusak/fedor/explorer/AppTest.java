package ru.rusak.fedor.explorer;

import ru.rusak.fedor.explorer.data.VacancyDto;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
public class AppTest {

	public static final String VACANCY_NAME = "Test Vacancy Name";
	public static final String DESCRIPTION = "Test Description";
	public static final String SOURCE_NAME = "Test Source Name";
	public static final String CORRESPONDING_ID = "Test Corresponding Id";

	@Autowired
	private MockMvc mvc;

	@Test
	public void checkWholeApp() throws Exception {
		//can't delete or read non-existing vacancy
		mvc.perform(delete("/vacancies/1"))
				.andExpect(status().isNotFound());

		mvc.perform(get("/vacancies/1"))
				.andExpect(status().isNotFound());

		//adding is validated
		mvc.perform(post("/vacancies")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"description\":\"Simple data\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Fields vacancyName, description, sourceName and correspondingId are mandatory."));

		//adding allows reading, searching and deleting
		VacancyDto dto = new VacancyDto();
		dto.setVacancyName(VACANCY_NAME);
		dto.setDescription(DESCRIPTION);
		dto.setSourceName(SOURCE_NAME);
		dto.setCorrespondingId(CORRESPONDING_ID);

		String postBody = new ObjectMapper().writeValueAsString(dto);

		mvc.perform(post("/vacancies")
				.contentType(MediaType.APPLICATION_JSON)
				.content(postBody))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));


		mvc.perform(get("/vacancies/1"))
				.andExpect(jsonPath("id").value("1"))
				.andExpect(jsonPath("creationTimestamp").exists())
				.andExpect(jsonPath("vacancyName").value(VACANCY_NAME))
				.andExpect(jsonPath("description").value(DESCRIPTION))
				.andExpect(jsonPath("sourceName").value(SOURCE_NAME))
				.andExpect(jsonPath("correspondingId").value(CORRESPONDING_ID))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, containsString(MediaType.APPLICATION_JSON.toString())))
				.andExpect(status().isOk());

		mvc.perform(get("/search?query=tEst"))
				.andExpect(content().string("[1]"))		
				.andExpect(status().isOk());

		mvc.perform(delete("/vacancies/1"))
				.andExpect(status().isOk());

		//after deleting can't delete, read or search for the vacancy
		mvc.perform(delete("/vacancies/1"))
				.andExpect(status().isNotFound());
 
		mvc.perform(get("/vacancies/1"))
				.andExpect(status().isNotFound());

		//it is (200) OK to get empty result when no vacancies match to the query
		mvc.perform(get("/search?query=tEst"))
			.andExpect(content().string("[]"))		
			.andExpect(status().isOk());
	}

}
