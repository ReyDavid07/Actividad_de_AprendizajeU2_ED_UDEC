import java.util.Scanner;

public class SistemaRestaurante {
    private final Lista listaGeneral;
    private Cola colaPendientes;
    private final Pila historialProcesados;
    private final Scanner scanner;

    public SistemaRestaurante() {
        this.listaGeneral = new Lista();
        this.colaPendientes = new Cola();
        this.historialProcesados = new Pila();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        SistemaRestaurante sistema = new SistemaRestaurante();
        sistema.ejecutarMenu();
    }

    private void ejecutarMenu() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            try {
                switch (opcion) {
                    case 1 -> registrarPedido();
                    case 2 -> verTodosLosPedidos();
                    case 3 -> verPendientes();
                    case 4 -> procesarSiguientePedido();
                    case 5 -> verHistorial();
                    case 6 -> buscarPedidoPorNumero();
                    case 7 -> cancelarPedidoPendiente();
                    case 8 -> deshacerUltimoProcesamiento();
                    case 9 -> verCantidadElementos();
                    case 10 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 10);
    }

    private void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE PEDIDOS - RESTAURANTE =====");
        System.out.println("1. Registrar elemento");
        System.out.println("2. Ver todos los elementos registrados");
        System.out.println("3. Ver elementos pendientes");
        System.out.println("4. Procesar siguiente elemento");
        System.out.println("5. Ver historial de elementos procesados");
        System.out.println("6. Buscar elemento por codigo");
        System.out.println("7. Cancelar elemento pendiente");
        System.out.println("8. Deshacer ultimo procesamiento");
        System.out.println("9. Ver cantidad de elementos");
        System.out.println("10. Salir");
    }

    private void registrarPedido() {
        System.out.println("\n--- Registrar pedido ---");
        String numero = leerTexto("Numero de pedido: ");
        if (existePedido(numero)) {
            System.out.println("Ya existe un pedido con ese numero.");
            return;
        }
        String cliente = leerTexto("Nombre del cliente: ");
        String descripcion = leerTexto("Descripcion del pedido: ");
        double total = leerDouble("Total del pedido: ");

        Pedido pedido = new Pedido(numero, cliente, descripcion, total, "PENDIENTE");
        listaGeneral.agregar(pedido);
        colaPendientes.encolar(pedido);
        System.out.println("Pedido registrado y enviado a la cola de pendientes.");
    }

    private void verTodosLosPedidos() {
        System.out.println("\n--- Lista general de pedidos ---");
        listaGeneral.mostrarAdelante();
    }

    private void verPendientes() {
        System.out.println("\n--- Cola de pedidos pendientes ---");
        colaPendientes.mostrar();
        System.out.println("Total pendientes: " + colaPendientes.tamanio());
        System.out.println("Siguiente pedido: " + colaPendientes.peek());
    }

    private void procesarSiguientePedido() {
        Pedido procesado = (Pedido) colaPendientes.desencolar();
        if (procesado == null) {
            System.out.println("No hay pedidos pendientes por procesar.");
            return;
        }
        procesado.setEstado("PROCESADO");
        historialProcesados.apilar(procesado);
        System.out.println("Pedido procesado: " + procesado);
    }

    private void verHistorial() {
        System.out.println("\n--- Historial de pedidos procesados ---");
        historialProcesados.mostrar();
        System.out.println("Ultimo procesado: " + historialProcesados.peek());
    }

    private void buscarPedidoPorNumero() {
        String numero = leerTexto("Ingrese el numero de pedido a buscar: ");
        Pedido buscado = new Pedido(numero, "", "", 0, "");
        Object resultado = listaGeneral.buscarDato(buscado);
        if (resultado == null) {
            System.out.println("No se encontro un pedido con ese numero.");
        } else {
            System.out.println("Pedido encontrado: " + resultado);
        }
    }

    private void cancelarPedidoPendiente() {
        String numero = leerTexto("Numero del pedido pendiente a cancelar: ");
        Cola auxiliar = new Cola();
        boolean cancelado = false;

        while (!colaPendientes.esVacia()) {
            Pedido actual = (Pedido) colaPendientes.desencolar();
            if (actual.getNumeroPedido().equalsIgnoreCase(numero) && actual.getEstado().equalsIgnoreCase("PENDIENTE")) {
                actual.setEstado("CANCELADO");
                cancelado = true;
            } else {
                auxiliar.encolar(actual);
            }
        }

        colaPendientes = auxiliar;
        if (cancelado) {
            System.out.println("Pedido cancelado correctamente usando cola auxiliar.");
        } else {
            System.out.println("No se encontro un pedido pendiente con ese numero.");
        }
    }

    private void deshacerUltimoProcesamiento() {
        Pedido ultimo = (Pedido) historialProcesados.desapilar();
        if (ultimo == null) {
            System.out.println("No hay pedidos procesados para deshacer.");
            return;
        }
        ultimo.setEstado("PENDIENTE");
        colaPendientes.encolar(ultimo);
        System.out.println("Se deshizo el procesamiento y el pedido regreso a pendientes: " + ultimo);
    }

    private void verCantidadElementos() {
        System.out.println("\n--- Cantidades ---");
        System.out.println("Pedidos registrados en lista: " + listaGeneral.cuentaElementos());
        System.out.println("Pedidos pendientes en cola: " + colaPendientes.tamanio());
        System.out.println("Pedidos procesados en historial: " + historialProcesados.tamanio());
    }

    private boolean existePedido(String numero) {
        Pedido buscado = new Pedido(numero, "", "", 0, "");
        return listaGeneral.contiene(buscado);
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = scanner.nextLine().trim();
        while (texto.isEmpty()) {
            System.out.print("El dato no puede estar vacio. " + mensaje);
            texto = scanner.nextLine().trim();
        }
        return texto;
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor < 0) {
                    System.out.println("El total no puede ser negativo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor numerico.");
            }
        }
    }
}
