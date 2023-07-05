import java.util.Scanner;

class Ejercicio3 extends Practicas {
    public String name = "3---Inversor dígitos";
    public String description = "Devuelve el número con los dígitos invertidos";

    public void mainExec() {
        showDescription(name, description);

        int b = in.nextInt();
        int h = in.nextInt();
        System.out.println(b*h/2);
    }
}

class Ejercicio2 extends Practicas {
    public String name = "2---Inversor dígitos";
    public String description = "Devuelve el número con los dígitos invertidos";

    public void mainExec() {
        showDescription(name, description);

        int input = in.nextInt();
        String temp = ""+input;
        String out = "";
        for(int i=0;i<temp.length();i++){
            out += temp.charAt(temp.length()-i-1);
        }
        int out1 = Integer.parseInt(out);
        System.out.println(out1);
    }
}

class Ejercicio1 extends Practicas {
    public String name = "1---Programa Hola mundo";
    public String description = "Simplemente Hola mundo";

    public void mainExec() {
        showDescription(name, description);
        System.out.println("Hello world!");
    }
}

public class   {
    Scanner in = new Scanner(System.in);

    public void showDescription(String name, String description) {
        System.out.println(String.format("Nombre: %s \nDescripción: %s\nResultado de ejecución:\n", name, description));
    }

    public static void main(String args[]) {
           Ejercicio1 ej1 = new Ejercicio1();
           ej1.mainExec();

           Ejercicio2 ej2 = new Ejercicio2();
           ej2.mainExec();

           Ejercicio3 ej3 = new Ejercicio3();
           ej3.mainExec();
    }
}
