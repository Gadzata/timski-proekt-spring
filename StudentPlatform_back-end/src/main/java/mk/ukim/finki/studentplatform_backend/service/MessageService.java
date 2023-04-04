package mk.ukim.finki.studentplatform_backend.service;

import mk.ukim.finki.studentplatform_backend.models.Message;
import mk.ukim.finki.studentplatform_backend.models.StudentEvent;
import mk.ukim.finki.studentplatform_backend.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(StudentEvent studentEvent, String text, Date dateWritten) {
        Message message = new Message(studentEvent, text, dateWritten);
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public void updateMessage(Integer messageId, StudentEvent studentEvent, String text, Date dateWritten) {
        Message message = getMessageById(messageId);
        if (message != null) {
            message.setStudentEvent(studentEvent);
            message.setText(text);
            message.setDateWritten(dateWritten);
            messageRepository.save(message);
        }
    }

    public void deleteMessage(Integer messageId) {
        messageRepository.deleteById(messageId);
    }
}
