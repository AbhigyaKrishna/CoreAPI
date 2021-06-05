package me.Abhigya.core.util.json;

public class JsonOptions {

    private boolean html_safe;
    private String indent;
    private boolean serialize_nulls;

    public JsonOptions() {
        this.html_safe = false;
        this.indent = null;
        this.serialize_nulls = true;
    }

    public boolean htmlSafe() {
        return html_safe;
    }

    public JsonOptions htmlSafe(boolean html_safe) {
        this.html_safe = html_safe;
        return this;
    }

    public String indent() {
        return (indent == null ? new String() : indent);
    }

    public JsonOptions indent(String indent) {
        this.indent = ((indent != null && indent.length() == 0) ? null : indent);
        return this;
    }

    public boolean serializeNulls() {
        return serialize_nulls;
    }

    public JsonOptions serializeNulls(boolean serialize_nulls) {
        this.serialize_nulls = serialize_nulls;
        return this;
    }

}
