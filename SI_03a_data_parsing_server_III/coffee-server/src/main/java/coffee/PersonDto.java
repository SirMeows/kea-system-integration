package coffee;

import lombok.*;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonDto {

    private String name;
    private List<String> hobbies;
}
