package org.springframework.samples.petclinic.pet;


import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.jayway.restassured.RestAssured;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the {@link PetServiceController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetServiceControllerTests {
	
	@LocalServerPort
	int port;
	 
    @MockBean
    private Pet pet;
    
    @MockBean
    private PetRepository pets;
    
    @Before
    public void setup() {
        pet = new Pet();
                
        RestAssured.port = port;
    }
    
    @Test
    public void testGetPetTypes() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/pet-management/pets/pettypes";
	        	
    	ResponseEntity<Collection<PetType>> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Collection<PetType>>() {});
    	
    	Collection<PetType> result = response.getBody();
	   
	    //Verify request succeed
    	assertThat(result, hasSize(0));
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testGetPetById() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/pet-management/pets/id/1";
	        	
    	ResponseEntity<Pet> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Pet>() {});
    	
	    //Verify request succeed
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testGetPetByName() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/pet-management/pets/name/Rosy";
	        	
    	ResponseEntity<List<Pet>> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<List<Pet>>() {});
    	
    	Collection<Pet> result = response.getBody();
 	   
	    //Verify request succeed
    	assertThat(result, hasSize(0));	    
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testGetPetsByOwnerId() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/pet-management/pets/owener/1";
	        	
    	ResponseEntity<List<Pet>> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<List<Pet>>() {});
    	
    	Collection<Pet> result = response.getBody();
 	   
	    //Verify request succeed
    	assertThat(result, hasSize(0));	    
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testSavePet() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/pet-management/pets";
	    
	    ResponseEntity<String> postRes = restTemplate.postForEntity(baseUrl, pet, String.class);
	   
	    //Verify request succeed
	    assertThat(200).isEqualTo(postRes.getStatusCodeValue());	    
    }
    
}
