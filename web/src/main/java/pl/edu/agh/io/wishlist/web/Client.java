package pl.edu.agh.io.wishlist.web;
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
import org.springframework.http.HttpStatus;
import pl.edu.agh.io.wishlist.domain.Gift;

import java.io.*;
import org.json.JSONObject;
import pl.edu.agh.io.wishlist.domain.User;

import java.util.List;

//Przed rozpoczeciem wywolac to shellu mongo:
//db.counters.insert( { _id: "giftID",  seq:  1 })
//db.counters.insert( { _id: "userID",  seq:  1 })


public class Client {
    public static void main(String[] args) {
        try {
            Checker checker = new Checker();
            //Users
            checker.addUser(1, "login1", "password1");
            checker.addUser(2, "login2", "password2");
            checker.addUser(3, "login3", "password3");
            checker.addUser(4, "login4", "password4");
            checker.getUser("login2");
            //Friends
            checker.addFriend(1, 2);
            checker.addFriend(1, 3);
            checker.addFriend(1, 4);
            checker.addFriend(1, 5);
            checker.getFriends(1);
            checker.deleteFriend(1, 2);
            checker.getFriends(1);
            //Gifts
            checker.addGift(1, "auto", "duze");
            checker.addGift(2, "samolot", "szybki");
            checker.addGift(3, "statek", "ekskluzywny");
            checker.addGift(1, "balon", "kolorowy");
            checker.getAllGifts(1);
            checker.getGift(1);
            checker.getGift(119);
            checker.getGift(120);
            checker.getGift(121);
            checker.removeGift(1);
            checker.updateGift(2, 1, "modified gift", "modified description");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Checker{


    List<User> getFriends(long id) throws IOException {
        String url = "http://localhost:8080/friends/get/" + id;
        HttpGet request = new HttpGet(url);
        String response = send(request);
        if(response.equalsIgnoreCase("")){
            return null;
        }
        List<User> users = new ObjectMapper().readValue(response, new TypeReference<List<User>>(){});
        System.out.println("----------------------------------------");
        for (User user : users) {
            System.out.println("Id: "+ user.getId()+"\nLogin: " + user.getLogin() + "\nPassword: "+user.getPassword()+"\nFriends: "+user.getFriends());
        }
        System.out.println("----------------------------------------");
        return users;
    }

    void addFriend(long userId, long friendId) throws IOException {

        String url = "http://localhost:8080/friends/add/"+userId+"?friendId="+friendId;
        System.out.println(url);
        HttpPut request = new HttpPut(url);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    void deleteFriend(long userId, long friendId) throws IOException {

        String url = "http://localhost:8080/friends/delete/"+userId+"?friendId="+friendId;

        HttpDelete request = new HttpDelete(url);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    User getUser(String login) throws IOException {
        String url = "http://localhost:8080/users/get/" + login;
        HttpGet request = new HttpGet(url);
        String response = send(request);
        System.out.println(response);
        User user= new ObjectMapper().readValue(response, User.class);
        System.out.println("----------------------------------------");
        System.out.println(user.getLogin());
        System.out.println(user.getPassword());
        System.out.println(user.getFriends());
        System.out.println("----------------------------------------");
        return user;
    }

    void addUser(long userID, String login, String password) throws IOException {

        User user = new User(userID, login, password);
        JSONObject jsonObject = new JSONObject(user);
        System.out.println(jsonObject);
        StringEntity params = new StringEntity(jsonObject.toString());
        String url = "http://localhost:8080/users/add";

        HttpPost request = new HttpPost(url);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    Gift getGift(long giftID) throws IOException {
        String url = "http://localhost:8080/gifts/getGift/" + giftID;
        HttpGet request = new HttpGet(url);
        String response = send(request);
        System.out.println(response);
        if("Operation failed".equalsIgnoreCase(response.subSequence(0,16).toString())){
            return null;
        }
        Gift gift = new ObjectMapper().readValue(response, Gift.class);
        System.out.println("----------------------------------------");
        System.out.println(gift.getName());
        System.out.println(gift.getDescription());
        System.out.println(gift.getUserID());
        System.out.println("----------------------------------------");
        return gift;
    }

    List<Gift> getAllGifts(long userID) throws IOException {

        String url = "http://localhost:8080/gifts/forUser/" + userID;
        HttpGet request = new HttpGet(url);
        String response = send(request);

        List<Gift> giftList = new ObjectMapper().readValue(response, new TypeReference<List<Gift>>(){});

        System.out.println("----------------------------------------");
        for (Gift giftTemp : giftList) {
            System.out.println("Id: "+ giftTemp.getId()+"\nName: " + giftTemp.getName() + "\nDesc: "+giftTemp.getDescription());
        }
        System.out.println("----------------------------------------");
        return giftList;
    }

    void addGift(long userID, String name, String desc) throws IOException {

        Gift gift = new Gift(userID, name, desc);
        JSONObject jsonObject = new JSONObject(gift);
        System.out.println(jsonObject);
        StringEntity params = new StringEntity(jsonObject.toString());
        String url = "http://localhost:8080/gifts/add";

        HttpPost request = new HttpPost(url);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }
    void removeGift(long giftID) throws IOException {
        String url = "http://localhost:8080/gifts/remove/"+giftID;
        HttpDelete request = new HttpDelete(url);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");
    }

    void updateGift(long giftID, long userID, String name, String desc) throws IOException {
        Gift gift = new Gift(userID, name, desc);
        JSONObject jsonObject = new JSONObject(gift);
        System.out.println(jsonObject);
        StringEntity params = new StringEntity(jsonObject.toString());
        String url = "http://localhost:8080/gifts/update/" + giftID;

        System.out.println(url);
        HttpPut request = new HttpPut(url);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    private String send(HttpRequestBase request){
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
                    } else if(status == HttpStatus.CONFLICT.value() || status == HttpStatus.NOT_FOUND.value()){
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
