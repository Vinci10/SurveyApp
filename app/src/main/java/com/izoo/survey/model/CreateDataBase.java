package com.izoo.survey.model;

/**
 * Created by mateusz on 11.08.17.
 */

public class CreateDataBase {

    public String addTypeUser = "Insert into Type_Users values (1, 'User'), (2,'Anonymous')";
    public String addUser = "Insert into Users values (1, 'Mateo','alamakota',1)";
    public String addSurvey = "Insert into Survey values (1, 'Ankieta Konsumencka','2017-08-10 01:25:00','2017-08-10 01:25:00')";
    public String addSection = "Insert into Section values (1, 'Informacje o respondencie',1),(2,'Ankieta',2)";
    public String addTypeAnswer = "Insert into Type_Answers values (1, 'Wielokrotnego'),(2,'Jednokrotnego'),(3,'Otwarta')";
    public String addQuestion = "Insert into Question values (1, 'Płeć:',1,1,'',1,2), (2,'Wykształcenie:',1,2,'',1,2), (3,'Wiek:',1,3,'',1,2), "+
    " (4,'Województwo:',1,4,'',1,2), (5,'Miejsce zamieszkania:',1,5,'',1,2),(6,'Dochody netto na osobę w gospodarstwie domowym:',1,6,'',1,2)";

    String addAnswers = "Insert Into Answers_To_Question values (1,'Kobieta',1,0,1), (2,'Mężczyzna',2,0,1), "+
            "(3,'Podstawowe',1,0,2), (4,'Zawodowe',2,0,3), (5,'Średnie',3,0,2), " +
            "(6,'Wyższe',4,0,2), (7,'do 20 lat',1,0,3), (8,'20 - 30 lat',2,0,3), " +
            "(9,'30 - 40 lat',3,0,3), (10,'40 - 50 lat',4,0,3), (11,'powyżej 50 lat',5,0,3), (12,'dolnośtląskie',1,0,4), " +
            "(13,'kujawsko-pomorkie',2,0,4), (14,'lubelskie',3,0,4), (15,'lubuskie',4,0,4), (16,'łódzkie',5,0,4), " +
            "(17,'małopolskie',6,0,4), (18,'mazowieckie',7,0,4), (19,'opolskie',8,0,4), (20,'podkarpackie',9,0,4), " +
            "(21,'podlaskie',10,0,4), (22,'pomorskie',111,0,4), (23,'śląskie',12,0,4), (24,'świętokrzyskie',13,0,4), " +
            "(25,'warmińsko-mazurskie',14,0,4), (26,'wielkopolskie',15,0,4), (27,'zachodniopomoskie',16,0,4), " +
            "(28,'Wieś',1,0,5), (29,'Miasto do 50 tys. mieszkańców',2,0,5), (30,'Miasto od 51 tys do 150 tys. mieszkańców',3,0,5), " +
            "(31,'Miasto od 151 tys do 250 tys. mieszkańców',4,0,5), (32,'Miasto od 251 tys do 500 tys. mieszkańców',5,0,5), " +
            "(33,'Miasto powyzej 500 tys. mieszkańców',6,0,5), (34,'Poniżej 1500 zł',1,0,6), (35,'1500 - 2500 zł',2,0,6), " +
            "(36,'Powyżej 2500 zł',3,0,6)";
    String addSurveyUsers = "Insert Into Survey_Users values (1,1,1)";
    String addSurveyInSection = "Insert Into Section_In_Survey values (1,1,1)";
    String addQuestionInSection = "Insert Into Question_In_Section values (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,1),(6,6,1)";
}
