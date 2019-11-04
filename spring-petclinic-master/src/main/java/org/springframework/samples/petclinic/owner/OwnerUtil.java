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
	
  public Collection<Pet> getPets(Owner owner, String ownerBaseUrl, String visitBaseUrl){
	  
	Collection<Pet> pets = RestCallManager.
      		Get(RestUrls.getPetByOwnerIdUrl(ownerBaseUrl)+owner.getId(), 
      				new ParameterizedTypeReference<Collection<Pet>>() {}); 
	
  	for (Pet pet : pets) {
  		List<Visit> visits = RestCallManager
  				.Get(RestUrls.getVisitUrl(visitBaseUrl) + pet.getId(), 
  						new ParameterizedTypeReference<List<Visit>>() {});
  		
  		for(Visit vst : visits) {
  			vst.setIsCurrent();  
  			vst.setVisitSlotDesc();
  		}
  		
  		pet.setVisitsInternal(visits);
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
