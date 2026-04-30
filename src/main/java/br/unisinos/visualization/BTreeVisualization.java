package br.unisinos.visualization;

import br.unisinos.model.BTree;
import br.unisinos.model.BTreeNode;

public class BTreeVisualization {

    public BTreeVisualization() {
        //no-op
    }

    public static String generateDot(final BTree tree) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph BTree {\n");
        sb.append("    node [shape=record];\n");

        if (tree.root != null) {
            generateDotNode(sb, tree.root, new int[]{0});
        }

        sb.append("}");
        return sb.toString();
    }

    private static int generateDotNode(final StringBuilder sb, final BTreeNode node, final int[] counter) {
        int myId = counter[0]++;

        // monta o label com as chaves do no
        StringBuilder label = new StringBuilder();
        for (int i = 0; i < node.getNumKeys(); i++) {
            if (i > 0) label.append(" | ");
            label.append(node.getKeys()[i]);
        }
        sb.append("    node").append(myId)
                .append(" [label=\"").append(label).append("\"];\n");

        // desce pros filhos e cria as arestas
        if (!node.isLeaf()) {
            for (int i = 0; i <= node.getNumKeys(); i++) {
                int childId = generateDotNode(sb, node.getChildren()[i], counter);
                sb.append("    node").append(myId)
                        .append(" -> node").append(childId).append(";\n");
            }
        }

        return myId;
    }
}