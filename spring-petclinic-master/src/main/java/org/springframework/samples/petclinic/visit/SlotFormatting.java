package org.springframework.samples.petclinic.visit;

import java.util.ArrayList;
import java.util.List;

public class SlotFormatting {
	public static ArrayList<VisitSlot> getAvailableSlots(List<Integer> bookedSlots)
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

    public static String GetTimeSlotDescription(Integer ts)
    {
    	return ts <= 4 ? String.format("%d AM to %d AM", (7 + ts), (8 + ts)) 
    			: String.format("%d PM to %d PM", ts, ts + 1);
    }
}
