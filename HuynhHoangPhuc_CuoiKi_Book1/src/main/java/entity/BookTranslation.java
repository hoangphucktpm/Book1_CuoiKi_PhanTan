package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Getter
@Setter
@Table(name = "book_translations")
public class BookTranslation extends Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "translate_name")
    private String translateName;
    private String language;

    public BookTranslation() {
    }

    public BookTranslation(String translateName, String language) {
        this.translateName = translateName;
        this.language = language;
    }

    @Override
    public String toString() {
        return "BookTranslation{" +
                "translateName='" + translateName + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
