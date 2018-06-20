package ru.iqdevelop.barterme.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:application.properties")
class MailConfig {

    @Autowired
    private Environment environment;

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(environment.getProperty("mail.host"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("mail.port")));
        mailSender.setUsername(environment.getProperty("mail.username"));
        mailSender.setPassword(environment.getProperty("mail.password"));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", environment.getProperty("mail.smtp.ssl.enable"));
        javaMailProperties.put("mail.smtps.auth", environment.getProperty("mail.smtps.auth"));
        javaMailProperties.put("mail.transport.protocol", environment.getProperty("mail.protocol"));
        javaMailProperties.put("mail.debug", environment.getProperty("mail.debug"));

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

}
