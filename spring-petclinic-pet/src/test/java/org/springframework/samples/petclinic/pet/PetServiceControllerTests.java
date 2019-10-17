package org.springframework.samples.petclinic.pet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.pet.PetServiceController;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

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
    
    @Before
    public void setup() {
        pet = new Pet();
        //visit.setPetId(2);
        //visit.setVetId(2);
        //visit.setDate(LocalDate.now());
        //visit.setDescription("Test Visit");
        //visit.setVisitSlot(3);
        
        RestAssured.port = port;
    }
    
    @Test
    public void testSaveVisit() throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"/pet-management/pets";
	    
	    ResponseEntity<String> postRes = restTemplate.postForEntity(baseUrl, pet, String.class);
	   
	    //Verify request succeed
	    assertThat(200).isEqualTo(postRes.getStatusCodeValue());	    
    }
    
}
