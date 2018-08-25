package Bank;

import java.util.ArrayList;



public class test {

	/**
	 * @param args
	 */
	public static AccountSet accountset = new AccountSet();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Account a1=new Account("01","jane","123");
		accountset.insert(a1);
		System.out.println("repOK " + a1.repOk());
		Account a2=new Account("02","greece","123");
		accountset.insert(a2);
		System.out.println("repOK " + a2.repOk());
		/*
		code continue
		*/
		//账户集合更改操作
		accountset.insert(a1);
		accountset.insert(a2);
		
		//储蓄卡测试
		DebitCardAccount da1 = new DebitCardAccount("1","Me","123");
		System.out.println("repOK " + da1.repOk());
		accountset.insert(da1);
		da1.depositMoney(1000.0);
		System.out.println("存款为负:" + da1.depositMoney(-100.0));
		System.out.println("余额:" + da1.getBalance());
		da1.drawMoney(400.0);
		System.out.println("余额:" + da1.getBalance());
		try {
			da1.transferMoney(a1.getAccountId(), a1.getName(), 200.0, accountset);
		} catch (Exception e){
			System.out.println("转账失败");
			e.printStackTrace(System.out);
		}
		
		//信用卡测试
		CreditCardAccount ca1 = new CreditCardAccount("2","You","321");
		System.out.println("repOK " + ca1.repOk());
		accountset.insert(ca1);
		ca1.depositMoney(1000.0);
		System.out.println("透支额度为500.0");
		ca1.setMax(500.0);
		ca1.drawMoney(1200.0);
		System.out.println("透支消费后余额" + ca1.getBalance());
		try {
			ca1.transferMoney(a2.getAccountId(), a2.getName(), 200.0, accountset);
		} catch (Exception e) {
			System.out.println("转账失败");
			e.printStackTrace(System.out);
		}
		
		//账户集合查询操作
		try {
		System.out.println("accountId = 1:\n" + accountset.queryByAccountId("1"));
		} catch (Exception e){
			System.out.println("查询失败");
		}
		try {
			System.out.println("userId = 1:\n" +accountset.queryByUserId("1"));
		} catch (Exception e){
			e.printStackTrace(System.out);
			System.out.println("查询失败");
		}
		//账户集合更改操作
		System.out.println("accountset size : " + accountset.size());
		try {
			accountset.removeByAccountId("1");
		} catch (Exception e){
			e.printStackTrace(System.out);
		}
		System.out.println("accountset size : " + accountset.size());
		try {
			accountset.removeByUserId("2");
		} catch (Exception e){
			e.printStackTrace(System.out);
		}
		System.out.println("accountset size : " + accountset.size());
	}

}
