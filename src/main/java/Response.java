import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Response {
    String CID, MNC, MCC, LAC;
    Response(String line) {
        MCC = line.split(";")[0];
        MNC = line.split(";")[1];
        LAC = line.split(";")[2];
        CID = line.split(";")[3];
    }

    String getResponse() throws UnirestException {
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("cellid", "201609359");
//        map.put("operatorid", "02");
//        map.put("countrycode", "250");
//        map.put("lac", "7833");
        map.put("cellid", CID);
        map.put("operatorid", MNC);
        map.put("countrycode", MCC);
        map.put("lac", LAC);
        String response = Unirest.get("http://mobile.maps.yandex.net/cellid_location/")
                .queryString(map)
                .asString().getBody();
        if (response.contains("location")) {
            return getCoordinates(response);
        } else return "null";
    }

    private String getCoordinates(String response) {
        ArrayList<String> list = new ArrayList<String>();
        Pattern pt = Pattern.compile("\"[^\"]*\"");
        Matcher mt = pt.matcher(response);
        while (mt.find()) {
            list.add(mt.group());
        }
        return list.get(3) + ";" + list.get(4);
    }
}
