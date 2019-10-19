package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.samples.petclinic.util.RestCallManager;
import org.springframework.samples.petclinic.util.RestUrls;
import org.springframework.samples.petclinic.visit.Visit;

public class OwnerUtil {
	
  public Collection<Pet> getPets(Owner owner){
	  
	Collection<Pet> pets = RestCallManager.
      		Get(RestUrls.getPetByOwnerIdUrl+owner.getId(), 
      				new ParameterizedTypeReference<Collection<Pet>>() {}); 
	
  	for (Pet pet : pets) {
  		List<Visit> visit = RestCallManager
  				.Get(RestUrls.getVisitUrl + pet.getId(), 
  						new ParameterizedTypeReference<List<Visit>>() {});
  		
        pet.setVisitsInternal(visit);
  	}
  	
  	return pets;
  }
//	
//  public List<Pet> getPets(Owner owner) {
//	  List<Pet> sortedPets = new ArrayList<>(owner.getPetsInternal());
//	  PropertyComparator.sort(sortedPets,
//	          new MutableSortDefinition("name", true, true));
//	  return Collections.unmodifiableList(sortedPets);
//}

}
