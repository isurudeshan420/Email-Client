import javax.mail.MessagingException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientHandler {

    // hashmap - birthday recipient obj list mapped with birthdays
    private static Map<String, ArrayList<Recipient>> birthday_recipient_details;

    // hashmap - email handler obj list mapped with sent dates
    private static Map<String, ArrayList<EmailHandler>> date_email_details;

    // obj list - birthday recipient obj list
    private static ArrayList<Recipient> recipient_obj ;

    // obj list - email handler recipient obj list
    private static ArrayList<EmailHandler> email_obj;

    //for clientObjList.txt file
    private static final String filename_client_obj = "clientObjList.txt";

    //for clientList.txt text file
    private static final String filename_client_list = "clientList.txt";

    //for EmailObjectList.txt file
    private static final String filename_email_obj = "EmailObjectList.txt";


    //Deserialization of EmailObjectList and clientObjList files.
    public static Object readFile(String filename) {
        Object obj = null;
        FileInputStream fileIn = null;
        ObjectInputStream objIn = null;

        try {
            fileIn = new FileInputStream(filename);
            objIn = new ObjectInputStream(fileIn);
            obj = objIn.readObject();
            objIn.close();
            fileIn.close();
        } catch (IOException ex) {
            //ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
        }

        return obj;
    }


    //To manage birthday wishes of today
    public static void birthdayWish(String today) throws MessagingException {

        //Deserialize and read files
        date_email_details = (Map<String, ArrayList<EmailHandler>>)readFile(filename_email_obj);
        birthday_recipient_details = (Map<String, ArrayList<Recipient>>)readFile(filename_client_obj);

        //update date to (MM-dd)
        String updated_birthday = today.substring(5);

        boolean NotWishedToday=false;
        boolean HasBirthdayToday=false;

//      //Print email details hashmap
//      for (String date: date_email_details.keySet()) {
//          String key = date.toString();
//          String value = date_email_details.get(date).toString();
//          System.out.println("key: " + key + " " + value);
//      }
//
//        System.out.println("\n");
//
//      //Print client details hashmap
//      for (String date: birthday_recipient_details.keySet()) {
//          String key = date.toString();
//          String value = birthday_recipient_details.get(date).toString();
//          System.out.println("key: " + key + " " + value);
//       }

        try{
            if(date_email_details.get(today)==null) {
                NotWishedToday=true;
            }
        }catch (NullPointerException e){
            NotWishedToday=true;

        }

        try {
            if(birthday_recipient_details.containsKey(updated_birthday)) {
                HasBirthdayToday=true;
            }
            if(HasBirthdayToday){
                //if(HasBirthdayToday && NotWishedToday){
                recipient_obj = birthday_recipient_details.get(updated_birthday);
                for(Recipient recipient:recipient_obj) {
                    if(recipient instanceof Wishable ) {
                        EmailHandler email_handler = ((Wishable) recipient).greeting(recipient,today);
                        addEmailDetails(email_handler, today);
                    }
                }
            }else{
                System.out.println("There are no birthdays today or already wished by the email client.");
            }


        }catch (NullPointerException e) {}
    }

    //Add recipients
    public static void addRecipientDetails(Recipient recipient , String date) {  // passing more specific object of a method by using poly

        String updated_birthday = date.substring(5);
        birthday_recipient_details = new HashMap<>();

        File check_Obj_file = null;
        File ClienttFile = null;
        FileWriter file = null;

        FileOutputStream origin_fileout = null;
        ObjectOutputStream origin_objOut = null;

        String designation_or_nickname = "";
        if(recipient instanceof OfficialPersonnel){
            designation_or_nickname = ((OfficialPersonnel) recipient).getDesignation();
        }else{
            designation_or_nickname = ((PersonalRecipient) recipient).getNickname();
        }

        //check and/or create required file and hashmap
        try {

            check_Obj_file = new File(filename_client_obj);
            //if clientObj.txt doesn't exit create new file and convert it to hold objects
            if (!check_Obj_file.exists()){
                origin_fileout = new FileOutputStream(filename_client_obj);
                //converting file to hold objects and add null hash map
                origin_objOut = new ObjectOutputStream(origin_fileout);
                origin_objOut.writeObject(birthday_recipient_details);
                origin_objOut.close();
            }

            //a txt file to keep record
            ClienttFile = new File(filename_client_list);
            file = new FileWriter(ClienttFile,true);
            file.write("Name : "+recipient.getName()+" ,  Birthday : "+updated_birthday+" ,  Email : "+recipient.getEmail()+" ,  designation_or_nickname : "+designation_or_nickname+"\n");
            file.close();

        } catch (IOException ex) {
            System.out.println("File is not available");
        }

        //getting back the existing hashmap in the clientObjList file
        birthday_recipient_details = (Map<String, ArrayList<Recipient>>)readFile(filename_client_obj);

        //insert details to map
        if(!updated_birthday.equals("null")) {
            //if the date exists as a key in hash map
            if(birthday_recipient_details.containsKey(updated_birthday)){
                birthday_recipient_details.get(updated_birthday).add(recipient);
            }
            //if doesn't exist making a key in hash map
            else{
                recipient_obj = new ArrayList<>();
                recipient_obj.add(recipient);
                birthday_recipient_details.put(updated_birthday,recipient_obj);
            }
        }

        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;

        //write map to file
        try {
            fileOut = new FileOutputStream(filename_client_obj);
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(birthday_recipient_details);
            objOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    //Handle email handler objects
    public static void addEmailDetails(EmailHandler e_handler , String date) {  // passing more specific object of a method by using poly

        File check_file = new File(filename_email_obj);
        FileOutputStream origin_fileout2 = null;
        ObjectOutputStream origin_objOut2 = null;
        date_email_details = new HashMap<>();

        //check and/or crete required file and hashmap

        //if EmailObjectList.txt doesn't exist create new file and convert it to hold objects
        if(!check_file.exists()){
            try {
                origin_fileout2 = new FileOutputStream(filename_email_obj);
                //converting file to hold objects and add null hashmap
                origin_objOut2 = new ObjectOutputStream(origin_fileout2);
                origin_objOut2.writeObject(date_email_details);
                origin_objOut2.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //deserialize email object file
        date_email_details = (Map<String, ArrayList<EmailHandler>>)readFile(filename_email_obj);

        //inserting details
        if(date_email_details.containsKey(date)){
            date_email_details.get(date).add(e_handler);
        }else{
            email_obj = new ArrayList<>();
            email_obj.add(e_handler);
            date_email_details.put(date, email_obj);
        }

        FileOutputStream fileOut2 = null;
        ObjectOutputStream objOut2 = null;

        //write map to  file
        try {
            fileOut2 = new FileOutputStream(filename_email_obj);
            objOut2 = new ObjectOutputStream(fileOut2);
            objOut2.writeObject(date_email_details);
            objOut2.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    //Print recipients who have birthdays on the given date
    public static void findBirthdays(String date){
        try{
            birthday_recipient_details = (Map<String, ArrayList<Recipient>>) ClientHandler.readFile(filename_client_obj);
            String birthday = date.substring(5);

            System.out.println("These recipients have birthdays on "+date);

            for (Recipient recipient : birthday_recipient_details.get(birthday)) {
                System.out.println(recipient.getName());
            }
        }catch(NullPointerException e) {
            System.out.println("No Birthdays Today");
        }
    }

    //Print the details of all the emails sent on the input date
    public static void sortEmailDate(String date){
        try {
            date_email_details = (Map<String, ArrayList<EmailHandler>>) ClientHandler.readFile(filename_email_obj);

            System.out.println("Emails sent on " + date + " are");

            for (EmailHandler e_handler : date_email_details.get(date)) {
                System.out.println("Recipient :" + e_handler.getEmail());
                System.out.println("Subject :" + e_handler.getSubject());
            }
        }catch (NullPointerException e){
            System.out.println("No Emails on this date");
        }
    }

    // To print the number of recipient objects in the application
    public static void getCount(){
        try {
            int count = 0;
            File ClienttFile = new File(filename_client_list);
            Scanner count_file = new Scanner(ClienttFile);
            while (count_file.hasNext()) {
                count_file.nextLine();
                count++;
            }
            System.out.println(count);
        }catch (NullPointerException e){
            System.out.println("No clients");
        }catch (FileNotFoundException e){
            System.out.println("Input clients first");
        }
    }
}
