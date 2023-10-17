// 200273P - W.M.I.D. Jayawickrama

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email_Client {

    private static LocalDate date;

    private static DateTimeFormatter dateFormatter;

    private static EmailHandler email_handler;

    public static void main(String[] args) throws MessagingException {

        date = LocalDate.now();
        System.out.println(" ");
        dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String today = date.format(dateFormatter);

        System.out.println(today);

        //Welcome message
        System.out.println("\nWelcome to Email Client Application!\n");

        //Send birthday wishes to PersonalRecipients and OfficeFriends who have birthdays today
        ClientHandler.birthdayWish(today);

        boolean active = true;

        Scanner scanner = new Scanner(System.in);

        while (active) {

            System.out.println("\nEnter option type: \n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application\n"
                    + "-1 - Close email client");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // adding a new recipient
                    // input format     - Official: nimal,nimal@gmail.com,ceo
                    //                  - Office_friend: kamal,kamal@gmail.com,clerk,2000/12/12
                    //                  - Personal: sunil,<nick-name>,sunil@gmail.com,2000/10/10

                    System.out.println("Adding a new recipient");
                    System.out.println("\ninput format: \n"
                            + "Official: <name>,<email>,<designation>\n"
                            + "Office_friend: <name>,<email>,<designation>,<birthday(yyyy/MM/dd)>\n"
                            + "Personal: <name>,<nick-name>,<email>,<birthday(yyyy/MM/dd)>");

                    String type = scanner.next();

                    String[] recipient_details = scanner.next().split(",");
                    System.out.println(type);

                    setRecipient(recipient_details,type);

                    break;

                case 2:
                    // send an email

                    scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter subject");
                    String subject = scanner.nextLine();
                    System.out.println("Enter content");
                    String content = scanner.nextLine();

                    date = LocalDate.now();
                    dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    String dayFormatted = date.format(dateFormatter);
                    System.out.println(dayFormatted);

                    email_handler = new EmailHandler(email, subject, content, dayFormatted);
                    ClientHandler.addEmailDetails(email_handler, dayFormatted);
                    break;

                case 3:
                    // code to print recipients who have birthdays on the given date
                    // input format - yyyy/MM/dd

                    scanner.nextLine();
                    System.out.println("input format - (yyyy/MM/dd)"); // input format - yyyy/MM/dd
                    String dateInput3 = scanner.nextLine();
                    ClientHandler.findBirthdays(dateInput3);
                    break;

                case 4:
                    // code to print the details of all the emails sent on the input date
                    // input format - yyyy/MM/dd

                    scanner.nextLine();
                    System.out.println("input format - (yyyy/MM/dd)"); // input format - yyyy/MM/dd
                    String dateInput4 = scanner.nextLine();
                    ClientHandler.sortEmailDate(dateInput4);
                    break;

                case 5:
                    // code to print the number of recipient objects in the application

                    ClientHandler.getCount();
                    break;

                case -1:
                    //exit application

                    System.out.println("Email Client Finished.");
                    active = false;
            }
        }
    }

    public static void setRecipient(String[] recipient_details, String type) {
        for (String a : recipient_details)
            System.out.println(a);
        RecipientFactory factory = new RecipientFactory();

        if (Objects.equals(type, "Official:")) {
            ClientHandler.addRecipientDetails(factory.createOfficialRecipient(recipient_details[0], recipient_details[1], recipient_details[2]), ".....null");
        } else if (Objects.equals(type, "Office_friend:")) {
            ClientHandler.addRecipientDetails(factory.createOfficialFriend(recipient_details[0], recipient_details[1], recipient_details[2], recipient_details[3]), recipient_details[3]);
        } else if (Objects.equals(type, "Personal:")) {
            ClientHandler.addRecipientDetails(factory.createPersonalRecipient(recipient_details[0], recipient_details[1], recipient_details[2], recipient_details[3]), recipient_details[3]);
        } else {
            System.out.println("Input error. Please try again.\n");
        }
    }

}











