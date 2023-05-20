import javax.swing.*;
import java.io.*;
import java.util.*;


public class WestminsterSkinConsultationManager implements SkinConsultationManager, Serializable {


//creating a Scanner object to take objects
    static Scanner input = new Scanner(System.in);

//Creating an array list that will store doctor details
    public static ArrayList<Doctor> doc_list = new ArrayList<>();



    static boolean looper; //for the while loop
    static int no_of_doc = 10; //number of doctors that can store





    public static void main(String[] args) {

        load_doc_list(doc_list);//calling the load method to load previous data

//        run_gui(doc_list);

        looper = true;
        while (looper) { //looping the console menu

            System.out.println("\n---------------------------------------------" +
                    "\n--------------------Menu---------------------" +
                    "\n---------------------------------------------");

            System.out.println("A : Add a new Doctor" +
                    "\nD : Delete a Doctor" +
                    "\nP : Print the List of Doctors" +
                    "\nS : Save in a File" +
                    "\nG : Run GUI" +
                    "\nQ : Exit the Program");

            System.out.println("---------------------------------------------");
            System.out.println("Select an Option : ");
            String option = input.next().toUpperCase();


            if (option.equals("Q")) { //if the input is "Q" quiting the menu loop
                System.out.println("Good Bye!");
                looper = false;
            } else { //if it is a another input calling the below method
                continueit(option);

            }
        }
    }


    public static void run_gui(ArrayList<Doctor> doc_list){

        ImageIcon logo = new ImageIcon("logo.png");

        Gui frame = new Gui(doc_list);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Westminster Skin Consultation");
        frame.setSize(1150,600);
        frame.setIconImage(logo.getImage());
        frame.setVisible(true);


    }



    public static void continueit(String option) {

        if (option.equals("A")) {take_Doctor_input();}
        else if (option.equals("D")){delete_input();}
        else if (option.equals("P")){printDoctors();}
        else if (option.equals("S")){save();}
        else if (option.equals("G")){run_gui(doc_list);}
        else{
            System.out.println("Please Enter a Valid Input");
        }
    }

    //taking inputs of the doctor and checking whether the doctor is on the array
    public static void take_Doctor_input(){

        System.out.println("Enter Doctor Medical License Number: ");
        String m_license = input.next();

        while (true){
//            String check_name = "N";        //created a variable to check whether the doc is in the list
            for (Doctor doctor : doc_list){     //checking the added medical number in the list
                if(doctor.getM_licence().equals(m_license)){
                        System.out.println("Doctor is already added to the list, Try a different medical number");
                        System.out.println("Re Enter the Medical Number: ");
                        m_license = input.next();
                        continue;

                }
            }
            break;
        }

        System.out.println("Enter first name: ");
        String f_name = input.next();
        System.out.println("Enter Surname: ");
        String s_name = input.next();
        System.out.println("Enter mobile number: ");
        String tel_no = input.next();
        System.out.println("Enter the date of Birth: ");
        String dob = input.next();
        System.out.println("Enter Doctors specialisation: ");
        String specialisation = input.next();


        Doctor d1 = new Doctor(m_license,f_name,s_name, specialisation,dob, tel_no );
        add_doctor(d1); //calling the method to add the details to the doc_list array

    }
    //adding the new doctor details to the doc_list array!
    public static void add_doctor( Doctor doctor_details){
        if(no_of_doc == doc_list.size()){
            System.out.println("Doctor List is FUll!");
        }else {
            doc_list.add(doctor_details);
            System.out.println("Doctor added to the List");
            System.out.println(doc_list.size());
            no_of_doc =- 1;
        }
    }

    //taking the doctors medical license number that need to be deleted from the doc_list
    public static void delete_input(){
        System.out.println("Enter the Medical license: ");
        String id = input.next();

        //checking whether the given medical license number is on the doc_list array
        while (true){
            String name_check = "E";
            for(Doctor doctor : doc_list){
                if(doctor.getM_licence().equals(id)){
                    name_check = "T";
                   break;
                }
            }
            //if it is not
            if(!name_check.equals("T")){
                System.out.println("There is no Similar Medical License Number");
                System.out.println("Do you want to Re Enter the number(Y/N): ");
                String option = input.next().toUpperCase();

                if(option.equals("Y")){
                    System.out.println("Re Enter Medical License Number: ");
                    id = input.next();
                    continue;
                }else {
                    break;
                }


            }else {
                deleteDoctor(id);   //if it is
                break;
            }
        }
    }



    //deleting the doctor who has the given id from list

    public static void deleteDoctor(String remove_ID){
            if(doc_list.size() == 0){   //checking whether the arraylist is blank or not
                System.out.println("There are no Doctors in the List");
            }else{
                for(int i=0; i<doc_list.size(); i++){
                    if (doc_list.get(i).getM_licence().equals(remove_ID)){
                        //displaying doctors details that need to be deleted before deleting it
                        System.out.println("Medical licence Number: "+ doc_list.get(i).getM_licence());
                        System.out.println("Name: "+doc_list.get(i).getF_name() +" " + doc_list.get(i).getS_name());
                        System.out.println("Mobile Number: " + doc_list.get(i).getTel_no());
                        System.out.println("Date of Birth: " + doc_list.get(i).getDob());
                        System.out.println("Specialisation: " + doc_list.get(i).getSpecialisation());
                        System.out.println(" ");
                        doc_list.remove(i);
                        System.out.println("Doctor deleted from the List");

                        no_of_doc =+ 1;
                    }
                }
            }
    }


    public static void printDoctors(){

        if(doc_list.size() == 0){
            System.out.println("There are No Doctors to Print");
        }else{
            Collections.sort(doc_list, Comparator.comparing(Person::getS_name));
            for (Doctor doctor: doc_list){
                System.out.println("License No: " + doctor.getM_licence());
                System.out.println("First Name: " + doctor.getF_name());
                System.out.println("Surname: " + doctor.getS_name());
                System.out.println("Tel No: " + doctor.getTel_no());
                System.out.println("Date of Birth: " + doctor.getDob());
                System.out.println("Specialisation: " + doctor.getSpecialisation() + "\n");


            }
        }

    }

    public static void save()  {
        try{
           FileWriter writer = new FileWriter("doc_list.txt");
           for(int i=0; i < doc_list.size(); i++){
               writer.write(doc_list.get(i).getM_licence() + "--" + doc_list.get(i).getF_name() + "--" + doc_list.get(i).getS_name() + "--" +
                       doc_list.get(i).getSpecialisation() + "--" + doc_list.get(i).getDob() + "--" + doc_list.get(i).getTel_no() + "\n");
           }
           writer.close();
            System.out.println("Doctor Details successfully stored");


        }catch (IOException e){
            e.printStackTrace();
        }





    }


    public static void load_doc_list (ArrayList<Doctor> doc_list){
        try{
         File file = new File("doc_list.txt");
         if(file.exists()){
             Scanner reader = new Scanner(file);

             while (reader.hasNextLine()){
                 String[] docArray = reader.nextLine().split("--");
                 doc_list.add(new Doctor(docArray[0],docArray[1],docArray[2],docArray[3],docArray[4],docArray[5]));
             }
             reader.close();
             System.out.println("Successfully loaded program data of the file");
         }else{
             System.out.println("NO saved data to load");
         }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
