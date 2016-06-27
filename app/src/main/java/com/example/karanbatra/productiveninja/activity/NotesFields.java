package com.example.karanbatra.productiveninja.activity;

/**
 * Created by neeraj.varshney on 6/22/2016.
 */
public class NotesFields {
    int _id;
    String _name;
    String _contents;
    int _delelte_var=0;
    int _recycle_delete=0;
    public NotesFields(){   }
    public NotesFields(int id, String name,String _contents, int _delelte_var, int _recycle_delete ){
        this._id = id;
        this._name = name;
        this._contents=_contents;
        this._delelte_var=_delelte_var;
        this._recycle_delete=_recycle_delete;

    }

    public NotesFields(String name ,String _contents){
        this._name = name;
        this._contents=_contents;

    }

    public String get_contents() {
        return _contents;
    }

    public void set_contents(String _contents) {
        this._contents = _contents;
    }


    public int get_delelte_var() {
        return _delelte_var;
    }

    public void set_delelte_var(int _delelte_var) {
        this._delelte_var = _delelte_var;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }


    public int get_recycle_delete() {
        return _recycle_delete;
    }

    public void set_recycle_delete(int _recycle_delete) {
        this._recycle_delete = _recycle_delete;
    }
}
