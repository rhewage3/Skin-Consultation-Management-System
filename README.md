# Skin-Consultation-Management-System


This repository contains a Java program that implements a system for managing a Skin Consultation Centre. The program allows the manager to perform various actions, such as adding and deleting doctors, scheduling and canceling consultations, and printing and saving information. It also includes a Graphical User Interface (GUI) for easy visualization and interaction.

Problem Description
The program aims to provide a comprehensive solution for managing a Skin Consultation Centre. The implementation should follow the Object-Oriented principles and be easy to understand and maintain. The program requires careful analysis of the problem statement to ensure all the necessary details are included in the solution.

Implementation Phases
Phase 1: Design and Classes Implementation
In this phase, the system design should adhere to Object-Oriented principles. UML diagrams, including a use case diagram and class diagram, should be created. The design should include the implementation of superclass Person and subclasses Doctor and Patient. The Person class should handle information such as name, surname, date of birth, and mobile number. The Doctor subclass should include additional information like medical license number and specialization. The Patient subclass should include a unique patient ID. The Consultation class should represent booked consultations with doctors and store relevant information. Finally, the WestminsterSkinConsultationManager class should manage the list of doctors and provide necessary methods.

Phase 2: Console Menu Implementation
The WestminsterSkinConsultationManager class should display a console menu with management actions. The actions include adding new doctors, deleting doctors, printing the list of doctors, and saving the information to a file. The program should enforce a maximum of 10 doctors in the system and provide proper output and feedback to the user.

Phase 3: Graphical User Interface (GUI) Implementation
A GUI should be implemented to enhance the user experience. The GUI should allow users to visualize the list of doctors, sort the list alphabetically, and select a doctor to book a consultation. The GUI should also enable users to check doctor availability, add patient information, enter consultation cost, add notes (encrypted for data privacy), and view stored consultation information.

Phase 4: Testing and System Validation
A comprehensive test plan should be created to ensure the program works as expected. Automated testing, using tools like JUnit or other preferred frameworks, should cover various use cases. The code should be robust, handling errors and input validation effectively. The quality of the code, adherence to coding standards, and conventions will also be evaluated.
