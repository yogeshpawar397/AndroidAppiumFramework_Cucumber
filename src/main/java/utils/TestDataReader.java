package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.FrameworkException;

import java.io.IOException;
import java.io.InputStream;

public final class TestDataReader {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    private TestDataReader() {
        // Utility class
    }

    public static JsonNode read(String fileName) {

        try (InputStream input =
                     TestDataReader.class
                             .getClassLoader()
                             .getResourceAsStream(
                                     "testdata/" + fileName
                             )) {

            if (input == null) {
                throw new FrameworkException(
                        "Test data file not found: "
                                + fileName
                );
            }

            return OBJECT_MAPPER.readTree(input);

        } catch (IOException e) {

            throw new FrameworkException(
                    "Unable to read test data file: "
                            + fileName,
                    e
            );
        }
    }
}