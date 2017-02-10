package mailing;


import javax.mail.*;

public class MailSender {


    public String sendNotification(SMTPProperties props, final MessageData messageData) {


        try {

            Transport.send(messageData.getMessage(getSession(props,
                                                             messageData.getUsername(),
                                                             messageData.getPassword())
                                                  )
                          );

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "Notification message was sent.";


    }

    private Session getSession(SMTPProperties props, final String username, final String password) {
        return Session.getDefaultInstance(props.getSMTPProperties(),
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username,password);
                        }
                    });
    }


}