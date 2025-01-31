package coffee;

import lombok.Getter;

@Getter
public enum FileType {
    XML(".xml"), JSON(".json"), TXT(".txt"), CSV(".csv"), YAML(".yaml");

    final String name;

    FileType(String name) {
        this.name = name;
    }
}
