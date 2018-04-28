package com.example.demo;

import com.example.demo.model.VehicleType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

// doesn't work as SlotRepository is an interface
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = {SlotController.class, SlotService.class, SlotRepository.class})

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DemoApplicationTests
{
    private static final class API
    {
        private static final String MSG_PARKING_SLOT_ALREADY_IN_USE = "Parking slot already in use";
        private static final String MSG_CANT_MARK_MOTO              = "Moto cannot park here";
        private static final String MSG_CANT_PARK_CAR               = "Car cannot park here";

        private static String urlOccupy(Long slotId, VehicleType vehicleType)
        {
            return String.format("/slots/%d/%s", slotId, vehicleType.name());
        }

        private static String urlRelease(Long slotId)
        {
            return String.format("/slots/%d", slotId);
        }
    }

    //    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void initMvc()
    {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test_occupy_invalid_car_small() throws Exception
    {
        long slotId = 1L;
        mvc.perform(delete(API.urlRelease(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.CAR))).andExpect(content().string(API.MSG_CANT_PARK_CAR));
    }

    @Test
    public void test_occupy_invalid_moto_compact() throws Exception
    {
        long slotId = 2L;
        mvc.perform(delete(API.urlRelease(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.MOTO))).andExpect(content().string(API.MSG_CANT_MARK_MOTO));
    }

    @Test
    public void test_occupy_normal_moto_small() throws Exception
    {
        long slotId = 1L;
        mvc.perform(delete(API.urlRelease(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.MOTO))).andExpect(content().string(String.valueOf(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.MOTO))).andExpect(content().string(API.MSG_PARKING_SLOT_ALREADY_IN_USE));
    }

    @Test
    public void test_occupy_normal_car_compact() throws Exception
    {
        long slotId = 2L;
        mvc.perform(delete(API.urlRelease(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.CAR))).andExpect(content().string(String.valueOf(slotId)));
    }

    @Test
    public void test_occupy_normal_car_large() throws Exception
    {
        long slotId = 3L;
        mvc.perform(delete(API.urlRelease(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.CAR))).andExpect(content().string(String.valueOf(slotId)));
    }

    @Test
    public void test_release() throws Exception
    {
        long slotId = 3L;
        mvc.perform(delete(API.urlRelease(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.CAR))).andExpect(content().string(String.valueOf(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.CAR))).andExpect(content().string(API.MSG_PARKING_SLOT_ALREADY_IN_USE));
        mvc.perform(delete(API.urlRelease(slotId)));
        mvc.perform(post(API.urlOccupy(slotId, VehicleType.CAR))).andExpect(content().string(String.valueOf(slotId)));
    }
}
