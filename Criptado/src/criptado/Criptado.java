/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package criptado;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author username
 */
public class Criptado {
    static ArrayList<String> valores = new ArrayList();
    static ArrayList<String> usuarios = new ArrayList();
    static ArrayList<String> contrasenas = new ArrayList();
    Scanner teclado = new Scanner(System.in);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        llenarValores();
        crearContactos();
        
        String respuesta;
        int opcion = 0;
        
        do{
            System.out.println("\nRegistrar usuario, pulse 1");
            System.out.println("Visualizar usuarios, pulse 2");
            System.out.println("Salir, pulse otro");
            respuesta = teclado.nextLine();
            //Comprobar que sea un numero
            if(Character.isLetter(respuesta.charAt(0))){
                opcion = 3;
            }else{
                opcion = Integer.parseInt(respuesta);
                switch (opcion) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        visualizar();
                        break;
                    default:
                        opcion = 3;
                        break;
                }
            }
        }while(opcion != 3);
        
    }
    /**
     * Metodo para tener reigstros por defecto
     */
    public static void crearContactos(){
        usuarios.add("Juan Carlos Reynosa Mendez");
        contrasenas.add(cifrar("juanito_93"));
        usuarios.add("Thalia Hernánez Gutierrez");
        contrasenas.add(cifrar("9876donas"));
        usuarios.add("Daniel Cervantez Fernánez");
        contrasenas.add(cifrar("cervFer28"));
    }
    /**
     * Método para visualizar los contacto y sus contrasenas
     */
    public static void visualizar(){
        Scanner teclado = new Scanner(System.in);
        /*
        Mostramos los contactos
        */
        if(usuarios.isEmpty()){
            System.out.println("------------No hay Contactos------------");
        }else{
            System.out.println("------------------------------Contactos------------------------");
            System.out.println("       Usuario                         Contraseña");
            for(int i = 0; i < usuarios.size(); i++){
                System.out.println((i+1) +" " +usuarios.get(i) +"      " +contrasenas.get(i));
            }
            System.out.println("---------------------------------------------------------------");
            System.out.println("\nPara descifrar una contraseña, ingrese el numero de contacto");
            System.out.println("Para descifrar todas, ingrese 0");
            String respuesta = teclado.nextLine();
            int opcion;
            
            /*
            Verificamos que no haya ingresado una letra
            */
            if(Character.isLetter(respuesta.charAt(0))){
               System.out.println("----------Contacto inexistente-----------");
            }else{
                opcion = Integer.parseInt(respuesta);
                
                if(opcion == 0){
                    System.out.println("--------------------------------------Contractos-------------------------------------------");
                    System.out.println("       Usuario                       Contraseña            Contraseña sin cifrar");
                    for(int i = 0; i < usuarios.size(); i++){
                        System.out.println((i+1) +" " +usuarios.get(i) +"     " +contrasenas.get(i)+"             " +descifrar(contrasenas.get(i)));

                    }
                    System.out.println("-------------------------------------------------------------------------------------------");
                }
                else if(opcion <= usuarios.size()){
                    String contrasena = descifrar(contrasenas.get((opcion)-1));
                    System.out.println("-------------------------------------Contracto "+opcion+"------------------------------------------");
                    System.out.println("       Usuario                       Contraseña            Contraseña sin cifrar");
                    System.out.println(opcion +" " +usuarios.get(opcion -1) +"     " +contrasenas.get((opcion)-1)+"             " +contrasena );
                    System.out.println("-------------------------------------------------------------------------------------------");
                }else{
                    System.out.println("----------Contacto inexistente-----------");
                }
            }
            
            
        }
    }    
    /**
     * Método para registrar un nuevo contacto ingresado por el usuario
     * mariela, liliana, chinos, dulce, la señora, alejandra, 
     */
    public static void registrar(){
        Scanner teclado = new Scanner(System.in);
        System.out.println("----------------Registrar---------------");
        System.out.println("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.println("Contraseña: ");
        String contrasena = teclado.nextLine();
        System.out.println("----------Contacto registrado-----------");
        /*
        CIFRAR contraseña y guardar registro
        */
        String cifrada = cifrar(contrasena);
        usuarios.add(nombre);
        contrasenas.add(cifrada);
    }
    /*********************METODOS PARA AGREGAR NUEVO*************************/
    /**
     * Método que invierte la contraseña, cambia los valores y agrega basura 
     * para cifrar la contraseña
     * @param contrasena
     * @return 
     */
    public static String cifrar(String contrasena){
        String invertida = invertir(contrasena);
        String cifrada="";
        for(int i = 0; i < invertida.length(); i++){
            char caracter = invertida.charAt(i);
            cifrada = cifrada + cifrarCaracter(caracter) + agregarBasura();
        }
        return cifrada;
    }
    /**
     * Método que hace un string inverso
     * @param contrasena
     * @return 
     */
    private static String invertir(String contrasena) {
        String invertida="";
        for (int x=contrasena.length()-1; x>=0; x--){
            invertida = invertida + contrasena.charAt(x);
        }
        return invertida;
    }
    /**
     * Método que reemplaza el caracter real por uno diferente (el siguiente del arreglo)
     * @param caracter
     * @return 
     */
    private static char cifrarCaracter(char caracter) {
        String letra = ""+caracter;
        int indiceActual = valores.indexOf(letra);
        int indiceNuevo;
        if(indiceActual == 105){
            indiceNuevo = 0;
        }else{
            indiceNuevo = indiceActual+1;
        }
        return valores.get(indiceNuevo).charAt(0);
    }
    /**
     * Método que agrega elementos basura despues de cada caracter
     * @return 
     */
    private static char agregarBasura() {
        Random rnd = new Random();
        int numero = (int) (Math.random()*106);
        return valores.get(numero).charAt(0);
    }
    
    /***********************METODOS PARA DESCIFRAR***************************/
    /**
     * Metodo mediante el cual eliminamos basura e invertimos el caracter para 
     * obtener la contraseña original
     * @param contrasena
     * @return 
     */
    public static String descifrar(String contrasena){
        String descifrada="";
        String sinBasura = eliminarBasura(contrasena);
        //System.out.println("Sin basura= " +eliminarBasura(sinBasura));
        //Descifrar
        for(int i = 0; i < sinBasura.length(); i++){
            char caracter = sinBasura.charAt(i);
            descifrada = descifrada + descifrarCaracter(caracter);
        }
        descifrada = invertir(descifrada);
        return descifrada;
    }
    /**
     * Método que elimina todos los elementos en indices pares, porque son basura
     * @param contrasena
     * @return 
     */
    private static String eliminarBasura(String contrasena){
        String sinBasura="";
        for(int i = 0; i <(contrasena.length()-1); i = i+2){
            sinBasura= sinBasura + contrasena.charAt(i);
        }
        return sinBasura;
    }
    /**
     * Método que regresa el caracter real
     * @param caracter
     * @return 
     */
    private static char descifrarCaracter(char caracter){
        String letra = ""+caracter;
        int indiceActual = valores.indexOf(letra);
        int indiceNuevo;
        if(indiceActual == 0){
            indiceNuevo = 105;
        }else{
            indiceNuevo = indiceActual-1;
        }
        return valores.get(indiceNuevo).charAt(0);
    }
    /**
     * Metodo que acomoda todos los valores que se pueden ingresar 
     * para no seguir el modelo ascii y que sea más difícil de descifrar
     */
    private static void llenarValores() {
        valores.add(",");valores.add("`");valores.add("D");valores.add("8");valores.add("(");valores.add("í");valores.add("q");valores.add("B");
        valores.add("'");valores.add("c");valores.add("Y");valores.add("}");valores.add("g");valores.add("i");valores.add("^");valores.add("F");
        valores.add("á");valores.add("w");valores.add("L");valores.add("S");valores.add("1");valores.add("#");valores.add("%");valores.add("_");
        valores.add("v");valores.add("h");valores.add("ú");valores.add("7");valores.add("Q");valores.add("j");valores.add("R");valores.add("ó");
        valores.add("O");valores.add("s");valores.add("f");valores.add(";");valores.add("A");valores.add("n");valores.add("N");valores.add("P");
        valores.add("2");valores.add("X");valores.add("]");valores.add("u");valores.add(".");valores.add("3");valores.add("¿");valores.add("m");
        valores.add(">");valores.add("0");valores.add("W");valores.add("a");valores.add("è");valores.add("J");valores.add("9");valores.add("*");
        valores.add("e");valores.add("b");valores.add("@");valores.add("ù");valores.add("C");valores.add("k");valores.add("E");valores.add("+");
        valores.add("H");valores.add("-");valores.add("x");valores.add("U");valores.add("¡");valores.add(")");valores.add("4");
        valores.add("{");valores.add("´");valores.add("d");valores.add("T");valores.add("ç");valores.add("Z");valores.add("t");
        valores.add("=");valores.add("M");valores.add("/");valores.add("z");valores.add("V");valores.add("6");valores.add("[");
        valores.add("!");valores.add("r");valores.add("$");valores.add("ì");valores.add("y");valores.add("Ñ");valores.add("o");
        valores.add("5");valores.add("p");valores.add("à");valores.add(":");valores.add("I");valores.add("ñ");valores.add("?");
        valores.add("ò");valores.add("<");valores.add("l");valores.add("é");valores.add("K");valores.add("G");valores.add("&");
    }

    
}
