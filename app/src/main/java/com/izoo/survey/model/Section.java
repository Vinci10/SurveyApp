package com.izoo.survey.model;

/**
 * Created by mateusz on 08.08.17.
 */

public class Section {
    int ID_Section;
    String Name;
    int Sequence;

    public Section() {
    }

    public Section(int ID_Section, String name, int sequence) {
        this.ID_Section = ID_Section;
        Name = name;
        Sequence = sequence;
    }

    public int getID_Section() {
        return ID_Section;
    }

    public void setID_Section(int ID_Section) {
        this.ID_Section = ID_Section;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }
}
