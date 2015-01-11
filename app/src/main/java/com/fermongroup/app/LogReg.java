package com.fermongroup.app;
/**
 * Created by Alex on 01/01/2015.
 */
public class LogReg {
    //private variables
    int _id;
    int _type;
    String _information;

    public LogReg(){}
    // constructor
    public LogReg(int id, int type, String _information){
        this._id = id;
        this._type = type;
        this._information = _information;
    }

    // constructor
    public LogReg(int type, String _information){
        this._type = type;
        this._information = _information;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting type
    public int getType(){
        return this._type;
    }

    // setting type
    public void setType(int type){
        this._type = type;
    }

    public String getInformation(){
        return this._information;
    }

    public void setInformation(String information){
        this._information = information;
    }
}
