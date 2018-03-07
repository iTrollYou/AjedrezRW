
public class Caballo extends Ficha {

    public Caballo(Jugador pJugador, Posicion pPosicion) {
        super(pJugador, pPosicion);
    }

    @Override
    public boolean comprobarMovimiento(Posicion posicionInicial, Posicion posicionDestino) {

        int filaIncio = posicionInicial.getFila();
        int filaFinal = posicionDestino.getFila();
        int columInicio = posicionInicial.getColumna();
        int columFinal = posicionDestino.getColumna();

        boolean correcto = false;

        int columDist = columFinal - columInicio;
        int filaDist = filaFinal - filaIncio;
        if (((Math.abs(filaDist) == 2) && (Math.abs(columDist) == 1)) ||
                ((Math.abs(filaDist) == 1) && (Math.abs(columDist) == 2))) {
            if (Tablero.posFinalCorrecta(columInicio, columFinal))
                correcto = true;
        }
        return correcto;
    }

    public String getIcono() {
        String icono;
        if (this.getJugador().getColor() == Color.WHITE) {
            icono = "\u2658";
        } else {
            icono = "\u265E";
        }
        return icono;
    }
}
