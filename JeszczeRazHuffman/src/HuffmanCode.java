import java.util.PriorityQueue;

abstract class HuffmanTree implements Comparable<HuffmanTree> {
    //implementuje metode do porównania czy jeden element ma mieć wiekszy priorytet lub mniejszy
    public final int frequency; // the frequency of this tree
    public HuffmanTree(int freq) { frequency = freq; }

    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
    // jeśliu watrość jest większa od 0 to mas WIĘKSZYT PRIORYTET!! >>> LINIJKA 37 PRIORITYQUEUE
}

class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents

    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}

class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // subtrees

    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}

public class HuffmanCode {
    // input is an array of frequencies, indexed by character code
    public static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();
                //kolejka priorytetowa posiada klucz/wartosc jak mapa
                //
        // initially, we have a forest of leaves
        // one for each non-empty character
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
        //JESLI WYSTĄPIEŃ WIĘCEJ NI 0 TO DODAJ DO KOLEJKI PRIORYTETOWEJ  0 >

        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();  // BIERZ 2 LITERY O NAJMNIEJSZSZEJ LICZBIE WYSTEPOWAN
            HuffmanTree b = trees.poll();

            // put into new node and re-insert into queue  // DODAJ DO LIŚCIA
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();  // WYRZUĆ Z KOLEJKI PRZEROBIONY ELEMENT i " wyrzuć wierzchołek drzewa ( góra)
        // który posiada wskaźnik do lewego i prawego liścia
    }

    public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;

            // print out character, frequency, and code for this leaf (which is just the prefix)
            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);

        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;

            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    public static void main(String[] args) {
        String test = "salamandra";

        // we will assume that all our characters will have
        // code less than 256, for simplicity
        int[] charFreqs = new int[256];
        // read each character and record the frequencies
        for (char c : test.toCharArray())
            charFreqs[c]++;   //zliczenie ilosci wystepowań znaków

        // build tree
        HuffmanTree tree = buildTree(charFreqs);

        // print out results
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        printCodes(tree, new StringBuffer());
    }
}