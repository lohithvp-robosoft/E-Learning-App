package com.robosoft.elearning.config;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.hibernate.collection.spi.PersistentMap;

import java.io.IOException;
import java.util.Map;

public class PersistentMapSerializer extends StdSerializer<PersistentMap> {
    public PersistentMapSerializer() {
        super(PersistentMap.class);
    }

    @Override
    public void serialize(PersistentMap value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        for (Object entry : value.entrySet()) {
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) entry;
            gen.writeObjectField(String.valueOf(e.getKey()), e.getValue());
        }
        gen.writeEndObject();
    }
}
