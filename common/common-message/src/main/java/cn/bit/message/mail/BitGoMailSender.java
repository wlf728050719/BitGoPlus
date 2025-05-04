package cn.bit.message.mail;

import cn.bit.core.exception.SysException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@AllArgsConstructor
public class BitGoMailSender {
    private JavaMailSender mailSender;
    public void send(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("18086270070@163.com"); // 发件人
        message.setTo(to); // 收件人
        message.setSubject(subject); // 邮件主题
        message.setText(content); // 邮件内容
        try {
            mailSender.send(message);
            log.info("邮件发送成功\n{},\n{},\n{}", to, subject, content);
        } catch (Exception e) {
            throw new SysException("mail send failed", e);
        }
    }
}
