package com.crudapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudapp.model.Pets;

/***
 * 
 * @author Senthil
 *
 */
@Repository
public interface PetsRepository extends JpaRepository<Pets, Long> {

	Optional<Pets> findByIdAndPersonId(Long petsId, Long personId);

	List<Pets> findByPersonId(Long personId);

}
