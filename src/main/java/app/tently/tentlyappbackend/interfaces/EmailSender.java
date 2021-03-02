package app.tently.tentlyappbackend.interfaces;

public interface EmailSender {
    void sendEmail(String to, String subject, String content);
}
