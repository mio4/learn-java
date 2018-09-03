package elevator;

import java.util.Scanner;

public class ElevatorSys {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		ALS_Scheduler root = new ALS_Scheduler(10, 1);
		String buf;
		int countLines = 0;
		while(in.hasNextLine()) {
			try {
				buf = in.nextLine();
				++countLines;
			} catch(Exception except) {
				System.out.println("Empty Input.");
				break;
			}
			if(countLines > 2048) {
				System.out.println("Too Long Input Data. Rejected.");
			}
			if("RUN".equals(buf)) {
				root.runElevator();
				System.out.println("Work Finished.");
				break;
			}
			try {
				char []tmp = buf.replace(" ", "").replace("\t", "").toCharArray();
				int state = 0; // DFA state
				buf = "";
				for(int i = 0; i < tmp.length; ++i) {
					if(state == 0 && (tmp[i] == '(')) {
						++state;
						buf += tmp[i];
					} else if(state == 1 && i + 2 < tmp.length && (tmp[i] == 'F' || tmp[i] == 'E') && tmp[i + 1] == 'R' && tmp[i + 2] == ',') {
						++state;
						buf += tmp[i];
						buf += tmp[i + 1];
						buf += tmp[i + 2];
						i += 2;
					} else if(state == 2 && (tmp[i] >= '0' && tmp[i] <= '9' || tmp[i] == ',')) {
						if(tmp[i] == ',') {
							++state;
						}
						buf += tmp[i];
					} else if(state == 3 && (tmp[i] == 'U' && i + 2 < tmp.length && tmp[i + 1] == 'P' && tmp[i + 2] == ',' || tmp[i] == 'D' && i + 4 < tmp.length && tmp[i + 1] == 'O' && tmp[i + 2] == 'W' && tmp[i + 3] == 'N' || tmp[i] >= '0' && tmp[i] <= '9')) {
						++state;
						if(tmp[i] == 'U') {
							buf += "UP,";
							i += 2;
						} else if(tmp[i] == 'D') {
							buf += "DOWN,";
							i += 4;
						} else { //	tmp[i] >= '0' && tmp[i] <= '9'
							buf += "NONE," + tmp[i];
						}
					} else if(state == 4 && (tmp[i] >= '0' && tmp[i] <= '9' || tmp[i] == ')')) {
						if(tmp[i] >= '0' && tmp[i] <= '9') {
							buf += tmp[i];
						} else { // tmp[i] == ')'
							++state;
							buf += tmp[i];
						}
					} else {
						throw new Exception("Invalid Character Or Format.");
					}
				}
				if(state != 5) {
					throw new Exception("Invalid Character Or Format.");
				}
				if(root.addQuery(new Query(buf)) == false) {
					throw new Exception("Invalid Query.");
				} else {
					System.out.println("Query Parsed.");
				}
			} catch(Exception except) {
				System.out.print(except.getMessage());
				if(except instanceof UnsortedException) {
					System.out.println("Stopped.");
					break;
				} else {
					System.out.println("Ignored.");
				}
			} catch(Throwable except) {
				System.out.println("Other Errors.Stopped.");
				break;
			}
		}
		in.close();
	}
}
