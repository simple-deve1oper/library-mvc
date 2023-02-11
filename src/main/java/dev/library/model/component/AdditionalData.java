package dev.library.model.component;

import java.util.StringJoiner;

/**
 * Класс для описания дополнительной информации о человеке
 * @version 1.0
 */
public class AdditionalData implements Cloneable {
	/* email */
	private String email;
	/* номер телефона */
	private String phoneNumber;

	/**
	 * Конструктор по умолчанию для создания нового объекта типа AdditionalData
	 */
	public AdditionalData() {}
	/**
	 * Конструктор для создания нового объекта типа AdditionalData
	 * @param email - email
	 * @param phoneNumber - номер телефона
	 * @see #AdditionalData(String)
	 */
	public AdditionalData(String email, String phoneNumber) {
		this(phoneNumber);
		this.email = email;
	}
	/**
	 * Конструктор для создания нового объекта типа AdditionalData
	 * @param phoneNumber - номер телефона
	 */
	public AdditionalData(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public AdditionalData clone() throws CloneNotSupportedException {
		AdditionalData data = (AdditionalData) super.clone();
		return data;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdditionalData data = (AdditionalData) obj;
        return (email == data.email || (email != null && email.equals(data.email))) && 
                (phoneNumber == data.phoneNumber || (phoneNumber != null && phoneNumber.equals(data.phoneNumber)));
    }
	
	@Override
    public int hashCode() {
        final int CODE = (int)(31 * 1 + (email == null ? 0 : email.hashCode()) + 
        		(phoneNumber == null ? 0 : phoneNumber.hashCode()));
        return CODE;
        		
	}
	
	@Override
	public String toString() {
		final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
        String objectToString = joiner.add("email=" + email).add("phoneNumber=" + phoneNumber).toString();
        return objectToString;
	}
}
