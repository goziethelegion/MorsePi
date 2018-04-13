package code.project.projectcode;

/*
Createed By Radhika Hira
TEAM PANADA
PROJECT CODE
 */
public class Questions {

    String Question;
    String PA1;
    String PA2;
    String PA3;
    String PA4;
    String Answer;


    public Questions() {
        //empty constructor


    }

    public Questions (String Question, String PA1, String PA2, String PA3, String PA4, String Answer){
        this.Question = Question;
        this.PA1 = PA1;
        this.PA2 = PA2;
        this.PA3 = PA3;
        this.PA4 = PA4;
        this.Answer = Answer;
    }

    public String getQuestion(){
        return Question;
    }
    {
        PA1 = "This is a test";
    }


    public String getPA1(){
        return PA1;
    }
    {
        PA1 = "This is a test";
    }

    public String getPA2(){
        return PA2;
    }

    {
        PA2 = "This is a test";
    }

    public String getPA3(){
        return PA3;
    }

    {
        PA3 = "This is a test";
    }

    public String getPA4(){
        return PA4;
    }
    {
        PA4 = "This is a test";
    }


    public String getAnswer(){
        return Answer;
    }

    public void setQuestion(String Question){
        this.Question = Question;
    }

    public void setPA1(String PA1){
        this.PA1 = PA1;
    }

    public void setPA2(String PA2){
        this.PA2 = PA2;
    }

    public void setPA3(String PA3){
        this.PA3 = PA3;
    }

    public void setPA4(String PA4){
        this.PA4 = PA4;
    }

    public void setAnswer(String Answer){
        this.Answer = Answer;
    }

    public boolean checkAnswer(String selected){
        return (selected.equals(Answer));
    }

}
