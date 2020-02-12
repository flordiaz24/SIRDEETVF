/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author PC
 */
@Configuration
public class InicioPageRedirect implements WebMvcConfigurer {
  
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/")
        .setViewName("forward:/Home/home.xhtml");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
}
}
