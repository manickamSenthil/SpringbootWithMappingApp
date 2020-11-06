package com.crudapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

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

import com.crudapp.SpringbootCrudApplication;
import com.crudapp.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootCrudApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class PersonServiceImplTest {

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

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/crudapp/persons", HttpMethod.GET,
				entity, String.class);
		System.out.println(response.getBody());
		assertNotNull(response.getBody());
	}

	@Test
	public void testInsertPersons() {
		Person Person = new Person("admin", "admin","12/12/1993","madurai");
		Person Person2 = new Person("admin", "admin","12/12/1993","madurai");
	
		ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/crudapp/persons", Person,
				Person.class);
		
		ResponseEntity<Person> postResponse2 = restTemplate.postForEntity(getRootUrl() + "/crudapp/persons", Person2,
				Person.class);
		assertEquals(postResponse.getBody().getFirstName()+postResponse.getBody().getLastName()
				, postResponse2.getBody().getFirstName()+postResponse2.getBody().getLastName());
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}
	
	
	@Test
	public void testUpdatePersons() {
		int id = 1;
		Person person = restTemplate.getForObject(getRootUrl() + "/crudapp/persons/" + id, Person.class);
		person.setFirstName("userlogin");
		person.setLastName("user");
		person.setAddress("mk");
		restTemplate.put(getRootUrl() + "/crudapp/persons/" + id, person);

		Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/crudapp/persons/" + id, Person.class);

		assertEquals("mk", updatedPerson.getAddress());
		assertNotNull(updatedPerson);
	}
	
	

	@Test
	public void testGetAllPersons() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/crudapp/persons", HttpMethod.GET,
				entity, String.class);
		System.out.println(response.getBody());
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetPersonById() {
		testInsertPersons();
		Person person = restTemplate.getForObject(getRootUrl() + "/crudapp/persons/1", Person.class);
		System.out.println(person.getFirstName());
		assertNotNull(person);
	}

	@Test
	public void testDeletePersons() {
		int id = 5;
		Person person = restTemplate.getForObject(getRootUrl() + "/crudapp/persons/" + id, Person.class);
		assertNotNull(person);

		restTemplate.delete(getRootUrl() + "/crudapp/persons/" + id);

		try {
			person = restTemplate.getForObject(getRootUrl() + "/crudapp/persons/" + id, Person.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

	@Test
	public void testGetPersonByAnyName() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		HttpEntity entity = new HttpEntity(headers);
		Map<String, String> params = new HashMap<String, String>();
		params.put("fName", "senthil");
		params.put("lname", "");
		HttpEntity<Person> response=restTemplate.exchange(getRootUrl() + "/crudapp/persons/anyname",  HttpMethod.GET, entity, Person.class, params);
		//Person person = restTemplate.getForObject(getRootUrl() + "/crudapp/persons/anyname", Person.class,params);
		
		assertNotNull(response);
	}


}
