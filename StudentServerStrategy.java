import java.util.*;

public class StudentServerStrategy implements ServerStrategy{
	List<String> file;
	boolean[] acks;
	int[] ack_tally; //For checking number of times ACK has been received
	int cwnd = 1;
	int ssthresh = 32;
	
	int bad_rtt_count = 0;
	boolean timeout = false;
	// ssthresh = ssthresh/2; cwnd = 1; slowStart = true;
	boolean triple_dup = false;
	// ssthresh = ssthresh/2; cwnd = ssthresh;  slowStart = false;
	boolean slowStart = true;
	// if false, do congestion avoidance, else exponentially increase
	
	
	
	 
	
	public StudentServerStrategy(){
		reset();
	}

	public void setFile(List<String> file){
		this.file = file;
		acks = new boolean[file.size()];
		ack_tally = new int[file.size()];
	}

	public void reset(){


	}

	public List<Message> sendRcv(List<Message> clientMsgs){
		if (clientMsgs.size() == 0) bad_rtt_count ++;
		for(Message m: clientMsgs){
			acks[m.num-1] =true;
			System.out.println(m.num+","+m.msg);
		}
		int firstUnACKed = 0;
		List<Message> msgs = new ArrayList<Message>();
		while( firstUnACKed < acks.length && acks[firstUnACKed]) ++firstUnACKed;
			ack_tally[firstUnACKed] +=1;
			if (ack_tally[firstUnACKed] >2) triple_dup = true;
			if (bad_rtt_count >3) timeout = true;
			
			
			//TODO Check if congestion
			if(cwnd < ssthresh && slowStart) {
				cwnd*=2;
			}
			else if(triple_dup) {
				ssthresh = cwnd/2;
				cwnd = ssthresh; 
				slowStart = false;	
				triple_dup = false;
			}
			else if(timeout) {//TODO
				ssthresh = cwnd/2;
				cwnd = 1;
				timeout = false;
				slowStart = true;
			}
						
			// TODO Send 
			for(int i = firstUnACKed; i <= cwnd; i++) {
				//if(i < acks.length) {
					if (i < acks.length) msgs.add(new Message(i,file.get(i)));   
      }
      if(!slowStart) cwnd ++; 
		return msgs;
	}
    
}
