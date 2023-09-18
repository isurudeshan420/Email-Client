import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

public class EmailHandler implements Serializable {

    private String email;
    private String subject;
    private String content;
    private String date;
    private static final String AccEmail = "isurutest00@gmail.com";;
    private static final String AccPass = "meoyylnmietpqknc";

    public EmailHandler(String email, String subject, String content , String date) throws MessagingException {
        this.email = email;
        this.subject = subject;
        this.content = content;
        this.date = date;
        sendmail();
    }

    public void sendmail() throws MessagingException {

        System.out.println("Please wait..........");

        Properties props = new Properties();
        props.put("mail.smtp.auth" , "true");
        props.put("mail.smtp.starttls.enable" , "true");
        props.put("mail.smtp.host" , "smtp.gmail.com");
        props.put("mail.smtp.port" , "587");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AccEmail,AccPass);
            }
        });

        Message message = prepareMessage(session , AccEmail , email , subject , content);

        Transport.send(message);
        System.out.println("Message sent successfully");

    }

    private static Message prepareMessage(Session session,  String AccEmail , String recipient , String subject, String content) {

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(AccEmail));
            message.setRecipient(Message.RecipientType.TO , new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(content);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }
}