package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "reviews")
public class Reviews implements Serializable {
    private static final long serialVersionUID = 1L;

    private int rating;
    private String comment;

    @Id
    @ManyToOne
    @JoinColumn(name = "ISBN")
    private Book books;

    @Id
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;


    public Reviews() {
    }

    public Reviews(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
