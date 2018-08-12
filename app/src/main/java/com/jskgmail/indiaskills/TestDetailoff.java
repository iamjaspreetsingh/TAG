package com.jskgmail.indiaskills;

/**
 * Created by JASPREET SINGH on 18-8-2018.
 */

public class TestDetailoff {

        //private variables
        private int _id;
    private String instructionList;
    private String testDetailss_array;
    private String arrayList_3_all_questions;
   private String arrayList_3_all_options;


    // Empty constructor
  public TestDetailoff(String instructionList, String testDetailss_array, String arrayList_3_all_questions, String arrayList_3_all_options)
  {
      this.instructionList = instructionList;
      this.testDetailss_array = testDetailss_array;
      this.arrayList_3_all_questions = arrayList_3_all_questions;
      this.arrayList_3_all_options = arrayList_3_all_options;
  }
        // constructor
        public TestDetailoff(int id, String instructionList, String testDetailss_array, String arrayList_3_all_questions, String arrayList_3_all_options)
    {
            this._id = id;
            this.instructionList = instructionList;
            this.testDetailss_array = testDetailss_array;
            this.arrayList_3_all_questions = arrayList_3_all_questions;
            this.arrayList_3_all_options = arrayList_3_all_options;
        }

    public TestDetailoff() {

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
        public String getInstructionList(){
            return this.instructionList;
        }

        // setting name
        public void setInstructionList(String instructionList){
            this.instructionList = instructionList;
        }

    public String getTestDetailss_array(){
        return this.testDetailss_array;
    }

    // setting name
    public void setTestDetailss_array( String testDetailss_array){
        this.testDetailss_array = testDetailss_array;
    }

    public String getArrayList_3_all_questions(){
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












