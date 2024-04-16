package com.bobabrewery.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class DateBeanSerializerModifier extends BeanSerializerModifier {
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for (Object beanProperty : beanProperties) {
            BeanPropertyWriter writer = (BeanPropertyWriter) beanProperty;
            if (isLocalDateType(writer)) {
                writer.assignSerializer(new LocalDateConverter());
            } else if (isLocalDateTimeType(writer)) {
                writer.assignSerializer(new LocalDateTimeConverter());
            }
        }
        return beanProperties;
    }

    private boolean isLocalDateType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return LocalDate.class.isAssignableFrom(clazz);
    }

    private boolean isLocalDateTimeType(BeanPropertyWriter writer) {
        Class<?> clazz = writer.getType().getRawClass();
        return LocalDateTime.class.isAssignableFrom(clazz);
    }

    public static class LocalDateConverter extends JsonSerializer<Object> {

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(((LocalDate) value).atStartOfDay().toInstant(ZoneOffset.of("+0")).toEpochMilli());
        }
    }

    public static class LocalDateTimeConverter extends JsonSerializer<Object> {

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(((LocalDateTime) value).toInstant(ZoneOffset.of("+0")).toEpochMilli());
        }
    }
}
