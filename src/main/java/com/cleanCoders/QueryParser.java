package com.cleanCoders;

import java.util.HashMap;

public class QueryParser {

    public static HashMap<String, String> parse(String filePath) {
        HashMap<String, String> queryMap = new HashMap<>();
        String[] query = filePath.split("\\?");

        if (query.length > 1) {
            String[] queryArray = query[1]
                    .replace("=", " ")
                    .replace("&", " ")
                    .split("\\s");

            for (int i = 0; i < queryArray.length; i += 2) {
                queryMap.put(queryArray[i], queryArray[i + 1]);
            }
        }
        return queryMap;
    }
}
