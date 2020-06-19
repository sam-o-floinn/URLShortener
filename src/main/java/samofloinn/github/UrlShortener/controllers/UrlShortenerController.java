package samofloinn.github.UrlShortener.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import samofloinn.github.UrlShortener.services.impl.UrlShortenerServiceImpl;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller for the UrlShortener project. Handles the mapping and calls the appropriate Service methods
 * from UrlShortenerServiceImpl
 * @author Sam O'Floinn (github.com/sam-o-floinn) (samofloinn@gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
public class UrlShortenerController {

    @Autowired
    private UrlShortenerServiceImpl urlShortenerService;

    /**
     * routeShortUrl: A request to create an UrlMapping for a given website URL.
     * Sends the below args to UrlShortenerServiceImpl's method of the same name
     * @param longUrl a normal Url to make an UrlMapping object of
     * @param shortUrl optional param, a user-requested shortUrl to map to the given longUrl
     * @return
     */
    @GetMapping("/shorten")
    public String routeShortUrl(@RequestParam final String longUrl,
                                @RequestParam(required = false) final String shortUrl) {

        log.info("URLShortenerController: routeShortUrl()" + longUrl + ", shortUrl " + shortUrl);

        return urlShortenerService.routeShortURL(longUrl, shortUrl);
    }

    /**
     * goToUrl: a redirect request to go to the longUrl that matches the given shortUrl in our UrlRepository
     * @param shortUrl a shortUrl of which there is an UrlMapping object. Used to return a longUrl
     * @param httpServletResponse A httpServlet object. Will redirect us to the matching longUrl above
     */
    @GetMapping("/go/{shortUrl}")
    public void goToUrl(@PathVariable final String shortUrl, HttpServletResponse httpServletResponse) {
        log.info("URLShortenerController: goToUrl(). Code:" + shortUrl);
        urlShortenerService.goToUrl(shortUrl, httpServletResponse);
    }
}
