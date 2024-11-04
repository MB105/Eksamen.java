package entities;

import Enum.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    public Trip(LocalDateTime startTime, LocalDateTime endTime, double longitude, double latitude, String name, double price, Category category, Guide guide) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }

    public Trip() {

    }




}



