package org.springframework.samples.petclinic.pet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
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
public class PetServiceController {
	
    @Inject
    private PetRepository pets;

    @RequestMapping(value = "/pet-management/pets/pettypes", method = RequestMethod.GET)
    public ResponseEntity<List<PetType>> findPetTypes(){
    	
    	List<PetType> petTypes = this.pets.findPetTypes();
    	    	
        return new ResponseEntity<List<PetType>>(petTypes, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/pet-management/pets/id/{petId}", method = RequestMethod.GET)
    public ResponseEntity<Pet> findById(@PathVariable Integer petId){
    	
    	Pet pet = this.pets.findById(petId);
    	    	
        return new ResponseEntity<Pet>(pet, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/pet-management/pets/owener/{ownerId}", method = RequestMethod.GET)
    public ResponseEntity<List<Pet>> findByOwnerId(@PathVariable Integer ownerId){
    	
    	List<Pet> pet = this.pets.findByOwnerId(ownerId);
    	    	
        return new ResponseEntity<List<Pet>>(pet, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/pet-management/pets/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Pet>> findByName(@PathVariable String name){
    	
    	List<Pet> pet = this.pets.findByName(name);
    	    	
        return new ResponseEntity<List<Pet>>(pet, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/pet-management/pets", method = RequestMethod.POST)
    public ResponseEntity<String> savePet(@RequestBody Pet pet) throws Exception {    	
    	this.pets.save(pet);
    	return new ResponseEntity<String>("Success", HttpStatus.OK);    	        
    } 
}
