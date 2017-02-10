package mailing;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MessageData {
    private final String username;
    private final String password;
    private final String addressList;
    private final String subject;
    private final String messageText;

    public MessageData(String username, String password, String addressList, String subject, String messageText) {
        this.username = username;
        this.password = password;
        this.addressList = addressList;
        this.subject = subject;
        this.messageText = messageText;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddressList() {
        return addressList;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessageText() {
        return messageText;
    }

    public Message getMessage(Session session) throws MessagingException  {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(getUsername()));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(getAddressList()));
        message.setSubject(getSubject());
        message.setText(getMessageText());

        return message;

    }

}
