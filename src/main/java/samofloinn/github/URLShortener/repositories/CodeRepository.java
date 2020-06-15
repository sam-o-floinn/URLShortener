package samofloinn.github.URLShortener.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import samofloinn.github.URLShortener.CodeObject;

@RepositoryRestResource(collectionResourceRel = "codes", path = "codes")
public interface CodeRepository extends JpaRepository<CodeObject, Long> {
    CodeObject findOneByCode(@Param("code") String code);
}
