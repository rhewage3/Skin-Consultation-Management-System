import javax.print.Doc;

public class Doctor extends Person {
    private String m_licence;
    private String specialisation;


    public Doctor (String m_licence,String f_name, String s_name, String specialisation,  String dob, String tel_no){
        super(f_name, s_name, tel_no, dob);
        this.m_licence = m_licence;
        this.specialisation = specialisation;
    }



    public void setM_licence(String m_licence){
        this.m_licence = m_licence;
    }

    public String getM_licence(){
        return m_licence;
    }

    public void setSpecialisation(String specialisation){
        this.specialisation = specialisation;
    }

    public String getSpecialisation(){
        return specialisation;
    }


}
