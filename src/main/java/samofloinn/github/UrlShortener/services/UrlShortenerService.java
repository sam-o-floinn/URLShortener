package samofloinn.github.UrlShortener.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Interface for the project's primary Service
 * @author Sam O'Floinn (samofloinn@gmail.com)
 * @version 1.0
 * @since 1.0
 */
public interface UrlShortenerService {

    public String routeShortURL(@RequestParam String longUrl,
                                @RequestParam(required = false) String shortUrl);

    public void goToUrl(@PathVariable String code, HttpServletResponse httpServletResponse);

    public String getRandomString();

    public String validateShortUrl(String shortUrl);

    public String httpCheck(String url);
}
