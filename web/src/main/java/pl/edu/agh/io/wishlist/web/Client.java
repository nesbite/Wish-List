package pl.edu.agh.io.wishlist.web;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import pl.edu.agh.io.wishlist.domain.Gift;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;


public class Client {
    public static void main(String[] args) {
        try {
            Checker checker = new Checker();
            checker.addGift(1, "auto", "duze");
            checker.addGift(2, "samolot", "szybki");
            checker.addGift(3, "statek", "ekskluzywny");
            checker.addGift(1, "balon", "kolorowy");
            checker.getAllGifts(1);
            checker.getGift(152);
            checker.getGift(119);
            checker.getGift(120);
            checker.getGift(121);
            checker.removeGift(106);
            checker.updateGift(151, "modified gift", "modified description");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Checker{
    Gift getGift(long giftID) throws IOException {
        String url = "http://localhost:8080/gifts/getGift/" + giftID;
        HttpGet request = new HttpGet(url);
        String response = send(request);
        Gift gift= new ObjectMapper().readValue(response, Gift.class);
        return gift;
    }

    List<Gift> getAllGifts(long userID) throws IOException {

        String url = "http://localhost:8080/gifts/forUser/" + userID;
        HttpGet request = new HttpGet(url);
        String response = send(request);

        List<Gift> giftList = new ObjectMapper().readValue(response, new TypeReference<List<Gift>>(){});


        for (Gift giftTemp : giftList) {
            System.out.println("Id: "+ giftTemp.getId()+"\nName: " + giftTemp.getName() + "\nDesc: "+giftTemp.getDescription());
        }

        return giftList;
    }

    void addGift(long userID, String name, String desc) throws IOException {

        Gift gift = new Gift(userID, name, desc);
        JSONObject jsonObject = new JSONObject(gift);
        System.out.println(jsonObject);
        StringEntity params = new StringEntity(jsonObject.toString());
        HttpPost request = new HttpPost("http://localhost:8080/gifts/add");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }
    void removeGift(long giftID) throws IOException {
        String url = "http://localhost:8080/gifts/remove/"+giftID;
        HttpPost request = new HttpPost(url);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");
    }

    void updateGift(long giftID, String name, String desc) throws IOException {
//        System.out.println(String.format("http://localhost:8080/gifts/update/%s?name=%s&description=%s", giftID, name ,desc));
        String url = String.format("http://localhost:8080/gifts/update/%s?name=%s&desc=%s", giftID, URLEncoder.encode(name, "UTF-8") ,URLEncoder.encode(desc, "UTF-8"));
        System.out.println(url);
        HttpPost request = new HttpPost(url);
        String response = send(request);
        System.out.println("----------------------------------------");
        System.out.println(response);
        System.out.println("----------------------------------------");

    }

    String send(HttpRequestBase request){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String responseBody = null;
        try {

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
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
