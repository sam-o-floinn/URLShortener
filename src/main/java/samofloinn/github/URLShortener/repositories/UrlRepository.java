package samofloinn.github.URLShortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import samofloinn.github.URLShortener.UrlObject;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "urls", path = "urls")
public interface UrlRepository extends JpaRepository<UrlObject, Long> {
    List<UrlObject> findByShortUrl(@Param("shortUrl") String shortUrl);
    UrlObject findOneByShortUrl(@Param("shortUrl") String shortUrl);
}
