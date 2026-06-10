import java.util.Objects;

public class Pedido {
    private String numeroPedido;
    private String cliente;
    private String descripcion;
    private double total;
    private String estado;

    public Pedido(String numeroPedido, String cliente, String descripcion, double total, String estado) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.total = total;
        this.estado = estado;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido='" + numeroPedido + "'" +
                ", cliente='" + cliente + "'" +
                ", descripcion='" + descripcion + "'" +
                ", total=" + total +
                ", estado='" + estado + "'" +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pedido)) {
            return false;
        }
        Pedido pedido = (Pedido) obj;
        return Objects.equals(numeroPedido, pedido.numeroPedido);
    }
}
