package org.springframework.samples.petclinic.system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.pet.PetRepository;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.jayway.restassured.RestAssured;

/**
 * Test class for the {@link RestResponseEntityExceptionHandler}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestResponseEntityExceptionHandlerTest {
	
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
    
    @Test(expected = Exception.class)
    public void restEndPointArgumentTypeMismatch_thenExpectException() {
    	RestTemplate restTemplate = new RestTemplate();
   	 
	    final String baseUrl = "http://localhost:"+port+"/pet-management/pets/id/g";
	        	
    	restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Pet>() {});
    }
    
    
}