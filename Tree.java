import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Tree {

    class Node {
        private String nodeParent_id;
        private String node_id;
        private String node_name;
        private String child_nodes, leaf_node;
        private String tolorg_link, extinct;
        private String confidence, phylesis;
        private LinkedList<Node> children;

        public Node(
                String nodeParent_id,
                String node_id,
                String node_name,
                String child_nodes,
                String leaf_node,
                String tolorg_link,
                String extinct,
                String confidence,
                String phylesis) {

            this.nodeParent_id = nodeParent_id;
            this.node_id = node_id;
            this.node_name = node_name;
            this.child_nodes = child_nodes;
            this.leaf_node = leaf_node;
            this.tolorg_link = tolorg_link;
            this.extinct = extinct;
            this.confidence = confidence;
            this.phylesis = phylesis;
            this.children = new LinkedList<>();
        }

        public Node() {

            this.nodeParent_id = null;
            this.node_id = null;
            this.node_name = null;
            this.child_nodes = null;
            this.leaf_node = null;
            this.tolorg_link = null;
            this.extinct = null;
            this.confidence = null;
            this.phylesis = null;
            this.children = new LinkedList<>();
        }

        public String getNodeId() {
            return node_id;
        }

        public String getNodeName() {
            return node_name;
        }

        public String getNodeParentId() {
            return nodeParent_id;
        }

        public LinkedList<Node> getchildren() {
            return children;
        }

        public void agregarHijo(Node child) {
            children.add(child);
        }
    }

    private Node root;

    // Cosntructor
    public Tree() {
        root = null; // arbol vacio
    }

    // Insertar nodos
    public void insert(
            String nodeParent_id, String node_id, String node_name, String child_nodes, String leaf_node,
            String tolorg_link, String extinct, String confidence, String phylesis) {

        Node node = new Node(nodeParent_id, node_id, node_name, child_nodes, leaf_node, tolorg_link, extinct,
                confidence, phylesis); // Creamos
        // el
        // nodo
        // Si esta vacio el primer nodo en agregar sera el parent_id
        if (root == null) {
            // Si el árbol está vacío, el nuevo nodo se convierte en la raíz
            root = node;
        } else {
            Node parent = findPaternNode(root, nodeParent_id);
            if (parent != null) {
                // Si se encuentra el nodo padre, se agrega el nuevo nodo como hijo
                parent.children.add(node);
            } else {
                System.out.println("El nodo padre no existe.");
            }
        }
    }

    // Buscar padre
    private Node findPaternNode(Node currentNode, String node_id) {
        if (currentNode.node_id.equals(node_id)) {
            return currentNode;
        }
        for (Node child : currentNode.children) {
            Node result = findPaternNode(child, node_id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    // Eliminar Nodo
    public void deleteNode(String node_id) {
        root = deleteNodeRecursive(root, node_id);
    }

    private Node deleteNodeRecursive(Node currentNode, String node_id) {
        if (currentNode == null) {
            return null;
        }

        if (currentNode.getNodeId().equals(node_id)) {
            return null; // Eliminar el nodo actual y todos sus descendientes
        }

        // Eliminar el nodo y sus descendientes de los hijos
        LinkedList<Node> updatedChildren = new LinkedList<>();
        for (Node child : currentNode.getchildren()) {
            Node updatedChild = deleteNodeRecursive(child, node_id);
            if (updatedChild != null) {
                updatedChildren.add(updatedChild);
            }
        }
        currentNode.children = updatedChildren;

        return currentNode;
    }

    // Buscar nodo
    public String findNode(String node_id) {
        Node nodo = new Node();
        // Guardamos el nodo buscado en uno nuevo para mostrar info
        nodo = findNode(root, node_id);
        // Retornamos la info del nodo
        return nodo.node_id
                + ", " + nodo.node_name + ", " + nodo.child_nodes
                + ", " + nodo.leaf_node + ", " + nodo.tolorg_link
                + ", " + nodo.extinct + ", " + nodo.confidence
                + ", " + nodo.phylesis;
    }

    private Node findNode(Node currentNode, String node_id) {
        // Si se encuentra se retorna el nodo
        if (currentNode.node_id.equals(node_id)) {
            return currentNode;
        }
        // Nodo a nodo se busca por los hijos
        for (Node child : currentNode.children) {
            Node result = findNode(child, node_id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    // Modificar nodo
    public void modifyNode(String node_id, String new_node_name,
            String child_nodes, String leaf_node, String tolorg_link,
            String extinct, String confidence, String phylesis) {
        modifyNode(root, node_id, new_node_name, child_nodes, leaf_node, tolorg_link, extinct, confidence, phylesis);
    }

    private boolean modifyNode(Node currentNode, String node_id, String new_node_name,
            String child_nodes, String leaf_node, String tolorg_link,
            String extinct, String confidence, String phylesis) {
        // Si no se encuentra se retorna null o si no hay nodos
        if (currentNode == null) {
            return false;
        }

        // Si se encuentra retorna un true saliendo del metodo
        if (currentNode.getNodeId().equals(node_id)) {
            // Modifica info
            currentNode.node_name = new_node_name;
            currentNode.child_nodes = child_nodes;
            currentNode.leaf_node = leaf_node;
            currentNode.tolorg_link = tolorg_link;
            currentNode.extinct = extinct;
            currentNode.confidence = confidence;
            currentNode.phylesis = phylesis;
            return true;
        }

        // Busca en los hijos del nodo si antes no se encontro
        for (Node child : currentNode.getchildren()) {
            if (modifyNode(child, node_id, new_node_name, child_nodes, leaf_node, tolorg_link, extinct, confidence,
                    phylesis)) {
                return true;
            }
        }

        return false;
    }

    // Comprobar CSV
    String nodesCSV = "treeoflife_nodes.csv";
    private String line = "";

    public void finderCSV() {
        System.out.println("Buscando...");
        File archivoComprobarNodes = new File(nodesCSV); // Comprobar que existe
        if (archivoComprobarNodes.exists()) {
            System.out.println("Archivos encontrados\n" + "Operacion exitosa");
        } else {
            System.out.println("Archivos no encontrados\n" + "Operacion fallida");
        }
    }

    // Lector de nodos
    public void readerNodes() {
        boolean isFirstLineNodes = true; // Variable para identificar la primera línea
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(nodesCSV))) {

            // Lectura del CSV Fila x Fila
            while ((line = br.readLine()) != null) {
                System.out.println(i); // Iterador de Filas
                if (isFirstLineNodes) {
                    isFirstLineNodes = false;
                    continue; // Saltar el procesamiento de la primera línea
                }

                // Leer dato por "," asignando a un String
                String[] data = line.split(",");
                String nodeParent_id = data[0];
                String node_id = data[1];
                String node_name = data[2];
                String child_nodes = data[3];
                String leaf_node = data[4];
                String tolorg_link = data[5];
                String extinct = data[6];
                String confidence = data[7];
                String phylesis = data[8];
                this.insert(nodeParent_id, node_id, node_name, child_nodes, leaf_node,
                        tolorg_link, extinct, confidence, phylesis);
                System.out.println("Inserccion realizada con exito");
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mostrar Arbol
    public void printTree() {
        printNode(root, "");
    }

    private void printNode(Node node, String espacio) {
        if (node == null) {
            return;
        }

        // Imprimir el nodo actual y su nombre
        System.out.println(espacio + "|- " + node.getNodeId() + " : " + node.getNodeName());

        // Recorrer los hijos
        for (Node child : node.getchildren()) {
            printNode(child, espacio + "   ");
        }
    }

    // Mostrar Nodo
    public void printNode(String node_id) {
        printOnlyOneNode(root, node_id, "");
    }

    private void printOnlyOneNode(Node node, String node_id, String espacio) {
        if (node == null) {
            return;
        }
        // Verificar si el nodo actual coincide con el node_id buscado
        if (node.getNodeId().equals(node_id)) {
            System.out.println(espacio + "|- " + node.getNodeId()
                    + " : " + node.getNodeName() + " : " + node.child_nodes
                    + " : " + node.leaf_node + " : " + node.tolorg_link
                    + " : " + node.extinct + " : " + node.confidence
                    + " : " + node.phylesis);
            return;
        }
        // Recorrer los hijos
        for (Node hijo : node.getchildren()) {
            printOnlyOneNode(hijo, node_id, espacio + "  ");
        }
    }
    
}