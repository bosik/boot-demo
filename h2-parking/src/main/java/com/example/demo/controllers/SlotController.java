package com.example.demo.controllers;

import com.example.demo.model.VehicleType;
import com.example.demo.services.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlotController
{
    @Autowired
    private SlotService slotService;

    @RequestMapping(path = "slots/{slotId}/{vehicleType}", method = RequestMethod.POST)
    public String occupySlot(@PathVariable("slotId") Long slotId, @PathVariable("vehicleType") String sVehicleType)
    {
        VehicleType vehicleType = VehicleType.valueOf(sVehicleType);
        return slotService.bookSlot(slotId, vehicleType);
    }

    @RequestMapping(path = "slots/{slotId}", method = RequestMethod.DELETE)
    public String releaseSlot(@PathVariable("slotId") Long slotId)
    {
        return slotService.releaseSlot(slotId);
    }
}
