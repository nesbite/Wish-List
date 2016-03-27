package pl.edu.agh.io.wishlist.web;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import pl.edu.agh.io.wishlist.domain.Gift;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import java.net.URLConnection;
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
            checker.getGift(118);
            checker.getGift(119);
            checker.getGift(120);
            checker.getGift(121);
            checker.removeGift(106);
            checker.updateGift(112, "modified gift", "modified description");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

class Checker{
    Gift getGift(long id) throws IOException {
        URL url = new URL("http://localhost:8080/gifts/getGift/" + id);
        HttpURLConnection  connection = (HttpURLConnection )url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if(connection.getResponseCode() != HttpStatus.OK.value()){
            System.out.println("\ngetGift("+id+") error\n");
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input = "";
        String line;
        while ((line = in.readLine()) != null) {
            input += line;
        }

        Gift gift = new ObjectMapper().readValue(input, Gift.class);

        System.out.println(input);
        System.out.println("Id: " + gift.getId()+"\nName: " + gift.getName() + "\nDesc: " + gift.getDescription());
        in.close();
        return gift;
    }

    List<Gift> getAllGifts(long id) throws IOException {

        URL url = new URL("http://localhost:8080/gifts/forUser/" + id);
        HttpURLConnection  connection = (HttpURLConnection )url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if(connection.getResponseCode() != HttpStatus.OK.value()){
            System.out.println("\ngetAllGifts("+id+") error\n");
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input = "";
        String line;
        while ((line = in.readLine()) != null) {
            input += line;
        }
        List<Gift> giftList = new ObjectMapper().readValue(input, new TypeReference<List<Gift>>(){});


        for (Gift giftTemp : giftList) {
            System.out.println("Id: "+ giftTemp.getId()+"\nName: " + giftTemp.getName() + "\nDesc: "+giftTemp.getDescription());
        }

        in.close();
        return giftList;
    }

    String addGift(long id, String name, String desc) throws IOException {

        Gift gift = new Gift(id,name, desc);
        JSONObject jsonObject = new JSONObject(gift);
        System.out.println(jsonObject);

        URL url = new URL("http://localhost:8080/gifts/add");
        URLConnection connection = url.openConnection();
//        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

//        if(connection.getResponseCode() != HttpStatus.OK.value()){
//            System.out.println("\naddGift("+id+", name, desc) error\n");
//            return null;
//        }

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(jsonObject.toString());
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input = "";
        String line;
        while ((line = in.readLine()) != null) {
            input += line;
        }

        System.out.println(input);
        in.close();
        return input;

    }
    String removeGift(long id) throws IOException {
        URL url = new URL("http://localhost:8080/gifts/remove/"+id);
        HttpURLConnection  connection = (HttpURLConnection )url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        if(connection.getResponseCode() != HttpStatus.OK.value()){
            System.out.println("\nremoveGift("+id+") error\n");
            return null;
        }


        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input = "";
        String line;
        while ((line = in.readLine()) != null) {
            input += line;
        }

        System.out.println(input);
        in.close();
        return input;
    }
    String updateGift(long id, String name, String desc) throws IOException {
        Gift gift = new Gift(id,name, desc);
        JSONObject jsonObject = new JSONObject(gift);
        System.out.println(jsonObject);

        URL url = new URL("http://localhost:8080/gifts/update/" + id);
        URLConnection  connection = url.openConnection();
//        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

//        if(connection.getResponseCode() != HttpStatus.OK.value()){
//            System.out.println("\nupdateGift("+id+", name, desc) error\n");
//            return null;
//        }

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(jsonObject.toString());
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input = "";
        String line;
        while ((line = in.readLine()) != null) {
            input += line;
        }

        System.out.println(input);
        in.close();
        return input;
    }
 }
