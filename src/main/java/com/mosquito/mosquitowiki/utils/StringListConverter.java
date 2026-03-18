package com.mosquito.mosquitowiki.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        try { return mapper.writeValueAsString(list != null ? list : List.of()); }
        catch (Exception e) { return "[]"; }
    }

    @Override
    public List<String> convertToEntityAttribute(String json) {
        try { return mapper.readValue(json, new TypeReference<>() {}); }
        catch (Exception e) { return new ArrayList<>(); }
    }
}