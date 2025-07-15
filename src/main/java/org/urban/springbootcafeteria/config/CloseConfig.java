package org.urban.springbootcafeteria.config;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

//Componente encargado de cerrar el pool de conexiones cuando se apaga la aplicación
@Component
public class CloseConfig {

    @Autowired
    private DataSource dataSource;

    //Metodo que se ejecuta automáticamente antes de que se apague el contexto de Spring.
    @PreDestroy
    public void alApagarAplicacion() {
        System.out.println("Cerrando las conexiones a la base de datos...");

        try {
            if (dataSource instanceof AutoCloseable) {
                ((AutoCloseable) dataSource).close();
                System.out.println("Conexiones cerradas correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar las conexiones:");
            e.printStackTrace();
        }
    }
}
