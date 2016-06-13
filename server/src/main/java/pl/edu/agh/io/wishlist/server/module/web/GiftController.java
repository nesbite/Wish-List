package pl.edu.agh.io.wishlist.server.module.web;

import org.apache.http.HttpResponse;

public interface GiftController {

    HttpResponse giftGet();

    HttpResponse giftPut();

    HttpResponse giftDelete();

    HttpResponse giftPost();
}
