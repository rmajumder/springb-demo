package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

public class OwnerUtil {
  public List<Pet> getPets(Owner owner) {
	  List<Pet> sortedPets = new ArrayList<>(owner.getPetsInternal());
	  PropertyComparator.sort(sortedPets,
	          new MutableSortDefinition("name", true, true));
	  return Collections.unmodifiableList(sortedPets);
  }
  
  
}
