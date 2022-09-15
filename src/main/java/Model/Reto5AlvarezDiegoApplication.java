package Model;

import Controller.ControllerProduct;
import Model.Product.IRepositoryProduct;
import View.ViewProduct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Reto5AlvarezDiegoApplication {
    @Autowired
    IRepositoryProduct repository;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Reto5AlvarezDiegoApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Bean
    ApplicationRunner applicationrunner(){
        return args -> {
            //final Log logger = LogFactory.getLog(getClass());
            ControllerProduct controllerProduct = new ControllerProduct(repository, new ViewProduct());

        };

    }
}