package main.java.database;

import main.java.data.Address;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DatabaseTransformers {

    public static final String ADDRESS_NUMBER_KEY = "number";
    public static final String ADDRESS_STREET_KEY = "street";
    public static final String ADDRESS_CITY_KEY = "city";
    public static final String ADDRESS_STATE_KEY = "state";
    public static final String ADDRESS_ZIP_KEY = "zip";
    public static final String ADDRESS_COUNTRY_KEY = "country";

    public static List<String> arrayToStringList(Array array) throws SQLException {
        List<String> list = new ArrayList();
        ResultSet resultSet = array.getResultSet();
        while(resultSet.next()) {
            list.add(resultSet.getString(2));
        }
        return list;
    }

    public static List<UUID> stringListToUUIDList(List<String> stringList) {
        List<UUID> uuidList = new ArrayList();
        stringList.forEach(string -> uuidList.add(UUID.fromString(string)));
        return uuidList;
    }

    public static Address getAddress(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        Address address = new Address();
        address.setNumber(Integer.parseInt((String)(jsonObject.get(ADDRESS_NUMBER_KEY))));
        address.setStreet((String)(jsonObject.get(ADDRESS_STREET_KEY)));
        address.setCity((String)(jsonObject.get(ADDRESS_CITY_KEY)));
        address.setState((String)(jsonObject.get(ADDRESS_STATE_KEY)));
        address.setZipCode(Integer.parseInt((String)(jsonObject.get(ADDRESS_ZIP_KEY))));
        address.setCountry((String)(jsonObject.get(ADDRESS_COUNTRY_KEY)));
        return address;
    }

    public static String getJSON(Address address) {
        String jsonString = "{ ";
        jsonString += addJSON(ADDRESS_NUMBER_KEY, String.valueOf(address.getNumber()));
        jsonString += addJSON(ADDRESS_STREET_KEY, address.getStreet());
        jsonString += addJSON(ADDRESS_CITY_KEY, address.getCity());
        jsonString += addJSON(ADDRESS_STATE_KEY, address.getState());
        jsonString += addJSON(ADDRESS_ZIP_KEY, String.valueOf(address.getZipCode()));
        jsonString += endJSON(ADDRESS_COUNTRY_KEY, address.getCountry());
        return jsonString;
    }

    public static String formatArrayOfUUID(List<UUID> uuidList) {
        if(uuidList == null || uuidList.size() == 0) {
            return "'{}'";
        }
        String formattedString = "'{ ";
        for (UUID uuid : uuidList) {
            if(uuid != null) {
                formattedString += "\"" + uuid + "\",";
            }
        }
        formattedString = formattedString.substring(0, formattedString.length()-1);
        formattedString += " }'";
        return formattedString;
    }

    private static String addJSON(String key, String value) {
        return "\"" + key + "\": \"" + value + "\", ";
    }

    private static String endJSON(String key, String value) {
        return "\"" + key + "\": \"" + value + "\" }";
    }

}
