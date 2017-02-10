package mailing;


import java.util.Properties;

public class SMTPProperties {
    private String host;
    private String auth;
    private String port;
    private String socketFactory;
    private String socketFactoryPort;


    public SMTPProperties withHost(String host) {
        this.host = host;
        return this;
    }

    public SMTPProperties withAuth(String auth) {
        this.auth = auth;
        return this;
    }

    public SMTPProperties withPort(String port) {
        this.port = port;
        return this;
    }

    public SMTPProperties withSocketFactory(String socketFactory) {
        this.socketFactory = socketFactory;
        return this;
    }

    public SMTPProperties withSocketFactoryPort(String socketFactoryPort) {
        this.socketFactoryPort = socketFactoryPort;
        return this;
    }

    public String getSocketFactoryPort() {
        return socketFactoryPort;
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


    public SMTPProperties(String host, String auth, String port, String socketFactory) {
        this.host = host;
        this.auth = auth;
        this.port = port;
        this.socketFactory = socketFactory;
    }

    public SMTPProperties(){
        this.host = "smtp.gmail.com";
        this.auth = "true";
        this.port = "465" ;
        this.socketFactory = "javax.net.ssl.SSLSocketFactory";
        this.socketFactoryPort = "465";
    }

    Properties getSMTPProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.getHost());
        props.put("mail.smtp.auth", this.getAuth());
        props.put("mail.smtp.port", this.getPort());
        props.put("mail.smtp.socketFactory.class",this.getSocketFactory());
        props.put("mail.smtp.socketFactory.port",this.getSocketFactoryPort());
        return props;
    }


}
