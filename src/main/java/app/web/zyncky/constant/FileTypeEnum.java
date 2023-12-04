package app.web.zyncky.constant;

import java.util.Objects;

import lombok.Getter;

@Getter
public enum FileTypeEnum {
    TXT(".txt"), PNG(".png"), SVG(".svg"), MP4(".mp4"), PDF(".pdf");

    private String value = "";

    private final static String DOT = ".";

    private FileTypeEnum(String value) {
        this.value = value.toLowerCase();
    }

    /**
     * Convert string value to it's equivalent 'FileTypeEnum'
     * 
     * @param value input value of String type
     * @return A FileTypeEnum equivalent to input value
     * @throws IllegalArgumentException if input is invalid (or) is not in the set
     *                                  of allowed FileTypeEnum values
     */
    public static FileTypeEnum parse(String value) throws IllegalArgumentException {
        if (Objects.isNull(value))
            throw new IllegalArgumentException("Unknown File Type");

        value = (value.startsWith(DOT)) ? value : DOT.concat(value);
        for (FileTypeEnum fileType : FileTypeEnum.values()) {
            if (fileType.getValue().equalsIgnoreCase(value)) {
                return fileType;
            }
        }

        throw new IllegalArgumentException("Unknown File Type");
    }
}
