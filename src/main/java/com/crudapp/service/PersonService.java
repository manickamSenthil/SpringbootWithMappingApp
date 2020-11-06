package com.crudapp.service;

import java.util.List;
import java.util.Map;

import com.crudapp.exception.ResourceNotFoundException;
import com.crudapp.model.Person;

public interface PersonService {
	public List<Person> getPresons();

	public Person insertPresons(Person person) throws ResourceNotFoundException;

	public Person updatePresons(Long personId, Person personDetails) throws ResourceNotFoundException;

	public Map<String, Boolean> deletePresons(Long personId) throws ResourceNotFoundException;

}
