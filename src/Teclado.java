
import java.util.Scanner;

class Teclado {

    public static String leerString(String mensaje) {
        System.out.print(mensaje);
        Scanner sc1 = new Scanner(System.in);
        return sc1.next();
    }

}
