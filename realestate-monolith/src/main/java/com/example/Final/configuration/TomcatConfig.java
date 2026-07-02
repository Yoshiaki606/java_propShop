package com.example.Final.configuration;

import org.apache.catalina.Context;
import org.apache.catalina.session.StandardManager;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            private void customizeContext(Context context) {
                // Disable session persistence
                StandardManager manager = new StandardManager();
                manager.setPathname(null); // <- This disables file-based session persistence
                context.setManager(manager);
            }
        };
    }
}
