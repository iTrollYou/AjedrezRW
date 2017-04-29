
public class Torre extends Ficha {

    public Torre(Jugador pJugador, Posicion pPosicion) {
        super(pJugador, pPosicion);
    }
    public Torre(){} //Comprobacion de movimiento reina
    @Override
    public boolean comprobarMovimiento(Posicion posicionInicial, Posicion posicionDestino) {

        int pFila1 = posicionInicial.getFila();
        int pFila2 = posicionDestino.getFila();
        int pColumna1 = posicionInicial.getColumna();
        int pColumna2 = posicionDestino.getColumna();

        boolean correcto = false;

        int offsetX = pColumna2 - pColumna1;
        int offsetY = pFila2 - pFila1;

        if ((offsetX == 0) || (offsetY == 0)) {
                if (this.comprobarIntermedio(pFila1, pColumna1, pFila2, pColumna2)) {
                    correcto = this.posFinalCorrecta(pFila2, pColumna2);
                    this.setPrimerMov();
                }
            }

        return correcto;
    }

    public String getIcono() {
        String icono;
        if (this.getJugador().getColor() == Color.WHITE) {
            icono = "\u2656";
        } else {
            icono = "\u265C";
        }
        return icono;
    }


}
