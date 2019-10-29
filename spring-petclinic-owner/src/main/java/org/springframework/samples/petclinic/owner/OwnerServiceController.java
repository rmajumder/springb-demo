package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.system.RestCallManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OwnerServiceController {

    @Inject
    private OwnerRepository owners;
    
    @RequestMapping(value = "/owner-management/lastname/{lastName}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Owner>> getOwnersByLastName(@PathVariable String lastName){    	
    	Collection<Owner> ownerList = this.owners.findByLastName(lastName);
    	    	
        return new ResponseEntity<Collection<Owner>>(ownerList, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/owner-management/ownerid/{ownerId}", method = RequestMethod.GET)    
    public ResponseEntity<Owner> getOwnerById(@PathVariable Integer ownerId){    	
    	Owner owner = this.owners.findById(ownerId);
    	
        return new ResponseEntity<Owner>(owner, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/owner-management/owners", method = RequestMethod.POST)
    public ResponseEntity<String> saveOwner(@RequestBody Owner owner) throws Exception {    	
    	this.owners.save(owner);
    	return new ResponseEntity<String>("Success", HttpStatus.OK);    	        
    } 
}
