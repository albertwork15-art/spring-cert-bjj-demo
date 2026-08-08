package tournamentbjj.demo.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class IocConfig {
    @Bean
    public String appVersion() {
        return "1.0.0-SPRING-CERTIFICATION";
    }

    @Bean
    @Scope("prototype")
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }
}
