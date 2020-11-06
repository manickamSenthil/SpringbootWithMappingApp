package com.crudapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.crudapp.exception.ResourceNotFoundException;
import com.crudapp.model.Person;
import com.crudapp.model.Pet;
import com.crudapp.repository.PersonRepository;
import com.crudapp.repository.PetRepository;

/****
 * 
 * @author Senthil
 *
 */
@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PetRepository petsRepository;

	@Autowired
	private PetServiceImpl petsServiceImpl;

	@Override
	public List<Person> getPersons() {
		return personRepository.findAll();
	}




	public List<Person> getPersons(String fName, String lName) throws ResourceNotFoundException {
		
		if (!fName.isEmpty() && lName.isEmpty()) {
			return personRepository.findByFirstNameIgnoreCase(fName);
			
		} else if (fName.isEmpty() && !lName.isEmpty()) {
			return personRepository.findByLastNameIgnoreCase(lName);
		
		}else if(!fName.isEmpty() && !lName.isEmpty()) {
			return personRepository.findByFirstNameAndLastNameIgnoreCase(fName, lName);
			
		}
		return null;
	}

	/****
	 * insert person
	 */
	@Override
	public Person insertPersons(Person person) throws ResourceNotFoundException {
		Person insertPerson = personRepository.save(person);
		return insertPerson;
	}

	/****
	 * update person details
	 */
	@Override
	public Person updatePersons(Long personId, Person personDetails) throws ResourceNotFoundException {
		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.getAuthorities().stream().count() == 1
				&& !person.getAddress().equals(personDetails.getAddress())) {
			throw new ResourceNotFoundException("User role does not have access to update the address");
		}

		person.setDob(personDetails.getDob());
		person.setLastName(personDetails.getLastName());
		person.setFirstName(personDetails.getFirstName());
		person.setAddress(personDetails.getAddress());
		Person updatedPersonDetail = personRepository.save(person);
		return updatedPersonDetail;
	}

	/***
	 * delete person
	 */
	@Override
	public Map<String, Boolean> deletePersons(Long personId) throws ResourceNotFoundException {
		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));

		List<Pet> pet = petsRepository.findByPersonId(personId);

		if (!pet.isEmpty()) {
			pet.stream().forEach(f -> {
				try {
					petsServiceImpl.deletePet(f.getId());
				} catch (ResourceNotFoundException e) {
					e.printStackTrace();
				}
			});
		}

		personRepository.delete(person);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	/***
	 * get person details using id
	 * 
	 * @param personId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Person getPersonsByid(Long personId) throws ResourceNotFoundException {
		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + personId));
		return person;
	}

	/***
	 * get person details using firstname or lastname
	 * 
	 * @param name
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<Person> findByPersonFnameOrLname(String name) throws ResourceNotFoundException {
		List<Person> byFirstName = personRepository.findByFirstNameIgnoreCase(name);
		List<Person> byLastName = personRepository.findByLastNameIgnoreCase(name);
		byFirstName.addAll(byLastName);
		return byFirstName;
	}

	/***
	 * get person details using Firstname and lastname
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<Person> findByPersonFnameAndLname(String firstName, String lastName) throws ResourceNotFoundException {
		return personRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
	}

}
