import java.util.*;

public class StudentServerStrategy implements ServerStrategy{
	List<String> file;
	boolean[] acks;
	int[] ack_tally; //For checking number of times ACK has been received
	int cwnd = 1;
	int ssthresh = 32;
	
	int RTT = 0;
	
	int[] bad_rtt_count;
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
		bad_rtt_count = new int[file.size()];
	}

	public void reset(){


	}

	public List<Message> sendRcv(List<Message> clientMsgs){
		System.out.println("=================RTT " +RTT + "====================" );
		RTT++;
	
		for(Message m: clientMsgs){
			acks[m.num-1] =true;
			System.out.println(m.num+","+m.msg);
		}
		
		int firstUnACKed = 0;
		List<Message> msgs = new ArrayList<Message>();
		while( firstUnACKed < acks.length && acks[firstUnACKed]) ++firstUnACKed;
		//System.out.println(firstUnACKed);
		if(firstUnACKed< acks.length) {
			if (clientMsgs.size() == 0) bad_rtt_count[firstUnACKed] ++;
			ack_tally[firstUnACKed] +=1;
			//System.out.println(firstUnACKed + "   " +ack_tally[firstUnACKed]);
			if (ack_tally[firstUnACKed] >2) triple_dup = true;
			if (bad_rtt_count[firstUnACKed] >3) timeout = true;
		}
			//TODO Check if congestion
			if(triple_dup) {
				//System.out.println("TrIP");
				ssthresh = cwnd/2;
				cwnd = ssthresh; 
				slowStart = false;	
				triple_dup = false;
			}
			else if(timeout) {
				//System.out.println("TIMEOUT");
				ssthresh = cwnd/2;
				cwnd = 1;
				timeout = false;
				slowStart = true;
			}
				
					
		if(acks.length > 0 ) { //&& (firstUnACKed< acks.length)
			for(int i = firstUnACKed; i <= cwnd; i++) {
					if (i < acks.length) msgs.add(new Message(i,file.get(i)));   
		  }
		  if(!slowStart && cwnd !=1) {cwnd ++;} else if(cwnd < ssthresh) {cwnd=cwnd*2;}
    }
		if (slowStart && cwnd ==1) cwnd +=1;
		return msgs;
	}
  
}
