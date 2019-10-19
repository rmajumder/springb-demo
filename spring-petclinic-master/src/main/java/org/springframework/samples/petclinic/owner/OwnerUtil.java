package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.util.RestCallManager;
import org.springframework.samples.petclinic.util.RestUrls;

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
