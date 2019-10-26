package org.springframework.samples.petclinic.vet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;

import java.util.Collection;

@RestController
public class VetServiceController {
	
    @Inject
    private VetRepository vets;

    @RequestMapping(value = "/vet-management/vets", method = RequestMethod.GET)
    public ResponseEntity<Collection<Vet>> findAllVets(){
    	
    	Collection<Vet> vetList = this.vets.findAll();
    	    	
        return new ResponseEntity<Collection<Vet>>(vetList, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/vet-management/vets/specialties", method = RequestMethod.GET)
    public ResponseEntity<Collection<Specialty>> findVetSpecialities(){
    	
    	Collection<Specialty> vetList = this.vets.findVetSpecialities();
    	    	
        return new ResponseEntity<Collection<Specialty>>(vetList, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/vet-management/vets/{vetId}", method = RequestMethod.GET)
    public ResponseEntity<Vet> findVetById(@PathVariable int vetId){
    	
    	Vet vet = this.vets.findById(vetId);
    	
        return new ResponseEntity<Vet>(vet, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/vet-management/vets", method = RequestMethod.POST)
    public ResponseEntity<String> saveVet(@RequestBody Vet vet) throws Exception {    	
    	this.vets.save(vet);
    	return new ResponseEntity<String>("Success", HttpStatus.OK);    	        
    } 
}
