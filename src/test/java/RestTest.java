import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class RestTest {
    private static String url = "https://api.mercadolibre.com/users/1";
    private static Integer expectedId = 1;
    private static String expectedCountry = "CL";
    private static String expectedTag = "test_user";
    private static Integer expectedNeutralRating = 0;


    @Test
    public void testResponseFromUserAPI() throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.get(url).asJson();
        JSONObject userInfo = request.getBody().getObject();
        JSONArray userTags = (JSONArray) userInfo.get("tags");
        JSONObject userSellerReputation = (JSONObject) userInfo.get("seller_reputation");
        JSONObject userTransactions = (JSONObject) userSellerReputation.get("transactions");
        JSONObject userRatings = (JSONObject) userTransactions.get("ratings");

        ArrayList<String> tags = new ArrayList<>();
        for (int i = 0; i < userTags.length(); i++ ) {
            tags.add(userTags.get(i).toString());
        }

        assertEquals(expectedNeutralRating, userRatings.get("neutral"));
        assertEquals(expectedId, userInfo.get("id"));
        assertEquals(expectedCountry, userInfo.get("country_id"));
        assertTrue(tags.contains(expectedTag));
    }
}