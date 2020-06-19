package samofloinn.github.UrlShortener.services.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import samofloinn.github.UrlShortener.entities.UrlMapping;
import samofloinn.github.UrlShortener.repositories.UrlRepository;
import samofloinn.github.UrlShortener.services.UrlShortenerService;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;

/**
 * UrlShortener Service Implementation. This class is responsible
 * The service class for the UrlShortener project
 * @author Sam O'Floinn (github.com/sam-o-floinn)
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    /**
     * Given a longUrl, creates an UrlMapping object for it with a code and saves it to the repository
     * @param longUrl the URL we want to map to our repository
     * @param shortUrl optional. Add if the user wants the code to be a specific code
     * @return an empty string. Should leave the user on the main page.
     */
    public String routeShortURL(@RequestParam String longUrl,
                                @RequestParam(required = false) final String shortUrl) {
        log.info("urlShortenerService: routeShortURL. longUrl = " + longUrl + ", shortUrl = " + shortUrl);

        String newShortUrl = validateShortUrl(shortUrl);

        //quickly verify the string starts with http://
        longUrl = httpCheck(longUrl);

        //save to DB
        UrlMapping newUrl = new UrlMapping(newShortUrl, longUrl, new Date());
        log.info("UrlShortenerService: saved repo at " + newUrl.getShortUrl() + ", " + newUrl.getLongUrl());
        urlRepository.save(newUrl);

        //in lieu of a ViewResolver
        return "";
    }

    /**
     * Given a shortUrl, redirects to the longUrl it is mapped to
     * @param shortUrl a string that matches some UrlMapping's shortUrl
     * @param httpServletResponse a redirect to the longUrl that shortUrl maps to
     */
    public void goToUrl(@PathVariable final String shortUrl, final HttpServletResponse httpServletResponse) {
        log.info("UrlShortenerServiceImpl: goToUrl. shortUrl = " + shortUrl);
        final UrlMapping matchingMapping = urlRepository.findOneByShortUrl(shortUrl);
        final String longUrl = matchingMapping.getLongUrl();

        //update repo
        matchingMapping.click();
        urlRepository.save(matchingMapping);

        httpServletResponse.setHeader("Location", longUrl);
        httpServletResponse.setStatus(302);
    }

    // == helper methods ==

    /**
     * generates a random 6-digit string of alphanumeric (lowercase) letters
     * @return the randomly generated string
     */
    @Override
    public String getRandomString() {
        Random r = new Random();

        String randomString = "";
        final String chars = "123xyz"; //can expand this as needs be
        for (int i = 0; i < 6; i++) {
            randomString += chars.charAt(r.nextInt(chars.length()));
        }
        log.info("Created " + randomString);
        return randomString;
    }

    /**
     * Verifies that a given shortUrl is unique. If not, it will use getRandomString() to create a new shortUrl
     * and try that instead
     * @param shortUrl the shortUrl to examine
     * @return a unique shortUrl
     */
    @Override
    public String validateShortUrl(String shortUrl) {
        if(urlRepository.findOneByShortUrl(shortUrl) != null || StringUtils.isEmpty(shortUrl)) {
            log.info("validateShortUrl: invalid or duplicate shortUrl given");
            return validateShortUrl(getRandomString());
        } //code isn't already in repo, so we can add it
        return shortUrl;
    }

    /**
     * Checks that a string starts with some type of 'http'. It it doesn't, adds 'http://' to it and returns it
     * @param url the string that should be a URL
     * @return either the string, or a new string that starts with 'http://'
     */
    public String httpCheck(String url) {
        return (!url.substring(0,7).equals("http://") && !url.substring(0,8).equals("https://")) ? "http://" + url : url;
    }

}
