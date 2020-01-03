package ru.rusak.fedor.explorer;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
public class AppTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void checkVacancyController() throws Exception {
		mvc.perform(post("/vacancies")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"description\":\"Simple data\"}"))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		mvc.perform(get("/vacancies/1"))
				.andExpect(content().string("{\"id\":1,\"description\":\"Simple data\"}"))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, containsString(MediaType.APPLICATION_JSON.toString())))
				.andExpect(status().isOk());
	}

}
