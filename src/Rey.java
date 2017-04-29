
public class Rey extends Ficha {
    private boolean enroqueLargo;
    private boolean enroqueCorto;

    public boolean getEnroqueLargo() {
        return enroqueLargo;
    }
    public boolean getEnroqueCorto() {
        return enroqueCorto;
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
        else if (enroqueLargo(posicionInicial, posicionDestino))
            correcto = true;
        else if (enroqueCorto(posicionInicial, posicionDestino))
            correcto = true;
        return correcto;

    }

    private boolean enroqueLargo(Posicion posicionInicial, Posicion posicionDestino) {

        int pFila1 = posicionInicial.getFila();

        int pColumna1 = posicionInicial.getColumna();
        int pColumna2 = posicionDestino.getColumna();

        boolean correcto = false;
        Ficha torre = Tablero.getTablero().getMatriz()[pFila1][pColumna2 - 1];
        if (torre != null && !(torre.getPrimerMov()) && !(Tablero.getTablero().getMatriz()[pFila1][pColumna1].getPrimerMov()))
            if (torre.comprobarMovimiento(posicionInicial, posicionDestino)) {
                this.setPrimerMov();
                this.enroqueLargo = true;
                correcto = true;
            }

        return correcto;
    }

    private boolean enroqueCorto(Posicion posicionInicial, Posicion posicionDestino) {
        int pFila1 = posicionInicial.getFila();

        int pColumna1 = posicionInicial.getColumna();
        int pColumna2 = posicionDestino.getColumna();

        boolean correcto = false;
        Ficha torre = Tablero.getTablero().getMatriz()[pFila1][pColumna2 + 1];
        if (torre != null && !(torre.getPrimerMov()) && !(Tablero.getTablero().getMatriz()[pFila1][pColumna1].getPrimerMov()))
            if (torre.comprobarMovimiento(posicionInicial, posicionDestino)) {
                this.setPrimerMov();
                this.enroqueCorto = true;
                correcto = true;
            }
        return correcto;
    }


}
