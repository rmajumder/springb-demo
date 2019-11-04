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
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.visit.SlotFormatting;

/**
 * Simple JavaBean domain object representing a visit.
 *
 * @author Ken Krebs
 * @author Dave Syer
 * @author rishi
 */

public class Visit extends BaseEntity {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String description;
    private Integer petId;
    private Integer vetId;
    private Integer visitSlot;
    private Boolean isCurrent;
    private String visitSlotDesc;

    /**
     * Creates a new instance of Visit for the current date
     */
    public Visit() {
        this.date = LocalDate.now();        
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPetId() {
        return this.petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }
    
    public Integer getVetId() {
        return this.vetId;
    }

    public void setVetId(Integer vetId) {
        this.vetId = vetId;
    }

    public Integer getVisitSlot() {
        return this.visitSlot;
    }

    public void setVisitSlot(Integer visitSlot) {
        this.visitSlot = visitSlot;
    }
    
    public void setIsCurrent() {
    	this.isCurrent = this.date.isAfter(LocalDate.now());
    }
    
    public Boolean getIsCurrent() {
    	return this.isCurrent;
    }
    
    public void setVisitSlotDesc() {
    	this.visitSlotDesc = SlotFormatting.GetTimeSlotDescription(this.visitSlot);
    }
    
    public String getVisitSlotDesc() {
    	return this.visitSlotDesc;
    }
}
