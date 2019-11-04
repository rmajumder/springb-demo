package org.springframework.samples.petclinic.util;

import org.springframework.beans.factory.annotation.Value;

public class RestUrls {
	
	public static String getOwnerByIdUrl(String base) {
		return "http://"+base+"/owner-management/ownerid/";
	}
	
	public static String getOwnerByLastNameUrl(String base) {
		return "http://"+base+"/owner-management/lastname/";
	}
	
	public static String ownerSaveUrl(String base) {
		return "http://"+base+"/owner-management/owners";
	}
		
    public static String getPetTypesUrl(String base) {
    	return "http://"+base+"/pet-management/pets/pettypes";
    }
    
    public static String getPetByIdUrl(String base) { 
    	return "http://"+base+"/pet-management/pets/id/";
    }
    
    public static String getPetByNameUrl(String base) { 
    	return "http://"+base+"/pet-management/pets/name/";
    }
    
    public static String petSaveUrl(String base) { 
    	return "http://"+base+"/pet-management/pets";
    }
    
    public static String getPetByOwnerIdUrl(String base) {
    	return "http://"+base+"/pet-management/pets/owener/";
    }
    
    //public static String visitServiceEndpoint = "localhost:8082";
    public static String visitSaveUrl(String base) { 
    	return "http://" + base + "/visit-management/visits";    
    }
    
    public static String getVisitUrl(String base) { 
    	return "http://"+base+"/visit-management/visits/";
    }
    
    public static String getSlotsUrl(String base) { 
    	return "http://"+base+"/visit-management/slots/";
    }
    
    public static String getVisitCancelUrl(String base) {
    	return "http://"+base+"/visit-management/cancel";
    }
        
    public static String getAllVetsUrl(String base) { 
    	return "http://"+base+"/vet-management/vets";
    }
    
    public static String getVetSpecialtiesUrl(String base) { 
    	return "http://"+base+"/vet-management/vets/specialties";
    }
    
    public static String getVetByIdUrl(String base) { 
    	return "http://"+base+"/vet-management/vets/";
    }
    
    public static String vetSaveUrl(String base) { 
    	return "http://"+base+"/vet-management/vets";
    }
}
