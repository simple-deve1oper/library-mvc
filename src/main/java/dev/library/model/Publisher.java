package dev.library.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

/**
 * Класс для описания информации об издательстве
 */
public class Publisher implements Cloneable {
	/* идентификатор */
	private long id;
	/* наименование */
	private String name;

	/**
	 * Конструктор по умолчанию для создания нового объекта типа Publisher
	 * @param id - идентификатор
	 * @param name - наименование
	 */
	@JsonCreator
	public Publisher(
			@JsonProperty("id") long id,
			@JsonProperty("name") String name
	) {
		this.id = id;
		this.name = name;
	}
	/**
	 * Конструктор по умолчанию для создания нового объекта типа Publisher
	 */
	public Publisher() {}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Publisher clone() throws CloneNotSupportedException {
		Publisher publisher = (Publisher) super.clone();
		return publisher;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Publisher publisher = (Publisher) obj;
		return (id == publisher.id) && (name == publisher.name || (name != null && name.equals(publisher.name)));
	}

	@Override
	public int hashCode() {
		final int CODE = (int)(31 * 1 + id + (name == null ? 0 : name.hashCode()));
		return CODE;
	}

	@Override
	public String toString() {
		final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
		String objectToString = joiner.add("id=" + id).add("name=" + name).toString();
		return objectToString;
	}
}
