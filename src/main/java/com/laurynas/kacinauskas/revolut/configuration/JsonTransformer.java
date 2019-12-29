package com.laurynas.kacinauskas.revolut.configuration;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class JsonTransformer {

    private static Jsonb jsonb = JsonbBuilder.create();

    private JsonTransformer() {
    }

    public static Jsonb getTransformer() {
        return jsonb;
    }

}
