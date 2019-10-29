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
package org.springframework.samples.petclinic.vet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.util.RestCallManager;
import org.springframework.samples.petclinic.util.RestUrls;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;

import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Rishi
 */
@Controller
class VetController {

	private static final String VIEWS_VETS_CREATE_OR_UPDATE_FORM = "vets/createOrUpdateVetForm";
    
	@Value("${vet}")
	public String vetBaseUrl;
	
    public VetController() {
        
    }
    
    
    public void populateSpecialities(Vet v, Map<String, Object> model) {
    	              	
    	ArrayList<SpecialtyExt> spcs = new ArrayList<SpecialtyExt>();
            	
        Collection<Specialty> selectedSpc = null;
        if(v != null)
        	selectedSpc = v.getSpecialtiesInternal();
        
        Collection<Specialty> allSpecialties = RestCallManager.
        		Get(RestUrls.getVetSpecialtiesUrl(vetBaseUrl), new ParameterizedTypeReference<Collection<Specialty>>() {});
        
        
        for(Specialty spc: allSpecialties) {
        	SpecialtyExt extSpec = new SpecialtyExt();
    		extSpec.setId(spc.getId());
    		extSpec.setName(spc.getName());
    		
    		if(v != null)
    		{
	        	for(Specialty s: selectedSpc)
	        	{        		
	        		if(s.getId() == spc.getId())
	        			extSpec.IsSelected = true;        		
	        		else
	        			extSpec.IsSelected = false;
	        	}
        	}
        	
        	spcs.add(extSpec);
        }
        
        model.put("allspecialties", spcs);
    }   
    
    
    @GetMapping("/vets.html")
    public String showVetList(Map<String, Object> model) {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
    	Collection<Vet> allVets = RestCallManager.
        		Get(RestUrls.getAllVetsUrl(vetBaseUrl), new ParameterizedTypeReference<Collection<Vet>>() {});
            	
        Vets vets = new Vets();
        vets.getVetList().addAll(allVets);
        model.put("vets", vets);
        return "vets/vetList";
    }

    @GetMapping({ "/vets" })
    public @ResponseBody Vets showResourcesVetList() {
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for JSon/Object mapping
    	
    	Collection<Vet> allVets = RestCallManager.
        		Get(RestUrls.getAllVetsUrl(vetBaseUrl), new ParameterizedTypeReference<Collection<Vet>>() {});
            	
        Vets vets = new Vets();
        vets.getVetList().addAll(allVets);
        return vets;
    }
    
    @GetMapping("/vets/new")
    public String initCreationForm(ModelMap model) {
    	populateSpecialities(null, model);
        Vet vet = new Vet();        
        model.put("vet", vet);
        return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
    }
    
    @PostMapping("/vets/new")
    public String processCreationForm(@Valid Vet vet, 
    		@RequestParam(value = "selectSpecList" , required = false) ArrayList<String> selectSpecList,
    		BindingResult result, ModelMap model) throws Exception {        
        if (result.hasErrors()) {
            model.put("vet", vet);
            return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
        } else {      	
        	
        	for(String s: selectSpecList)
        	{
        		String[] st = s.split("_"); 
    			Specialty spc = new Specialty();
    			spc.setId(Integer.parseInt(st[0]));
    			spc.setName(st[1]);
    			vet.addSpecialty(spc);        	
        	}
        	
        	ResponseEntity<String> postRes = RestCallManager.Post(RestUrls.vetSaveUrl(vetBaseUrl), vet);
    		if(postRes.getStatusCodeValue() != 200)
    	    	throw new Exception("Error in saving vet");
    		        	
            //this.vets.save(vet);
            return "redirect:/vets.html";
        }
    }
    
    @GetMapping("/vets/{vetId}/edit")
    public String initUpdateForm(@PathVariable("vetId") int vetId, ModelMap model) {
    	Vet vet = RestCallManager.
        		Get(RestUrls.getVetByIdUrl(vetBaseUrl)+vetId, new ParameterizedTypeReference<Vet>() {});
          
        populateSpecialities(vet, model);
        model.put("vet", vet);
        return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/vets/{vetId}/edit")
    public String processUpdateForm(
    		@Valid Vet vet, 
    		@RequestParam(value = "selectSpecList" , required = false) ArrayList<String> selectSpecList,
    		BindingResult result, ModelMap model, @PathVariable("vetId") int vetId) throws Exception {
        if (result.hasErrors()) {            
            model.put("vet", vet);
            return VIEWS_VETS_CREATE_OR_UPDATE_FORM;
        } else {
        	for(String s: selectSpecList)
        	{
        		String[] st = s.split("_"); 
    			Specialty spc = new Specialty();
    			spc.setId(Integer.parseInt(st[0]));
    			spc.setName(st[1]);
    			vet.addSpecialty(spc);        	
        	}
            ResponseEntity<String> postRes = RestCallManager.Post(RestUrls.vetSaveUrl(vetBaseUrl), vet);
            if(postRes.getStatusCodeValue() != 200)
    	    	throw new Exception("Error in saving vet");
            
            return "redirect:/vets.html";
        }
    }

}
