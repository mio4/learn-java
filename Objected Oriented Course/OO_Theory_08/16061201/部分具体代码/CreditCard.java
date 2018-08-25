public class CreditCard extends Account{

	private String type = "Normal"; //默认为普通用户

	public CreditCard(String name,String id,String pw,String code){
		super(name,id,pw,code);
	}

	public void setVIP(int i){
		this.type = "VIP";
	}
}
