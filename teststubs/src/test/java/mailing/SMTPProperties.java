package mailing;


import java.util.Properties;

public class SMTPProperties {
    private final String host;
    private final String auth;
    private final String port;
    private final String socketFactory;

    public SMTPProperties(String host, String auth, String port, String socketFactory) {
        this.host = host;
        this.auth = auth;
        this.port = port;
        this.socketFactory = socketFactory;
    }


    Properties getSMTPProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.getHost());
        props.put("mail.smtp.auth", this.getAuth());
        props.put("mail.smtp.port", this.getPort());
        props.put("mail.smtp.socketFactory.class",this.getSocketFactory());
        return props;
    }

    public String getHost() {
        return host;
    }

    public String getAuth() {
        return auth;
    }

    public String getPort() {
        return port;
    }

    public String getSocketFactory() {
        return socketFactory;
    }
}
