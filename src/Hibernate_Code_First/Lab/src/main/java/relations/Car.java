package relations;

import entities.Vehicle;
import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //creating a join column and inserting it inside plate_numbers table in DB
    @OneToOne
    @JoinColumn(name = "plate_number_id",referencedColumnName = "id")
    private PlateNumber plateNumber;

    public Car(){}

    public Car(PlateNumber number){
        this.plateNumber = number;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlateNumber getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(PlateNumber plateNumber) {
        this.plateNumber = plateNumber;
    }
}
