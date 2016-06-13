package pl.edu.agh.io.wishlist.server.module.web;


import org.apache.http.HttpResponse;

public interface FriendController {

    HttpResponse friendGet();

    HttpResponse friendPut();

    HttpResponse friendDelete();

    HttpResponse friendPost();
}
