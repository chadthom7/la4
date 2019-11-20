import java.util.*;

public class StudentServerStrategy implements ServerStrategy{
		List<String> file;
        boolean[] acks;

		public StudentServerStrategy(){
				reset();
		}

		public void setFile(List<String> file){
				this.file = file;
                acks = new boolean[file.size()];
		}

		public void reset(){


		}

	public List<Message> sendRcv(List<Message> clientMsgs){
		int cwnd = 1;
		int ssthresh = 32;
		boolean timeout = false;
		// ssthresh = ssthresh/2; cwnd = 1; slowStart = true;
		boolean triple_dup = false;
		// ssthresh = ssthresh/2; cwnd = ssthresh;  slowStart = false;
		boolean slowStart = true;
		// if false, do congestion avoidance, else exponentially increase
		
		for(Message m: clientMsgs){
			acks[m.num-1] =true;
			System.out.println(m.num+","+m.msg);
		}
		int firstUnACKed = 0;
		List<Message> msgs = new ArrayList<Message>();
			
			
		// Slow start
		while(firstUnACKed < acks.length) {
			
			//TODO Check is congestions
			
			
			// TODO increment counter
			if(cwnd < ssthresh && slowStart) {
				firstUnACKed*=2;
			}
			else if(triple_dup) {
				

			}
			else if(timeout) {
			
			}
		}

		return msgs;
	}
    
}
