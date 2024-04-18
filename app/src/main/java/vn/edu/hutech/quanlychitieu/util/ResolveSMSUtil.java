package vn.edu.hutech.quanlychitieu.util;

import java.util.Arrays;
public class ResolveSMSUtil {

    private static final String[] listOfBanks = {"AGRIBANK", "VIETCOMBANK", "VIETINBANK", "BIDV", "TECHCOMBANK", "SACOMBANK", "MBBANK", "SHBANK", "VPBANK", "ACB", "TPBANK", "DONGABANK", "SEABANK", "ABBANK", "BACABANK", "MSB"};

    public static int getAmountFromMessage(String message) {
        message = message.replace(" ", "");
        message = message.replace(",", "");

        boolean isIncoming  = false, isOutgoing = false;
        int incomingValue  = -1, outGoingValue = -1;
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '+' && Character.isDigit(message.charAt(i+1))) {
                int j = i + 1;
                StringBuilder amount = new StringBuilder();
                while (j < message.length() && Character.isDigit(message.charAt(j))) {
                    amount.append(message.charAt(j));
                    j++;
                }

                if (amount.toString().length() >= 3) {
                    return ConversionUtil.stringToInt(amount.toString(), -1);
                }
            } else if (message.charAt(i) == '-' && Character.isDigit(message.charAt(i+1))) {
                int j = i + 1;
                StringBuilder amount = new StringBuilder();
                while ( j < message.length() && Character.isDigit(message.charAt(j))) {
                    amount.append(message.charAt(j));
                    j++;
                }
                if (amount.toString().length() >= 3) {
                    return -ConversionUtil.stringToInt(amount.toString(), -1);
                }
            }
        }
        return -1;
    }

    public static boolean isBank(String sender) {
        return Arrays.asList(listOfBanks).contains(sender.toUpperCase());
    }
}
