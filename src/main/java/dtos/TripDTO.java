package dtos;

import Enum.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double longitude;
    private double latitude;
    private String name;
    private double price;
    private Category category;


    public TripDTO(LocalDateTime startTime, LocalDateTime endTime, double longitude, double latitude, String name, double price, Category category) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}





