import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class WestminsterSkinConsulAutomatedTester {

    ArrayList<Doctor> doc_list;
    private final PrintStream out = System.out;
    //new output stream to capture console prints
    private final ByteArrayOutputStream out_s_capture = new ByteArrayOutputStream();

    @BeforeEach
    public void startup(){
        //setting a custom output stream
        System.setOut(new PrintStream(out_s_capture));
        //removing all elements from the arraylist
        doc_list = new ArrayList<>();
    }

    @AfterEach
    public void cutoff(){
        //resetting default output stream after each test
        System.setOut(out);
    }


    @Test
    void addDoctor(){
        //checking the arraylist size after adding a doctor
        doc_list = WestminsterSkinConsultationManager.doc_list;
        WestminsterSkinConsultationManager.add_doctor(new Doctor("DR3","Daniel","Ricciardo","cosmetic", "1989.7.1","0465645612"));
        assertEquals(1,doc_list.size());

    }

    @Test
    void print_Doctor_list_that_is_null(){
        //checking that whether the program identify when the arraylist is empty and showing the message that there are no doc list to print
        WestminsterSkinConsultationManager.printDoctors();
        assertEquals("There are No Doctors to Print", out_s_capture.toString().trim());

    }

    @Test
    void print_Doc_list(){
        doc_list = WestminsterSkinConsultationManager.doc_list;
        doc_list.add(new Doctor("DR3","Daniel","Ricciardo","cosmetic","1989.7.1","1234567892"));
        WestminsterSkinConsultationManager.printDoctors();
        String Expected = """
                License No: DR3
                First Name: Daniel
                Surname: Ricciardo
                Tel No: 1234567892
                Date of Birth: 1989.7.1
                Specialisation: cosmetic""";
        assertEquals(Expected,out_s_capture.toString().trim());
    }


    @Test
    void gui_run(){
        WestminsterSkinConsultationManager.run_gui(doc_list);
        String Expected2 = "GUI Running Perfectly";
        assertEquals(Expected2,out_s_capture.toString().trim());
    }

    @Test
    void save_and_load(){
        doc_list = WestminsterSkinConsultationManager.doc_list;
        //adding a doctor
        doc_list.add(new Doctor("DR3","Daniel","Ricciardo","cosmetic","1989.7.1","1234567892"));
        //saving the added doctor to the file
        WestminsterSkinConsultationManager.save();
        //resetting the arraylist
        doc_list = new ArrayList<>();
        //loading the doctor from th file
        WestminsterSkinConsultationManager.load_doc_list(doc_list);
        //checking the saved doctors license number and loaded doctors medical no
        assertEquals("DR3", doc_list.get(0).getM_licence());
    }


    @Test
    void delete_doc(){
        doc_list.add(new Doctor("DR3","Daniel","Ricciardo","cosmetic","1989.7.1","1234567892"));
        WestminsterSkinConsultationManager.deleteDoctor(doc_list.get(0).getM_licence());
        String expected = """
                Medical licence Number: DR3
                Name: Daniel Ricciardo
                Mobile Number: 1234567892
                Date of Birth: 1989.7.1
                Specialisation: cosmetic
                
                Doctor deleted from the List""";
    }








}
