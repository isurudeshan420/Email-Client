import javax.mail.MessagingException;

public class OfficialFriend extends OfficialPersonnel implements Wishable {

    private String birthday;

    // declaring a more generic field by using poly
    private EmailHandler email_Handler;

    public OfficialFriend(String name, String email, String designation, String birthday){
        super(name, email, designation);
        this.birthday=birthday;
    }

    public EmailHandler greeting(Recipient recipient , String date) throws MessagingException {
        System.out.println("Your Office friend " + recipient.getName() + "'s birthday is today");
        String subject = "Greetings on your Birthday" ;
        String content = "Wish you a Happy Birthday - Isuru " ;

        email_Handler = new EmailHandler(recipient.getEmail(), subject, content, date);

        return email_Handler;

    }
}