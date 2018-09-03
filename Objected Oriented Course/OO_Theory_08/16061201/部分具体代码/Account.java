import java.text.SimpleDateFormat;

public class Account {

	private static long account_id_cnt = 0;
	private long acount_id = 0;
	private String name;
	private String person_id;
	private String password;
	private double balance;
	private String create_time;
	private String create_code;
	
	public Account(String name,String id,String pw,String code){
		this.name = name;
		this.person_id = id;
		account_id_cnt++;
		this.acount_id = account_id_cnt;
		this.create_time = String.valueOf(System.currentTimeMillis());
		this.create_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
		this.create_code = code;
	}
	public double getBalance(){
		return this.getBalance();
	}

	public long getAccountId(){
		return this.acount_id;
	}
	public String getPersonId(){
		return this.person_id;
	}

	public boolean depositMoney(double money){
		if(money < 0){
			System.out.println("存款失败");
			return false;
		}
		this.balance += money;
		System.out.println("存款成功：" + money);
		return true;
	}
	
	public boolean drawMoney(double money){
		if(money < 0)
			return false;
		return true;
	}
	
}
