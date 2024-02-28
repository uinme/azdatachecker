package org.uinme.tools.azdatachecker.csvutil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Component
public class CsvReader {

    public List<List<String>> read(Path path) {
        CsvSchema schema =
                CsvSchema
                    .emptySchema()
                    .withoutHeader();
        
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        
        try {
            MappingIterator<List<String>> iterator =
                    mapper
                        .readerFor(List.class)
                        .with(schema)
                        .readValues(path.toFile());
            return (List<List<String>>) iterator.readAll();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
}
