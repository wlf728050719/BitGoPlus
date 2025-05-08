package cn.bit.message.mail;

import cn.bit.core.event.MailSendEvent;
import cn.bit.core.exception.SysException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@AllArgsConstructor
public class BitGoMailSender {
    private final JavaMailSender mailSender;
    private final ApplicationEventPublisher eventPublisher;

    public void send(Object source, String to, String subject, String content) {
        eventPublisher.publishEvent(new MailSendEvent(source, to, subject, content));
    }

    @Async // 异步处理
    @EventListener // 监听 MailSendEvent 事件
    public void handleMailSendEvent(MailSendEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("18086270070@163.com");
        message.setTo(event.getEmail());
        message.setSubject(event.getSubject());
        message.setText(event.getContent());
        try {
            mailSender.send(message);
            log.info("邮件发送成功\n{},\n{},\n{}", event.getEmail(), event.getSubject(), event.getContent());
        } catch (Exception e) {
            throw new SysException("mail send failed", e);
        }
    }
}
