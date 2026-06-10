public class Cola {
    private Nodo frente;
    private Nodo fin;
    private int tamanio;

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamanio = 0;
    }

    public void encolar(Object dato) {
        Nodo nuevo = new Nodo(dato);
        if (esVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            nuevo.setAnterior(fin);
            fin = nuevo;
        }
        tamanio++;
    }

    public Object desencolar() {
        if (esVacia()) {
            return null;
        }
        Object dato = frente.getDato();
        if (frente == fin) {
            frente = null;
            fin = null;
        } else {
            frente = frente.getSiguiente();
            frente.setAnterior(null);
        }
        tamanio--;
        return dato;
    }

    public Object peek() {
        return esVacia() ? null : frente.getDato();
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean esVacia() {
        return tamanio == 0;
    }

    public boolean contiene(Object dato) {
        Nodo actual = frente;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public void limpiar() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    public void mostrar() {
        if (esVacia()) {
            System.out.println("La cola esta vacia.");
            return;
        }
        Nodo actual = frente;
        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }
}
