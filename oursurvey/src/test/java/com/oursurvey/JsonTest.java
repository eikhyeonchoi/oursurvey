package com.oursurvey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JsonTest {
    @Test
    void test() throws JSONException {
        String json = "{\"subject\":\"test survey\",\"content\":\"test survey content\",\"startDate\":\"2022-11-01\",\"endDate\":\"2022-12-20\",\"minute\":60,\"openFl\":1,\"closingComment\":\"closing comment\",\"sections\":[{\"title\":\"this is test section\",\"content\":\"section content\",\"nextSection\":1,\"questions\":[{\"ask\":\"question 1\",\"explain\":\"question 1 explain\",\"multiFl\":1,\"essFl\":1,\"dupFl\":1,\"oder\":0,\"questionItems\":[{\"content\":\"item1\",\"oder\":0,\"nextSection\":null},{\"content\":\"item2\",\"oder\":1,\"nextSection\":null},{\"content\":\"item3\",\"oder\":2,\"nextSection\":null}]},{\"ask\":\"question 2\",\"explain\":\"question 2 explain\",\"multiFl\":0,\"essFl\":1,\"dupFl\":0,\"oder\":1,\"questionItems\":[]}]},{\"title\":\"this is test section22\",\"content\":\"section content22\",\"nextSection\":-1,\"questions\":[{\"ask\":\"question 1\",\"explain\":\"question 1 explain\",\"multiFl\":0,\"essFl\":1,\"dupFl\":0,\"oder\":0,\"questionItems\":[]},{\"ask\":\"question 2\",\"explain\":\"question 2 explain\",\"multiFl\":0,\"essFl\":1,\"dupFl\":0,\"oder\":1,\"questionItems\":[]}]}]}";

        JSONObject o = new JSONObject(json);

        JSONArray s = o.getJSONArray("sections");
        JSONObject s1 = s.getJSONObject(0);
        System.out.println("s1 = " + s1);
        String title = s1.getString("title");
        System.out.println("title = " + title);
    }
}
