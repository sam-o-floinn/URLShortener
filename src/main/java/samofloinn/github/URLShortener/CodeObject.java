package samofloinn.github.URLShortener;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CodeObject {

    // == fields ==
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    private String code;

    // == constructor ==
    public CodeObject(String code) {
        this.code = code;
    }

    public CodeObject() {
    }
}
