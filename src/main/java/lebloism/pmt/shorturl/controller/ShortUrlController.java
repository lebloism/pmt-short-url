package lebloism.pmt.shorturl.controller;

import lebloism.pmt.shorturl.dao.ShortUrlRepository;
import lebloism.pmt.shorturl.dto.ShortUrlCreationDto;
import lebloism.pmt.shorturl.dto.ShortUrlUpdateDto;
import lebloism.pmt.shorturl.exception.ShortUrlIdMismatchException;
import lebloism.pmt.shorturl.exception.ShortUrlNotFoundException;
import lebloism.pmt.shorturl.model.ShortUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RestController
@RequestMapping("/short_url")
public class ShortUrlController {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @GetMapping
    public Iterable findAll() {
        return shortUrlRepository.findAll();
    }

    @GetMapping("/{id}")
    public ShortUrl findOne(@PathVariable Long id) {
        return shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShortUrl create(@RequestBody ShortUrlCreationDto dto) {
        String shortUrlString = randomAlphabetic(8);
        ShortUrl shortUrl = new ShortUrl(shortUrlString, dto.getLongUrl());
        return shortUrlRepository.save(shortUrl);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
        shortUrlRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ShortUrl updateShortUrl(@RequestBody ShortUrlUpdateDto dto, @PathVariable Long id) {

        ShortUrl shortUrl = shortUrlRepository.findById(id)
                .orElseThrow(ShortUrlNotFoundException::new);
        if (dto.getShortUrl()!=null){
            shortUrl.setShortUrl(dto.getShortUrl());
        }
        if (dto.getLongUrl()!=null){
            shortUrl.setLongUrl(dto.getLongUrl());
        }
        return shortUrlRepository.save(shortUrl);
    }
}