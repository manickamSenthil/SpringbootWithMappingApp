package com.crudapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/***
 * 
 * @author Senthil
 *
 */
@Entity
@Table(name = "pets")
public class Pets {

	private long id;
	private String petName;
	private String petAge;
	private Person person;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pets() {

	}

	public Pets(String petName, String petAge) {
		this.petName = petName;
		this.petAge = petAge;

	}

	@Column(name = "pet_name", nullable = false)
	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	@Column(name = "pet_age", nullable = false)
	public String getPetAge() {
		return petAge;
	}

	public void setPetAge(String petAge) {
		this.petAge = petAge;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_id")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Pets [id=" + id + ", petName=" + petName + ", petAge=" + petAge + "]";
	}

}
