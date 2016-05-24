package pl.edu.agh.io.wishlist.server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public  class HttpsRedirectStrategy extends DefaultRedirectStrategy {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response,
                             String redirectUrl) throws IOException {


    }
}
