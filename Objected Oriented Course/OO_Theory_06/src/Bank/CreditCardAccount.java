package Bank;


public class CreditCardAccount extends Account{
	//OVERVIEW:信用卡帐户有最高透支额度
	//消费不能超出额度
		private double overdraftLimit;//最高透支额度
	
	  	public CreditCardAccount(String id,String name,String pwd){
	  		super(id,name,pwd);
	  		this.setBalance(0.0);
	  	}
	  	
	  	public void setMax(double money){
	  		this.overdraftLimit=money;
	  	}
	  	
	  	public double getMax()
	  	{
	  		return this.overdraftLimit;
	  	}
	 		  	
	  	public void changeStatus() {
	     	if(this.getBalance()<0.0-overdraftLimit)
	     		this.setIsActivate(false);
	     	else 
	     		this.setIsActivate(true);
	     }
	  	 
		//信用卡账户存款
	  	 public boolean depositMoney(double money) {
	  		super.depositMoney(money);
	 		changeStatus();
	 		return true;
	     }
	  	 
	  	//信用卡账户消费
		public boolean drawMoney(double money) {
			double balance=this.getBalance();
			if(!this.getIsActivate()) return false;
	  		if (balance + this.overdraftLimit < money)
	  			return false;
			this.setBalance(balance-money);
			return true;
		}
			
		//信用卡账户转账
		public boolean transferMoney(String AccountId,String name,double money,AccountSet accounts) throws InvalidOperationException{
			if(this.getBalance()<0) return false;
			else if(this.getBalance()<money) return false;
			else 
				return super.transferMoney(AccountId, name, money, accounts);
	    }
		
		public boolean repOk(){
		//请补全repOK()方法
		//不变式：UserId != null && pwd != null && overdraftLimit >= 0
			super.repOk();
			if(this.overdraftLimit < 0)
				return false;
			return true;
		}
}