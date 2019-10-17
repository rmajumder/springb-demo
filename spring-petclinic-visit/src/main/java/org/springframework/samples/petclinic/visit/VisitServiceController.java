package org.springframework.samples.petclinic.visit;

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
	
    @Inject
    private VisitRepository visits;

    @RequestMapping(value = "/visit-management/slots/{vetId}/{visitDate}", method = RequestMethod.GET)
    public ResponseEntity<List<VisitSlot>> getVisitSlots(@PathVariable int vetId, 
    		@PathVariable String visitDate){
    	
    	List<Integer> bookedSlots = this.visits.getBookedSlots(LocalDate.parse(visitDate), vetId);
    	    	
        return new ResponseEntity<List<VisitSlot>>(getAvailableSlots(bookedSlots), HttpStatus.OK);
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
    
    private ArrayList<VisitSlot> getAvailableSlots(List<Integer> bookedSlots)
    {
    	ArrayList<VisitSlot> slots = new ArrayList<VisitSlot>();
    	
    	for(Integer i = 1; i < 9; i++)
    	{
    		if(!bookedSlots.contains(i))
    		{
    			VisitSlot vs = new VisitSlot();
    			vs.TimeSlotDescription = GetTimeSlotDescription(i);
    			vs.TimeSlotNum = i;
    			
    			slots.add(vs);
    		}
    	}
    	
    	return slots;
    }

    private String GetTimeSlotDescription(Integer ts)
    {
    	return ts <= 4 ? String.format("%d AM to %d AM", (7 + ts), (8 + ts)) 
    			: String.format("%d PM to %d PM", ts, ts + 1);
    }
}
