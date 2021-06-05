package me.Abhigya.core.util.json;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import me.Abhigya.core.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;
import java.util.Map;
import java.util.Set;

public final class Json {

    public static Json load(File json_file, boolean check_encrypted) throws MalformedJsonException {
        try {
            StringBuilder contents = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(json_file));
            reader.lines().forEach(line -> {
                contents.append(line + StringUtils.LINE_SEPARATOR);
            });

            reader.close();
            return loadFromString(contents.toString(), check_encrypted);
        } catch (IOException e) {
            throw new MalformedJsonException(e);
        }
    }

    public static Json load(File json_file) throws MalformedJsonException {
        return load(json_file, true);
    }

    public static Json loadFromString(String contents, boolean check_encrypted) {
        if (check_encrypted) {
            if (StringEscapeUtils.escapeJava(StringUtils.deleteWhitespace(contents))
                    .equals(StringUtils.deleteWhitespace(contents))) {  // if encrypted
                contents = new String(Base64.decodeBase64(contents)); // decode!
            }
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(contents);
        if (!element.isJsonObject()) {
            throw new JsonSyntaxException("Illegal syntax!");
        }
        return new Json(element.getAsJsonObject());
    }

    public static Json loadFromString(String contents) {
        return loadFromString(contents, true);
    }

    public static Json getNew() {
        return new Json(new JsonObject());
    }

    private final JsonObject handle;
    private final Json root;
    private final JsonOptions options;

    private Json(JsonObject handle, Json root) {
        this.handle = handle;
        this.root = root;
        this.options = root.getOptions();
    }

    private Json(JsonObject handle) {
        this.handle = handle;
        this.root = this;
        this.options = new JsonOptions();
    }

    public JsonObject getHandle() {
        return handle;
    }

    public Json getRoot() {
        return root;
    }

    public JsonOptions getOptions() {
        return options;
    }

    public Json createObject(String name) {
        JsonElement present = handle.get(name);
        if (present != null) {
            if (!present.isJsonObject()) {
                return null;
            }
        } else {
            handle.add(name, (present = new JsonObject()));
        }
        return new Json(present.getAsJsonObject(), this);
    }

    public void add(String property, JsonElement value) {
        getHandle().add(property, value);
    }

    public JsonElement remove(String property) {
        return getHandle().remove(property);
    }

    public void addProperty(String property, String value) {
        getHandle().addProperty(property, value);
    }

    public void addProperty(String property, Number value) {
        getHandle().addProperty(property, value);
    }

    public void addProperty(String property, Boolean value) {
        getHandle().addProperty(property, value);
    }

    public void addProperty(String property, Character value) {
        getHandle().addProperty(property, value);
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return getHandle().entrySet();
    }

    public boolean has(String memberName) {
        return getHandle().has(memberName);
    }

    public JsonElement get(String memberName) {
        return getHandle().get(memberName);
    }

    public JsonPrimitive getAsJsonPrimitive(String memberName) {
        return getHandle().getAsJsonPrimitive(memberName);
    }

    public JsonArray getAsJsonArray(String memberName) {
        return getHandle().getAsJsonArray(memberName);
    }

    public JsonObject getAsJsonObject(String memberName) {
        return getHandle().getAsJsonObject(memberName);
    }

    public Json getAsJson(String memberName) {
        return new Json(getAsJsonObject(memberName), this);
    }

    public void save(File json_file, boolean encrypt) throws IOException {
        String contents = toString();
        if (encrypt) { // encrypting!
            contents = Base64.encodeBase64String(contents.getBytes());
        }

        FileWriter writer = new FileWriter(json_file);
        writer.append(contents);
        writer.close();
    }

    public void save(File json_file) throws IOException {
        save(json_file, false);
    }

    @Override
    public String toString() {
        try {
            StringWriter string_writer = new StringWriter();
            JsonWriter json_writer = new JsonWriter(string_writer);
            json_writer.setLenient(true);
            json_writer.setHtmlSafe(getOptions().htmlSafe());
            json_writer.setIndent(getOptions().indent());
            json_writer.setSerializeNulls(getOptions().serializeNulls());

            Streams.write(getHandle(), json_writer);
            return string_writer.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return getHandle().equals(obj);
    }

    @Override
    public int hashCode() {
        return getHandle().hashCode();
    }

}
