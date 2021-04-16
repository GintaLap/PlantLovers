package com.example.plant_lovers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    public CustomLocalDateTimeSerializer(){
        this(null);
    }


    protected CustomLocalDateTimeSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

        Date date = java.sql.Timestamp.valueOf(localDateTime);

        var str = formatter.format(date);

        var str2 = localDateTime.toString();

        jsonGenerator.writeString(str);
    }
}
