package project;

public enum CodColor {
    RED("#FF0000"),
    GREEN("#00FF00"),
    BLUE("#0000FF"),
    YELLOW("#FFFF00"),
    ORANGE("#FFA500"),
    PURPLE("#800080"),
    PINK("#FFC0CB"),
    BLACK("#000000"),
    WHITE("#FFFFFF"),
    GRAY("#808080"),
    BROWN("#A52A2A"),
    GOLD("#FFD700"),
    SILVER("#C0C0C0"),
    BRONZE("#CD7F32"),
    CYAN("#00FFFF"),
    MAGENTA("#FF00FF"),
    LIME("#00FF00"),
    OLIVE("#808000"),
    MAROON("#800000"),
    NAVY("#000080"),
    TEAL("#008080"),
    AQUA("#00FFFF"),
    VIOLET("#EE82EE"),
    INDIGO("#4B0082"),
    TURQUOISE("#40E0D0"),
    BEIGE("#F5F5DC"),
    LAVENDER("#E6E6FA"),
    MINT("#F5FFFA"),
    APRICOT("#FBCEB1"),
    LAVENDER_BLUSH("#FFF0F5"),
    PEACH("#FFE5B4"),
    LEMON("#FFFACD"),
    MAGENTA_ROSE("#FF00A5"),
    DARK_GREEN("#006400"),
    DARK_BLUE("#00008B"),
    DARK_YELLOW("#CCCC00"),
    DARK_ORANGE("#FF8C00"),
    DARK_PURPLE("#4B0082"),
    DARK_PINK("#FF1493"),
    DARK_GRAY("#A9A9A9"),
    LIGHT_GRAY("#D3D3D3"),
    LIGHT_BROWN("#B5651D"),
    LIGHT_GOLD("#FFD700"),
    LIGHT_SILVER("#D9D9D9"),
    LIGHT_CYAN("#E0FFFF"),
    LIGHT_MAGENTA("#FFB6C1"),
    LIGHT_LIME("#32CD32"),
    LIGHT_OLIVE("#808000"),
    LIGHT_NAVY("#000080"),
    LIGHT_TEAL("#008080"),
    LIGHT_AQUA("#00FFFF"),
    LIGHT_VIOLET("#DA70D6"),
    LIGHT_INDIGO("#9370DB"),
    LIGHT_TURQUOISE("#AFEEEE"),
    LIGHT_BEIGE("#F5F5DC"),
    LIGHT_LAVENDER("#E6E6FA"),
    LIGHT_MINT("#BDFCC9"),
    LIGHT_APRICOT("#FFDAB9"),
    LIGHT_PEACH("#FFDAB9"),
    LIGHT_LEMON("#FFFACD");

    private final String hexCode;

    CodColor(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }
}