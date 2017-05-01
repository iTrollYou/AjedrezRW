
public class Jugador {

    //Atributos
    private final Color color;
    private final String nombre;

    //Contructora
    public Jugador(Color pColor, String pNombre) {
        this.color = pColor;
        this.nombre = pNombre;
    }

    public Color getColor() {
        return this.color;
    }

    public String getNombre() {
        return this.nombre;
    }

}
