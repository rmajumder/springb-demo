package org.springframework.samples.petclinic.system;

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
import org.springframework.samples.petclinic.owner.OwnerRepository;
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
    
    
    @Test(expected = Exception.class)
    public void restEndPointArgumentTypeMismatch_thenExpectException() {
    	RestTemplate restTemplate = new RestTemplate();
   	 
    	final String baseUrl = "http://localhost:"+port+"/owner-management/ownerid/p";
    	
    	restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Owner>() {});
    }
    
    
}