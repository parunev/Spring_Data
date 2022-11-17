package com.example.json_exercise.constants;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public enum Utils {
    ;
    public static final ModelMapper MODEL_MAPPER = new ModelMapper();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void writeJsonIntoFile(List<?> objects, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(objects, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }

    public static void writeJsonIntoFile(Object object, Path filePath) throws IOException {
        final FileWriter fileWriter = new FileWriter(filePath.toFile());

        GSON.toJson(object, fileWriter);

        fileWriter.flush();
        fileWriter.close();
    }
}
