public enum Grade {
    PREPA,
    UNI;

    public static Grade parse(String text) {
        if (text == null) {
            return null;
        }
        return switch (text.trim().toLowerCase()) {
            case "prepa" -> PREPA;
            case "uni" -> UNI;
            default -> null;
        };
    }
}
