package com.driver.HotelManagementService;

import com.driver.HotelManagementRepository.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService
{
    @Autowired
    HotelManagementRepository hotelManagementRepository;

    public  Integer addUser(User user) {
        return hotelManagementRepository.addUser(user);
    }

    public String addHotel(Hotel hotel) {
        return hotelManagementRepository.addHotel(hotel);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        String UUIDbookingId=String.valueOf(UUID.randomUUID());
        booking.setBookingId(UUIDbookingId);
       return hotelManagementRepository.bookARoom(UUIDbookingId,booking);
    }

    public int getBooking(Integer aadharCard) {
        return hotelManagementRepository.getBooking(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.updateFacilities(newFacilities, hotelName);
    }
}
