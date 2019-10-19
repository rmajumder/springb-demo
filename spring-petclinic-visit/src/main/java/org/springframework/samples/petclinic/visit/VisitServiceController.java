package org.springframework.samples.petclinic.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VisitServiceController {
	
	@Autowired
    private VisitRepository visits;

    @RequestMapping(value = "/visit-management/slots/{vetId}/{visitDate}", method = RequestMethod.GET)
    public ResponseEntity<List<Integer>> getVisitSlots(@PathVariable int vetId, 
    		@PathVariable String visitDate){
    	LocalDate vd = LocalDate.parse(visitDate);
    	List<Integer> bookedSlots = this.visits.getBookedSlots(vd, vetId);
    	    	
        return new ResponseEntity<List<Integer>>(bookedSlots, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/visit-management/visits/{petId}", method = RequestMethod.GET)
    public ResponseEntity<List<Visit>> getVisit(@PathVariable int petId){
    	
    	List<Visit> visits = this.visits.findByPetId(petId);
    	    	
        return new ResponseEntity<List<Visit>>(visits, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/visit-management/visits", method = RequestMethod.POST)
    public ResponseEntity<String> saveVisit(@RequestBody Visit visit) throws Exception {    	
    	this.visits.save(visit);
    	return new ResponseEntity<String>("Success", HttpStatus.OK);    	        
    }
    
   
}
