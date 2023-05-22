public class testTree {
    public static void main(String[] args) {

        Tree arbol = new Tree();

        //Inicializar nodos, crear arbol y mostrar
        arbol.finderCSV();  //Buscamos el archivo .csv comprobando que existe
        arbol.readerNodes();    //Leemos los nodos del archivo .csv
        arbol.printTree();  //Mostramos el arbol en la terminal

        //Eliminar nodo
        arbol.deleteNode("2");
        arbol.printTree();  //Mostramos el arbol en la terminal

        //Mostrar Nodo
        System.out.println(arbol.findNode("1"));
        arbol.printTree();  //Mostramos el arbol en la terminal

        //Modificar nodo
        arbol.modifyNode("1","Morty","1","1","1","1","1","1");
        arbol.printTree();  //Mostramos el arbol en la terminal

        //Mostrar info de un nodo
        arbol.printNode("1");


    }
}
