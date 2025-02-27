package coffee;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;

@Service
@NoArgsConstructor
public class DataParser {

    public Mono<Person> parseFile(String fileType) {
        var directory = "resources/";
        var fileName = "me";
        var lowerCaseFileType = fileType.toLowerCase();
        var filePath = directory + fileName + "." + lowerCaseFileType;

        return
                switch (lowerCaseFileType) {
                    case "xml" ->
                            parseXML(filePath).onErrorResume(Exception.class, e -> Mono.error(new FileParseException(filePath, e)));
                    case "json" ->
                            parseJSON(filePath).onErrorResume(Exception.class, e -> Mono.error(new FileParseException(filePath, e)));
                    case "yaml" ->
                            parseYAML(filePath).onErrorResume(Exception.class, e -> Mono.error(new FileParseException(filePath, e)));
                    case "csv" ->
                            parseCSV(filePath).onErrorResume(Exception.class, e -> Mono.error(new FileParseException(filePath, e)));
                    default -> Mono.error(new IllegalArgumentException("Unsupported file type: " + fileType));
                };
    }

    private Mono<Person> parseXML(String filePath) {
        return Mono.fromCallable(() -> {
            var xmlMapper = new XmlMapper();
            xmlMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            var person = xmlMapper.readValue(new File(filePath), Person.class);
            System.out.println("coffee.Person object from XML:" + person);
            return person;
        });
    }

    private Mono<Person> parseJSON(String filePath) {
        return Mono.fromCallable(() -> {
            var person = new ObjectMapper().readValue(new File(filePath), Person.class);
        System.out.println("coffee.Person object from JSON:"+person);
        return person;
        });
    }

    private Mono<Person> parseYAML(String filePath) {
        return Mono.fromCallable(() -> {
            var mapper = new ObjectMapper(new YAMLFactory());
            var person = mapper.readValue(new File(filePath), Person.class);
            System.out.println("coffee.Person object from YAML:" + person);
            return person;
        });
    }

    private Mono<Person> parseCSV(String filePath) {
        var csvMapper = new CsvMapper();
        var schema = csvMapper.schemaFor(Person.class).withHeader();

        return Mono.fromCallable(() -> {
            var reader = csvMapper.readerFor(Person.class).with(schema);
            return reader.<Person>readValues(new File(filePath)).next();
        });
    }
}