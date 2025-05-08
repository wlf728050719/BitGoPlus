package cn.bit.core.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MailSendEvent extends ApplicationEvent {
    private final String email; // 收件人邮箱
    private final String subject; // 邮件主题
    private final String content; // 邮件内容

    public MailSendEvent(Object source, String email, String subject, String content) {
        super(source);
        this.email = email;
        this.subject = subject;
        this.content = content;
    }
}
