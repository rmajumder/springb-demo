/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.util.RestCallManager;
import org.springframework.samples.petclinic.util.RestUrls;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitSlot;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 * @author rishi
 */
@Controller
class VisitController {
        
    public VisitController() {
        
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }    
    
    @ModelAttribute("vets")
    public Collection<Vet> findOwner(Map<String, Object> model) {    
    	
    	Collection<Vet> allVets = RestCallManager.
          		Get(RestUrls.getAllVetsUrl, new ParameterizedTypeReference<Collection<Vet>>() {});
        
        model.put("selectedvet", allVets.iterator().next()); 
        
        return allVets;
    }
    
    
    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param petId
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") int petId, Map<String, Object> model) {
    	Pet pet = RestCallManager.
        		Get(RestUrls.getPetByIdUrl+petId, new ParameterizedTypeReference<Pet>() {});

        List<Visit> visits = RestCallManager.
        		Get(RestUrls.getVisitUrl+ petId, new ParameterizedTypeReference<List<Visit>>() {});
        
        pet.setVisitsInternal(visits);
        model.put("pet", pet);
                
        Visit visit = new Visit();
        pet.addVisit(visit);
        return visit;
    }    

    public String loadVisitSlotsForm(Map<String, Object> model, Visit visit) {    	
    	
    	List<Integer> vSlotIds = RestCallManager.Get(RestUrls.getSlotsUrl+visit.getVetId()+"/"+visit.getDate().toString(),
    			new ParameterizedTypeReference<List<Integer>>() {});
    	
    	List<VisitSlot> visitSlots = getAvailableSlots(vSlotIds);
    	
    	model.put("availableslots", visitSlots);
    	
    	return "pets/createOrUpdateVisitForm";
    }
    
    
    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
        return "pets/createOrUpdateVisitForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit,    		
    		@RequestParam(value = "action" , required = true) String action,
    		@RequestParam(value = "vetSelection" , required = false) String selectedVet,
    		BindingResult result, Map<String, Object> model) {
    	
    	visit.setVetId(Integer.parseInt(selectedVet));
    	
    	if(action.equals("checkavail"))
    		return loadVisitSlotsForm(model, visit);
    	else
    		return save(visit, result, model);    	
    }
    
    private String save(Visit visit, BindingResult result, Map<String, Object> model)
    {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {   
        	try
        	{        		
        		ResponseEntity<String> postRes = RestCallManager.Post(RestUrls.visitSaveUrl, visit);
        		if(postRes.getStatusCodeValue() != 200)
        	    	throw new Exception();
        		
                return "redirect:/owners/{ownerId}";
        	}
        	catch(Exception ex)
        	{
        		return "pets/createOrUpdateVisitForm";
        	}
            
        }
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
