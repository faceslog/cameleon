package cameleon;

public class Config
{
    public static String GetANSI(int playerId)
    {
        switch (playerId)
        {
            case 1 -> {
                return ANSI_RED;
            }
            case 2 -> {
                return ANSI_BLUE;
            }
            default -> {
                return ANSI_RESET;
            }
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static final int FREE_SQUARE = 0;
    public static final int BRAVE_MAX_CASE_EARN = 8;
    public static final int ZONE_SIZE = 3; // CANNOT BE EQUAL TO 0 !!!

    public static final int RKL_SUB_ZONE_TO_EARN = 3; // Majorité ici 3 QuadTree.MAX_CAPACITY - 1
}
