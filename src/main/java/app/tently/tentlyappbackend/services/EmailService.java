package app.tently.tentlyappbackend.services;

public interface EmailService {

    void sendEmail(String to, String subject, String content);
//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
}
