package lebloism.pmt.shorturl.controller;

import lebloism.pmt.shorturl.dao.ShortUrlRepository;
import lebloism.pmt.shorturl.exception.ShortUrlNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

/**
 * To redirect to asked url
 */
@RestController
@RequestMapping("/")
public class ProxyController {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    /**
     * Redirect to the page associated to the short url
     *
     * @param shortUrl the short url (string)
     * @return the page associated to the short url
     * @throws ShortUrlNotFoundException if not found
     */
    @GetMapping("/{shortUrl}")
    public RedirectView goTo(@PathVariable String shortUrl) {
        String longUrl = shortUrlRepository.findByShortUrl(shortUrl)
                .orElseThrow(ShortUrlNotFoundException::new).getLongUrl();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(longUrl);
        return redirectView;
    }
}
