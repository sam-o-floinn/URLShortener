package samofloinn.github.URLShortener;

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

    private String code;

    // == constructor ==
    public CodeObject(String code) {
        this.code = code;
    }

    public CodeObject() {

    }

    // == getters ==
    public String getCode() {
        return code;
    }

    // == setters ==

    public void setCode(String code) {
        this.code = code;
    }
}
