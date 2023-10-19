package Tarea2;
public class Expendedor {
    private final Deposito<Producto> cocaCola;
    private final Deposito<Producto> fanta;
    private final Deposito<Producto> sprite;
    private final Deposito<Producto> snickers;
    private final Deposito<Producto> superOcho;
    private final Deposito<Moneda> monedasVuelto;
    public Expendedor(int numeroProductos) {
        this.cocaCola = new Deposito<>();
        this.fanta = new Deposito<>();
        this.sprite = new Deposito<>();
        this.snickers = new Deposito<>();
        this.superOcho = new Deposito<>();
        this.monedasVuelto = new Deposito<>();

        for (int i = 0; i < numeroProductos; i++) {
            this.cocaCola.add(new CocaCola(i));
            this.fanta.add(new Fanta(i));
            this.sprite.add(new Sprite(i));
            this.snickers.add(new Snickers(i));
            this.superOcho.add(new SuperOcho(i));
        }
    }
    public Producto comprarProducto(Moneda moneda, TipoProducto tipo)
            throws NoHayProductoException, PagoIncorrectoException, PagoInsuficienteException {
        if (moneda == null) {
            throw new PagoIncorrectoException("No se ingreso dinero.");
        }
        if (moneda.getValor() < tipo.getPrecio()) {
            this.monedasVuelto.add(moneda);
            throw new PagoInsuficienteException("lo minimo a pagar es $"
                    + tipo.getPrecio()
                    + " (tu pagaste $" + moneda.getValor() + ")");
        }
        Deposito<Producto> deposito = tipo == TipoProducto.COCA_COLA ? this.cocaCola
                : tipo == TipoProducto.FANTA ? this.fanta
                : tipo == TipoProducto.SPRITE ? this.sprite
                : tipo == TipoProducto.SNICKERS ? this.snickers
                : tipo == TipoProducto.SUPEROCHO ? this.superOcho
                : null;

        Producto producto = deposito != null ? deposito.get() : null;
        if (producto == null) {
            this.monedasVuelto.add(moneda);
            throw new NoHayProductoException("No quedan  " + tipo.getTipo());
        }

        int vuelto = moneda.getValor() - tipo.getPrecio();
        while (vuelto != 0) {
            this.monedasVuelto.add(new Moneda100());
            vuelto -= 100;
        }
        return producto;
    }
    public Moneda getVuelto() {
        return this.monedasVuelto.get();
    }
}