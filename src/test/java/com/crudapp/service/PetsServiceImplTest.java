package com.crudapp.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.crudapp.SpringbootCrudAppApplication;
import com.crudapp.model.Pets;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootCrudAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class PetsServiceImplTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Before
	public void startUp() {
		restTemplate = new TestRestTemplate("user", "user");
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void securityTest() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/crudapp/pets", HttpMethod.GET, entity,
				String.class);
		System.out.println(response.getBody());
		assertNotNull(response.getBody());
	}

	@Test
	public void testInsertPet() {
		// restTemplate = new TestRestTemplate("user", "user");

		Pets pets = new Pets();
		pets.setPetAge("5");
		pets.setPetName("Tom");
		ResponseEntity<Pets> postResponse = restTemplate.postForEntity(getRootUrl() + "/crudapp/pets", pets, Pets.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePet() {
		// restTemplate = new TestRestTemplate("user", "user");
		testInsertPet();
		int id = 1;
		Pets pet = restTemplate.getForObject(getRootUrl() + "/crudapp/pets/" + id, Pets.class);
		pet.setPetName("Tom");
		pet.setPetAge("13");
		restTemplate.put(getRootUrl() + "/crudapp/pets/" + id, pet);

		Pets updatedPet = restTemplate.getForObject(getRootUrl() + "/crudapp/pets/" + id, Pets.class);
		assertEquals("Tom", updatedPet.getPetName());
		assertNotNull(updatedPet);
	}

	@Test
	public void testGetAllPets() {
		testInsertPet();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/crudapp/pets", HttpMethod.GET, entity,
				String.class);
		System.out.println(response.getBody());
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetPetById() {
		testInsertPet();
		Pets pet = restTemplate.getForObject(getRootUrl() + "/crudapp/pets/1", Pets.class);
		System.out.println(pet.getPetName());
		assertNotNull(pet);
	}

	@Test
	public void testDeletePet() {
		testInsertPet();
		int id = 5;
		Pets pet = restTemplate.getForObject(getRootUrl() + "/crudapp/pets/" + id, Pets.class);
		assertNotNull(pet);

		restTemplate.delete(getRootUrl() + "/crudapp/pets/" + id);

		try {
			pet = restTemplate.getForObject(getRootUrl() + "/crudapp/pets/" + id, Pets.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
