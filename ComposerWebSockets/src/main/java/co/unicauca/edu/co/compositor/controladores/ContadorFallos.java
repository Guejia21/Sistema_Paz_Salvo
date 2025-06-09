package main.java.co.unicauca.edu.co.compositor.controladores;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ContadorFallos {
    private final AtomicInteger contador = new AtomicInteger(0);

    public int siguienteIntento() {
        return contador.incrementAndGet();
    }

    public void resetear() {
        contador.set(0);
    }
}
