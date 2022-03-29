package ictgradschool.web;


import ictgradschool.json.JSONUtils;
import ictgradschool.user.UserDetailCreate;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class sends a request to the HTML API interface and accepts the data from the API interface...
 */
public class API {
    private static API instance;

    /**
     * defult URL...
     */
    private static final String BASE_URL = "http://localhost:3000/api";

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    private final CookieManager cookieManager;
    private final HttpClient client;

    private API() {
        this.cookieManager = new CookieManager();

        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(Duration.ofSeconds(10))
                .cookieHandler(this.cookieManager)
                .build();
    }

    /**
     * the method will be called when user click the login button, and it will return the status code from the interface...
     * @param username
     * @param password
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public int loginRequestPost(String username, String password) throws IOException, InterruptedException {
        Map values = new HashMap<String, String>();
        values.put("username", username);
        values.put("password", password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/Login"))
                .POST(buildFormDataFromMap(values))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.statusCode();
    }

    /**
     * the method will be called when user click cancel button, and it will return the status code from the interface...
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public int logoutRequestGet() throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/logout"))
                .setHeader("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody());

        HttpRequest request = builder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    /**
     * The method will be invoked from the SwingWorker sub-class, when user login as admin,
     * There are three possibilities for the information being received from the api,
     * 2 for String messages, 1 for json array...
     * @param cookies
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Object getAllUsersDetail(String cookies) throws IOException, InterruptedException {

        String json1 = JSONUtils.toJSON(cookies);
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users"))
                .setHeader("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.ofString(json1));

        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        if (json.equals("Authentication failed because you are not admin!")){
            return json;
        }else if(json.equals("Authentication failed because user doesn't exist!")){
            return json;
        }else{
            return JSONUtils.toList(json, UserDetailCreate.class);
        }
    }

    /**
     * The method will be invoked when user click delete button, and it will return the String message from the interface...
     * @param id
     * @param cookies
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String deleteUserById(int id, String cookies) throws IOException, InterruptedException {
        String json1 = JSONUtils.toJSON(cookies);
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users" + "/" + id))
                .setHeader("Accept", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(json1));

        HttpRequest request = builder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        return json;
    }

    /**
     * The method will be called,and it will return the current existing authToken...
     * @return
     */
    public String getAuthToken() {
        List<HttpCookie> cookies = this.cookieManager.getCookieStore().get(URI.create(BASE_URL));
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equals("authToken")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * The function is used to split data from HashMap and make the data identifiable by the POST method for the api interface...\
     * @param data
     * @return
     */
    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
