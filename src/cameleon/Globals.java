package cameleon;

public class Globals
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
    public static final int ZONE_SIZE = 3;
}
