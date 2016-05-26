package pl.edu.agh.io.wishlist.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private MessageSource messages;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private RedirectStrategy redirectStrategy;


    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
//        setDefaultFailureUrl("/login?error=true");

//        super.onAuthenticationFailure(request, response, exception);

//        final Locale locale = localeResolver.resolveLocale(request);
//
//        String errorMessage = messages.getMessage("message.badCredentials", null, locale);
//
//        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
//            errorMessage = messages.getMessage("auth.message.disabled", null, locale);
//        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
//            errorMessage = messages.getMessage("auth.message.expired", null, locale);
//        } else if (exception.getMessage().equalsIgnoreCase("blocked")) {
//            errorMessage = messages.getMessage("auth.message.blocked", null, locale);
//        }
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With, Origin");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
//        handle(request, response);
    }

    protected void handle(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String targetUrl = "/login?error=true";

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}