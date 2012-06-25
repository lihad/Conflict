package Lihad.Conflict;

public class Perk {

    public Perk(String n) { name = n; }

    String name;
    public String getName() { return name; }
    
    Node node;
    public Node getNode() { return node; }
    public void setNode(Node n) { node = n; }

};