package ru.learn.skillbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;

@ComponentScan("ru.learn.skillbox")
public class AppConfig {

    @Value("${app.path}")
    private String filename;

    private AppConfig() {
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("application.yaml"));

        configurer.setProperties(Objects.requireNonNull(yamlPropertiesFactoryBean.getObject()));
        return configurer;
    }

    @Bean
    public String filename() {
        return filename;
    }

}
