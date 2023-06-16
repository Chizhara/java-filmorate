package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

public class GsonBuilder {

    private static Gson gsonInstance;

    public static Gson getGson() {
        if (gsonInstance == null) {
            com.google.gson.GsonBuilder gsonBuilder = new com.google.gson.GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
            return gsonInstance = gsonBuilder.create();
        } else {
            return gsonInstance;
        }
    }

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {

        @Override
        public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
            if (localDate == null) {
                jsonWriter.value("null");
                return;
            }
            jsonWriter.value(localDate.toString());
        }

        @Override
        public LocalDate read(JsonReader jsonReader) throws IOException {
            final String text = jsonReader.nextString();
            if (text.equals("null")) {
                return null;
            }
            return LocalDate.parse(text);
        }
    }
}


