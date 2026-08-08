package tournamentbjj.demo.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class IocDemoRunner implements CommandLineRunner {

    // 1. Inyección de dependencias clásica
    @Autowired
    private LifecycleDemoBean lifecycleBean;
    @Autowired
    private String appVersion; // Inyecta el Bean String que definimos en IocConfig
    // El ApplicationContext es el contenedor de Spring. Lo usamos para pedir beans
    // manualmente.
    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== 🚀 INICIANDO DEMOSTRACIÓN DE IOC Y BEANS ===\n");
        // A. Probando el Bean de ciclo de vida
        System.out.println("Versión de la aplicación inyectada: " + appVersion);
        lifecycleBean.doWork();
        // B. Demostración del Scope Prototype (Examen de certificación)
        System.out.println("\n--- 🧪 Demostración de Scopes (Singleton vs Prototype) ---");

        // Pedimos dos veces el PrototypeBean a Spring
        PrototypeBean p1 = context.getBean(PrototypeBean.class);
        PrototypeBean p2 = context.getBean(PrototypeBean.class);
        System.out.println("PrototypeBean 1 ID: " + p1.getId());
        System.out.println("PrototypeBean 2 ID: " + p2.getId());
        System.out.println("¿Son la misma instancia? " + (p1 == p2 ? "SÍ (Singleton)" : "NO (Prototype)"));
        System.out.println("\n=== 🛑 FIN DE LA DEMOSTRACIÓN DE IOC ===\n");
    }
}