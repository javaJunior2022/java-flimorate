package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class FilmorateApplication {
    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        ctx = SpringApplication.run(FilmorateApplication.class, args);
    }

    public static void start() {
        ctx = SpringApplication.run(FilmorateApplication.class);
    }

    public static void stop() {
        ctx.close();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

    static class LocalDateTimeAdapter extends TypeAdapter<LocalDate> {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE;

        @Override
        public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
            if (localDate == null) {
                jsonWriter.value("null");
                return;
            }
            jsonWriter.value(localDate.format(fmt));
        }

        @Override
        public LocalDate read(JsonReader jsonReader) throws IOException {
            final String text = jsonReader.nextString();
            if (text.equals("null")) {
                return null;
            }
            return LocalDate.parse(text, fmt);
        }
    }
}
