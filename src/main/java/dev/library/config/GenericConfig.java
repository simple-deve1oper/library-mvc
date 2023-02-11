package dev.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Основной класс конфигурации проекта
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:database.properties")
public class GenericConfig {
}
