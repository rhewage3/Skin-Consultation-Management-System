import java.util.ArrayList;
import java.time.LocalDate;

public class Person {
    private String f_name;
    private String s_name;
    private String dob;
    private String tel_no;

    public Person(String f_name, String s_name, String tel_no, String dob){
        this.tel_no = tel_no;
        this.f_name = f_name;
        this.s_name = s_name;
        this.dob = dob;
    }



    public void setF_name(String f_name){
        this.f_name = f_name;
    }
    public String getF_name(){
        return f_name;
    }

    public void setS_name(String s_name){
        this.s_name = s_name;
    }

    public String getS_name(){
        return s_name;
    }

    public void setDob(String dob){
        this.dob = dob;
    }
    public String getDob(){
        return dob;
    }

    public void setTel_no(String tel_no){
        this.tel_no = tel_no;
    }
    public String getTel_no(){return tel_no;}



}
