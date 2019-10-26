package org.springframework.samples.petclinic.visit;

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
import org.springframework.samples.petclinic.model.Visit;
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
public class VisitServiceControllerTest {
	
	@LocalServerPort
	int port;
	 
    @MockBean
    private Visit visit;
    
    @MockBean
    private VisitRepository visits;
    
    @Before
    public void setup() {
        visit = new Visit();
        visit.setPetId(2);
        visit.setVetId(2);
        visit.setDate(LocalDate.now());
        visit.setDescription("Test Visit");
        visit.setVisitSlot(3);
        
        RestAssured.port = port;
    }
    
    @Test
    public void testGetSlotIntegration() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/visit-management/slots/7/2010-03-04";
	        	
    	ResponseEntity<Collection<Integer>> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Collection<Integer>>() {});
    	Collection<Integer> result = response.getBody();
	   
	    //Verify request succeed
    	assertThat(result, hasSize(0));
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testGetVisitIntegration() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/visit-management/visits/1";
	        	
    	ResponseEntity<Collection<Visit>> response = restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Collection<Visit>>() {});
    	Collection<Visit> result = response.getBody();
	   
	    //Verify request succeed
    	assertThat(result, hasSize(0));
    	assertThat(200).isEqualTo(response.getStatusCodeValue());	
    }
    
    @Test
    public void testSaveVisitIntegration() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/visit-management/visits";
	    
	    ResponseEntity<String> postRes = restTemplate.postForEntity(baseUrl, visit, String.class);
	   
	    //Verify request succeed
	    assertThat(200).isEqualTo(postRes.getStatusCodeValue());	    
    }
    
}
