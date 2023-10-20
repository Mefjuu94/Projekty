import java.util.*;

class HuffmanNode implements Comparable<HuffmanNode> {
    char character;
    int frequency;
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }
    public String toString(){ // dla zobaczenia ułozenia
        return String.valueOf(this.character);
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}

public class HuffmanCoding {
    public static Map<Character, String> encode(String input) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1); // jesli znajdzie litere zwiększa o jeden
            // jeśli nie domyślnie o 0  [zbiór litera/ilosc wystapien]
        }

        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }  // przeszukaj mape weź elementy i wsadź do kolejki priorytetowej > stwórz " obiekt

        // priorytet znaków ulozenie
//        System.out.println(Arrays.toString(priorityQueue.toArray()));

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode newNode = new HuffmanNode('\0', left.frequency + right.frequency);
            //tworzymy nowy wierzchołek > którego frequency bedzie sumą tych dwóch poprzednich
            newNode.left = left;
            newNode.right = right;
            priorityQueue.add(newNode);
            System.out.println("lewy : " + left.character);
            System.out.println("prawy : " + right.character);
        }

        HuffmanNode root = priorityQueue.poll();
        //gdzy zostana 2 elementy utworz ostatni wierzcholek który będzie korzeniem
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, "", huffmanCodes);

        return huffmanCodes;
    }

    private static void generateHuffmanCodes(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }

        if (node.character != '\0') { //
            huffmanCodes.put(node.character, code);
        }

        generateHuffmanCodes(node.left, code + "0", huffmanCodes);
        generateHuffmanCodes(node.right, code + "1", huffmanCodes);
        //rekurencja -> zbieranie kodów (prefiksow) poprzez rekurencyjne przechodzenie po drzewie
        //jak skończy zwraca całe drzewo
    }

    public static String encodeText(String input, Map<Character, String> huffmanCodes) {
        StringBuilder encodedText = new StringBuilder();
        for (char c : input.toCharArray()) {
            encodedText.append(huffmanCodes.get(c));
        }
        return encodedText.toString();
    }

    public static String decode(Map<Character, String> huffmanCodes, String encodedText) {
        StringBuilder decodedText = new StringBuilder();
        int index = 0;

        while (index < encodedText.length()) {
            for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) { //jedz po kazdynm elemencie mapy
                String code = entry.getValue(); // weź wertosć od elementu
                if (encodedText.startsWith(code, index)) { // jak kod zaczyna się prefiksem
                    decodedText.append(entry.getKey()); // dodaj do stringa
                    index += code.length();
                    break;
                }
            }
        }

        return decodedText.toString();
    }

    public static void main(String[] args) {
        String input = "sialababamakniewiedzialajak";
        Map<Character, String> huffmanCodes = encode(input);
        System.out.println("Kodowanie Huffmana: " + huffmanCodes);
        String encodedText = encodeText(input, huffmanCodes);
        System.out.println("Zakodowany tekst: " + encodedText);
        String decodedText = decode(huffmanCodes, encodedText);
        System.out.println("Odkodowany tekst: " + decodedText);
    }
}