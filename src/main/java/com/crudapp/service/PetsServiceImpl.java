package com.crudapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.exception.ResourceNotFoundException;
import com.crudapp.model.Pets;
import com.crudapp.repository.PersonRepository;
import com.crudapp.repository.PetsRepository;

/****
 * *
 * 
 * @author Senthil
 *
 */
@Service
public class PetsServiceImpl implements PetsService {

	@Autowired
	private PetsRepository petsRepository;

	@Autowired
	private PersonRepository personRepository;

	/***
	 * get all pets
	 */
	@Override
	public List<Pets> getPets() {
		return petsRepository.findAll();
	}

	/***
	 * insert the pet
	 */
	@Override
	public Pets insertPet(Pets pet) throws ResourceNotFoundException {
		return petsRepository.save(pet);
	}

	/*****
	 * update the pets
	 */
	@Override
	public Pets updatePet(Long petId, Pets petDetails) throws ResourceNotFoundException {
		if (!petsRepository.existsById(petId)) {
			throw new ResourceNotFoundException("petsId not found");
		}
		return petsRepository.findById(petId).map(pets -> {
			pets.setPetName(petDetails.getPetName());
			pets.setPetAge(petDetails.getPetAge());
			return petsRepository.save(pets);
		}).orElseThrow(() -> new ResourceNotFoundException("Pets id not found"));
	}

	/***
	 * delete pets
	 */
	@Override
	public Map<String, Boolean> deletePet(Long petId) throws ResourceNotFoundException {

		return petsRepository.findById(petId).map(pets -> {
			petsRepository.delete(pets);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}).orElseThrow(() -> new ResourceNotFoundException("Pets not found with pet id " + petId));
	}

	/***
	 * find pet by id
	 * 
	 * @param petId
	 * @return
	 */
	public Optional<Pets> findPetsById(Long petId) {
		return petsRepository.findById(petId);
	}

	/***
	 * 
	 * @param personId
	 * @param pets
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Pets insertPetWithPerson(Long personId, Pets pets) throws ResourceNotFoundException {
		return personRepository.findById(personId).map(person -> {
			pets.setPerson(person);
			return petsRepository.save(pets);
		}).orElseThrow(() -> new ResourceNotFoundException("person not found"));
	}

}
