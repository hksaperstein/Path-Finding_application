


package edu.wpi.cs3733d18.teamp.Pathfinding;

import java.util.*;

public class DepthFirst extends Pathfinder {
    /**
     * getChildren takes a node and returns the nodes it is connected to
     * @param root
     * @return ArrayList<Node> the list of nodes that is connected to the root node with an edge
     */

    // Takes in a node and returns the nodes it is connected to
    public ArrayList<Node> getChildren(Node root) {

        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Node> children = new ArrayList<Node>();

        edges = root.getEdges();
        for (Edge e : edges) {
            Node nextNode;
            if (e.getStart() == root) {
                nextNode = e.getEnd();
                children.add(nextNode);
            } else {
                nextNode = e.getStart();
                children.add(nextNode);

            }
        }
        return children;
    }

    /**
     * findPath implemented with depth first search algorithm
     * @param srcNode
     * @param destNode
     * @return ArrayList<Node> that constitutes the path
     */
    @Override
    public ArrayList<Node> findPath(Node srcNode, Node destNode) {
        System.out.println("depth first");
        Stack<Node> stack = new Stack<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();
        stack.add(srcNode);
        visited.add(srcNode);
        // the loop will run until there are no more nodes left in the stack
        // or we find the destination and return the path
        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            System.out.println(currentNode + " " + destNode);

            // checks if it is the destination node
            if (currentNode.equals(destNode)) {
                System.out.println(currentNode.getParent());
                return this.getPath(currentNode);

            // if it is not the destination node it adds it to the visited nodes
            // and checks its children
            } else {
                visited.add(currentNode);

                List<Node> children = getChildren(currentNode);
                for (int i = 0; i < children.size(); i++) {
                    Node n = children.get(i);
                    if (n != null && !visited.contains(n)) {
                        stack.add(n);
                        n.setParent(currentNode);
                    }
                }
            }
        }
        return null;
    }
}
