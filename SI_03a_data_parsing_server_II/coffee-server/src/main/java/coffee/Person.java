package coffee;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonPropertyOrder({"name", "hobbies"})
public class Person {
    private String name;

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> hobbies;
}
