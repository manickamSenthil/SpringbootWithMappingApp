package com.crudapp.service;

import java.util.List;
import java.util.Map;

import com.crudapp.exception.ResourceNotFoundException;
import com.crudapp.model.Person;

public interface PersonService {
	
	public List<Person> getPersons();

	public Person insertPersons(Person person) throws ResourceNotFoundException;

	public Person updatePersons(Long personId, Person personDetails) throws ResourceNotFoundException;

	public Map<String, Boolean> deletePersons(Long personId) throws ResourceNotFoundException;

}
