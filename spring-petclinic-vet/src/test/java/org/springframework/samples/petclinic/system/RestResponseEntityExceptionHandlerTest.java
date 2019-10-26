/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.jayway.restassured.RestAssured;

import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestResponseEntityExceptionHandlerTest {
	
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
    
    
    @Test(expected = HttpServerErrorException.class)
    public void restEndPointArgumentTypeMismatch_thenExpectException() {
    	RestTemplate restTemplate = new RestTemplate();
    	    	 
	    final String baseUrl = "http://localhost:"+port+"//vet-management/vets/pp";
	        	
    	restTemplate.exchange(
    			baseUrl,
    			HttpMethod.GET,
    			null,
    			new ParameterizedTypeReference<Collection<Integer>>() {});
    	
    }
    
}