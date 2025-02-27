package coffee;

import lombok.Getter;

@Getter
public class FileParseException extends RuntimeException {
    private final String filePath;

    public FileParseException(String filePath, Throwable cause) {
        super("Error parsing file: " + filePath, cause);
        this.filePath = filePath;
    }

}
