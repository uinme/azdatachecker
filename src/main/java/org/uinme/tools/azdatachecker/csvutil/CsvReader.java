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

    public Csv read(Path path) {
        CsvSchema schema = CsvSchema
                .emptySchema()
                .withoutHeader();

        CsvMapper mapper = CsvMapper
                .builder()
                .enable(CsvParser.Feature.WRAP_AS_ARRAY)
                .enable(CsvParser.Feature.SKIP_EMPTY_LINES)
                .build();

        try {
            MappingIterator<List<String>> iterator = mapper
                    .readerFor(List.class)
                    .with(schema)
                    .readValues(path.toFile());
            Csv csv = new Csv();
            csv.setData((List<List<String>>) iterator.readAll());
            return csv;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
