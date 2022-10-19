//*********************************************************
//* Archivo: ArbolBinario.java
//Nombre: 
//Legajo: 
//DNI: 


import java.io.*;
//Definición de la clase NodoBinario
class NodoBinario{
    int dato;
    NodoBinario Hizq, Hder;
    //Constructor
    NodoBinario (int Elem){
        // Dato a almacenar
        dato = Elem;
        //Definición de hijos
        NodoBinario Hizq, Hder = null;
    }
}
//Definición de la clase Arbol
class Arbol{
    NodoBinario Padre;
    public NodoBinario Raiz;
    //Constructor
    public Arbol(){
        Raiz = null;
    }
    //Se codificaron 2 métodos y en caso de que el nodo padre de referencia sea nulo se asume que es la raíz
    // Inserción de un hijo izquierdo
    public NodoBinario InsertaNodoHIzq(NodoBinario Nodo, int Elem){
        NodoBinario result=null;
        Integer Elemento = new Integer(Elem);
        if (Nodo==null) {
            NodoBinario NodoAux = new NodoBinario (Elem);
            result=NodoAux;
            Raiz=NodoAux;
        } else {
            if(Nodo.Hizq == null) {
                NodoBinario NodoAux = new NodoBinario (Elem);
                Nodo.Hizq=NodoAux;
                result=NodoAux;
            }
            else
                System.out.print ("ERR- Hijo izquierdo de "+
                        Elemento.toString() +" no es nulo");
        }
        return result;
    }
    //Inserción de un hijo derecho
    public NodoBinario InsertaNodoHDer(NodoBinario Nodo, int Elem){
        NodoBinario result=null;
        Integer Elemento = new Integer(Elem);
        if (Nodo==null) {
            NodoBinario NodoAux = new NodoBinario (Elem);
            result=NodoAux;
            Raiz=NodoAux;
        } else {
            if(Nodo.Hder == null) {
                NodoBinario NodoAux = new NodoBinario (Elem);
                Nodo.Hder=NodoAux;
                result=NodoAux;
            }
            else
                System.out.print ("ERR- Hijo Derecho de "+
                        Elemento.toString() +" no es nulo");
        }
        return result;
    }
    //Inorden Recursivo del arbol
    public void Inorden (NodoBinario Nodo){
        if (Nodo.Hizq != null){
            this.Inorden(Nodo.Hizq);
        }
        System.out.println(Nodo.dato);
        if (Nodo.Hder != null) {
            this.Inorden(Nodo.Hder);
        }

    }
    //Altura del arbol
    public int Altura (NodoBinario Nodo){
        if(Nodo == null){
            return -1;
        } else {
            return 1 + Math.max(this.Altura(Nodo.Hizq), this.Altura(Nodo.Hder));
        }

    }
}
class ArbolBinario{
    public static void main(String[] ar) {
        Arbol A = new Arbol();
        System.out.print("Agregando la raiz 30 \n");
        NodoBinario
        NodoAux=null,NodoAux2=null,NodoAux3=null,NodoAux4=null;
        NodoAux2=A.InsertaNodoHIzq(NodoAux, 30);
        NodoAux=NodoAux2;
        NodoAux2=A.InsertaNodoHIzq(NodoAux, 25);
        NodoAux3=A.InsertaNodoHDer(NodoAux, 45);
        NodoAux=NodoAux2;
        NodoAux2=A.InsertaNodoHIzq(NodoAux, 20);
        NodoAux4=A.InsertaNodoHDer(NodoAux, 27);
        System.out.print("\n El árbol binario en In orden es: \n");
        A.Inorden (A.Raiz);
        Integer Altura = new Integer(A.Altura (A.Raiz));
        System.out.print("\n La altura del árbol es: "+ Altura.toString() +"\n");
    }
}