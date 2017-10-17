package com.izoo.survey.model;

public class DBName {

    // Logcat tag
    public static final String LOG = "DatabaseHelper";

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "SurveyDatabase";

    // Table Names
    public static final String TABLE_Users = "User";
    public static final String TABLE_Type_Users = "Type_User";
    public static final String TABLE_History = "History";
    public static final String TABLE_Survey_Users = "Survey_User";
    public static final String TABLE_Survey = "Survey";
    public static final String TABLE_Survey_Anonymous = "Survey_Anonymous";
    public static final String TABLE_Section_In_Survey = "Sections_In_Survey";
    public static final String TABLE_Section = "Section";
    public static final String TABLE_Question_In_Section = "Questions_In_Section";
    public static final String TABLE_Question = "Question";
    public static final String TABLE_Answers_To_Question = "Answers_To_Question";
    public static final String TABLE_Answers = "Answers";
    public static final String TABLE_Type_Question = "Type_Question";

    // Common column names
    public static final String KEY_Name = "Name";
    public static final String KEY_Sequence = "Sequence";
    public static final String KEY_Date_Of_The_Survey = "Date_Of_The_Survey";

    // Users Table - column names
    public static final String KEY_ID_User = "ID_User";
    public static final String KEY_Login = "Login";
    public static final String KEY_Password = "Password";

    // Type_Users Table - column names
    public static final String KEY_ID_Type_Users = "ID_Type_Users";
    public static final String KEY_Name_Type_User = "Name_Type_User";

    // Survey Table - column names
    public static final String KEY_ID_Survey = "ID_Survey";
    public static final String KEY_Date_Create = "Date_Create";
    public static final String KEY_Date_Update = "Date_Update";
    public static final String KEY_Anonymous = "Anonymous";

    // Section Table - column names
    public static final String KEY_ID_Section = "ID_Section";

    // Question Table - column names
    public static final String KEY_ID_Question = "ID_Question";
    public static final String KEY_Text_Question = "Text_Question";
    public static final String KEY_Required = "Required";
    public static final String KEY_Tips = "Tips";

    // Answers _To_Question Table - column names
    public static final String KEY_Text_Answers = "Text_Answer";
    public static final String KEY_Has_Text = "Has_text";

    // Type_Answers Table - column names
    public static final String KEY_ID_Type_Question = "ID_Type_Question";

    // Answers Table - column names
    public static final String KEY_ID_Answers_to_Question = "ID_Answers_to_Question";

    // History Table - column names
    public static final String KEY_ID_History = "ID_History";
    public static final String KEY_Date_Hour = "Date_Hour";

    // Table Create Statements
    // Type_Users table create statement
    public static final String CREATE_TABLE_Type_Users = "CREATE TABLE "
            + TABLE_Type_Users + "(" + KEY_ID_Type_Users + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name_Type_User
            + " VARCHAR(30) NOT NULL)";

    // Users table create statement
    public static final String CREATE_TABLE_Users = "CREATE TABLE "
            + TABLE_Users + "(" + KEY_ID_User + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Login
            + " VARCHAR(45) NOT NULL," + KEY_Password + " VARCHAR(45)," + KEY_ID_Type_Users
            + " INTEGER, " +
            " FOREIGN KEY "+ "("+ KEY_ID_Type_Users+")"+ " REFERENCES "+
            TABLE_Type_Users + "(" + KEY_ID_Type_Users+")" +
            ")";

    // Survey table create statement
    public static final String CREATE_TABLE_Survey = "CREATE TABLE "
            + TABLE_Survey + "(" + KEY_ID_Survey + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(100) NOT NULL," + KEY_Date_Create + " DATETIME," + KEY_Date_Update + " DATETIME, " +
            KEY_Anonymous + " BOOLEAN NOT NULL )";

    // Section table create statement
    public static final String CREATE_TABLE_Section = "CREATE TABLE "
            + TABLE_Section + "(" + KEY_ID_Section + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(50) NOT NULL)";

    // Type_Answers table create statement
    public static final String CREATE_TABLE_Type_Question = "CREATE TABLE "
            + TABLE_Type_Question + "(" + KEY_ID_Type_Question + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(50) NOT NULL)";

    // Question table create statement
    public static final String CREATE_TABLE_Question = "CREATE TABLE "
            + TABLE_Question + "(" + KEY_ID_Question + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_Text_Question
            + " VARCHAR(250) NOT NULL, " + KEY_Required + " BOOLEAN NOT NULL, " + KEY_Tips
            + " VARCHAR(250), " + KEY_ID_Type_Question + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_Type_Question + ")" + " REFERENCES " + TABLE_Type_Question +
            "(" + KEY_ID_Type_Question + ")" + ")";

    // Answers_To_Question table create statement
    public static final String CREATE_TABLE_Answers_To_Question = "CREATE TABLE "
            + TABLE_Answers_To_Question + "(" + KEY_ID_Answers_to_Question + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_Text_Answers + " VARCHAR(250), " + KEY_Sequence +" INTEGER NOT NULL," + KEY_Has_Text + " BOOLEAN NOT NULL, " +
            KEY_ID_Question + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_Question + ")" +
            " REFERENCES " + TABLE_Question + "(" + KEY_ID_Question + ")" + ")";

    // Answers table create statement
    public static final String CREATE_TABLE_Answers = "CREATE TABLE "
            + TABLE_Answers + "(" + KEY_ID_Answers_to_Question + " INTEGER," + KEY_Text_Answers
            + " VARCHAR(250), " + KEY_ID_History + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_History + ")" +
            " REFERENCES " + TABLE_History + "(" + KEY_ID_History + "), " +
            " FOREIGN KEY " + "(" + KEY_ID_Answers_to_Question + ")" +
            " REFERENCES " + TABLE_Answers_To_Question + "(" + KEY_ID_Answers_to_Question + ")" +")";

    // Survey_Users table create statement
    public static final String CREATE_TABLE_Survey_Users = "CREATE TABLE " +TABLE_Survey_Users +
            "(" + KEY_ID_User + " INTEGER, " + KEY_ID_Survey + " INTEGER," +
            " FOREIGN KEY " + "(" + KEY_ID_Survey+ ")" + " REFERENCES " + TABLE_Survey + "(" + KEY_ID_Survey + "),"+
            "FOREIGN KEY " + "(" + KEY_ID_User+ ")" + " REFERENCES " + TABLE_Users+ "(" + KEY_ID_User+ ")," +
            "PRIMARY KEY (" + KEY_ID_User + "," + KEY_ID_Survey + ")" + ")";

    // Survey_Anonymous table create statement
    public static final String CREATE_TABLE_Survey_Anonymous = "CREATE TABLE " + TABLE_Survey_Anonymous+
            "(" + KEY_ID_Survey + " INTEGER," + KEY_ID_User + " INTEGER," + KEY_Date_Of_The_Survey + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            " FOREIGN KEY " + "(" + KEY_ID_Survey+ ")" + " REFERENCES " + TABLE_Survey+ "(" + KEY_ID_Survey+ "),"+
            " FOREIGN KEY " + "(" + KEY_ID_User+ ")" + " REFERENCES " + TABLE_Users+ "(" + KEY_ID_User+ ")" + ")";

    // Section_In_Survey table create statement
    public static final String CREATE_TABLE_Section_In_Survey = "CREATE TABLE "+ TABLE_Section_In_Survey +
            "(" + KEY_ID_Section + " INTEGER," + KEY_ID_Survey + " INTEGER, " + KEY_Sequence +" INTEGER NOT NULL," +
            "FOREIGN KEY " + "(" + KEY_ID_Section+ ")" + " REFERENCES " + TABLE_Section+ "(" + KEY_ID_Section+ "),"+
            "FOREIGN KEY " + "(" + KEY_ID_Survey+ ")" + " REFERENCES " + TABLE_Survey+ "(" + KEY_ID_Survey+ ")," +
            "PRIMARY KEY (" + KEY_ID_Section + "," + KEY_ID_Survey + ")" + ")";

    // Question_In_Section table create statement
    public static final String CREATE_TABLE_Question_In_Section = "CREATE TABLE " + TABLE_Question_In_Section +
            "(" + KEY_ID_Question + " INTEGER," + KEY_ID_Section + " INTEGER, " + KEY_Sequence +" INTEGER NOT NULL," +
            " FOREIGN KEY " + "(" + KEY_ID_Section+ ")" + " REFERENCES " + TABLE_Section+ "(" + KEY_ID_Section+ "),"+
            " FOREIGN KEY " + "(" + KEY_ID_Question+ ")" + " REFERENCES " + TABLE_Question+ "(" + KEY_ID_Question+ ")," +
            "PRIMARY KEY (" + KEY_ID_Section + "," + KEY_ID_Question + ")" + ")";

    // History table create statement
    public static final String CREATE_TABLE_History = "CREATE TABLE " + TABLE_History +
            "(" + KEY_ID_History + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Date_Hour +
            " DATETIME DEFAULT CURRENT_TIMESTAMP," + KEY_ID_User + " INTEGER," + KEY_ID_Survey + " INTEGER," +
            " FOREIGN KEY " + "(" + KEY_ID_Survey + ")" + " REFERENCES " + TABLE_Survey + "(" + KEY_ID_Survey+ "),"+
            " FOREIGN KEY " + "(" + KEY_ID_User + ")" + " REFERENCES " + TABLE_Users + "(" + KEY_ID_User + ")" + ")";

}
