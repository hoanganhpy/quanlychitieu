package vn.edu.hutech.quanlychitieu.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import vn.edu.hutech.quanlychitieu.model.Notification;
import vn.edu.hutech.quanlychitieu.model.Transaction;
import vn.edu.hutech.quanlychitieu.model.TransactionGroup;
import vn.edu.hutech.quanlychitieu.model.WalletType;

public class SQLiteUtil extends SQLiteOpenHelper {

    // Database configuration
    private static final String DATABASE_NAME = "QUAN_LY_CHI_TIEU";
    private static final int DATABASE_VERSION = 1;

    // Tables
    private static final String TABLE_TRANSACTION = "TBL_TRANSACTION";
    private static final String TABLE_PROPERTIES = "TBL_PROPERTIES";
    private static final String TABLE_GROUP = "TABLE_GROUP";
    private static final String TABLE_NOTIFICATION = "TABLE_NOTIFICATION";

    // Table transaction
    private static final String TRANSACTION_ID = "ID";
    private static final String TRANSACTION_AMOUNT = "AMOUNT";
    private static final String TRANSACTION_GROUP_ID = "GROUP_ID";
    private static final String TRANSACTION_NOTE = "NOTE";
    private static final String TRANSACTION_DATE = "DATE";
    private static final String TRANSACTION_WALLET_TYPE = "WALLET_TYPE";

    //Table properties
    private static final String PROPERTIES_KEY = "KEY_";
    private static final String PROPERTIES_VALUE = "VALUE";

    //Table group
    private static final String GROUP_ID = "ID";
    private static final String GROUP_NAME = "NAME";
    private static final String GROUP_TYPE = "TYPE";

    //Table notification
    private static final String NOTIFICATION_ID = "ID";
    private static final String NOTIFICATION_CONTENT = "CONTENT";
    private static final String NOTIFICATION_DATE = "DATE";
    private static final String NOTIFICATION_STATUS = "STATUS";

    //Constant app properties
    public static final String PROPERTIES_CURRENT_MONEY_AMOUNT_NORMAL = "CURRENT_MONEY_AMOUNT_NORMAL";
    public static final String PROPERTIES_CURRENT_MONEY_AMOUNT_BANK_CARD = "CURRENT_MONEY_AMOUNT_BANK_CARD";
    public static final String PROPERTIES_USER_SECRET_KEY = "PROPERTIES_USER_SECRET_KEY";


    private static final String CREATE_TABLE_PROPERTIES =
            "CREATE TABLE " + TABLE_PROPERTIES + " ("
                    + PROPERTIES_KEY + " TEXT, "
                    + PROPERTIES_VALUE + " TEXT, "
                    + "PRIMARY KEY (" + PROPERTIES_KEY + "," + PROPERTIES_VALUE + ")"
                    + ")";



    public SQLiteUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //logger =LogUtil.getLogger("Data Manipulation Object");
    }



    private Map<String, String> prepareCreateTableTransaction() {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(TRANSACTION_ID, " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        mp.put(TRANSACTION_AMOUNT, " INTEGER, ");
        mp.put(TRANSACTION_GROUP_ID, " INTEGER, ");
        mp.put(TRANSACTION_NOTE, " TEXT, ");
        mp.put(TRANSACTION_DATE, " INTEGER, ");
        mp.put(TRANSACTION_WALLET_TYPE, " INTEGER");
        return mp;
    }
    private Map<String, String> prepareCreateTableGroup() {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(GROUP_ID, " INTEGER PRIMARY KEY, ");
        mp.put(GROUP_NAME, " TEXT, ");
        mp.put(GROUP_TYPE, " INTEGER");
        return mp;
    }
    private Map<String, String> prepareCreateTableNotification() {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(NOTIFICATION_ID, " INTEGER PRIMARY KEY, ");
        mp.put(NOTIFICATION_CONTENT, " TEXT, ");
        mp.put(NOTIFICATION_DATE, " INTEGER, ");
        mp.put(NOTIFICATION_STATUS, " TEXT");
        return mp;
    }



    private Map<String, String> prepareInsertIntoTableTransaction(int amount, int groupId, String note, long date, int walletType) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(TRANSACTION_AMOUNT + ", ", amount + ", ");
        mp.put(TRANSACTION_GROUP_ID + ", ", groupId + ", ");
        mp.put(TRANSACTION_NOTE + ", ", "'" + note + "', ");
        mp.put(TRANSACTION_DATE + ", ", date + ", ");
        mp.put(TRANSACTION_WALLET_TYPE, walletType + "");
        return mp;
    }
    private Map<String, String> prepareInsertIntoTableGroup(String groupName, int groupType) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(GROUP_NAME + ", ", "'" + groupName + "', ");
        mp.put(GROUP_TYPE, groupType + "");
        return mp;
    }
    private Map<String, String> prepareInsertIntoTableNotification(String content, long date, String status) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(NOTIFICATION_CONTENT + ", ", "'" + content + "', ");
        mp.put(NOTIFICATION_DATE + ", ", date + ", ");
        mp.put(NOTIFICATION_STATUS, "'" + status + "'");
        return mp;
    }
    private Map<String, String> prepareInsertIntoTableProperties(String key, String value) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(PROPERTIES_KEY + ", ", "'" + key + "', ");
        mp.put(PROPERTIES_VALUE, "'" + value + "'");
        return mp;
    }



    private Map<String, String> prepareUpdateTableTransaction(int amount, int groupId, String note, long date, int walletType) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(TRANSACTION_AMOUNT, amount + ", ");
        mp.put(TRANSACTION_GROUP_ID, groupId + ", ");
        mp.put(TRANSACTION_NOTE, "'" + note + "', ");
        mp.put(TRANSACTION_DATE, date + "");
        return mp;
    }
    private Map<String, String> prepareUpdateTableGroup(String groupName, int groupType) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(GROUP_NAME, "'" + groupName + "', ");
        mp.put(GROUP_TYPE, groupType + "");
        return mp;
    }
    private Map<String, String> prepareUpdateTableNotification(String content, long date, String status) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(NOTIFICATION_CONTENT, "'" + content + "', ");
        mp.put(NOTIFICATION_DATE, date + "");
        mp.put(NOTIFICATION_STATUS, "'" + status + "'");
        return mp;
    }
    private Map<String, String> prepareUpdateTableProperties(String key, String value) {
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put(PROPERTIES_VALUE, "'" + value + "'");
        return mp;
    }

    private String getCreateQuery(String tableName, Map<String, String> tableFields) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ").append(tableName).append("(");
        for (Map.Entry<String, String> entry : tableFields.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key).append(" ").append(value);
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
    private String getInsertQuery(String tableName, Map<String, String> values) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("INSERT INTO ").append(tableName).append("(");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" VALUES(");

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder1.append(key);
            stringBuilder2.append(value);
        }
        stringBuilder1.append(")");
        stringBuilder2.append(")");
        return stringBuilder1.toString() + stringBuilder2.toString();
    }
    private String getDeleteQuery(String tableName, int id) {
        return "DELETE FROM " + tableName + " WHERE id = " + id;
    }
    private String getUpdateQuery(String tableName, Map<String, String> values, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE ").append(tableName).append(" SET ");

        for (Map.Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key).append(" = ").append(value);
        }
        stringBuilder.append(" where id = ").append(id);

        return stringBuilder.toString();
    }
    private String getUpdatePropertiesQuery(String key, String value) {
        return "UPDATE " + TABLE_PROPERTIES + " SET " +
                PROPERTIES_VALUE + " = " + value +
                " where " + PROPERTIES_KEY + " = '" + key + "'";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table transaction
        db.execSQL(getCreateQuery(TABLE_TRANSACTION, prepareCreateTableTransaction()));

        //Create table properties
        db.execSQL(CREATE_TABLE_PROPERTIES);

        //Create table group
        db.execSQL(getCreateQuery(TABLE_GROUP, prepareCreateTableGroup()));

        //Create table notification
        db.execSQL(getCreateQuery(TABLE_NOTIFICATION, prepareCreateTableNotification()));

        //Insert init properties
        db.execSQL(getInsertQuery(TABLE_PROPERTIES, prepareInsertIntoTableProperties(PROPERTIES_CURRENT_MONEY_AMOUNT_NORMAL, "0")));
        db.execSQL(getInsertQuery(TABLE_PROPERTIES, prepareInsertIntoTableProperties(PROPERTIES_CURRENT_MONEY_AMOUNT_BANK_CARD, "0")));
        db.execSQL(getInsertQuery(TABLE_PROPERTIES, prepareInsertIntoTableProperties(PROPERTIES_USER_SECRET_KEY, "1234")));

        //Insert init group
        ArrayList<String> outGoingList = new ArrayList<>(Arrays.asList("Ăn uống", "Cafe", "Tiền điện", "Tiền nước", "Thẻ điện thoại", "Internet", "Thuê nhà", "Xăng xe", "Thời trang", "Thiết bị điện tử", "Bạn bè & Người yêu", "Xem phim", "Du lịch", "Thuốc", "Chăm sóc cá nhân", "Tiệc", "Con cái", "Sửa chữa nhà cửa", "Sách", "Rút tiền", "Khoản chi khác"));
        ArrayList<String> inComingList = new ArrayList<>(Arrays.asList("Lương", "Thưởng", "Tiền lãi", "Bán đồ", "Vay tiền", "Khoản thu khác"));

        for (String item : outGoingList) {
            db.execSQL(getInsertQuery(TABLE_GROUP, prepareInsertIntoTableGroup(item, TransactionGroup.OUTGOING)));
        }

        for (String item : inComingList) {
            db.execSQL(getInsertQuery(TABLE_GROUP, prepareInsertIntoTableGroup(item, TransactionGroup.INCOMING)));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //System.out.println("Database version has changed, updating database...");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        //System.out.println("DROP TABLE IF EXISTS " + TABLE_ADS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);
        //System.out.println("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        //System.out.println("DROP TABLE IF EXISTS " + TABLE_ADS_TO_BE_DELETED);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);

        onCreate(db);

    }



    public void insertNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(getInsertQuery(TABLE_NOTIFICATION, prepareInsertIntoTableNotification(notification.getContent(), notification.getDate().getTime(), Notification.STATUS_UNSEEN)));
    }
    public void updateNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(getUpdateQuery(TABLE_NOTIFICATION, prepareUpdateTableNotification(notification.getContent(), notification.getDate().getTime(), notification.getStatus()), String.valueOf(notification.getId())));
    }
    public void deleteNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(getDeleteQuery(TABLE_NOTIFICATION, notification.getId()));
    }
    public List<Notification> getAllNotification() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTIFICATION + " ORDER BY "+ NOTIFICATION_DATE + " DESC";

        List<Notification> lst = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return lst;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex(NOTIFICATION_ID));
                String content = c.getString(c.getColumnIndex(NOTIFICATION_CONTENT));
                Date date = ConversionUtil.timestampToDate(c.getLong(c.getColumnIndex(NOTIFICATION_DATE)));
                String status = c.getString(c.getColumnIndex(NOTIFICATION_STATUS));
                lst.add(new Notification(id, content, date, status));
                c.moveToNext();
            }
            c.close();
            //System.out.println(lst.size() + " ads file to be deleted");
            return lst;
        }
    }


    public void insertProperties(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(getInsertQuery(TABLE_PROPERTIES, prepareInsertIntoTableProperties(key, value)));
    }
    public void updateProperties(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(getUpdatePropertiesQuery(key, value));
    }
    public boolean propertiesExists(String key) {
        boolean result = false;
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String query = "Select * from " + TABLE_PROPERTIES + " where " + PROPERTIES_KEY + " = " + key;
        Cursor cursor = sqldb.rawQuery(query, null);
        if(cursor.getCount() > 0){
            result = true;
        }
        cursor.close();
        return result;
    }
    public long getCurrentMoney(int walletType) {
        String value1 = getProperty(PROPERTIES_CURRENT_MONEY_AMOUNT_BANK_CARD);
        String value2 = getProperty(PROPERTIES_CURRENT_MONEY_AMOUNT_NORMAL);
        if (walletType == WalletType.BANK_CARD) {
            return ConversionUtil.stringToLong(value1);
        } else if (walletType == WalletType.NORMAL_WALLET)  {
            return ConversionUtil.stringToLong(value2);
        } else {
            return ConversionUtil.stringToLong(value1) + ConversionUtil.stringToLong(value2);
        }
    }

    public String getProperty(String key) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PROPERTIES + " WHERE " + PROPERTIES_KEY + " = '" + key + "'";
        //System.out.println(query);

        Cursor c = db.rawQuery(query, null);

        String result = "";

        if (c == null) {
            return result;
        } else {
            c.moveToFirst();
            if (!c.isAfterLast()) {
                result = c.getString(c.getColumnIndex(PROPERTIES_VALUE));
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return result;
        }
    }




    public TransactionGroup getGroupById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_GROUP + " WHERE " + GROUP_ID + " = " + id;
        //System.out.println(query);

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            TransactionGroup group = null;
            if (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndex(GROUP_NAME));
                int type = c.getInt(c.getColumnIndex(GROUP_TYPE));
                group = new TransactionGroup(id, name, type);
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return group;
        }
    }
    public List<TransactionGroup> getAllGroup() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GROUP;

        List<TransactionGroup> lst = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return lst;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex(GROUP_ID));
                String name = c.getString(c.getColumnIndex(GROUP_NAME));
                int type = c.getInt(c.getColumnIndex(GROUP_TYPE));
                lst.add(new TransactionGroup(id, name, type));
                c.moveToNext();
            }
            c.close();
            //System.out.println(lst.size() + " ads file to be deleted");
            return lst;
        }
    }



    public void insertTransaction(Transaction transaction) {
        //System.out.println("Deleting ads that is marked as to be deleted..");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(getInsertQuery(TABLE_TRANSACTION, prepareInsertIntoTableTransaction((int)transaction.getMoneyAmount(), transaction.getGroup().getId(), transaction.getNote(), transaction.getDate().getTime(), transaction.getWalletType())));
        long currentMoney = getCurrentMoney(transaction.getWalletType());
        if (transaction.getWalletType() == WalletType.BANK_CARD) {
            db.execSQL(getUpdatePropertiesQuery(PROPERTIES_CURRENT_MONEY_AMOUNT_BANK_CARD, String.valueOf((currentMoney + (long) transaction.getMoneyAmount()))));
        } else {
            db.execSQL(getUpdatePropertiesQuery(PROPERTIES_CURRENT_MONEY_AMOUNT_NORMAL, String.valueOf((currentMoney + (long) transaction.getMoneyAmount()))));
        }

    }

    public void updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(getUpdateQuery(TABLE_TRANSACTION, prepareUpdateTableTransaction((int)transaction.getMoneyAmount(), transaction.getGroup().getId(), transaction.getNote(), transaction.getDate().getTime(), transaction.getWalletType()), String.valueOf(transaction.getId())));
        long currentMoney = getCurrentMoney(transaction.getWalletType());
        if (transaction.getWalletType() == WalletType.BANK_CARD) {
            db.execSQL(getUpdatePropertiesQuery(PROPERTIES_CURRENT_MONEY_AMOUNT_BANK_CARD, String.valueOf((currentMoney + (long) transaction.getMoneyAmount()))));
        } else {
            db.execSQL(getUpdatePropertiesQuery(PROPERTIES_CURRENT_MONEY_AMOUNT_NORMAL, String.valueOf((currentMoney + (long) transaction.getMoneyAmount()))));
        }
    }

    public List<Transaction> getAllTransaction() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTION + " ORDER BY " + TRANSACTION_DATE + " DESC";

        List<Transaction> lst = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return lst;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex(TRANSACTION_ID));
                Double amount = c.getDouble(c.getColumnIndex(TRANSACTION_AMOUNT));
                String note = c.getString(c.getColumnIndex(TRANSACTION_NOTE));
                int walletType = c.getInt(c.getColumnIndex(TRANSACTION_WALLET_TYPE));
                int groupId = c.getInt(c.getColumnIndex(TRANSACTION_GROUP_ID));
                Date date = ConversionUtil.timestampToDate(c.getLong(c.getColumnIndex(TRANSACTION_DATE)));

                lst.add(new Transaction(id, amount, getGroupById(groupId), note, date, walletType));
                c.moveToNext();
            }
            c.close();
            //System.out.println(lst.size() + " ads file to be deleted");
            return lst;
        }
    }

    public Transaction getTransactionById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + TRANSACTION_ID + " = " + id;
        //System.out.println(query);

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            Transaction transaction = null;
            if (!c.isAfterLast()) {

                Double amount = c.getDouble(c.getColumnIndex(TRANSACTION_AMOUNT));
                String note = c.getString(c.getColumnIndex(TRANSACTION_NOTE));
                int walletType = c.getInt(c.getColumnIndex(TRANSACTION_WALLET_TYPE));
                int groupId = c.getInt(c.getColumnIndex(TRANSACTION_GROUP_ID));
                Date date = ConversionUtil.timestampToDate(c.getLong(c.getColumnIndex(TRANSACTION_DATE)));
                transaction = new Transaction(id, amount, getGroupById(groupId), note, date, walletType);
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return transaction;
        }
    }

    public List<Transaction> getTransactionInRange(Date startDate, Date endDate) {
        long startDateInMilliseconds = DateUtil.getStartDayTime(startDate);
        long endDateInMilliseconds = DateUtil.getEndDayTime(endDate);

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + TRANSACTION_DATE + " >= " + startDateInMilliseconds + " AND " + TRANSACTION_DATE + " < " + endDateInMilliseconds + " ORDER BY " + TRANSACTION_DATE + " DESC";
        //System.out.println(query);

        List<Transaction> lst = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return lst;
        } else {
            c.moveToFirst();
            while  (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex(TRANSACTION_ID));
                Double amount = c.getDouble(c.getColumnIndex(TRANSACTION_AMOUNT));
                String note = c.getString(c.getColumnIndex(TRANSACTION_NOTE));
                int walletType = c.getInt(c.getColumnIndex(TRANSACTION_WALLET_TYPE));
                int groupId = c.getInt(c.getColumnIndex(TRANSACTION_GROUP_ID));
                Date date = ConversionUtil.timestampToDate(c.getLong(c.getColumnIndex(TRANSACTION_DATE)));
                lst.add(new Transaction(id, amount, getGroupById(groupId), note, date, walletType));
                c.moveToNext();
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return lst;
        }
    }


    public long getMoneyAmountInSpecificDay(Date date, int walletType) {
        long dateInMilliseconds = DateUtil.getStartDayTime(date);
        long currentAmount = getCurrentMoney(walletType);

        SQLiteDatabase db = this.getReadableDatabase();

        String query;
        if (walletType == WalletType.BOTH) {
            query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + TRANSACTION_DATE + " >= " + dateInMilliseconds;
        } else if (walletType == WalletType.NORMAL_WALLET) {
            query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + TRANSACTION_DATE + " >= " + dateInMilliseconds + " AND " + TRANSACTION_WALLET_TYPE + " = " + WalletType.BANK_CARD;
        } else {
            query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + TRANSACTION_DATE + " >= " + dateInMilliseconds + " AND " + TRANSACTION_WALLET_TYPE + " = " + WalletType.NORMAL_WALLET;
        }

        long result = 0;

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return result;
        } else {
            c.moveToFirst();
            Transaction transaction = null;
            while (!c.isAfterLast()) {
                float amount = c.getFloat(c.getColumnIndex(TRANSACTION_AMOUNT));
                result += amount;
                c.moveToNext();
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return currentAmount - result;
        }
    }

    public long getMoneyInADay(Date date, int walletType) {
        long start = DateUtil.getStartDayTime(date);
        long end = DateUtil.getEndDayTime(date);

        SQLiteDatabase db = this.getReadableDatabase();
        String query;
        if (walletType == WalletType.BOTH) {
            query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + TRANSACTION_DATE + " >= " + start + " AND " + TRANSACTION_DATE + " < " + end + " ORDER BY " + TRANSACTION_DATE + " ASC";
        } else {
            query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + TRANSACTION_DATE + " >= " + start + " AND " + TRANSACTION_DATE + " < " + end + " AND " + TRANSACTION_WALLET_TYPE + " = " + walletType + " ORDER BY " + TRANSACTION_DATE + " ASC";
        }

        //System.out.println(query);

        Cursor c = db.rawQuery(query, null);

        long result = 0;

        if (c == null) {
            return 0;
        } else {
            c.moveToFirst();
            while  (!c.isAfterLast()) {
                float amount = c.getFloat(c.getColumnIndex(TRANSACTION_AMOUNT));
                result += ((long) amount);
                c.moveToNext();
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return result;
        }
    }


    public TransactionGroup getGroupByGroupName(String groupName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_GROUP + " WHERE " + GROUP_NAME + " = '" + groupName + "'";
        //System.out.println(query);

        Cursor c = db.rawQuery(query, null);

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            TransactionGroup group = null;
            if (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex(GROUP_ID));
                String name = c.getString(c.getColumnIndex(GROUP_NAME));
                int type = c.getInt(c.getColumnIndex(GROUP_TYPE));
                group = new TransactionGroup(id, name, type);
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return group;
        }
    }

    public Notification getNotificationById(int id_) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NOTIFICATION + " WHERE " + NOTIFICATION_ID + " = '" + id_ + "'";
        //System.out.println(query);

        Cursor c = db.rawQuery(query, null);

        Notification notification = null;

        if (c == null) {
            return null;
        } else {
            c.moveToFirst();
            if (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex(NOTIFICATION_ID));
                String content = c.getString(c.getColumnIndex(NOTIFICATION_CONTENT));
                Date date = ConversionUtil.timestampToDate(c.getLong(c.getColumnIndex(NOTIFICATION_DATE)));
                String status = c.getString(c.getColumnIndex(NOTIFICATION_STATUS));
                notification = new Notification(id, content, date, status);
            }
            c.close();
            //System.out.println("Ads retrieved: " + ads);
            return notification;
        }
    }
}
