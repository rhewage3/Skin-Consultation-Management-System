import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient extends Person {
    private String patient_id;


    public Patient(String patient_id, String f_name, String s_name,String dob, String tel_no){
        super(f_name,tel_no, s_name,dob);
        this.patient_id = patient_id;
    }

    public void setPatient_id(String patient_id){
        this.patient_id = patient_id;
    }
    public String getPatient_id(){
        return patient_id;
    }


}
