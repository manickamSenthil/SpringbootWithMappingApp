package com.crudapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudapp.exception.ResourceNotFoundException;
import com.crudapp.model.Pet;
import com.crudapp.repository.PersonRepository;
import com.crudapp.repository.PetRepository;

/****
 * *
 * 
 * @author Senthil
 *
 */
@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private PersonRepository personRepository;

	/***
	 * get all pets
	 */
	@Override
	public List<Pet> getPets() {
		return petRepository.findAll();
	}

	/***
	 * insert the pet
	 */
	@Override
	public Pet insertPet(Pet pet) throws ResourceNotFoundException {
		return petRepository.save(pet);
	}

	/*****
	 * update the pets
	 */
	@Override
	public Pet updatePet(Long petId, Pet petDetails) throws ResourceNotFoundException {
		if (!petRepository.existsById(petId)) {
			throw new ResourceNotFoundException("petId not found");
		}
		return petRepository.findById(petId).map(pets -> {
			pets.setPetName(petDetails.getPetName());
			pets.setPetAge(petDetails.getPetAge());
			return petRepository.save(pets);
		}).orElseThrow(() -> new ResourceNotFoundException("PetId not found"));
	}

	/***
	 * delete pets
	 */
	@Override
	public Map<String, Boolean> deletePet(Long petId) throws ResourceNotFoundException {

		return petRepository.findById(petId).map(pets -> {
			petRepository.delete(pets);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}).orElseThrow(() -> new ResourceNotFoundException("Pet not found for petId " + petId));
	}

	/***
	 * find pet by id
	 * 
	 * @param petId
	 * @return
	 */
	public Optional<Pet> findPetsById(Long petId) {
		return petRepository.findById(petId);
	}

	/**
	 * Linking pet to person
	 * 
	 * @param petId
	 * @param personId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Pet mappingPetToPerson(Long petId, Long personId) throws ResourceNotFoundException {
		if (!petRepository.existsById(petId) || !personRepository.existsById(personId)) {
			throw new ResourceNotFoundException("petId or personId is not found");
		}
		Pet petDetail = petRepository.findById(petId)
				.orElseThrow(() -> new ResourceNotFoundException("pet not found"));

		return personRepository.findById(personId).map(person -> {
			petDetail.setPerson(person);
			return petRepository.save(petDetail);
		}).orElseThrow(() -> new ResourceNotFoundException("person not found"));

	}

}
