package org.springframework.samples.petclinic.util;

public class RestUrls {
	public static String ownerServiceEndpoint = "localhost:8083";
    public static String getOwnerByIdUrl = "http://"+ownerServiceEndpoint+"/owner-management/ownerid/";
    public static String getOwnerByLastNameUrl = "http://"+ownerServiceEndpoint+"/owner-management/lastname/";
    public static String ownerSaveUrl = "http://"+ownerServiceEndpoint+"/owner-management/owners";

    public static String petServiceEndpoint = "localhost:8084";    
    public static String getPetTypesUrl = "http://"+petServiceEndpoint+"/pet-management/pets/pettypes";
    public static String getPetByIdUrl = "http://"+petServiceEndpoint+"/pet-management/pets/id/";
    public static String getPetByNameUrl = "http://"+petServiceEndpoint+"/pet-management/pets/name/";
    public static String petSaveUrl = "http://"+petServiceEndpoint+"/pet-management/pets";
    public static String getPetByOwnerIdUrl = "http://"+petServiceEndpoint+"/pet-management/pets/owener/";
    
    public static String visitServiceEndpoint = "localhost:8082";    
    public static String visitSaveUrl = "http://" + visitServiceEndpoint + "/visit-management/visits";    
    public static String getVisitUrl = "http://"+visitServiceEndpoint+"/visit-management/visits/";
    public static String getSlotsUrl = "http://"+visitServiceEndpoint+"/visit-management/slots/";
        
    
    public static String vetServiceEndpoint = "localhost:8085";
    public static String getAllVetsUrl = "http://"+vetServiceEndpoint+"/vet-management/vets";
    public static String getVetSpecialtiesUrl = "http://"+vetServiceEndpoint+"/vet-management/vets/specialties";
    public static String getVetByIdUrl = "http://"+vetServiceEndpoint+"/vet-management/vets/";
    public static String vetSaveUrl = "http://"+vetServiceEndpoint+"/vet-management/vets";
}
