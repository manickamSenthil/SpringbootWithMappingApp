package com.crudapp.service;

import java.util.List;
import java.util.Map;

import com.crudapp.exception.ResourceNotFoundException;
import com.crudapp.model.Pet;

public interface PetService {

	public List<Pet> getPets();

	public Pet insertPet(Pet pet) throws ResourceNotFoundException;

	public Pet updatePet(Long petId, Pet petDetails) throws ResourceNotFoundException;

	public Map<String, Boolean> deletePet(Long petId) throws ResourceNotFoundException;
}
