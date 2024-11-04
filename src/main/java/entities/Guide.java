package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "guides")
@Getter
@Setter
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int yearsOfExperience;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL)
    private List<Trip> trips;

    public Guide(String firstName, String lastName, String email, String phone, int yearsOfExperience, List<Trip> trips) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
        this.trips = trips;
    }

    public Guide() {

    }
}




