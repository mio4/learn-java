import java.util.ArrayList;

public class AccountSet {
	ArrayList<Account> account_set = new ArrayList<Account>();

	public void addAcount(Account account){
		this.account_set.add(account);
	}

	public Account getAccountByPersonId(String person_id){
		for(int i=0;i < this.account_set.size();i++){
			if(person_id == this.account_set.get(i).getPersonId())
				return this.account_set.get(i);
		}
		return null;
	}

	public Account getAccountByAccountId(long account_id){
		for(int i=0;i < this.account_set.size();i++){
			if(account_id == this.account_set.get(i).getAccountId())
				return this.account_set.get(i);
		}
		return null;
	}

	public boolean delAccountByPersonId(String person_id){
		for(int i=0;i < this.account_set.size();i++){
			if(person_id == this.account_set.get(i).getPersonId())
				this.account_set.remove(i);
		}
		return true;
	}

	public boolean delAccountByAccountId(long account_id){
		for(int i=0;i < this.account_set.size();i++){
			if(account_id == this.account_set.get(i).getAccountId())
				this.account_set.remove(i);
		}
		return true;
	}

	public void ConnectAccount(){ //将储蓄卡和银行卡账号关联起来即可

	}
}
