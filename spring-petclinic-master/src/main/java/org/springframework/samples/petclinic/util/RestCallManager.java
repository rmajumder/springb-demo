package org.springframework.samples.petclinic.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestCallManager<T> {

	public static <T> T Get(String url, ParameterizedTypeReference<T> responseType)
	{
		RestTemplate restTemplate = new RestTemplate();
    	
    	ResponseEntity<T> response = restTemplate.exchange(
    			url,
    			HttpMethod.GET,
    			null,
    			responseType);
    	T result = response.getBody();
    	
    	return result;    
	}
	
	public static <T> ResponseEntity<String> Post(String url, T t)
	{
		RestTemplate restTemplate = new RestTemplate();
	 
	    ResponseEntity<String> postRes = restTemplate.postForEntity(url, t, String.class);
	    
	    return postRes;
	}
	
}
