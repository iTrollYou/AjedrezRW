
public class Reina extends Ficha {

    public Reina(Jugador pJugador, Posicion pPosicion) {
        super(pJugador, pPosicion);
    }

    @Override
    public boolean comprobarMovimiento(Posicion posicionInicial, Posicion posicionDestino) {
        Ficha alfil = new Alfil(); //temporales
        Ficha torre = new Torre();
        return alfil.comprobarMovimiento(posicionInicial, posicionDestino) ||
                torre.comprobarMovimiento(posicionInicial, posicionDestino);

    }

    public String getIcono() {
        String icono;
        if (this.getJugador().getColor() == Color.WHITE) {
            icono = "\u2655";
        } else {
            icono = "\u265B";
        }
        return icono;
    }
}

