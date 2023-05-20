import javax.crypto.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Gui extends JFrame implements ActionListener {

    JPanel body, table_panel, head, date_clock,  patient_panel1,patient_panel2, patient_form_panel,form_wrap_panel,patient_table_panel;
    JLabel header1, header2, pic, description, sorter_description1, sorter_description2, patient_label1, patient_label2,patient_text, warning_text;
    JButton sd_button, home_button,date_time_submit, fill_patient_details_btn ,p_img_Button,patient_submit_button,see_patient_list,back_home;
    JTable table,patient_table;
    JScrollPane scrollPane,patient_pane;
    ArrayList<Doctor> doctors = new ArrayList<>();
    ArrayList<Consultation> consultationArrayList = new ArrayList<Consultation>();
    DefaultTableModel model, patient_table_model;
    Integer doctor_no;
    JSpinner select_date,patient_dob;
    LocalDateTime user_date,con_dob,temp_consuldate;
    JTextField tf_surname, tf_name,tf_mobile,tf_id,tf_notes;
    int chosen_d;
    File[] image_array;
    String temp_choosen_doc_license;
    static ArrayList<Patient> patient_list = new ArrayList<>();
    static SecretKey s_key;

    public Gui(ArrayList<Doctor> doc_list) {

        System.out.println("GUI Running Perfectly");
        load_patient_list(patient_list);

        //assigning the console arraylist to a new arraylist in the gui class
        doctors = doc_list;
        //home page picture
        ImageIcon image1 = new ImageIcon("doctor.png");
        setLayout(new BorderLayout());

        patient_table_panel = new JPanel();
        patient_table_panel.setLayout(null);





        patient_table_model = new DefaultTableModel();
        patient_table_model.addColumn("First Name");
        patient_table_model.addColumn("Surname");
        patient_table_model.addColumn("Date of Birth");
        patient_table_model.addColumn("Mobile Number");
        patient_table_model.addColumn("Patient ID");

        patient_table = new JTable(patient_table_model);
        patient_table.setAutoCreateRowSorter(true);
        patient_table.getTableHeader().setReorderingAllowed(false);

        patient_pane = new JScrollPane(patient_table);
        patient_pane.setBounds(100, 100, 900, 300);

        patient_table.getModel();

        Object[] row_pat = new Object[5];
        for (Patient patient : patient_list){
            row_pat[0] = patient.getF_name();
            row_pat[1] = patient.getS_name();
            row_pat[2] = patient.getDob();
            row_pat[3] = patient.getTel_no();
            row_pat[4] = patient.getPatient_id();
            patient_table_model.addRow(row_pat);
        }


        back_home = new JButton("Go Back Home");
        back_home.setBounds(500,400,200,40);
        back_home.setFont(new Font("COMIC SANS", Font.BOLD,18));
        back_home.setFocusable(false);
        back_home.setBorder(new LineBorder(Color.black, 0, true));
        back_home.addActionListener(this);






        //creating a model for the table
        model = new DefaultTableModel();
        model.addColumn("Medical License");
        model.addColumn("First Name");
        model.addColumn("Surname");
        model.addColumn("Specialisation");
        model.addColumn("Mobile Number");
        model.addColumn("Date of Birth");

        //creating a Jtable and adding the model
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);

        //adding the created table to a scrollPAne
        scrollPane = new JScrollPane(table);
//        scrollPane.setSize(900,300);
        scrollPane.setBounds(100, 100, 900, 300);
        table.setFillsViewportHeight(true);


        //adding the data to the table
        table.getModel();
        Object[] rowData = new Object[6];
        for (Doctor doctor : doc_list) {
            rowData[0] = doctor.getM_licence();
            rowData[1] = doctor.getF_name();
            rowData[2] = doctor.getS_name();
            rowData[3] = doctor.getSpecialisation();
            rowData[4] = doctor.getTel_no();
            rowData[5] = doctor.getDob();
            model.addRow(rowData);
        }

        //created a mouse listener to the table to know what row the user click
        table.addMouseListener(new TableSelect(doc_list));






        //creating a panel that hold the title name
        head = new JPanel();
//        head.setBackground(Color.cyan);
        head.setPreferredSize(new Dimension(100, 120));
        head.setLayout(null);


        //creating a panel that hold the home page labels
        body = new JPanel();
//        body.setBackground(Color.black);
        body.setLayout(null);
        body.setPreferredSize(new Dimension(100, 120));

        //creating a panel that hold the date and time selector
        date_clock = new JPanel();
        date_clock.setLayout(null);
        date_clock.setPreferredSize(new Dimension(500,400));
        date_clock.setVisible(true);
//        date_clock.setBackground(Color.red);

        patient_panel1 = new JPanel();
        patient_panel1.setLayout(null);
        patient_panel1.setPreferredSize(new Dimension(1150,600));
        patient_panel1.setVisible(false);
        patient_panel1.setBackground(Color.BLUE);
        patient_panel1.setVisible(true);

        patient_panel2 = new JPanel();
        patient_panel2.setLayout(null);
        patient_panel2.setPreferredSize(new Dimension(1150,600));
        patient_panel2.setVisible(false);
//        patient_panel2.setBackground(Color.red);
        patient_panel2.setVisible(true);




        JLabel date_clock_label1 = new JLabel("Please Enter The Date and Time for the Consultation");
        date_clock_label1.setBounds(350, 150, 600,40);
        date_clock_label1.setFont(new Font("ARIAL",Font.PLAIN,18));

        JLabel date_clock_label2 = new JLabel("Year : Month : Date : Hour");
        date_clock_label2.setBounds(450,180, 500,40);
        date_clock_label2.setFont(new Font("ARIAL", Font.PLAIN,18));

        //setting today date and from that setting the tomorrow's date
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        //creating a date model to select the date and time that user can select
        select_date = new JSpinner(new SpinnerDateModel(tomorrow,today,null, Calendar.MONTH));
        //setting the format of the date and time selector
        JSpinner.DateEditor editor = new JSpinner.DateEditor(select_date,"yyyy/MM/dd/HH:00");
        select_date.setEditor(editor);
        select_date.setBounds(425,220,270,50);
        select_date.setFont(new Font("MONOSPACED",Font.BOLD,25));
        //creating a button to check the availability of the doctor at the entered time by the user
        date_time_submit = new JButton("Check Availability");
        date_time_submit.setBounds(435,280,250,50);
        date_time_submit.setFocusable(false);
        date_time_submit.setFont(new Font("COMIC SANS", Font.BOLD, 18));
        date_time_submit.setBorder(new LineBorder(Color.black, 0, true));
        date_time_submit.addActionListener(this);






        table_panel = new JPanel();
        table_panel.setLayout(null);
        table_panel.setPreferredSize(new Dimension(500, 400));
        table_panel.setVisible(true);

//        table_panel.setBackground(Color.GREEN);


        fill_patient_details_btn = new JButton("OK,Fill the Form");
        fill_patient_details_btn.setFocusable(false);
        fill_patient_details_btn.setFont(new Font("COMIC SANS", Font.BOLD, 18));
        fill_patient_details_btn.setBorder(new LineBorder(Color.black, 0, true));
        fill_patient_details_btn.setBounds(450, 300, 200, 40);
        fill_patient_details_btn.addActionListener(this);
        fill_patient_details_btn.addActionListener(this);





        //panel that has the form that patient fill
        patient_form_panel = new JPanel();
//        patient_form_panel.setLayout(new GridLayout(7,2,0,5));
        patient_form_panel.setLayout(null);
        patient_form_panel.setPreferredSize(new Dimension(1150,600));
//        patient_form_panel.setBackground(Color.cyan);
        patient_form_panel.setFont(new Font("ARIAL", Font.PLAIN,16));
        patient_text = new JLabel("Fill the below blank fields to place your appointment");
        patient_text.setFont(new Font("ARIAL",Font.BOLD,18));
        patient_text.setBounds(180,70,600,40);
        //creating a panel that wraps the form
        form_wrap_panel = new JPanel();
        form_wrap_panel.setLayout(null);
        form_wrap_panel.setBounds(50,40,500,250);
//        form_wrap_panel.setPreferredSize(new Dimension(600,250));
        form_wrap_panel.setBorder(new EmptyBorder(0,25,25,25));
        //setting the text fields
        JLabel s_namelabel = new JLabel("Enter Patient Surname: ");
        s_namelabel.setBounds(280,120,200,20);
        tf_surname = new JTextField();
        tf_surname.setBounds(600,120,200,20);
        JLabel namelabel = new JLabel("Enter patient first name: ");
        namelabel.setBounds(280,150,200,20);
        tf_name = new JTextField();
        tf_name.setBounds(600,150,200,20);
        JLabel moblabel = new JLabel("Enter Mobile Number: ");
        moblabel.setBounds(280,180,200,20);
        tf_mobile = new JTextField();
        tf_mobile.setBounds(600,180,200,20);
        JLabel id_label = new JLabel("Enter ID: ");
        id_label.setBounds(280,210,100,20);
        tf_id = new JTextField();
        tf_id.setBounds(600,210,200,20);
        JLabel dob_label = new JLabel("Enter Birthday: ");
        dob_label.setBounds(280,240,100,20);
        //jspiner for patient dob
        patient_dob = new JSpinner(new SpinnerDateModel(new Date(473385600000L), new Date(-2208988800000L), today,Calendar.MONTH));
        patient_dob.setPreferredSize(new Dimension(220,50));
        patient_dob.setBounds(600,240,200,20);
        //dob input format
        JSpinner.DateEditor editor2 = new JSpinner.DateEditor(patient_dob, "yyyy/MM/dd");
        patient_dob.setEditor(editor2);
        JLabel P_noteslabel = new JLabel("Enter any Notes");
        P_noteslabel.setBounds(280,270,100,20);
        tf_notes = new JTextField();
        tf_notes.setBounds(600,270,200,100);
        JLabel P_imagelabel = new JLabel("Upload any images of the skin:");
        P_imagelabel.setBounds(280,390,200,20);
        p_img_Button = new JButton("Browse");
        p_img_Button.setBounds(600,390,100,20);
        p_img_Button.setFocusable(false);
        p_img_Button.setBorder(new LineBorder(Color.black, 0, true));
        p_img_Button.addActionListener(this);

        patient_submit_button = new JButton("Submit");
        patient_submit_button.setBounds(420,450,200,50);
        patient_submit_button.setFont(new Font("ARIAL", Font.BOLD,24));
        patient_submit_button.setFocusable(false);
        patient_submit_button.setBorder(new LineBorder(Color.black, 0, true));
        patient_submit_button.addActionListener(this);

        warning_text = new JLabel("Please Fill all the Blank Fields");
        warning_text.setForeground(Color.RED);
        warning_text.setBounds(280,400,200,50);
        warning_text.setVisible(false);


        patient_form_panel.add(s_namelabel);
        patient_form_panel.add(tf_surname);
        patient_form_panel.add(namelabel);
        patient_form_panel.add(tf_name);
        patient_form_panel.add(moblabel);
        patient_form_panel.add(tf_mobile);
        patient_form_panel.add(id_label);
        patient_form_panel.add(tf_id);
        patient_form_panel.add(P_noteslabel);
        patient_form_panel.add(tf_notes);
        patient_form_panel.add(P_imagelabel);
        patient_form_panel.add(p_img_Button);
        patient_form_panel.add(dob_label);
        patient_form_panel.add(patient_dob);
        patient_form_panel.add(patient_text);
        patient_form_panel.add(patient_submit_button);
        patient_form_panel.add(warning_text);










        sorter_description1 = new JLabel("Click the Column title to sort the List Alphabetically");
        sorter_description1.setBounds(50,15,600,40);
        sorter_description1.setFont(new Font("INTER", Font.PLAIN, 18));

        sorter_description2 = new JLabel("Select the Row of the Doctor to add Consultation");
        sorter_description2.setBounds(50,50,600,40);
        sorter_description2.setFont(new Font("INTER", Font.PLAIN, 18));



        header1 = new JLabel("WESTMINSTER");
        header1.setBounds(470, 2, 200, 100);
        header1.setFont(new Font("MONOSPACED", Font.PLAIN, 29));

        header2 = new JLabel("SKIN CONSULTATION MANAGER");
        header2.setBounds(305, 30, 600, 100);
        header2.setFont(new Font("MONOSPACED", Font.BOLD, 35));

        pic = new JLabel(image1);
        pic.setBounds(100, 0, 500, 500);


        description = new JLabel("Welcome,To know about our Doctors \n press Doctor List button");
        description.setBounds(590, 150, 600, 40);
        description.setBounds(590, 150, 600, 40);
        description.setFont(new Font("INTER", Font.PLAIN, 18));

        sd_button = new JButton("See Doctor List");
        sd_button.setFocusable(false);
        sd_button.setFont(new Font("COMIC SANS", Font.BOLD, 18));
        sd_button.setBorder(new LineBorder(Color.black, 0, true));
        sd_button.setBounds(730, 250, 200, 40);
        sd_button.addActionListener(this);

        see_patient_list = new JButton("Patient List");
        see_patient_list.setBounds(730,300,200,40);
        see_patient_list.setFont(new Font("COMIC SANS", Font.BOLD,18));
        see_patient_list.setFocusable(false);
        see_patient_list.setBorder(new LineBorder(Color.black, 0, true));
        see_patient_list.addActionListener(this);



        home_button = new JButton("Home");
        home_button.setFocusable(false);
        sd_button.setFont(new Font("COMIC SANS", Font.BOLD, 18));
        sd_button.setBorder(new LineBorder(Color.black, 0, true));
        home_button.setBounds(450, 420, 200, 40);
        home_button.addActionListener(this);

//        patient_label1 = new JLabel("Sorry, Dr." + doctors.get(chosen_d).getS_name()+"is not available at the chosen time. Dr." + doctors.get(doctor_no).getS_name() + "will see you on the chosen time");


//        patient_label2 = new JLabel("Dr." + doctors.get(doctor_no).getS_name()+ "is available on the selected date");




        body.add(description);
        head.add(header1);
        head.add(header2);
        body.add(pic);
        body.add(sd_button);
        body.add(see_patient_list);
        table_panel.add(home_button);
        table_panel.add(sorter_description1);
        table_panel.add(sorter_description2);
        table_panel.add(scrollPane);
        date_clock.add(date_clock_label1);
        date_clock.add(date_clock_label2);
        date_clock.add(select_date);
        date_clock.add(date_time_submit);
        patient_table_panel.add(patient_pane);
        patient_table_panel.add(back_home);








        add(head, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sd_button) {
            System.out.println("KNOCK KNOCK 1");
            head.setVisible(false);
            body.setVisible(false);
            add(table_panel);
            revalidate();
            repaint();

        }
        else if (e.getSource() == home_button) {
            System.out.println("KNOCK KNOCK 2");
            head.setVisible(true);
            body.setVisible(true);
            table_panel.setVisible(false);

        }
        else if (e.getSource() == date_time_submit){
            //assigning the date and time to a temporary variable
            Date t_date_time = (Date)select_date.getValue();
            //converting the given time to the local-date-time api format
            user_date = t_date_time.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            //changing minutes, seconds and nanoseconds to zero
            user_date = user_date.withMinute(0).withSecond(0).withNano(0);
            date_clock.setVisible(false);

            //created an m_consult to check weather the needed doctor free in the given time
            Boolean consult_match;
            //calling th consult_check method to check weather there is similar consultation at the time
            consult_match = consult_check();

            if (consult_match){
                System.out.println("Doctor is Busy");
                //assigning a random doctor because the chosen doctor is busy
                chosen_d = doctor_no;
                //setting the maximum no, min no and range
                int max = doctors.size() - 1;
                int min = 0;
                int range = max - min + 1;
                //creating a random number between the made range
                int rand = (int)(Math.random() * range) + min;
                doctor_no = rand;

                //checking whether the new random doctor is equal to same chosen one
                while (chosen_d == doctor_no || consult_check()){
                    //if the previous doctor no same as the random doc no seting a new random doctor no
                    rand = (int)(Math.random() * range) + min;
                    doctor_no = rand;
                }
                //telling user that chosen doctor is not available and assigned a random doctor


                patient_label2 = new JLabel("sorry, Dr. " + doctors.get(chosen_d).getS_name()+ "is not available on the selected date. Dr. " + doctors.get(doctor_no).getS_name() + "will see you on the selected date");
                patient_label2.setBounds(150,200,800,50);
                patient_label2.setFont(new Font("ARIAL", Font.BOLD, 24));

                patient_panel1.add(fill_patient_details_btn);
                patient_panel1.add(patient_label1);
                add(patient_panel1);
//                patient_label1.setText("Sorry, Dr." + doctors.get(chosen_d).getS_name()+"is not available at the chosen time. Dr." + doctors.get(doctor_no).getS_name() + "will see you on the chosen time");

            }else{

                patient_label2 = new JLabel("Dr." + doctors.get(doctor_no).getS_name()+ " is available on the selected date");
                patient_label2.setBounds(320,200,800,50);
                patient_label2.setFont(new Font("ARIAL", Font.BOLD, 24));

                patient_panel2.add(fill_patient_details_btn);
                patient_panel2.add(patient_label2);
                add(patient_panel2);
                revalidate();
                repaint();
            //                patient_label2.setText("Dr." + doctors.get(doctor_no).getS_name()+ "is available on the selected date");


            }


        }else if (e.getSource() == fill_patient_details_btn){
            patient_panel1.setVisible(false);
            patient_panel2.setVisible(false);
            add(patient_form_panel);
        }else if (e.getSource() == p_img_Button){
            //file choose window
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            //allowing to select multiple files
            fileChooser.setMultiSelectionEnabled(true);
            //restricitng file types
            fileChooser.setAcceptAllFileFilterUsed(false);
            //title
            fileChooser.setDialogTitle("Upload Images");
            //only images
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only Images", "jpg","png","jpeg");
            fileChooser.addChoosableFileFilter(restrict);
            //saving
            int r = fileChooser.showSaveDialog(null);

            if(r == JFileChooser.APPROVE_OPTION){
                image_array = fileChooser.getSelectedFiles();
                for(int i=0; i < image_array.length; i++){}
            }

        }else if (e.getSource() == patient_submit_button){

            warning_text.setVisible(false);
            Boolean error_check = false;
            //checking whether there are any blank fields

            if (tf_surname.getText().isEmpty()){
                error_check = true;
                error_pop_up(true);
            }else if (tf_name.getText().isEmpty()){
                error_check = true;
                error_pop_up(true);
            }else if (tf_id.getText().isEmpty()){
                error_check = true;
                error_pop_up(true);
            }else if (tf_mobile.getText().isEmpty()){
                error_check = true;
                error_pop_up(true);
            }else if (tf_notes.getText().isEmpty()){
                error_check = true;
                error_pop_up(true);

            }else{



                patient_form_panel.setVisible(false);



                //getting data from the textfields
                String con_fname = tf_name.getText();
                String con_sname = tf_surname.getText();
                String con_id = tf_id.getText();
                String con_mob_no = tf_mobile.getText();
                String con_notes = tf_notes.getText();

                Date tmep_dob = (Date)patient_dob.getValue();
                con_dob = tmep_dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime currentLocaldatetime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String formatted_dob = currentLocaldatetime.format(formatter);
                int cost= 25;
                String doc =doctors.get(doctor_no).getS_name();




                Patient p1 = new Patient(con_id,con_fname,con_sname,formatted_dob,con_mob_no);
                add_patient_list(p1);


                add(patient_table_panel);





            }

        }else if(e.getSource() == see_patient_list){
            add(patient_table_panel);
            body.setVisible(false);
            head.setVisible(false);
        }else if (e.getSource() == back_home){
            patient_table_panel.setVisible(false);
            body.setVisible(true);
            head.setVisible(true);

        }

    }

    private void add_patient_list(Patient p1) {
        patient_list.add(p1);
        save_patient();
        System.out.println("patients saved!");

    }

    private void save_patient() {
        try{
            FileWriter writer = new FileWriter("patient.txt");
            for (int i = 0;i < patient_list.size();i++){
                //writing data
                writer.write(patient_list.get(i).getF_name()+ "--"+ patient_list.get(i).getS_name()+ "--"+patient_list.get(i).getPatient_id()+"--"+ patient_list.get(i).getDob()+"--"+ patient_list.get(i).getTel_no()+"\n");

            }
            writer.close();
            System.out.println("saved successfully");

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }

    private static void load_patient_list (ArrayList<Patient> patient_list){
        try{
            File file = new File("patient.txt");
            if (file.exists()){
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()){
                    String [] data = reader.nextLine().split("--");
                    patient_list.add(new Patient(data[0],data[1],data[2],data[3],data[4]));
                }
                reader.close();
                System.out.println("succesfully loaded");
            }else {
                System.out.println("NO saved data");
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }


    private void error_pop_up(Boolean error_check) {
        if (error_check.equals(true)){
            warning_text.setVisible(true);
        }
    }

    //mouse listner to listen to the table clicks
    private class TableSelect implements MouseListener {

        //create an arraylist to save doctor list
        private ArrayList<Doctor> doc_list;

        //calling the method with doctor data
        public TableSelect(ArrayList<Doctor> doc_list) {
            this.doc_list = doc_list;
        }

        //actions to mouse click events
        @Override
        public void mouseClicked(MouseEvent e) {
            //get the medical license number of the selected row
            String licence_no = (String) (table.getValueAt(table.getSelectedRow(),0));
            int doc_no = 0;


            //for loop to search the clicked medical number
            for (int i = 0; i < doc_list.size(); i++) {
                if (doc_list.get(i).getM_licence() == licence_no) {
                    doc_no = i;
                }
            }
            //saving the selected doc no
            doctor_no = doc_no;
            table_panel.setVisible(false);
            System.out.println(doctor_no);
            add(date_clock);

        }

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

    }
    //creating an arraylist for consultation data
    public class Temp_Consultation {
        //creating an array list to save consultation details
        private ArrayList<Consultation> consultationArrayList;

        public Temp_Consultation(ArrayList<Consultation> consultationArrayList){
            this.consultationArrayList = consultationArrayList;
        }
    }

    //checking that the selected doctor is free on selected time
    public Boolean consult_check(){
        Boolean consult_match = false;

        for(int i=0; i<consultationArrayList.size();i++){
            //getting the license number of the doctor of the consultation
            String li_no_consult = consultationArrayList.get(i).getDoctor().getM_licence();
            //getting the selected doctors license number
            String choosen_license = doctors.get(doctor_no).getM_licence();
            temp_choosen_doc_license = choosen_license;
            //getting the date and time of the consultation
            LocalDateTime consult_date = consultationArrayList.get(i).getDateTime();
            temp_consuldate = consult_date;

            //checking weather the selected license and the date and time equals
            System.out.println(li_no_consult);
            System.out.println(choosen_license);
            System.out.println(consult_date);
            if ((li_no_consult == choosen_license) && consult_date.equals(user_date)){
                    consult_match = true;
            }
        }
        return consult_match;


    }

}
