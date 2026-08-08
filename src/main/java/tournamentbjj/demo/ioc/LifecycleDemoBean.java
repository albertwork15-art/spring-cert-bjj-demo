
package tournamentbjj.demo.ioc;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class LifecycleDemoBean {

    public LifecycleDemoBean() {
        System.out.println("1. Constructor de LifecycleDemoBean llamado.");
    }

    @PostConstruct
    public void init() {
        System.out.println("2. @PostConstruct de LifecycleDemoBean: El Bean ya está inicializado y listo para usarse.");
    }

    public void doWork() {
        System.out.println("3. LifecycleDemoBean realizando alguna tarea.");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("4. @PreDestroy de LifecycleDemoBean: El contenedor se está cerrando, liberando recursos.");
    }
}
