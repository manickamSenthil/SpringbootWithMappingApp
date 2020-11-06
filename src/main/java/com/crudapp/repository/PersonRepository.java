package com.crudapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crudapp.model.Person;

/****
 * 
 * @author Senthil
 *
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	List<Person> findByFirstName(String firstName);

	List<Person> findByLastName(String firstname);

	List<Person> findByFirstNameAndLastName(String firstName, String lastName);

}
