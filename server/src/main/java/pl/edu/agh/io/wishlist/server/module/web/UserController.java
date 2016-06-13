package pl.edu.agh.io.wishlist.server.module.web;

import org.apache.http.HttpResponse;

public interface UserController {

    HttpResponse userGet();

    HttpResponse userPut();

    HttpResponse userDelete();

    HttpResponse userPost();
}
