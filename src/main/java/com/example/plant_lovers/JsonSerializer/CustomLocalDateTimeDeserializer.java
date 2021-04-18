package com.example.plant_lovers.JsonSerializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    public CustomLocalDateTimeDeserializer(){
        this(null);
    }


    protected CustomLocalDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        var str = jsonParser.getText();

        var dateFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return LocalDateTime.parse(str, dateFormater);
    }

}
