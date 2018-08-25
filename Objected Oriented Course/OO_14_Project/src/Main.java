public class Main {
	/** overview:主类，获取用户的输入并且完成电梯调度
	 * 表示对象：None;
	 * 抽象函数：None;
	 * 不变式：true
	 */

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result==true;
	 */
	public boolean repOK(){
		return true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \request.equals(获取用户输入，并且完成调度，输出调度结果);
	 */
	public static void main(String[] args){
		try {
			//获取输入
			InputHandler in = new InputHandler();
			in.getInput();
			//完成调度
			Scheduler scheduler = new Scheduler(in.getRequest());
			scheduler.schedule();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
