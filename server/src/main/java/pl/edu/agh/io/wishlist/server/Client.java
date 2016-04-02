package pl.edu.agh.io.wishlist.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.domain.UserDetails;

import java.io.IOException;
import java.util.List;

//Przed rozpoczeciem wywolac to shellu mongo:
//db.counters.insert( { _id: "giftID",  seq:  1 })
//db.counters.insert( { _id: "userID",  seq:  1 })


public class Client {
    public static void main(String[] args) {
        try {
            Checker checker = new Checker();
            //Users
            checker.registerUser("login5", "password1", "asda@asda");
            checker.registerUser("login2", "password2", "asda@asda");
            checker.registerUser("login3", "password3", "asda@asda");
            checker.registerUser("login4", "password4", "asda@asda");
            checker.getUser("login2");
            String id1 = checker.getUser("login2").getId();
            String id2 = checker.getUser("login3").getId();
            String id3 = checker.getUser("login4").getId();
            String id4 = checker.getUser("login5").getId();
//            //Friends
            checker.addFriend(id1, id2);
            checker.addFriend(id1, id3);
            checker.addFriend(id1, id4);
////            checker.addFriend(id1, id5);
            checker.getFriends(id1);
            checker.deleteFriend(id1, id2);
            checker.getFriends(id1);
//            //Gifts
            checker.addGift(id1, "auto", "duze");
            checker.addGift(id2, "samolot", "szybki");
            checker.addGift(id3, "statek", "ekskluzywny");
            checker.addGift(id1, "balon", "kolorowy");
            List<Gift> gifts = checker.getAllGifts(id1);
            for (Gift gift : gifts) {
                System.out.println(gift);
            }
            checker.getGift(gifts.get(0).getId());
            checker.getGift(gifts.get(1).getId());
//            checker.getGift(120);
//            checker.getGift(121);
            checker.removeGift(id1,gifts.get(0).getId());
            checker.updateGift(gifts.get(1).getId(), "modified gift", "modified description");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

class Checker {
    public Long toLong(String s){
        return Long.parseLong(s);
    }

    List<User> getFriends(String id) throws IOException {
        String url = "http://localhost:80/friends/getAll/" + id;
        HttpGet request = new HttpGet(url);
        String response = send(request);
        if (response.equalsIgnoreCase("")) {
            return null;
        }
        List<User> users = new ObjectMapper().readValue(response, new TypeReference<List<User>>() {
        });
        System.out.println("----------------------------------------");
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("----------------------------------------");
        return users;
    }

    void addFriend(String userId, String friendId) throws IOException {

        String url = "http://localhost:80/friends/add/" + userId + "?friendId=" + friendId;
        System.out.println(url);
        HttpPut request = new HttpPut(url);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    void deleteFriend(String userId, String friendId) throws IOException {

        String url = "http://localhost:80/friends/delete/" + userId + "?friendId=" + friendId;

        HttpDelete request = new HttpDelete(url);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    User getUser(String login) throws IOException {
        String url = "http://localhost:80/users/" + login;
        HttpGet request = new HttpGet(url);
        String response = send(request);
        System.out.println(response);
        User user = new ObjectMapper().readValue(response, User.class);
        System.out.println("----------------------------------------");
        System.out.println(user);
        System.out.println("----------------------------------------");
        return user;
    }

    void registerUser(String login, String password, String email) throws IOException {

        UserDetails user = new UserDetails(login, password, email);
        JSONObject jsonObject = new JSONObject(user);
        System.out.println(jsonObject);
        StringEntity params = new StringEntity(jsonObject.toString());
        String url = "http://localhost:80/users/register";

        HttpPost request = new HttpPost(url);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");
    }

    Gift getGift(String giftID) throws IOException {
        String url = "http://localhost:80/gifts/getGift/" + giftID;
        HttpGet request = new HttpGet(url);
        String response = send(request);
        System.out.println(response);
        if ("Operation failed".equalsIgnoreCase(response.subSequence(0, 16).toString())) {
            return null;
        }
        Gift gift = new ObjectMapper().readValue(response, Gift.class);
        System.out.println("----------------------------------------");
        System.out.println(gift.getName());
        System.out.println(gift.getDescription());
        System.out.println("----------------------------------------");
        return gift;
    }

    List<Gift> getAllGifts(String userID) throws IOException {

        String url = "http://localhost:80/gifts/forUser/" + userID;
        HttpGet request = new HttpGet(url);
        String response = send(request);

        List<Gift> giftList = new ObjectMapper().readValue(response, new TypeReference<List<Gift>>() {
        });

        System.out.println("----------------------------------------");
        for (Gift giftTemp : giftList) {
            System.out.println("Id: " + giftTemp.getId() + "\nName: " + giftTemp.getName() + "\nDesc: " + giftTemp.getDescription());
        }
        System.out.println("----------------------------------------");
        return giftList;
    }

    void addGift(String userID, String name, String desc) throws IOException {

        Gift gift = new Gift(name, desc);
        JSONObject jsonObject = new JSONObject(gift);
        System.out.println(jsonObject);
        StringEntity params = new StringEntity(jsonObject.toString());
        String url = "http://localhost:80/gifts/add/" + userID;

        HttpPost request = new HttpPost(url);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    void removeGift(String userID, String giftID) throws IOException {
        String url = "http://localhost:80/gifts/remove/" + userID +"/" + giftID;
        HttpDelete request = new HttpDelete(url);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");
    }

    void updateGift(String giftID, String name, String desc) throws IOException {
        Gift gift = new Gift(name, desc);
        JSONObject jsonObject = new JSONObject(gift);
        System.out.println(jsonObject);
        StringEntity params = new StringEntity(jsonObject.toString());
        String url = "http://localhost:80/gifts/update/" + giftID;

        System.out.println(url);
        HttpPut request = new HttpPut(url);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    private String send(HttpRequestBase request) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String responseBody = null;
        try {

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else if (status == HttpStatus.CONFLICT.value() || status == HttpStatus.NOT_FOUND.value()) {
                        return "Operation failed: " + status;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            responseBody = httpClient.execute(request, responseHandler);
//            System.out.println("----------------------------------------");
//            System.out.println(responseBody);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }
}
