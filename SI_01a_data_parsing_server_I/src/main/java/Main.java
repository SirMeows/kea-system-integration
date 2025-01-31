import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DataParser dataParser = new DataParser();
        var dir = "src/main/resources/";
        try {
            dataParser.parseXML(dir + "me.xml");
            dataParser.parseJSON(dir + "/me.json");
            dataParser.parseYAML(dir + "me.yaml");
            dataParser.parseCSV(dir + "me.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
