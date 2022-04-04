package com.example.hooka_androidapp;

import com.example.hooka_androidapp.models.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;

public class Services {
    static String baseURL = "http://192.168.50.215:3000";


    /* -------------------------------------USER FUNCTIONS------------------------------------- */

    public static List<User> getUsers() {
        try {
            List<User> users = new ArrayList<>();

            String apiPath = "users/list";
            String result = callGet(apiPath);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            Map<String, Object> map = mapper.readValue(result, Map.class);
            String data = mapper.writeValueAsString(map.get("data"));

            users = mapper.readValue(data, new TypeReference<List<User>>(){});
            return users;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean createUser(String userType, String fullname, String mobile, String password) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            User user = new User(userType, fullname, mobile, password);
            String body = mapper.writeValueAsString(user);
            String apiPath = "users/create";
            String result = callPost(apiPath, body);

            Map<String,Object> map = mapper.readValue(result, Map.class);
            String status = map.get("status").toString();
            return status.equals("success");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User login(String fullname, String password) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            User user = new User(null, fullname, null, password);
            String body = mapper.writeValueAsString(user);
            String apiPath = "users/login";
            String result = callPost(apiPath, body);

            Map<String,Object> map = mapper.readValue(result, Map.class);
            String status = map.get("status").toString();
            boolean isAuthenticated = Boolean.valueOf(map.get("isAuthenticated").toString());

            if (!isAuthenticated)
                return null;

            String json = mapper.writeValueAsString(map.get("userData"));
            User userData = mapper.readValue(json, new TypeReference<User>(){});
            return userData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean deleteUser(int userID) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("id", userID);
            String body = mapper.writeValueAsString(objectMap);
            String apiPath = "users/delete";
            String result = callPost(apiPath, body);

            Map<String,Object> map = mapper.readValue(result, Map.class);
            String status = map.get("status").toString();
            return status.equals("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* -------------------------------------BASE FUNCTIONS------------------------------------- */

    // this is the base function to call the GET API based on path
    private static String callGet(String apiPath) {
        String fullURL = String.format("%s/%s", baseURL, apiPath);

        String result = "";
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(fullURL);
                //open a URL connection

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();

                while (data != -1) {
                    result += (char) data;
                    data = isw.read();

                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
        System.out.println(result);
        return result;
    }

    // this is the base function to call the POST API based on path
    private static String callPost(String apiPath, String data) {
        String fullURL = String.format("%s/%s", baseURL, apiPath);

        String result = "";
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(fullURL);
                //open a URL connection

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                try(OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = data.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    result = response.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
        System.out.println(result);
        return result;
    }
}
