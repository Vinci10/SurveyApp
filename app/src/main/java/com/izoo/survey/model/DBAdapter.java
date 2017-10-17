package com.izoo.survey.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.izoo.survey.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class DBAdapter {

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private CreateDataBase cdb = new CreateDataBase();

        public DatabaseHelper(Context context) {
            super(context, DBName.DATABASE_NAME, null, DBName.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.beginTransaction();

            try {
            // creating required tables
                db.execSQL(DBName.CREATE_TABLE_Type_Users);
                db.execSQL(DBName.CREATE_TABLE_Users);
                db.execSQL(DBName.CREATE_TABLE_Survey);
                db.execSQL(DBName.CREATE_TABLE_Section);
                db.execSQL(DBName.CREATE_TABLE_Type_Question);
                db.execSQL(DBName.CREATE_TABLE_Question);
                db.execSQL(DBName.CREATE_TABLE_Answers_To_Question);
                db.execSQL(DBName.CREATE_TABLE_Answers);
                db.execSQL(DBName.CREATE_TABLE_Survey_Users);
                db.execSQL(DBName.CREATE_TABLE_Survey_Anonymous);
                db.execSQL(DBName.CREATE_TABLE_Section_In_Survey);
                db.execSQL(DBName.CREATE_TABLE_Question_In_Section);
                db.execSQL(DBName.CREATE_TABLE_History);
                db.execSQL(cdb.addTypeUser);
                db.execSQL(cdb.addUser);
                db.execSQL(cdb.addSurvey);
                db.execSQL(cdb.addSection);
                db.execSQL(cdb.addTypeAnswer);
                db.execSQL(cdb.addQuestion);
                db.execSQL(cdb.addAnswers);
                db.execSQL(cdb.addQuestionInSection);
                db.execSQL(cdb.addSurveyInSection);
                db.execSQL(cdb.addSurveyUsers);

                db.setTransactionSuccessful();
            } catch(Exception e) {
                throw e;
            } finally {
                db.endTransaction();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            // on upgrade drop older tables
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Type_Users);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Users);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Survey);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Section);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Type_Question);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Question);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Answers_To_Question);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Answers);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Survey_Users);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Survey_Anonymous);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Section_In_Survey);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_Question_In_Section);
            sqLiteDatabase.execSQL("DROP DBName.TABLE IF EXISTS " + DBName.TABLE_History);

            // create new tables
            onCreate(sqLiteDatabase);
        }

    }

    private SQLiteDatabase db;
    protected Context context;
    private DatabaseHelper dbHelper;
    private Cursor cursor;

    public DBAdapter(Context context) {
        this.context = context;
    }

    public DBAdapter openReadableDatabase() throws Exception {
        try {
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getReadableDatabase();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();
            throw new SQLiteException("błąd podczas otwierania bazy danych");
        }
        return this;
    }

    public DBAdapter openWritableDatabase() throws Exception {
        try {
            dbHelper = new DatabaseHelper(context);
            db = dbHelper.getWritableDatabase();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(context, "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();
            throw new SQLiteException("błąd podczas otwierania bazy danych");
        }
        return this;
    }

    public long Insert(String dbTable, ContentValues contentValues) throws Exception {
        try {
            return db.insert(dbTable, null, contentValues);
        }catch(SQLiteException e)
        {
            throw new SQLiteException("błąd przy zapisie do "+dbTable);
        }
    }

    public long Replace(String dbTable, ContentValues contentValues,String where) throws  Exception
    {
        boolean update  = Update(dbTable, contentValues, where);
        if(update)
            return 0;
        else
            return Insert(dbTable, contentValues);
    }

    public boolean Update(String dbTable, ContentValues contentValues,String where) throws Exception
    {
        try
        {
            return db.update(dbTable, contentValues, where, null) > 0;
        }catch(Exception e)
        {
            throw new SQLiteException("błąd przy update do "+dbTable);
        }
    }

    public boolean Delete(String dbTable, String where) throws Exception
    {
        try
        {
            return db.delete(dbTable, where, null) > 0;
        }catch(Exception e)
        {
            throw new SQLiteException("błąd przy usuwaniu do "+dbTable);
        }
    }

    public Cursor Get(String dbTable, String[] columns, String selection, String[] selectionArgs,
                      String groupBy, String having, String orderBy ) throws  Exception {
        try
        {
            return db.query(dbTable, columns, selection, selectionArgs, groupBy, having, orderBy);
        }catch(Exception e)
        {
            throw new SQLiteException("błąd przy pobieraniu wartości z "+dbTable);
        }
    }

    public void Close()
    {
        if(db != null) db.close();
        if(cursor != null) cursor.close();
    }

    public void BeginTransaction()
    {
        db.beginTransaction();
    }

    public void SetTransactionSuccessful()
    {
        db.setTransactionSuccessful();
    }

    public void EndTransaction()
    {
        db.endTransaction();
    }

    public Cursor getAllSection() throws Exception{
        String[] columns = {DBName.KEY_ID_Section + " as _id",DBName.KEY_Name,DBName.KEY_Sequence};
        Cursor cursor = Get(DBName.TABLE_Section,columns,null,null,null,null,null);
        return cursor;
    }

    public Users findUser(String login, String password) throws Exception{
        cursor = Get(DBName.TABLE_Users,
                new String[]{DBName.KEY_ID_User, DBName.KEY_Login,DBName.KEY_Password, DBName.KEY_ID_Type_Users},
                DBName.KEY_Login + " = ? AND " + DBName.KEY_Password + " = ?",
                new String[]{login,password},
                null,null,null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            Users user = new Users(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            cursor = Get(DBName.TABLE_Type_Users,
                    new String[]{DBName.KEY_Name_Type_User},
                    DBName.KEY_ID_Type_Users + " = ?",
                    new String[]{Integer.toString(cursor.getInt(3))},null,null,null);
            cursor.moveToFirst();
            user.setType_Users(cursor.getString(0));
            return user;
        }
        return null;
    }
    public Cursor getAllUsers() throws Exception{
        cursor = Get(DBName.TABLE_Users,new String[]{DBName.KEY_ID_User + " as _id",DBName.KEY_Login},null,null,null,null,null);
        return cursor;
    }
    public Cursor getOrdinaryUsers()throws Exception{
        cursor = Get(DBName.TABLE_Type_Users,new String[]{DBName.KEY_ID_Type_Users},DBName.KEY_Name_Type_User + " = ?",
                new String[]{"User"},null,null,null);
        cursor.moveToFirst();
        cursor = Get(DBName.TABLE_Users,new String[]{DBName.KEY_ID_User + " as _id",DBName.KEY_Login},DBName.KEY_ID_Type_Users + " = ?",
                new String[]{Integer.toString(cursor.getInt(0))},null,null,null);
        return cursor;
    }

    public List<SurveyList> getSurveyList(Users user, boolean isStatistics)throws Exception{
        List <SurveyList> surveyList = new ArrayList<>();
        if(user.getType_Users().equals("User") || !isStatistics){
            cursor = Get(DBName.TABLE_Survey_Users,
                    new String[]{DBName.KEY_ID_Survey},
                    DBName.KEY_ID_User + " = ?",
                    new String[]{Integer.toString(user.getID_User())},null,null,null);
        } else{
            cursor = Get(DBName.TABLE_Survey,
                    new String[]{DBName.KEY_ID_Survey}, null,null,null,null,null);
        }
        if(cursor.getCount() > 0){
            StringBuilder id = new StringBuilder();
            while(cursor.moveToNext()){
                id.append(Integer.toString(cursor.getInt(0))+",");
            }
            id.deleteCharAt(id.length()-1);
            cursor = Get(DBName.TABLE_Survey, null,
                    DBName.KEY_ID_Survey + " IN ("+id+")",null,null,null,null);
            Cursor cursor1 = null;
            while(cursor.moveToNext()){
                Survey survey = new Survey(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getInt(4),null);
                List<History> histories = new ArrayList<>();
                int lp;
                int lpAll = 0;

                if(user.getType_Users().equals("User")){
                    if(survey.isAnonymous() == 0){
                        cursor1 = getHistoryBySurveyAndUserID(user.getID_User(),survey.getID_Survey());
                        while (cursor1.moveToNext()){
                            History history = new History(cursor1.getInt(0),cursor1.getString(1),cursor1.getInt(2),cursor1.getInt(3));
                            histories.add(history);
                        }
                        lp = cursor1.getCount();
                        surveyList.add(new SurveyList(survey,histories,lpAll,lp));
                    }else{
                        surveyList.add(new SurveyList(survey,null,0,0));
                    }
                }else{
                    cursor1 = getHistoryBySurveyID(survey.getID_Survey());
                    lpAll = cursor1.getCount();
                    if(survey.isAnonymous() == 0){
                        while (cursor1.moveToNext()){
                            History history = new History(cursor1.getInt(0),cursor1.getString(1),cursor1.getInt(2),cursor1.getInt(3));
                            histories.add(history);
                        }
                        cursor1 = Get(DBName.TABLE_History,
                                new String[]{"COUNT(*) as total"},
                                DBName.KEY_ID_Survey + " = ? AND " + DBName.KEY_ID_User + " = ?",
                                new String[]{Integer.toString(survey.getID_Survey()), Integer.toString(user.getID_User())},
                                null,null,null);
                        cursor1.moveToFirst();
                        lp = cursor1.getInt(0);
                    }else{
                        while (cursor1.moveToNext()){
                            History history = new History(cursor1.getInt(0),survey.getDate_Create(),-1,survey.getID_Survey());
                            histories.add(history);
                        }
                        lp = lpAll;
                    }
                    surveyList.add(new SurveyList(survey,histories,lpAll,lp));
                }
            }
            if(cursor1 != null) cursor1.close();
        }
        return surveyList;
    }

    private Cursor getHistoryBySurveyAndUserID(int ID_User, int ID_Survey)throws Exception{
        return Get(DBName.TABLE_History,null,
                DBName.KEY_ID_Survey + " = ? AND " + DBName.KEY_ID_User + " = ?",
                new String[]{Integer.toString(ID_Survey), Integer.toString(ID_User)},
                null,null,null);
    }

    private Cursor getHistoryBySurveyID(int ID_Survey)throws Exception{
        return Get(DBName.TABLE_History,null,
                DBName.KEY_ID_Survey + " = ?",
                new String[]{Integer.toString(ID_Survey)},
                null,null,null);
    }

    public String getHistoryIDListBySurveyID(int ID_Survey)throws Exception{
        StringBuilder historyIDList = new StringBuilder("");
        cursor = getHistoryBySurveyID(ID_Survey);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            historyIDList.append(Integer.toString(cursor.getInt(0)));
            while(cursor.moveToNext()){
                historyIDList.append("," + Integer.toString(cursor.getInt(0)));
            }
        }
        return historyIDList.toString();
    }

    public Survey getSurvey(int id_Survey)throws Exception{
        cursor = Get(DBName.TABLE_Survey, null,
                DBName.KEY_ID_Survey + " = ?",
                new String[]{Integer.toString(id_Survey)},null, null, null);
        cursor.moveToFirst();
        Survey survey = new Survey(cursor.getInt(0),cursor.getString(1),
                cursor.getString(2),cursor.getString(3),cursor.getInt(4),null);
        ArrayList<Integer> list = new ArrayList<>();
        cursor = Get(DBName.TABLE_Section_In_Survey,
                new String[]{DBName.KEY_ID_Section},
                DBName.KEY_ID_Survey + " = ?",
                new String[]{Integer.toString(id_Survey)}, null,null, DBName.KEY_Sequence);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                list.add(cursor.getInt(0));
            }
            ArrayList<Section> sections = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                sections.add(getSection(list.get(i)));
            }
            survey.setSections(sections);
            return survey;
        }
        else{
            return survey;
        }
    }

    public Section getSection(int id_Section)throws Exception{
        cursor = Get(DBName.TABLE_Section,null,
                DBName.KEY_ID_Section + " = ?",
                new String[]{Integer.toString(id_Section)},null,null,null);
        cursor.moveToFirst();
        Section section = new Section(cursor.getInt(0),cursor.getString(1),null);
        ArrayList<Integer> list = new ArrayList<>();
        cursor = Get(DBName.TABLE_Question_In_Section,
                new String[]{DBName.KEY_ID_Question},
                DBName.KEY_ID_Section + " = ?",
                new String[]{Integer.toString(id_Section)}, null,null, DBName.KEY_Sequence);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                list.add(cursor.getInt(0));
            }
            ArrayList<Question> questions = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                questions.add(getQuestion(list.get(i)));
            }
            section.setQuestions(questions);
            return section;
        }
        else return section;
    }

    public Question getQuestion(int id_Question)throws Exception{
        cursor = Get(DBName.TABLE_Question,null,
                DBName.KEY_ID_Question + " = ?",
                new String[]{Integer.toString(id_Question)},null,null,null);
        cursor.moveToFirst();
        int id_type_question = cursor.getInt(4);
        Question question = new Question(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),
                cursor.getString(3), null, null);
        cursor = Get(DBName.TABLE_Type_Question,
                new String[]{DBName.KEY_Name},
                DBName.KEY_ID_Type_Question + " = ?",
                new String[]{Integer.toString(id_type_question)},null,null,null);
        cursor.moveToFirst();
        question.setType_Question(cursor.getString(0));

        ArrayList<Answers_To_Question> answers = new ArrayList<>();
        cursor = Get(DBName.TABLE_Answers_To_Question, null,
                DBName.KEY_ID_Question + " = ?",
                new String[]{Integer.toString(id_Question)}, null,null, DBName.KEY_Sequence);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                answers.add(new Answers_To_Question(cursor.getInt(0),cursor.getString(1),cursor.getInt(3),cursor.getInt(4)));
            }
        }
        question.setAvailableAnswers(answers);
        return question;
    }

    public Answers_To_Question getGivenAnswer(int ID_History, Answers_To_Question answers_to_question)throws Exception{
        cursor = Get(DBName.TABLE_Answers,null,
                DBName.KEY_ID_Answers_to_Question + " = ? AND " + DBName.KEY_ID_History + " = ?",
                new String[]{Integer.toString(answers_to_question.getId_Answers_To_Question()),Integer.toString(ID_History)},
                null,null,null);
        if(cursor.moveToFirst()){
            Answers_To_Question answer = new Answers_To_Question(answers_to_question);
            if(answer.getHas_Text() == 1){
                if(cursor.getString(1) != null){
                    answer.setText(cursor.getString(1));
                } else{
                    answer.setText("");
                }
            }
            return answer;
        }else{
            return null;
        }
    }

    public void saveAnswers(Survey survey, int ID_User)throws Exception{
        BeginTransaction();
        try {
            cursor = Get(DBName.TABLE_History,
                    new String[]{"COUNT(*) as total"},null,null,null,null,null);
            cursor.moveToFirst();
            int ID_History = cursor.getInt(0) + 1;
            ContentValues historyValues = new ContentValues();
            if(survey.isAnonymous() == 1){
                historyValues.putNull(DBName.KEY_Date_Hour);
                historyValues.putNull(DBName.KEY_ID_User);
                ContentValues surveyAnonymousValues = new ContentValues();
                surveyAnonymousValues.put(DBName.KEY_ID_Survey,survey.getID_Survey());
                surveyAnonymousValues.put(DBName.KEY_ID_User,ID_User);
                Insert(DBName.TABLE_Survey_Anonymous,surveyAnonymousValues);
            }
            else{
                historyValues.put(DBName.KEY_ID_User, ID_User);
            }
            historyValues.put(DBName.KEY_ID_Survey,survey.getID_Survey());
            Insert(DBName.TABLE_History,historyValues);
            List<Section> sections = survey.getSections();
            for(int i = 0; i < sections.size(); i++){
                List<Question> questions = sections.get(i).getQuestions();
                for(int j = 0; j < questions.size(); j++){
                    Question question = questions.get(j);
                    if(question.getGivenAnswers() != null){
                        List<Answers_To_Question> answers = question.getGivenAnswers();
                        if(answers != null){
                            for(int k = 0; k < answers.size(); k++){
                                Answers_To_Question answers_to_question = answers.get(k);
                                ContentValues answerValues = new ContentValues();
                                answerValues.put(DBName.KEY_ID_Answers_to_Question,answers_to_question.getId_Answers_To_Question());
                                answerValues.put(DBName.KEY_ID_History,ID_History);
                                if(answers_to_question.getHas_Text() == 1){
                                    answerValues.put(DBName.KEY_Text_Answers,answers_to_question.getText());
                                }
                                else{
                                    answerValues.putNull(DBName.KEY_Text_Answers);
                                }
                                Insert(DBName.TABLE_Answers,answerValues);
                            }
                        }
                    }
                }
            }
            cursor.close();
            SetTransactionSuccessful();
        } catch(Exception e) {
            throw e;
        } finally {
            EndTransaction();
        }
    }

    public void countGivenAnswers(String historyIDList, Answers_To_Question answer)throws Exception{
        cursor = Get(DBName.TABLE_Answers, null,
                DBName.KEY_ID_Answers_to_Question + " = ?  AND " + DBName.KEY_ID_History + " IN ("+historyIDList+")",
                new String[]{Integer.toString(answer.getId_Answers_To_Question())},
                null,null,null);
        answer.setSummaryAmountAnswers(cursor.getCount());
        if(answer.getHas_Text() == 1 && cursor.getCount() > 0){
            List <String> list = new ArrayList<>();
            while(cursor.moveToNext()){
                String text = cursor.getString(1);
                if(text != null && !text.equals("")){
                    list.add(text);
                }
            }
            answer.setSummaryTextAnswers(list);
        }
    }

    public long insertSurvey(Survey survey)throws Exception{
        long ID_Survey;
        BeginTransaction();
        try {
            ContentValues surveyValues = new ContentValues();
            surveyValues.put(DBName.KEY_Name,survey.getName());
            surveyValues.put(DBName.KEY_Date_Create,survey.getDate_Create());
            surveyValues.put(DBName.KEY_Date_Update,survey.getDate_Update());
            surveyValues.put(DBName.KEY_Anonymous,survey.isAnonymous());
            ID_Survey = Insert(DBName.TABLE_Survey,surveyValues);
            List<Section> sections = survey.getSections();
            for(int i = 0; i < sections.size(); i++){
                insertSection(sections.get(i),i,ID_Survey);
            }
            cursor.close();
            SetTransactionSuccessful();
        } catch(Exception e) {
            ID_Survey = -1;
            throw e;
        } finally {
            EndTransaction();
        }
        return ID_Survey;
    }
    private void insertSection(Section section, int i, long ID_Survey)throws Exception{
        ContentValues sectionValues = new ContentValues();
        sectionValues.put(DBName.KEY_Name,section.getName());
        long ID_Section = Insert(DBName.TABLE_Section,sectionValues);
        ContentValues section_In_SurveyValues = new ContentValues();
        section_In_SurveyValues.put(DBName.KEY_ID_Section,ID_Section);
        section_In_SurveyValues.put(DBName.KEY_ID_Survey,ID_Survey);
        section_In_SurveyValues.put(DBName.KEY_Sequence,i+1);
        Insert(DBName.TABLE_Section_In_Survey,section_In_SurveyValues);
        List<Question> questions = section.getQuestions();
        for(int j = 0; j < questions.size(); j++){
            insertQuestion(questions.get(j),j,ID_Section);
        }
    }
    private void insertQuestion(Question question,int j, long ID_Section)throws Exception{
        ContentValues questionValues = new ContentValues();
        questionValues.put(DBName.KEY_Text_Question,question.getText_Question());
        questionValues.put(DBName.KEY_Required,question.isRequired());
        questionValues.put(DBName.KEY_Tips,question.getTips());
        cursor = Get(DBName.TABLE_Type_Question,
                new String[]{DBName.KEY_ID_Type_Question},
                DBName.KEY_Name + " = ?",
                new String[]{question.getType_Question()},null,null,null);
        cursor.moveToFirst();
        questionValues.put(DBName.KEY_ID_Type_Question,cursor.getInt(0));
        long ID_Question = Insert(DBName.TABLE_Question,questionValues);
        ContentValues question_In_SectionValues = new ContentValues();
        question_In_SectionValues.put(DBName.KEY_ID_Question,ID_Question);
        question_In_SectionValues.put(DBName.KEY_ID_Section,ID_Section);
        question_In_SectionValues.put(DBName.KEY_Sequence,j+1);
        Insert(DBName.TABLE_Question_In_Section,question_In_SectionValues);
        List<Answers_To_Question> answers = question.getAvailableAnswers();
        for(int k = 0; k < answers.size(); k++){
            insertAnswers_To_Question(answers.get(k),k,ID_Question);
        }
    }
    private void insertAnswers_To_Question(Answers_To_Question answers_to_question, int k, long ID_Question)throws Exception{
        ContentValues answers_To_QuestionValues = new ContentValues();
        answers_To_QuestionValues.put(DBName.KEY_Text_Answers,answers_to_question.getText_Answers());
        answers_To_QuestionValues.put(DBName.KEY_Sequence,k+1);
        answers_To_QuestionValues.put(DBName.KEY_Has_Text,answers_to_question.getHas_Text());
        answers_To_QuestionValues.put(DBName.KEY_ID_Question,ID_Question);
        Insert(DBName.TABLE_Answers_To_Question,answers_To_QuestionValues);
    }
}
