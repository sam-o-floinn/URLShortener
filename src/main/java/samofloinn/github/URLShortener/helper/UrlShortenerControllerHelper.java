package samofloinn.github.URLShortener.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import samofloinn.github.URLShortener.repositories.CodeRepository;
import samofloinn.github.URLShortener.repositories.UrlRepository;

import java.util.Random;

@Slf4j
public class UrlShortenerControllerHelper {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private CodeRepository codeRepository;



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

//    public void listUrlObjects() {
//        log.info("listUrlObjects()");
//        List<UrlObject> allUrls = urlRepository.findAll();
//        for (UrlObject url: allUrls) {
//            log.info("- URL " + url.getLongUrl() + ", code " + url.getShortUrl());
//        }
//    }
//
//    @RequestMapping({"home"})
//    @GetMapping("home")
//    public String test(Model model) {
//        log.info("test()");
//        model.addAttribute("msg", "Message Confirmed");
//        return "myURL";
//    }
}
