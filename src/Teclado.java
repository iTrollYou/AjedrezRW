
import java.util.Scanner;

class Teclado {

    //@SuppressWarnings("resource")
    public static String leerString(String mensaje) {
        System.out.print(mensaje);
        Scanner sc1 = new Scanner(System.in);
        return sc1.next();
    }

// --Commented out by Inspection START (01/05/2017 18:25):
//    //@SuppressWarnings("resource")
//    public static boolean leerBoolean(String mensaje) {
//        System.out.print(mensaje);
//        boolean bool = false;
//        Scanner sc2 = new Scanner(System.in);
//        String text = sc2.next();
//        if (text.equals("Y") || text.equals("y")) {
//            bool = true;
//        }
//        return bool;
//    }
// --Commented out by Inspection STOP (01/05/2017 18:25)
}
