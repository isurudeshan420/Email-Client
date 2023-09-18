import javax.mail.MessagingException;

public class PersonalRecipient extends Recipient implements Wishable {

    private String nickname;

    private String birthday;

    //declaring a more generic field by using poly
    private EmailHandler email_handler;

    public PersonalRecipient(String name, String nickname, String email, String birthday) {
        super(name, email);
        this.nickname=nickname;
        this.birthday=birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public EmailHandler greeting(Recipient recipient , String date) throws MessagingException {
        System.out.println("Your friend " + nickname + "'s birthday is today");
        String subject = "Greetings on your Birthday" ;
        String content = "Hugs and love on your birthday - Isuru " ;

        email_handler = new EmailHandler(recipient.getEmail(), subject, content, date);

        return email_handler;

    }
}