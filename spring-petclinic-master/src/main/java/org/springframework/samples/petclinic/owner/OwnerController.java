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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.util.RestCallManager;
import org.springframework.samples.petclinic.util.RestUrls;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author rishi
 */
@Controller
class OwnerController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    
    @Value("${owner}")
	public String ownerBaseUrl;
    
    @Value("${visit}")
	public String visitBaseUrl;
    
    @Value("${pet}")
    public String petBaseUrl;
    
    public OwnerController() {        
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/owners/new")
    public String initCreationForm(Map<String, Object> model) {
        Owner owner = new Owner();
        model.put("owner", owner);
        
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
        	ResponseEntity<String> postRes = RestCallManager.Post(RestUrls.ownerSaveUrl(ownerBaseUrl), owner);
        	if(postRes.getStatusCodeValue() != 200)
    	    	throw new Exception("Error in saving owner");
        	
        	return "redirect:/owners/find";
        }
    }

    @GetMapping("/owners/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("owner", new Owner());
        return "owners/findOwners";
    }

    @GetMapping("/owners")
    public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Owner> results = RestCallManager.
        		Get(RestUrls.getOwnerByLastNameUrl(ownerBaseUrl)+owner.getLastName(), new ParameterizedTypeReference<Collection<Owner>>() {});
        
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.put("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
    	Owner owner = RestCallManager.
        		Get(RestUrls.getOwnerByIdUrl(ownerBaseUrl)+ownerId, new ParameterizedTypeReference<Owner>() {});
        model.addAttribute(owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId) throws Exception {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
           
            ResponseEntity<String> postRes = RestCallManager.Post(RestUrls.ownerSaveUrl(ownerBaseUrl), owner);
            if(postRes.getStatusCodeValue() != 200)
    	    	throw new Exception("Error in saving vet");
    		
            return "redirect:/owners/{ownerId}";
        }
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") int ownerId, Map<String, Object> model) {
        
    	ModelAndView mav = new ModelAndView("owners/ownerDetails");
        
        Owner owner = RestCallManager.
        		Get(RestUrls.getOwnerByIdUrl(ownerBaseUrl)+ownerId, new ParameterizedTypeReference<Owner>() {});
       
        OwnerUtil oUtil = new OwnerUtil();
        
        model.put("pets", oUtil.getPets(owner, petBaseUrl, visitBaseUrl));
      
        mav.addObject(owner);
        return mav;
    }

    @PostMapping("/owners/{ownerId}/visit/cancel/{visitId}")
    public String processPetVisitCancel(
    		@PathVariable("ownerId") int ownerId,
    		@PathVariable("visitId") int visitId) throws Exception {
    	
        ResponseEntity<String> postRes = RestCallManager.Post(
        		RestUrls.getVisitCancelUrl(visitBaseUrl), visitId);
        
        if(postRes.getStatusCodeValue() != 200)
	    	throw new Exception("Error in saving visit");
		
        return "redirect:/owners/{ownerId}";
    }   
    
}
