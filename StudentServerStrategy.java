import java.util.*;

public class StudentServerStrategy implements ServerStrategy{
    List<String> file;


    public StudentServerStrategy(){
        reset();
    }

    public void setFile(List<String> file){
        this.file = file;
    }

    public void reset(){


    }

    public List<Message> sendRcv(List<Message> clientMsgs){
        int cwnd = 1;
        int ssthresh;
        boolean slowStart = true; // false = congestion avoidance; true = slow start
        


        //return clientMsgs; //obviously wrong


    }
    
}