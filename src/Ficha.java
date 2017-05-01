
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
    } //Comprobacion de movimiento reina


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

    boolean comprobarIntermedio(int pFila1, int pColumna1, int pFila2, int pColumna2) {

        Ficha[][] matrix = Tablero.getTablero().getMatriz();

        boolean correcto = true;
        int offsetX = pColumna2 - pColumna1;
        int offsetY = pFila2 - pFila1;
        int absOffsetX = Math.abs(pColumna2 - pColumna1);
        int absOffsetY = Math.abs(pFila2 - pFila1);

        if (absOffsetX == absOffsetY) {
            if ((offsetX > 0 && offsetY < 0) || (offsetX < 0 && offsetY > 0)) { //Diagonal con pendiente positiva
                int filaMenor = Math.min(pFila1, pFila2);
                int columnaMayor = Math.max(pColumna1, pColumna2);
                int i = 1;
                while (i < absOffsetY && correcto) {
                    if (matrix[filaMenor + i][columnaMayor - i] != null) {
                        correcto = false;
                    }
                    i++;
                }
            } else {//Diagonal con pendiente negativa
                int filaMenor = Math.min(pFila1, pFila2);
                int columnaMenor = Math.min(pColumna1, pColumna2);

                int i = 1;
                while (i < absOffsetY && correcto) {
                    if (matrix[filaMenor + i][columnaMenor + i] != null) {
                        correcto = false;
                    }
                    i++;
                }
            }
        } else {
            if (absOffsetX == 0) { //Movimiento vertical
                int filaMenor = Math.min(pFila1, pFila2);
                int i = 1;
                while (i < absOffsetY && correcto) {
                    if (matrix[filaMenor + i][pColumna1] != null) {
                        correcto = false;
                    }
                    i++;
                }
            } else { //Movimiento horizontal
                int columnaMenor = Math.min(pColumna1, pColumna2);
                int i = 1;
                while (i < absOffsetX && correcto) {
                    if (matrix[pFila1][columnaMenor + i] != null) {
                        correcto = false;
                    }
                    i++;
                }
            }
        }
        return correcto;
    }

    boolean posFinalCorrecta(int pFila2, int pColumna2) {
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
