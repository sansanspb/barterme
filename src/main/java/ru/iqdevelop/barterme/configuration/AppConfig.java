package ru.iqdevelop.barterme.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import({WebConfig.class, JpaConfiguration.class, SecurityConfig.class, MailConfig.class, WebSocketConfig.class})
@ComponentScan(basePackages = "ru.iqdevelop.barterme")
@EnableScheduling
public class AppConfig {
}
