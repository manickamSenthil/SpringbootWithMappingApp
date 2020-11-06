package com.crudapp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crudapp.exception.ResourceNotFoundException;
import com.crudapp.model.Pet;
import com.crudapp.service.PetServiceImpl;

/**
 * 
 * @author Senthil
 *
 */
@RestController
@RequestMapping("/crudapp")
public class PetsController {

	@Autowired
	private PetServiceImpl petsServiceImpl;

	@GetMapping("/pets")
	public List<Pet> getAllPets() {
		List<Pet> petList = petsServiceImpl.getPets();
		return petList;
	}

	@GetMapping("/pets/{petid}")
	public Optional<Pet> getPetsById(@PathVariable(value = "petid") Long petId) {
		return petsServiceImpl.findPetsById(petId);
	}

	@PostMapping("/pets")
	public Pet createPets(@RequestBody Pet pets) throws ResourceNotFoundException {
		return petsServiceImpl.insertPet(pets);
	}

	@PutMapping("/pets/{petid}/persons/{personid}")
	public Pet mappingPetToPerson(@PathVariable(value = "petid") Long petId,
			@PathVariable(value = "personid") Long personId) throws ResourceNotFoundException {		
		return petsServiceImpl.mappingPetToPerson(petId, personId);
	}

	@PutMapping("/pets/{petid}")
	public Pet updatePets(@PathVariable(value = "petid") Long petId, @RequestBody Pet petsRequest)
			throws ResourceNotFoundException {
		Pet updatedPet = petsServiceImpl.updatePet(petId, petsRequest);
		return updatedPet;
	}

	@DeleteMapping("/pets/{petid}")
	public Map<String, Boolean> deletePets(@PathVariable(value = "petid") Long petId) throws ResourceNotFoundException {
		Map<String, Boolean> deletedPet = petsServiceImpl.deletePet(petId);
		return deletedPet;
	}

}
