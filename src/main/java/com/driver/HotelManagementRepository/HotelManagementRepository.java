package com.driver.HotelManagementRepository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HotelManagementRepository {
    HashMap<String,Hotel> hotelDb=new HashMap<>();
     HashMap<Integer,User> userDb=new HashMap<>();
     HashMap<String,Booking> bookingDb=new HashMap<>();
     HashMap<Integer,List<Booking>> userBookingDb=new HashMap<>();

    public  Integer addUser(User user) {
        if(!userDb.containsKey(user.getaadharCardNo()))
        {
            userDb.put(user.getaadharCardNo(),user);
            return user.getaadharCardNo();
        }
        return 0;
    }

    public String addHotel(Hotel hotel) {
        if(!hotelDb.containsKey(hotel.getHotelName()))
        {
            hotelDb.put(hotel.getHotelName(),hotel);
            return "SUCCESS";
        }
        return "FAILURE";

    }

    public String getHotelWithMostFacilities() {
        if(hotelDb.isEmpty())
        {
            return "";
        }
        int max=0;
        String hotelName="";
        for(String hotel:hotelDb.keySet())
        {
            if(max<hotelDb.get(hotel).getFacilities().size())
            {
                hotelName=hotel;
            }
            else if (max==hotelDb.get(hotel).getFacilities().size())
            {
                if(hotelName==null){
                    hotelName=hotel;
                }else if(hotelName.compareTo(hotel)>0){
                    hotelName=hotel;
                }
            }
        }
        return hotelName;
    }

    public int bookARoom(String uuiDbookingId, Booking booking) {
        if(hotelDb.get(booking.getHotelName()).getAvailableRooms()<booking.getNoOfRooms())
        {
            return -1;
        }
        if(!bookingDb.containsKey(uuiDbookingId))
        {

            hotelDb.get(booking.getHotelName()).setAvailableRooms( hotelDb.get(booking.getHotelName()).getAvailableRooms()-booking.getNoOfRooms());
            int payableAmount=booking.getNoOfRooms()*hotelDb.get(booking.getHotelName()).getPricePerNight();
            booking.setAmountToBePaid(payableAmount);
            bookingDb.put(uuiDbookingId,booking);
            List<Booking> bookings=userBookingDb.getOrDefault(booking.getBookingAadharCard(),new ArrayList<>());
            bookings.add(booking);
            userBookingDb.put(booking.getBookingAadharCard(),bookings);
            return booking.getAmountToBePaid();
        }

        return 0;
    }

    public int getBooking(Integer aadharCard) {
        if(userBookingDb.containsKey(aadharCard))
        {
            return userBookingDb.get(aadharCard).size();
        }
        return 0;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
      if(hotelDb.containsKey(hotelName))
      {
          List<Facility> facilities=hotelDb.get(hotelName).getFacilities();
          for(Facility fac:newFacilities)
          {
              if(!facilities.contains(fac))
              {
                  facilities.add(fac);
              }
          }
          hotelDb.get(hotelName).setFacilities(facilities);
          return  hotelDb.get(hotelName);
      }
      return null;
    }
}
