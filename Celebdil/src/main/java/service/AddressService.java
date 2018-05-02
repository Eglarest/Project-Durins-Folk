package main.java.service;

import main.java.data.Address;
import main.java.exception.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static main.java.controller.ControllerConstants.ADDRESS_CITY_KEY;
import static main.java.controller.ControllerConstants.ADDRESS_COUNTRY_KEY;
import static main.java.controller.ControllerConstants.ADDRESS_NUMBER_KEY;
import static main.java.controller.ControllerConstants.ADDRESS_STATE_KEY;
import static main.java.controller.ControllerConstants.ADDRESS_STREET_KEY;
import static main.java.controller.ControllerConstants.ADDRESS_ZIP_KEY;

@Service
public class AddressService {

    @Autowired
    private ValidationService validationService;
    
    public Address extractAddress(Map<String, String> params) throws InvalidParameterException {
        String addressNumber = params.get(ADDRESS_NUMBER_KEY);
        String addressStreet = params.get(ADDRESS_STREET_KEY);
        String addressCity = params.get(ADDRESS_CITY_KEY);
        String addressState = params.get(ADDRESS_STATE_KEY);
        String addressZipCode = params.get(ADDRESS_ZIP_KEY);
        String addressCountry = params.get(ADDRESS_COUNTRY_KEY);

        validationService.isInteger(addressNumber, ADDRESS_NUMBER_KEY);
        validationService.isNotNullOrEmpty(addressStreet, ADDRESS_STREET_KEY);
        validationService.isNotNullOrEmpty(addressCity, ADDRESS_CITY_KEY);
        validationService.isNotNullOrEmpty(addressState, ADDRESS_STATE_KEY);
        validationService.isInteger(addressZipCode, ADDRESS_ZIP_KEY);
        validationService.isNotNullOrEmpty(addressCountry, ADDRESS_COUNTRY_KEY);

        Address address = new Address();
        address.setNumber(Integer.parseInt(addressNumber));
        address.setStreet(addressStreet);
        address.setCity(addressCity);
        address.setState(addressState);
        address.setZipCode(Integer.parseInt(addressZipCode));
        address.setCountry(addressCountry);

        return address;
    }
}
