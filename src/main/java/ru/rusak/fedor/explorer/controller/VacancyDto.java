package ru.rusak.fedor.explorer.controller;

/**
 *  Data of this format is used for vacancy CRUD API.
 */
public class VacancyDto {

	private int id;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
