package org.springframework.samples.petclinic.vet;

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
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.jayway.restassured.RestAssured;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the {@link VetServiceController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VetServiceControllerTests {
	
	@LocalServerPort
	int port;
	 
    @MockBean
    private Vet vet;
    
    @MockBean
    private VetRepository vets;
    
    @Before
    public void setup() {
        vet = new Vet();
        
        RestAssured.port = port;
    }
    
    @Test
    public void testGetVets() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/vet-management/vets";
	        	
    	ResponseEntity<Collection<Vet>> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Collection<Vet>>() {});
    	Collection<Vet> result = response.getBody();
	   
	    //Verify request succeed
    	assertThat(result, hasSize(0));
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testGetVetSpecialties() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/vet-management/vets/specialties";
	        	
    	ResponseEntity<Collection<Specialty>> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Collection<Specialty>>() {});
    	Collection<Specialty> result = response.getBody();
	   
	    //Verify request succeed
    	assertThat(result, hasSize(0));
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testGetVetById() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/vet-management/vets/7";
	        	
    	ResponseEntity<Vet> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Vet>() {});
    	
	    //Verify request succeed
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testSaveVisit() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/vet-management/vets";
	    
	    ResponseEntity<String> postRes = restTemplate.postForEntity(baseUrl, vet, String.class);
	   
	    //Verify request succeed
	    assertThat(200).isEqualTo(postRes.getStatusCodeValue());	    
    }
 

}
