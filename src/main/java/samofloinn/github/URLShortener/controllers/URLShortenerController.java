package samofloinn.github.URLShortener.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import samofloinn.github.URLShortener.CodeObject;
import samofloinn.github.URLShortener.UrlObject;
import samofloinn.github.URLShortener.repositories.CodeRepository;
import samofloinn.github.URLShortener.repositories.UrlRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
        String newCode = !isNull(code) && !code.isEmpty() ? code : getCode(); //random code if code param is not set or is empty

        if (!isNull(id)) { //for future reference, if we ever wish to verify a user's ID for this step
            log.info("Check if I'm a verified ID!");
        }

        newCode = checkCode(newCode);
        codeRepository.saveAndFlush(new CodeObject(newCode));

        //quickly verify the string starts with http://
        url = httpCheck(url);

        //save to DB
        UrlObject newUrl = new UrlObject(newCode, url, new Date());
        urlRepository.saveAndFlush(newUrl);

        //in lieu of a ViewResolver
        return "";
    }

    // == go to URL from repo ==
    @GetMapping("/go/{code}")
    public void goToUrl(@PathVariable String code, HttpServletResponse httpServletResponse) {
        UrlObject matchingId = urlRepository.findOneByShortUrl(code);
        String longUrl = matchingId.getLongUrl();

        //update repo
        matchingId.click();
        urlRepository.save(matchingId);

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
        String chars = "123xyz"; //can expand this as needs be
        for (int i = 0; i < 6; i++) {
            code += chars.charAt(r.nextInt(chars.length()));
        }
        log.info("Created " + code);
        return code;
    }

    /*
     * checkCode(String code):
     * confirms the given code is not already assigned to another URL. If it is, generate a new one.
     */
    public String checkCode(String code) {
        if (codeRepository.findOneByCode(code) != null) {
            return checkCode(getCode());
        } //code isn't already in repo, so we can add it
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
