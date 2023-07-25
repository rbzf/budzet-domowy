package ps.as;

public enum Type {
    W("wydatek"), P("przychod");

    private final String description;

    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
