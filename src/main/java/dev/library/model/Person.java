package dev.library.model;

import java.util.StringJoiner;

import dev.library.model.component.AdditionalData;
import dev.library.model.component.BasicData;

/**
 * Класс для описания информации о человеке
 * @version 1.0
 */
public class Person implements Cloneable {
	/* идентификатор */
	private long id;
	/* основная информация */
	private BasicData basicData;
	/* дополнительная информация */
	private AdditionalData additionalData;

	/**
	 * Конструктор по умолчанию для создания нового объекта типа Person
	 */
	public Person() {}
	/**
	 * Конструктор для создания нового объекта типа Person
	 * @param basicData - основная информация
	 * @param additionalData - дополнительная информация
	 * @see #Person(long, BasicData, AdditionalData)
	 */
	public Person(BasicData basicData, AdditionalData additionalData) {
		this(0, basicData, additionalData);
	}
	/**
	 * Конструктор для создания нового объекта типа Person
	 * @param id - идентификатор
	 * @param basicData - основная информация
	 * @param additionalData - дополнительная информация
	 */
	public Person(long id, BasicData basicData, AdditionalData additionalData) {
		this.id = id;
		this.basicData = basicData;
		this.additionalData = additionalData;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BasicData getBasicData() {
		return basicData;
	}
	public void setBasicData(BasicData basicData) {
		this.basicData = basicData;
	}
	public AdditionalData getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(AdditionalData additionalData) {
		this.additionalData = additionalData;
	}
	
	@Override
	public Person clone() throws CloneNotSupportedException {
		Person person = (Person) super.clone();
		person.basicData = (BasicData) basicData.clone();
		person.additionalData = (AdditionalData) additionalData.clone();
		return person;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return (id == person.id) &&
                (basicData == person.basicData || (basicData != null && basicData.equals(person.basicData))) && 
                (additionalData == person.additionalData || (additionalData != null && additionalData.equals(person.additionalData)));
    }
	
	@Override
    public int hashCode() {
        final int CODE = (int)(31 * 1 + id + (basicData == null ? 0 : basicData.hashCode()) + 
        		(additionalData == null ? 0 : additionalData.hashCode()));
        return CODE;
        		
	}
	
	@Override
	public String toString() {
		final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
        String objectToString = joiner.add("id=" + id).add("basicData=" + basicData)
        		.add("additionalData=" + additionalData).toString();
        return objectToString;
	}
}
