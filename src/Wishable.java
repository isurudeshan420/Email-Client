import javax.mail.MessagingException;

public interface Wishable{

    EmailHandler greeting(Recipient recipient, String date) throws MessagingException;
}