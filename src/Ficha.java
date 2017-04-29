
public abstract class Ficha {
    //Atributos
    private Jugador jugador;
    private Posicion posicion;
    private boolean primerMov;

    public Ficha(Jugador pJugador, Posicion pPosicion) {
        this.jugador = pJugador;
        this.posicion = pPosicion;
    }


    public abstract boolean comprobarMovimiento(Posicion posicionInicial, Posicion posicionDestino);

    public abstract String getIcono();

    public Jugador getJugador() {
        return this.jugador;
    }

    public boolean getPrimerMov() {
        return primerMov;
    }

    public void setPrimerMov(boolean primerMov) {
        this.primerMov = primerMov;
    }

    public boolean comprobarIntermedio(int pFila1, int pColumna1, int pFila2, int pColumna2) {

        Ficha[][] matrix = Tablero.getTablero().getMatriz();

        boolean correcto = true;
        int offsetX = pColumna2 - pColumna1;
        int offsetY = pFila2 - pFila1;
        int absOffsetX = Math.abs(pColumna2 - pColumna1);
        int absOffsetY = Math.abs(pFila2 - pFila1);

        if (offsetY == 0) { //Horizontal
            for (int i = 1; i < Math.abs(offsetX); i++) {
                if (matrix[pFila1][pColumna1 + (offsetX < 0 ? -i : i)] != null)
                    correcto = false;
            }
        } else if (offsetX == 0) { //Vertical
            for (int i = 1; i < absOffsetY; i++) {
                if (matrix[pFila1 + (offsetY < 0 ? -i : i)][pColumna1] != null)
                    correcto = false;
            }
        } else if (Math.abs(offsetY) == absOffsetX) {//Diagonal
            for (int i = 1; i < Math.abs(offsetX); i++) {
                if (matrix[pFila1 + (offsetY < 0 ? -i : i)][pColumna1 + (offsetX < 0 ? -i : i)] != null)
                    correcto = false;
            }
        }
        return correcto;
    }

    public boolean posFinalCorrecta(int pFila2, int pColumna2) {
        Ficha[][] matrix = Tablero.getTablero().getMatriz();
        return ((matrix[pFila2][pColumna2] == null) || (matrix[pFila2][pColumna2].getJugador() != Tablero.getTablero().getTurnoJugador()));
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

    public boolean realizarMovimiento(Posicion posicionDestino) {
        boolean movimientoCorrecto = this.comprobarMovimiento(this.posicion, posicionDestino);
        if(movimientoCorrecto) {
            Ficha[][] matrix = Tablero.getTablero().getMatriz();
            int filaInicial = this.posicion.getFila();
            int columnaInicial = this.posicion.getColumna();
            Ficha ficha = matrix[this.posicion.getFila()][this.posicion.getColumna()];
            ficha.posicion.setFila(posicionDestino.getFila());
            ficha.posicion.setColumna(posicionDestino.getColumna());
            matrix[posicionDestino.getFila()][posicionDestino.getColumna()] = ficha;
            matrix[filaInicial][columnaInicial] = null;
            if(ficha instanceof Rey  ) { //Enroque largo
                if (((Rey)ficha).getEnroqueLargo()){
                    matrix[posicionDestino.getFila()][posicionDestino.getColumna() + 1] = matrix[posicionDestino.getFila()][posicionDestino.getColumna() - 1];
                    matrix[posicionDestino.getFila()][posicionDestino.getColumna() - 1] = null;
                }else if (((Rey)ficha).getEnroqueCorto()){
                    matrix[posicionDestino.getFila()][posicionDestino.getColumna() - 1] = matrix[posicionDestino.getFila()][posicionDestino.getColumna() + 1];
                    matrix[posicionDestino.getFila()][posicionDestino.getColumna() + 1] = null;
                }

            }

            Tablero.getTablero().setMatriz(matrix);
        }

        return movimientoCorrecto;
    }

}
