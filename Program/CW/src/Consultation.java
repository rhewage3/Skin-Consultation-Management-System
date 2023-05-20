import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Consultation {
    private int cost;
    private String id;
    private LocalDateTime dateTime;
    private String notes;
    private Doctor doctor;
    private Patient patient;


    public Consultation (String id,LocalDateTime dateTime, int cost,Doctor doctor, Patient patient, String notes ){
        this.id = id;
        this.dateTime = dateTime;
        this.cost = cost;
        this.notes = notes;
        this.doctor = doctor;
        this.patient = patient;

    }

    public String getId(){return id;}
    public Doctor getDoctor(){return doctor;};
    public Patient getPatient(){return patient;};
    public LocalDateTime getDateTime(){return dateTime;};
    public int getCost(){return cost;}
    public String getNotes(){return notes;}
}



