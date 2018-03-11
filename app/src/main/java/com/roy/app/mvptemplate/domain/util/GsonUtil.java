package com.roy.app.mvptemplate.domain.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by arvato on 16/8/7.
 */

public class GsonUtil {

    public static TypeAdapter getBooleanAsIntAdapter(){
        TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
            @Override public void write(JsonWriter out, Boolean value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value);
                }
            }

            @Override public Boolean read(JsonReader in) throws IOException {
                JsonToken peek = in.peek();
                switch (peek) {
                    case BOOLEAN:
                        return in.nextBoolean();
                    case NULL:
                        in.nextNull();
                        return null;
                    case NUMBER:
                        return in.nextInt() == 1;
                    case STRING:
                        String v = in.nextString();
                        return v.equals("1")||Boolean.parseBoolean(v);
                    default:
                        throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
                }
            }
        };
        return booleanAsIntAdapter;
    }

    public static TypeAdapter getStringAdapter(){
        TypeAdapter<String> stringAdapter = new TypeAdapter<String>() {
            @Override public void write(JsonWriter out, String value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value);
                }
            }
            @Override public String read(JsonReader in) throws IOException {
                JsonToken peek = in.peek();
                switch (peek) {
                    case BOOLEAN:
                        return String.valueOf(in.nextBoolean());
                    case NULL:
                        in.nextNull();
                        return null;
                    case NUMBER:
                        String data = in.nextString();
                        if(data.endsWith(".0")){
                            data = data.substring(0,data.length()-2);
                        }else if(data.contains(".")&&data.endsWith("0")){
                            data = data.substring(0,data.length()-1);
                        }
                        return data;
                    case STRING:
                        return in.nextString();
                    default:
                        throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
                }
            }
        };
        return stringAdapter;
    }

    public static TypeAdapter getIntegerAdapter(){
        TypeAdapter<Integer> stringAdapter = new TypeAdapter<Integer>() {
            @Override public void write(JsonWriter out, Integer value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value);
                }
            }
            @Override public Integer read(JsonReader in) throws IOException {
                JsonToken peek = in.peek();
                switch (peek) {
                    case NUMBER:
                        return in.nextInt();
                    case BEGIN_OBJECT:
                        in.skipValue();
                        return -1;
                    case BEGIN_ARRAY:
                        in.skipValue();
                        return -1;
                    case BOOLEAN:
                        in.nextBoolean();
                        return -1;
                    case NULL:
                        in.nextNull();
                        return -1;
                    case STRING:
                        in.nextString();
                        return -1;
                    default:
                        throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
                }
            }
        };
        return stringAdapter;
    }

}
