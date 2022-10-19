import java.io.*;
import java.lang.Math;
import java.util.Scanner;
//Nombre: 
//Legajo:
//DNI: 
public class huffman {

    // Sub clases
    class ArbolHuffman{
        //Clase Nodo de arbol de huffman
        class NodoArbol {
            char clave;
            int frecuencia;
            boolean esdato;
            NodoArbol izquierda, derecha;

            public NodoArbol(char car, int frec,boolean EsD){
                clave = car;
                frecuencia=frec;
                esdato=EsD;
                izquierda = derecha = null;
            }
        }

        //Clase Nodo de una lista (lista de árboles)
        class NodoLista {
            public NodoArbol raiz;
            public NodoLista siguiente;

            public NodoLista(NodoArbol Nodo){
                raiz = Nodo;
                siguiente = null;
            }
        }
        // Nodo inicial de la lista, dato referencial
        NodoLista NodoInicial;

        //Tabla de Huffman para caracteres ASCCI
        int TablaHuffman[];
        //Tabla de Huffman para las frecuencias
        String TablaHuffmanCodigos[];
        int tamanotabla=0;
        int ByteToUnsignedByte(byte dato){
            int resultado=(int)dato;
            if (dato<0) {
                resultado=(int)dato+256;
            }
            return resultado;
        }

        // Creamos la tabla de Huffman en el arreglo (tabla de frecuencias)
        void CargarTablaDeArchivo(String NombreArchivo) {
            NodoInicial = null;
            TablaHuffman= new int[256];
            TablaHuffmanCodigos= new String[256];
            for (int i = 0; i <= 255; i++) {
                TablaHuffman[i]=0;
                TablaHuffmanCodigos[i]="";

            }
            System.out.println("Cargar archivo: "+NombreArchivo);
            try {
                RandomAccessFile file = new RandomAccessFile(NombreArchivo, "r");
                byte dato;
                int entero;
                long cont=0;
                long tamano = file.length();
                System.out.print("tamaño: ");
                System.out.println(tamano);
                while (cont<tamano) {
                    file.seek(cont);
                    dato=file.readByte();
                    entero=ByteToUnsignedByte(dato);
                    TablaHuffman[entero]++;
                    cont++;
                }
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i <= 255; i++) {
                if (TablaHuffman[i]>0) {
                    tamanotabla++;
                }

            }
            System.out.print("Tamaño de la tabla: ");
            System.out.println(tamanotabla);
        }

        //Crear Lista de arboles
        void InsertarNodoLista(NodoLista NodoAinsertar) {
            NodoLista AUXposnodo,AUXposnodoant,auxnodo;
            // Insertar el nodo en la lista
            if (NodoInicial== null){
                NodoInicial=NodoAinsertar;
            } else {
                AUXposnodo=NodoInicial;
                AUXposnodoant=null;

                while ((NodoAinsertar.raiz.frecuencia>=AUXposnodo.raiz.frecuencia) &
                        (AUXposnodo.siguiente != null)) {
                    AUXposnodoant=AUXposnodo;
                    AUXposnodo=AUXposnodo.siguiente;
                }
                if (AUXposnodoant == null) {
                    if (NodoAinsertar.raiz.frecuencia>=AUXposnodo.raiz.frecuencia)
                    {
                        AUXposnodo.siguiente=NodoAinsertar;
                        //NodoAinsertar.siguiente=null;

                    } else {
                        NodoAinsertar.siguiente=NodoInicial;
                        NodoInicial=NodoAinsertar;
                    }

                } else {
                    //AUXposnodoant.siguiente=NodoAinsertar;
                    if (NodoAinsertar.raiz.frecuencia>=AUXposnodo.raiz.frecuencia)
                    {
                        auxnodo=AUXposnodo.siguiente;
                        AUXposnodo.siguiente=NodoAinsertar;
                        NodoAinsertar.siguiente=auxnodo;
                    } else {
                        AUXposnodoant.siguiente=NodoAinsertar;
                        NodoAinsertar.siguiente=AUXposnodo;
                    }
                }

            }
        }

        void MostrarListaDeArboles() {
            NodoLista AUXnodo=NodoInicial;
            int count=0;
            System.out.print("Lista de arboles: ");
            while (AUXnodo != null){
                count++;
                System.out.print(count);
                System.out.print("'"+AUXnodo.raiz.clave+"'");
                System.out.print("(");
                System.out.print(AUXnodo.raiz.frecuencia);
                System.out.print("), ");
                AUXnodo=AUXnodo.siguiente;
            }
            System.out.println(" ");

        }

        void CrearListaDeArboles() {
            int entero;
            int pos=0;
            char Caracter;
            NodoLista AUXultimonodo=NodoInicial,AUXposnodo,AUXposnodoant;
            System.out.println("Tabla:");
            for (int i = 0; i <= 255; i++) {
                if (TablaHuffman[i]>0){
                    pos++;
                    Caracter=(char)i;
                    //Crear un nodo raiz de arbol que contiene DATOS (flag en true)
                    NodoArbol AUXNA = new NodoArbol((char)i,TablaHuffman[i],true);
                    NodoLista AUXNL = new NodoLista(AUXNA);
                    NodoLista AUXRECORIDONL;

                    //Contenido de la tabla
                    System.out.print("Nodo ");
                    System.out.print(pos);
                    System.out.print(" "+(char)i+"(");
                    System.out.print(TablaHuffman[i]);
                    System.out.print("); ");


                    InsertarNodoLista(AUXNL);
                }
            }
            System.out.println(" ");
            MostrarListaDeArboles();
        }


        // Proceso para reducir la lista a un solo nodo con un solo árbol
        void ProcesarListaDeArboles() {
            if (NodoInicial!= null){
                NodoLista AUXRECORIDONL,AUXNL1,AUXNL2,AUXNODOANT=null;
                NodoArbol AUXNAR1,AUXNAR2;
                AUXRECORIDONL=NodoInicial;
                //Verificar que exista la lista
                if (AUXRECORIDONL!= null) {
                    //Mientras la lista tenga más de 2 nodos, procesar.
                    while (AUXRECORIDONL.siguiente!=null) {
                        //Buscar los 2 primeros nodos
                        AUXNL1=AUXRECORIDONL;
                        AUXNL2=AUXRECORIDONL.siguiente;
                        //Asignar los árboles de cada nodo
                        AUXNAR1=AUXNL1.raiz;
                        AUXNAR2=AUXNL2.raiz;

                        // Eliminar ambos nodos de la lista
                        NodoInicial=AUXNL2.siguiente;
                        // Crear un nuevo nodo raíz que tenga los 2 subárboles que NO contiene DATOS (flag en false)
                        // con la sumatoria de frecuencias
                        NodoArbol AUXNA = new
                                NodoArbol('*',AUXNL1.raiz.frecuencia+AUXNL2.raiz.frecuencia,false);
                        AUXNA.izquierda=AUXNL1.raiz;
                        AUXNA.derecha=AUXNL2.raiz;

                        // Creamos un nuevo nodo de lista para insertar en reemplazo de los 2 eliminados
                        NodoLista AUXNL = new NodoLista(AUXNA);

                        // Insertar nodo nuevo a la lista en forma ordenada
                        InsertarNodoLista(AUXNL);
                        AUXRECORIDONL=NodoInicial;
                        //
                    }
                }
            }
        }

        void GenerarCodigosDeHuffman_recursivo(NodoArbol AUXNODORAIZ,String codigo) {
            // Si el nodo es una hoja lo agrego al mapa de codigos
            if (AUXNODORAIZ.izquierda == null && AUXNODORAIZ.derecha == null && AUXNODORAIZ.clave != 0){
                TablaHuffmanCodigos[AUXNODORAIZ.clave] = codigo;
                return;
            }
            // En caso de no ser un nodo hoja, se se le agrega un 0 o un 1 segun corresponde izquierda o derecha y se vuelva a aplicar la comparacion
            GenerarCodigosDeHuffman_recursivo(AUXNODORAIZ.izquierda, codigo + "0");
            GenerarCodigosDeHuffman_recursivo(AUXNODORAIZ.derecha, codigo + "1");
        }

        byte stringbytetobyte(String strtobyte) {
            byte Byteresult=0;
            int Intresult=0;

            if (strtobyte.length()>0)
                if (Integer.parseInt(strtobyte.substring(0,1)) > 0)
                    Intresult=Intresult+128;
            if (strtobyte.length()>1)
                if (Integer.parseInt(strtobyte.substring(1,2)) > 0)
                    Intresult=Intresult+64;
            if (strtobyte.length()>2)
                if (Integer.parseInt(strtobyte.substring(2,3)) > 0)
                    Intresult=Intresult+32;
            if (strtobyte.length()>3)
                if (Integer.parseInt(strtobyte.substring(3,4)) > 0)
                    Intresult=Intresult+16;
            if (strtobyte.length()>4)
                if (Integer.parseInt(strtobyte.substring(4,5)) > 0)
                    Intresult=Intresult+8;
            if (strtobyte.length()>5)
                if (Integer.parseInt(strtobyte.substring(5,6)) > 0)
                    Intresult=Intresult+4;
            if (strtobyte.length()>6)
                if (Integer.parseInt(strtobyte.substring(6,7)) > 0)
                    Intresult=Intresult+2;
            if (strtobyte.length()>7)
                if (Integer.parseInt(strtobyte.substring(7,8)) > 0)
                    Intresult=Intresult+1;
            Byteresult=(byte)Intresult;
            return Byteresult;
        }

        String procesarbuffer(String STRBUFF,RandomAccessFile archivo) throws
                IOException {
            String Auxstr=STRBUFF,STRINGBYTE="";
            while (Auxstr.length()>=8) {
                STRINGBYTE=Auxstr.substring(0,8);
                Auxstr=Auxstr.substring(8,Auxstr.length());
                archivo.writeByte(stringbytetobyte(STRINGBYTE));

            }
            return Auxstr;
        }

        void GenerarArchivoComprimido(String NombreArchivoO,String NombreArchivoD) {
            // Grabamos el archivo a comprimir
            String STRBuffer="";
            String STRBuffertmp="";
            //Borrar si existe el archivo
            File arch = new File(NombreArchivoD);
            if(arch.delete()) System.out.println("archivo borrado");
            try {
                //Abriendo el archivo original de solo lectura
                RandomAccessFile archivoorigen = new RandomAccessFile(NombreArchivoO, "r");
                //Abriendo el archivo destino como lectura escritura
                RandomAccessFile archivodestino = new RandomAccessFile(NombreArchivoD, "rw");
                int entero;
                byte dato;
                long cont=0;
                long tamano = archivoorigen.length();
                //System.out.println(STRBuffer);
                while (cont<tamano) {
                    archivoorigen.seek(cont);
                    dato=archivoorigen.readByte();
                    entero=ByteToUnsignedByte(dato);
                    //Codificar en buffer de string
                    //System.out.println(entero);
                    STRBuffer=STRBuffer+TablaHuffmanCodigos[entero];
                    STRBuffertmp=STRBuffertmp+" "+TablaHuffmanCodigos[entero];
                    //System.out.println(TablaHuffmanCodigos[entero]);
                    STRBuffer=procesarbuffer(STRBuffer,archivodestino);

                    cont++;
                }
                System.out.println(STRBuffertmp);
                archivoorigen.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Huffman
    public ArbolHuffman AH;

    void Comprimir(String NombreArchivo) {
        ArbolHuffman AH = new ArbolHuffman();
        if (AH !=null) {
            AH.CargarTablaDeArchivo(NombreArchivo);
            //System.out.println("crea lista: ");
            AH.CrearListaDeArboles();
            AH.ProcesarListaDeArboles();
            AH.GenerarCodigosDeHuffman_recursivo(AH.NodoInicial.raiz,"");
            AH.GenerarArchivoComprimido(NombreArchivo,NombreArchivo+".compress");
        }
    }

    public static void main(String[] args)  {

        // Crear arbol de huffman
        huffman AHuffman = new huffman();

        // Cambiar el nombre del archivo por el archivo deseado
        AHuffman.Comprimir("E:\\workspace\\AyED2\\tp4\\pruebacompresion.txt");
    }
}