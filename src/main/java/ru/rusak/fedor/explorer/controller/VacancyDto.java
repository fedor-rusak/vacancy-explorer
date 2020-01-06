package ru.rusak.fedor.explorer.controller;

/**
 *  Data of this format is used for vacancy CRUD API.
 */
public class VacancyDto {

	private int id;
	private String creationTimestamp; //easier to understand for humans in text format rather than millis

	private String vacancyName;
	private String description;
	private String sourceName;
	private String correspondingId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreationTimestamp() {
		return creationTimestamp;
	}

    public void setCreationTimestamp(String creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getVacancyName() {
		return vacancyName;
	}

	public void setVacancyName(String vacancyName) {
		this.vacancyName = vacancyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getCorrespondingId() {
		return correspondingId;
	}

	public void setCorrespondingId(String correspondingId) {
		this.correspondingId = correspondingId;
	}

}
