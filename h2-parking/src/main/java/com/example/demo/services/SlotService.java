package com.example.demo.services;

import com.example.demo.model.Slot;
import com.example.demo.model.VehicleType;
import com.example.demo.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlotService
{
    @Autowired
    private SlotRepository slotRepository;

    public String bookSlot(Long slotId, VehicleType vehicleType)
    {
        Optional<Slot> slot = slotRepository.findById(slotId);

        if (slot.isPresent())
        {
            if (slot.get().getOccupied())
            {
                return "Parking slot already in use";
            }
            else
            {
                switch (slot.get().getType())
                {
                    case Slot.Type.SMALL:
                    {
                        if (vehicleType == VehicleType.MOTO)
                        {
                            slot.get().setOccupied(true);
                            slotRepository.save(slot.get());
                            return String.valueOf(slot.get().getId());
                        }
                        if (vehicleType == VehicleType.CAR)
                        {
                            return "Car cannot park here";
                        }
                        else
                        {
                            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
                        }
                    }

                    case Slot.Type.COMPACT:
                    case Slot.Type.LARGE:
                    {
                        if (vehicleType == VehicleType.MOTO)
                        {
                            return "Moto cannot park here";
                        }
                        if (vehicleType == VehicleType.CAR)
                        {
                            slot.get().setOccupied(true);
                            slotRepository.save(slot.get());
                            return String.valueOf(slot.get().getId());
                        }
                        else
                        {
                            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
                        }
                    }

                    default:
                    {
                        throw new IllegalArgumentException("Unsupported slot type: " + slot.get().getType());
                    }
                }
            }
        }
        else
        {
            return "Slot " + slotId + " not found";
        }
    }

    public String releaseSlot(Long slotId)
    {
        Optional<Slot> slot = slotRepository.findById(slotId);

        if (slot.isPresent())
        {
            if (slot.get().getOccupied())
            {
                slot.get().setOccupied(false);
                slotRepository.save(slot.get());
                return "Slot " + slotId + " released";
            }
            else
            {
                return "Slot " + slotId + " already released";
            }
        }
        else
        {
            return "Slot " + slotId + " not found";
        }
    }

    public void init()
    {
        slotRepository.deleteAll();

        slotRepository.save(new Slot(Slot.Type.SMALL, false));
        slotRepository.save(new Slot(Slot.Type.COMPACT, false));
        slotRepository.save(new Slot(Slot.Type.LARGE, false));
    }
}
