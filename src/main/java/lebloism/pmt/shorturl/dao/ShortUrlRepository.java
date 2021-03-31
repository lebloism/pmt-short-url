package lebloism.pmt.shorturl.dao;

import lebloism.pmt.shorturl.model.ShortUrl;
import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortUrl, Long> {
}
