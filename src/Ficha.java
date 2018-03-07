
public abstract class Ficha {
    //Atributos
    private Jugador jugador;
    private Posicion posicion;
    private boolean primerMov;

    Ficha(Jugador pJugador, Posicion pPosicion) {
        this.jugador = pJugador;
        this.posicion = pPosicion;
        this.primerMov = false;
    }

    Ficha() {
    }


    protected abstract boolean comprobarMovimiento(Posicion posicionInicial, Posicion posicionDestino);

    public abstract String getIcono();

    public Jugador getJugador() {
        return this.jugador;
    }

    boolean getPrimerMov() {
        return primerMov;
    }

    void setPrimerMov() {
        this.primerMov = true;
    }


    public String getTipoFicha() {
        String tipo;

        if (this instanceof Peon) {
            tipo = "PEON";
        } else if (this instanceof Torre) {
            tipo = "TORRE";
        } else if (this instanceof Caballo) {
            tipo = "CABALLO";
        } else if (this instanceof Alfil) {
            tipo = "ALFIL";
        } else if (this instanceof Rey) {
            tipo = "REY";
        } else {
            tipo = "REINA";
        }
        return tipo;
    }

    //
    public boolean realizarMovimiento(Posicion posicionDestino) {
        boolean movimientoCorrecto = this.comprobarMovimiento(this.posicion, posicionDestino);
        if (movimientoCorrecto) {
            Ficha[][] matrix = Tablero.getTablero().getMatriz();
            int filaInicial = this.posicion.getFila();
            int columnaInicial = this.posicion.getColumna();
            Ficha ficha = matrix[this.posicion.getFila()][this.posicion.getColumna()];
            ficha.posicion.setFila(posicionDestino.getFila());
            ficha.posicion.setColumna(posicionDestino.getColumna());
            matrix[posicionDestino.getFila()][posicionDestino.getColumna()] = ficha;
            matrix[filaInicial][columnaInicial] = null;
            if (ficha instanceof Rey) { //Enroque largo
                if (((Rey) ficha).getEnroqueLargo()) {
                    matrix[posicionDestino.getFila()][posicionDestino.getColumna() + 1] = matrix[posicionDestino.getFila()][posicionDestino.getColumna() - 1];
                    matrix[posicionDestino.getFila()][posicionDestino.getColumna() - 1] = null;
                    Ficha torre = matrix[posicionDestino.getFila()][posicionDestino.getColumna() + 1];
                    torre.posicion.setFila(posicionDestino.getFila());
                    torre.posicion.setColumna(posicionDestino.getColumna() + 1);
                }
                if (((Rey) ficha).getEnroqueCorto()) { //Enroque corto
                    matrix[posicionDestino.getFila()][posicionDestino.getColumna() - 1] = matrix[posicionDestino.getFila()][posicionDestino.getColumna() + 1];
                    matrix[posicionDestino.getFila()][posicionDestino.getColumna() + 1] = null;
                    Ficha torre = matrix[posicionDestino.getFila()][posicionDestino.getColumna() - 1];
                    torre.posicion.setFila(posicionDestino.getFila());
                    torre.posicion.setColumna(posicionDestino.getColumna() - 1);
                }

            }

            Tablero.getTablero().setMatriz(matrix);
        }

        return movimientoCorrecto;
    }

}
