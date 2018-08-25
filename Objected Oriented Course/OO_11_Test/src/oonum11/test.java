package oonum11;


public class test extends Thread{
	protected Taxinew[] Taxiline; 
	public test(Taxinew[] Taxiline) {
		this.Taxiline = Taxiline;
	}
	public void run() {
		try {
			  sleep(50000);
		  }catch(InterruptedException e) {}
		System.out.println("begin");
			Taxiline[3].pre();
		    Taxiline[3].next();
			Taxiline[3].next();


	}

}