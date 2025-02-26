import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;


class InvalidOperationException extends Exception{
   public InvalidOperationException(String message) {
       super(message);
   }
}

abstract class Question{
   String question;
   int points;

   public Question(String question,int points) {
       this.question = question;
       this.points=points;
   }

   public int getPoints() {
       return points;
   }
   abstract String getAnswer();
   public String getQuestion() {
       return question;
   }

   public void setQuestion(String question) {
       this.question = question;
   }

}
class MCQuestion extends Question{

   char answer;
   public MCQuestion(String question,int points, char answer) {
       super(question,points);
       this.answer=answer;
   }
   @Override
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("Multiple Choice Question: ").append(question).append( " Points ").append(points).append(" Answer: ").append(answer);
       return sb.append("\n").toString();
   }


   @Override
   String getAnswer() {
       return answer+"";
   }
}
class TFQuestion extends Question{

   private boolean answer;
   public TFQuestion(String question, int points, boolean answer) {
       super(question, points);
       this.answer=answer;
   }



   @Override
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("True/False Question: ").append(question).append( " Points: ").append(points).append(" Answer: ").append(answer);
       return sb.append("\n").toString();
   }

   @Override
   String getAnswer() {
       if(answer) return "true";
       else return "false";
   }
}

class Quiz{
   ArrayList<Question> questions;
   Quiz(){
       questions=new ArrayList<>();
   }
   void addQuestion(String questionData) throws InvalidOperationException{
       String[] q = questionData.split(";");
       if(q[0].equals("MC")){
           try{
               if(q[3].charAt(0)<'A'||q[3].charAt(0)>'E') throw new InvalidOperationException(q[3].charAt(0)+" is not allowed option for this question");
               else questions.add(new MCQuestion(q[1],Integer.parseInt(q[2]),q[3].charAt(0)));
           }
           catch (InvalidOperationException e){
               System.out.println(e.getMessage());
           }
       }
       else {
           questions.add(new TFQuestion(q[1],Integer.parseInt(q[2]), !q[3].equals("false")));
       }
   }

   void printQuiz(OutputStream os){
       questions.sort(Comparator.comparing(Question::getPoints).reversed());
       StringBuilder sb = new StringBuilder();
       PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
       for(int i=0;i<questions.size();i++){
           sb.append(questions.get(i).toString());
       }
       pw.print(sb.toString());
       pw.close();
   }

   void answerQuiz(List<String> answers, OutputStream os){
       try{
           PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
           StringBuilder sb = new StringBuilder();
           double suma=0;
           if(answers.size()!=questions.size())
               throw new InvalidOperationException("Answers and questions must be of same length!");
           else {
               for (int i=0;i<questions.size();i++){
                   if(answers.get(i).equals(questions.get(i).getAnswer())){
                       suma+=questions.get(i).getPoints();
                       sb.append(String.format("%d. %.2f\n",i+1,(float)questions.get(i).getPoints()));
//                        (i + 1)).append(". ").append(questions.get(i).getPoints()).append("\n"
                   }
                   else {
                       if(questions.get(i) instanceof MCQuestion){
                           suma-=0.2*questions.get(i).getPoints();
                           sb.append(String.format("%d. -%.2f\n",i+1,questions.get(i).getPoints() * 0.2));
                       }
                       else {
                           sb.append(String.format("%d. 0.00\n",i+1));
                       }
                   }
               }
           }
           sb.append(String.format("Total points: %.2f",suma));
           pw.print(sb.toString());
           pw.close();
       }
       catch (InvalidOperationException e){
           System.out.println(e.getMessage());
       }
   }

}

public class QuizTest {
   public static void main(String[] args) throws InvalidOperationException {

       Scanner sc = new Scanner(System.in);

       Quiz quiz = new Quiz();

       int questions = Integer.parseInt(sc.nextLine());

       for (int i=0;i<questions;i++) {
           quiz.addQuestion(sc.nextLine());
       }

       List<String> answers = new ArrayList<>();

       int answersCount =  Integer.parseInt(sc.nextLine());

       for (int i=0;i<answersCount;i++) {
           answers.add(sc.nextLine());
       }

       int testCase = Integer.parseInt(sc.nextLine());

       if (testCase==1) {
           quiz.printQuiz(System.out);
       } else if (testCase==2) {
           quiz.answerQuiz(answers, System.out);
       } else {
           System.out.println("Invalid test case");
       }
   }
}