package com.crudapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudapp.model.Pet;

/***
 * 
 * @author Senthil
 *
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

	Optional<Pet> findByIdAndPersonId(Long petId, Long personId);

	List<Pet> findByPersonId(Long personId);

}
