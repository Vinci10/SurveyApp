package com.izoo.survey.model;

/**
 * Created by mateusz on 11.08.17.
 */

public class CreateDataBase {

    public String addTypeUser = "Insert into Type_User values (1,'User'), (2,'Employee'), (3,'Administrator')";
    public String addUser = "Insert into User values (1,'User','abc',1),(2,'Employee','abc',2),(3,'Administrator','abc',3)";
    public String addSurvey = "Insert into Survey values (1,'Ankieta Konsumencka','2017-08-10 01:25:00','2017-08-10 01:25:00',0), (2,'Ankieta Anonimowa','2017-08-12 01:25:00','2017-08-12 01:25:00',1)";
    public String addSection = "Insert into Section values (1,'Informacje o respondencie'),(2,'Ankieta'), (3,'Zwierzeta'), (4,'Rosliny')";
    public String addTypeAnswer = "Insert into Type_Answer values (1,'Multiple'),(2,'Single'),(3,'Open')";
    public String addQuestion = "Insert into Question (Text_Question,Required,Tips,ID_Type_Answers) values ('Płeć:',1,'Podaj płeć',2), ('Wykształcenie:',1,'Podaj wykształcenie',2), ('Wiek:',0,'Niewymagane',2), " +
    "('Województwo:',0,'brak',2), ('Miejsce zamieszkania:',1,'',2), ('Dochody netto na osobę w gospodarstwie domowym:',1,'',2), ('Ulubione zwierze:',1,'Wybierz kilka',1), " +
            "('Masz zwierze?:',1,'Napisz jakie',3), ('Zwierze :',1,'Single',2), ('Lala:',0,'Niewymagne',2), " + "('Ulubiona roslina:',1,'Wybierz kilka',1), " +
            "('Lubisz kwiaty?:',1,'Napisz jakie',3), ('Roslina :',1,'Single',2)";


    String addAnswers = "Insert Into Answer (Text_Answer) values ('Kobieta'), ('Mężczyzna'), "+
            "('Podstawowe'), ('Zawodowe'), ('Średnie'), " +
            "('Wyższe'), ('do 20 lat'), ('20 - 30 lat'), " +
            "('30 - 40 lat'), ('40 - 50 lat'), ('powyżej 50 lat'), ('dolnośtląskie'), " +
            "('kujawsko-pomorkie'), ('lubelskie'), ('lubuskie'), ('łódzkie'), " +
            "('małopolskie'), ('mazowieckie'), ('opolskie'), ('podkarpackie'), " +
            "('podlaskie'), ('pomorskie'), ('śląskie'), ('świętokrzyskie'), " +
            "('warmińsko-mazurskie'), ('wielkopolskie'), ('zachodniopomoskie'), " +
            "('Wieś'), ('Miasto do 50 tys. mieszkańców'), ('Miasto od 51 tys do 150 tys. mieszkańców'), " +
            "('Miasto od 151 tys do 250 tys. mieszkańców'), ('Miasto od 251 tys do 500 tys. mieszkańców'), " +
            "('Miasto powyzej 500 tys. mieszkańców'), ('Poniżej 1500 zł'), ('1500 - 2500 zł'), " +
            "('Powyżej 2500 zł'), ('Krowa'), ('Owca'), ('Pies'), ('Koń'), ('Królik'), ('Kot'), ('Gepard'), ('Lew'), ('Słoń'), ('Tak'), ('Nie'), ('Dąb'), ('Róża'), ('Palma'), ('Manuka')";

    String addSurveyUsers = "Insert Into Survey_User values (1,1), (1,2), (2,2), (2,1), (3,1)";
    String addSurveyInSection = "Insert Into Sections_In_Survey values (1,1,1), (2,1,2), (2,2,2), (3,2,3), (4,2,1)";
    String addQuestionInSection = "Insert Into Questions_In_Section values (1,1,1),(2,1,3),(3,1,2),(4,1,4),(5,1,5),(6,2,1), (7,2,2), (7,3,2), (8,3,1), (9,3,3), (10,3,4), (11,4,1), (12,4,3), (10,4,2), (13,4,4)";
    String addAnswerInQuestion = "Insert Into Answers_In_Question values (1,1,1), (1,2,2), (2,3,1), (2,4,2), (2,5,3), (2,6,4), " +
            "(3, 7,1), (3,9,3), (3,8,2), (3,10,4), (3,11,5), (4,12,1), (4,13,2), (4,14,3), (4,15,4), (4,16,5), (4,17,6), (4,18,7), (4,19,8), (4,20,9), (4,21,10), (4,22,11), (4,23,12), (4,24,13), (4,25,14), (4,26,15), (4,27,16), "+
            " (5,28,1), (5,29,2), (5,30,3), (5,31,4), (5,32,5), (5,33,6), (6,35,2), (6,34,1), (6,36,3), (7,37,1), (7,38,2), (7,39,3), (7,40,4), (7,41,5), (9,42,1), (9,43,2), (9,44,3),(10,46,1),(10,47,2), "+
            "(11,49,3), (11,48,1), (11,50,2 ), (13,49,1), (13,50,2), (13,51,3)";
}
