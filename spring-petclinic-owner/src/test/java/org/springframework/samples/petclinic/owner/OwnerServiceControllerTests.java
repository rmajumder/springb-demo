package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.jayway.restassured.RestAssured;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Test class for the {@link VisitServiceController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerServiceControllerTests {
	
	@LocalServerPort
	int port;
	 
    @MockBean
    private Owner owner;
    
    @MockBean
    private OwnerRepository owners;
    
    @Before
    public void setup() {
    	owner = new Owner();
    	owner.setAddress("Test Address");
    	owner.setCity("Test City");
    	owner.setFirstName("TestFirstName");
        owner.setLastName("TestLastName");
    	owner.setTelephone("9938399383");
        
        RestAssured.port = port;
    }
    
    @Test
    public void testSaveOwner() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/owner-management/owners";
	    
	    ResponseEntity<String> postRes = restTemplate.postForEntity(baseUrl, owner, String.class);
	   
	    //Verify request succeed
	    assertThat(200).isEqualTo(postRes.getStatusCodeValue());	    
    }
    
    @Test
    public void testGetOwnerByLastName() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
   	 
	    final String baseUrl = "http://localhost:"+port+"/owner-management/lastname/TestLastName";
	        	
    	ResponseEntity<Collection<Owner>> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Collection<Owner>>() {});
    	Collection<Owner> result = response.getBody();
	   
	    //Verify request succeed
    	assertThat(result, hasSize(0));
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testGetOwnerById() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
   	 
	    final String baseUrl = "http://localhost:"+port+"/owner-management/ownerid/1";
	        	
    	ResponseEntity<Owner> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Owner>() {});
    	Owner result = response.getBody();
	   
	    //Verify request succeed
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
}
