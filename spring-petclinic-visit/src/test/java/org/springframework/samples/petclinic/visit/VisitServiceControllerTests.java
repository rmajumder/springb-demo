package org.springframework.samples.petclinic.visit;

import static org.mockito.BDDMockito.given;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class VisitServiceControllerTests {
	
	@LocalServerPort
	int port;
	 
    @MockBean
    private Visit visit;
    
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
    public void testSaveVisit() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/visit-management/visits";
	    
	    ResponseEntity<String> postRes = restTemplate.postForEntity(baseUrl, visit, String.class);
	   
	    //Verify request succeed
	    assertThat(200).isEqualTo(postRes.getStatusCodeValue());	    
    }
    
}
