package samofloinn.github.URLShortener.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import samofloinn.github.URLShortener.CodeObject;
import samofloinn.github.URLShortener.UrlObject;
import samofloinn.github.URLShortener.repositories.CodeRepository;
import samofloinn.github.URLShortener.repositories.UrlRepository;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.util.Objects.isNull;

@Slf4j
@Controller
public class URLShortenerController {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private CodeRepository codeRepository;

    @RequestMapping("/shorten")
    @GetMapping("/shorten")
    public String routeShortURL(@RequestParam String url,
                                @RequestParam(required = false) String code,
                                @RequestParam(required = false) String id) {
        log.info("URL to shorten(): " + url);
        log.info("AAAAAAAA");
        boolean goodCode = false;
        String newCode = !isNull(code) && !code.isEmpty() ? code : getCode(); //random code if code param is not set or is empty

        if(!isNull(code)) {
            log.info("Hi code: " + code);
        }
        if (!isNull(id)) {
            log.info("Check if I'm a verified ID!");
        }

        checkCode(newCode);
        codeRepository.saveAndFlush(new CodeObject(newCode));

        url = httpCheck(url);
        log.info("Url after httpCheck: " + url);

        UrlObject newUrl = new UrlObject(newCode, url, new Date());
        urlRepository.saveAndFlush(newUrl);

        log.info("short code for " + newUrl.getLongUrl() + " is " + newUrl.getShortUrl());
        return "";
    }

    // == go to URL from repo ==
    @GetMapping("/go/{code}")
    public void goToUrl(@PathVariable String code, HttpServletResponse httpServletResponse) {
        log.info("goToUrl(). Code = " + code);
        log.info("Test for Docker!");
        UrlObject matchingId = urlRepository.findOneByShortUrl(code);

        if (matchingId == null) log.info("no ID matches");

        String longUrl = matchingId.getLongUrl();

        log.info("We got the longUrl " + longUrl);

        matchingId.click();
        matchingId = urlRepository.save(matchingId);
        log.info("Url clicked " + matchingId.getTimesClicked());
        log.info(code + " is taking us to " + longUrl);

        httpServletResponse.setHeader("Location", longUrl);
        httpServletResponse.setStatus(302);
    }



    // == helper methods ==
    /*
     * getCode():
     * generates a random code among the chars to use as a URL reference
     */
    public String getCode() {
        Random r = new Random();

        String code = "";
        String chars = "123xyz";
        for (int i = 0; i < 6; i++) {
            code += chars.charAt(r.nextInt(chars.length()));
        }
        log.info("Created " + code);
        return code;
    }

    /*
        checkCode(String code):
        confirms the given code is not already assigned to another URL. If it is, generate a new one.
     */
    public String checkCode(String code) {
        if (codeRepository.findOneByCode(code) != null) {
            log.info("We already have this code " + code + " in repository: " + codeRepository.findOneByCode(code).getCode());
            return checkCode(getCode());
        }
        log.info("We can add this code, " + code);
        return code;
    }

    /*
     * httpCheck(String url):
     * checks the given URL starts with 'http://' or 'https://'. Added to make implementation easier so links like 'www.google.com' work
     */
    public String httpCheck(String url) {
        return (!url.substring(0,7).equals("http://") && !url.substring(0,8).equals("https://")) ? "http://" + url : url;
    }
}
