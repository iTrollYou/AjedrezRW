
public class Rey extends Ficha {
    private boolean enroqueLargo;

    public boolean getEnroque() {
        return enroqueLargo;
    }

    public Rey(Jugador pJugador, Posicion pPosicion) {
        super(pJugador, pPosicion);
    }

    public String getIcono() {
        String icono;
        if (this.getJugador().getColor() == Color.WHITE) {
            icono = "\u2654";
        } else {
            icono = "\u265A";
        }
        return icono;
    }

    public boolean comprobarMovimiento(Posicion posicionInicial, Posicion posicionDestino) {

        int pFila1 = posicionInicial.getFila();
        int pFila2 = posicionDestino.getFila();
        int pColumna1 = posicionInicial.getColumna();
        int pColumna2 = posicionDestino.getColumna();

        boolean correcto = false;

        int absOffsetX = Math.abs(pColumna2 - pColumna1);
        int absOffsetY = Math.abs(pFila2 - pFila1);

        if (pFila1 != pFila2 || pColumna1 != pColumna2) {
            if (absOffsetX < 2 && absOffsetY < 2) {
                correcto = posFinalCorrecta(pFila2, pColumna2);
            }

        }
        if (enroqueLargo(posicionInicial,posicionDestino))
            correcto = true;

        return correcto;

    }

    private boolean enroqueLargo(Posicion posicionInicial, Posicion posicionDestino) {

        int pFila1 = posicionInicial.getFila();
        //int pFila2 = posicionDestino.getFila();
        int pColumna1 = posicionInicial.getColumna();
        int pColumna2 = posicionDestino.getColumna();

        boolean correcto = false;
        Ficha ficha = Tablero.getTablero().getMatriz()[pFila1][pColumna2 - 1];
        if (ficha != null && !(ficha.getPrimerMov()) && !(Tablero.getTablero().getMatriz()[pFila1][pColumna1].getPrimerMov()))
            if (ficha.comprobarMovimiento(posicionInicial, posicionDestino)) {
                setPrimerMov(true);
                enroqueLargo = true;
                correcto = true;
            }

        return correcto;
    }


}
