package Tarea2;
public class Comprador {
    private String sonido;
    private int vuelto;
    public Comprador(Moneda m, TipoProducto tipoProducto, Expendedor exp)
            throws NoHayProductoException, PagoIncorrectoException, PagoInsuficienteException {
        Producto producto = exp.comprarProducto(m, tipoProducto);
        if (producto instanceof Bebida bebida) {
            this.sonido = bebida.beber();
        } else if (producto instanceof Dulce dulce) {
            this.sonido = dulce.comer();
        }
        while (true) {
            Moneda moneda = exp.getVuelto();
            if (moneda == null) break;
            this.vuelto += moneda.getValor();
        }
    }
    public int CantidadVuelto() {
        return this.vuelto;
    }
    public String queBebio() {
        return this.sonido;
    }
}