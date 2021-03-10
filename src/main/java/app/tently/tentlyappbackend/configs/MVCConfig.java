package app.tently.tentlyappbackend.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/api/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/api/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/api/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        final Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("charset", "utf-8");

        configurer.defaultContentType(new MediaType(
                MediaType.APPLICATION_JSON, parameterMap));
    }
}

