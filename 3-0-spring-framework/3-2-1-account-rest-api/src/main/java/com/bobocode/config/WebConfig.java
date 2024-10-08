package com.bobocode.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * This class provides web (servlet) related configuration.
 * <p>
 * todo: 1. Mark this class as Spring config class
 * todo: 2. Enable web mvc using annotation
 * todo: 3. Enable component scanning for package "web" using annotation value
 */
@Configuration  // 1. Marca esta classe como uma classe de configuração do Spring
@EnableWebMvc   // 2. Habilita o suporte ao Spring MVC
@ComponentScan(basePackages = "com.bobocode.web")  // 3. Escaneia o pacote "web"
public class WebConfig {
}
