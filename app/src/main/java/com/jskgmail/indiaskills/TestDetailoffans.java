package com.jskgmail.indiaskills;

/**
 * Created by JASPREET SINGH on 18-8-2018.
 */

public class TestDetailoffans {

        //private variables
        private int _id;
    private String candidateid;
    private String testDetailss_array;
    private String arrayList_3_all_questions;//batchpic
    private String arrayList_3_all_options;

    // Empty constructor
  public TestDetailoffans(String instructionList, String testDetailss_array, String arrayList_3_all_questions,
                          String arrayList_3_all_options) {
      this.candidateid = instructionList;
      this.testDetailss_array = testDetailss_array;
      this.arrayList_3_all_questions = arrayList_3_all_questions;
      this.arrayList_3_all_options = arrayList_3_all_options;

  }
    // constructor
        public TestDetailoffans(int id, String instructionList, String testDetailss_array,
                                String arrayList_3_all_questions, String arrayList_3_all_options)
    {
            this._id = id;
            this.candidateid = instructionList;
            this.testDetailss_array = testDetailss_array;
            this.arrayList_3_all_questions = arrayList_3_all_questions;
            this.arrayList_3_all_options = arrayList_3_all_options;


        }

    public TestDetailoffans() {

    }


    // constructor


        // getting ID
        public int getID(){
            return this._id;
        }

        // setting id
        public void setID(int id){
            this._id = id;
        }



        // getting name
        public String getCandidateid(){
            return this.candidateid;
        }

        // setting name
        public void setCandidateid(String candidateid){
            this.candidateid = candidateid;
        }

    public String getTestDetailss_array(){
        return this.testDetailss_array;
    }

    // setting name
    public void setTestDetailss_array( String testDetailss_array){
        this.testDetailss_array = testDetailss_array;
    }

    public String getArrayList_3_all_pics(){
        return this.arrayList_3_all_questions;
    }

    // setting name
    public void setArrayList_3_all_questions(String arrayList_3_all_questions){
        this.arrayList_3_all_questions = arrayList_3_all_questions;
    }


    public String getArrayList_3_all_options(){
        return this.arrayList_3_all_options;
    }

    // setting name
    public void setArrayList_3_all_options(String arrayList_3_all_options){
        this.arrayList_3_all_options = arrayList_3_all_options;
    }

}












