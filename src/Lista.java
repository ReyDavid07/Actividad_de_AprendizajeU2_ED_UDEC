public class Lista {
    private Nodo primero;
    private Nodo ultimo;
    private int tamanio;

    public Lista() {
        this.primero = null;
        this.ultimo = null;
        this.tamanio = 0;
    }

    public void agregar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            ultimo = nuevo;
        }
        tamanio++;
    }

    public void agregarAlInicio(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.setSiguiente(primero);
            primero.setAnterior(nuevo);
            primero = nuevo;
        }
        tamanio++;
    }

    public void agregarEnPosicion(int indice, Object dato) {
        validarIndiceInsercion(indice);
        if (indice == 0) {
            agregarAlInicio(dato);
            return;
        }
        if (indice == tamanio) {
            agregar(dato);
            return;
        }
        Nodo actual = obtenerNodo(indice);
        Nodo anterior = actual.getAnterior();
        Nodo nuevo = new Nodo(dato);
        anterior.setSiguiente(nuevo);
        nuevo.setAnterior(anterior);
        nuevo.setSiguiente(actual);
        actual.setAnterior(nuevo);
        tamanio++;
    }

    public Object eliminarPrimero() {
        if (primero == null) {
            return null;
        }
        Object dato = primero.getDato();
        if (primero == ultimo) {
            primero = null;
            ultimo = null;
        } else {
            primero = primero.getSiguiente();
            primero.setAnterior(null);
        }
        tamanio--;
        return dato;
    }

    public Object eliminarUltimo() {
        if (ultimo == null) {
            return null;
        }
        Object dato = ultimo.getDato();
        if (primero == ultimo) {
            primero = null;
            ultimo = null;
        } else {
            ultimo = ultimo.getAnterior();
            ultimo.setSiguiente(null);
        }
        tamanio--;
        return dato;
    }

    public Object eliminarEnPosicion(int indice) {
        validarIndice(indice);
        if (indice == 0) {
            return eliminarPrimero();
        }
        if (indice == tamanio - 1) {
            return eliminarUltimo();
        }
        Nodo actual = obtenerNodo(indice);
        Nodo anterior = actual.getAnterior();
        Nodo siguiente = actual.getSiguiente();
        anterior.setSiguiente(siguiente);
        siguiente.setAnterior(anterior);
        tamanio--;
        return actual.getDato();
    }

    public Object buscarDato(int indice) {
        validarIndice(indice);
        return obtenerNodo(indice).getDato();
    }

    public Object buscarDato(Object dato) {
        Nodo actual = primero;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public boolean contiene(Object dato) {
        return buscarDato(dato) != null;
    }

    public int cuentaElementos() {
        return tamanio;
    }

    public void limpiar() {
        primero = null;
        ultimo = null;
        tamanio = 0;
    }

    public void mostrarAdelante() {
        if (primero == null) {
            System.out.println("La lista esta vacia.");
            return;
        }
        Nodo actual = primero;
        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }

    public void mostrarAtras() {
        if (ultimo == null) {
            System.out.println("La lista esta vacia.");
            return;
        }
        Nodo actual = ultimo;
        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getAnterior();
        }
    }

    private Nodo obtenerNodo(int indice) {
        Nodo actual;
        if (indice < tamanio / 2) {
            actual = primero;
            for (int i = 0; i < indice; i++) {
                actual = actual.getSiguiente();
            }
        } else {
            actual = ultimo;
            for (int i = tamanio - 1; i > indice; i--) {
                actual = actual.getAnterior();
            }
        }
        return actual;
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
    }

    private void validarIndiceInsercion(int indice) {
        if (indice < 0 || indice > tamanio) {
            throw new IndexOutOfBoundsException("Indice de insercion fuera de rango: " + indice);
        }
    }
}
