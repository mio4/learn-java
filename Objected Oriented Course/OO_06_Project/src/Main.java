
public class Main {
	public static void main(String[] args) {
		try {
			//处理输入
			InputHandler inputHandler = new InputHandler();
			inputHandler.work();
			//运行线程
			Trigger[] triggers = new Trigger[10]; 
			int cnt = inputHandler.getCnt();
			for(int i=0; i < cnt;i++) {
				triggers[i] = new Trigger(inputHandler.getObje(i),inputHandler.getTrig(i),inputHandler.getTask(i)); 
				triggers[i].start();
			}		
			
			//测试程序
			//TestThread testThread = new TestThread();	
			//testThread.start();
			
		} catch (Exception e) {
			System.exit(0);
		}
	}
}
