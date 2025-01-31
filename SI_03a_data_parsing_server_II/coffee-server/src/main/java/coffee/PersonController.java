package coffee;

import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@Getter
@Setter
public class PersonController {
    private PersonService personService;
    private ModelMapper modelMapper;

    @PermitAll
    @GetMapping("/test")
    public String getPersonString() {
        return "Returning a plain String to test that there's a response from Coffee-Server";
    }

    //Endpoint to call for getting data from snake-server
    @PermitAll
    @GetMapping("/snake-data")
    public Mono<ResponseEntity<PersonDto>> getPerson() {
        //Response Mono is a stream where conversion of POJO to JSON is handled automatically
        return personService.getPerson()
                .flatMap(person -> {
                    var personDto = modelMapper.map(person, PersonDto.class);
                    return Mono.just(ResponseEntity.ok(personDto));
                        })
                            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //Endpoint to call to get data from a file of specified file type on this server
    //TODO: Add error handling with .onErrorResume()
    @PermitAll
    @GetMapping("/{fileType}")
    public Mono<ResponseEntity<PersonDto>> getPersonFromFile(@PathVariable String fileType) {
        return personService.getPersonFromFile(fileType)
                .flatMap(person -> {
                    var personDto = modelMapper.map(person, PersonDto.class);
                    return Mono.just(ResponseEntity.ok(personDto));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //Endpoint to call to get data from some file on this server
    //Hardcoded file type = xml
    @PermitAll
    @GetMapping("/from-file")
    public Mono<ResponseEntity<PersonDto>> getPersonFromFile() {
        var fileTypeXml = "xml";
        return personService.getPersonFromFile(fileTypeXml)
                .flatMap(person -> {
                    var personDto = modelMapper.map(person, PersonDto.class);
                    return Mono.just(ResponseEntity.ok(personDto));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
