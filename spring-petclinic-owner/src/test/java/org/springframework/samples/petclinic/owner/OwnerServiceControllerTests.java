package org.springframework.samples.petclinic.owner;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.samples.petclinic.util.RestCallManager;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerServiceController;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

import com.jayway.restassured.RestAssured;

import static org.assertj.core.api.Assertions.assertThat;
import static com.jayway.restassured.RestAssured.get;

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
    	    	 
	    final String url = "http://localhost:"+port+"/owner-management/lastname/TestLastName";

    	List<Owner> ownerList =  RestCallManager.
        		Get(url, new ParameterizedTypeReference<List<Owner>>() {});
    	   	
	    //Verify request succeed
	    assertThat(ownerList).isNotEmpty();    
    }
    
}
