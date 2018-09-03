package Bank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountSet {
	
//OVERVIEW:帐户的集合，不区别帐户类别
//任意选择一种数据结构，对照规格补全代码
    private ArrayList<Account> accounts;
	
   //constructors
   public AccountSet(){
   /**
    * @REQUIRES:None;
    * @MODIFIES:this;
    * @EFFECTS： (\result = \this) &&(\this.isEmpty());
    */
	//请补全构造函数
       this.accounts = new ArrayList<Account>();
   }
   
   
   public boolean insert(Account a){
   /**
    * @ REQUIRES: a!= null ;
    * @ MODIFIES: this;
    * @ EFFECTS: 
    * (\result==false)==>(\old(\this).contains(a)==true)
    * (\this.size == \old(\this).size+1) && (\this.contains(a)==true)&&(\result==true); 
    */
	//请根据规格补充代码
        for(Account t : this.accounts)
           if(a == t)
              return false;
        this.accounts.add(a);
        return true;
   }   
   
   public void removeByAccountId(String id) throws InvalidOperationException{
    /**
     * @ MODIFIES:this;
     * @ EFFECTS:
     * (\old(\this).queryByAccountId(id) !=null) ==> (\this.size == \old(\this).size-1) && (\this.contains(\old(\this).queryByAccountId(id))==false);
     * (\old(\this).queryByAccountId(id)==null)==>exceptional_behavior(InvalidOperationException("Invalid Account!"));                   
     */
	//请根据规格补充代码
      Account tempAccount;
      if((tempAccount = queryByAccountId(id))!=null)
          this.accounts.remove(tempAccount);
      else throw new InvalidOperationException("Invalid Account!");
   }

   public void removeByUserId(String id)throws InvalidOperationException{
    /**
     * @ MODIFIES:this;
     * @ EFFECTS:
     * (\old(\this).queryByUserId(id) !=null) ==> (\this.size == \old(\this).size-\old(\this).queryByUserId(id).size()) && (this.contains(\old(\this).queryByUserId(id))==false));           
     * (\old(\this).queryByUserId(id) ==null)==>exceptional_behavior(InvalidOperationException("Invalid User!"))                   
    */
	//请根据规格补充代码
      AccountSet tempAccount;
      if((tempAccount = queryByUserId(id))!=null) {
      	   Iterator<Account> iterator = tempAccount.elements();
      	   while(iterator.hasNext())
			   this.accounts.remove(iterator.next());
	  }
      else throw new InvalidOperationException("Invalid Account!");
   }
   
  
   public boolean isIn(Account a){
   /**
    * @ REQUIRES: a!= null ;
    * @ MODIFIES:none; 
    * @ EFFECTS:
    * (\result==true)==>(this.contains(a)==true);
    * (\result==false)==>(this.contains(a)==false);
    */
	//请根据规格补充代码
       for(int i=0;i < this.accounts.size();i++)
          if(a.equals(this.accounts.get(i))) //非地址比较
             return true;
       return false;
   }   

   public long size(){
   /**
    * @ EFFECTS: 
    * (\result==this.size());
    */
	//请根据规格补充代码
      return this.accounts.size();
   }   
  
   public Iterator<Account> elements(){
    /**
	 * @ Effects: 
	 * (\result != null) && (\result instanceof Iterator) && (\all int i; 0<=i<\this.size;\result.contains(\this[i])); 
	 */
	//请根据规格补充代码
       return this.accounts.iterator();
   }   
   
   public Account queryByAccountId(String accountId) throws InvalidOperationException{
   /**
    * @ REQUIRES: accountId!= null ;
    * @ EFFECTS: 
    * (\result==a)==>(\all Account a;(\this.contains(a)==true)&&a.getAccountId()==accountId);
    * (\all Account a;a.getAccountId()==accountId && (\this.contains(a)==false))==>exceptional_behavior("Invalid Account!") ;
    */
   //请根据规格补充代码
         for(int i=0;i < this.accounts.size();i++)
            if(this.accounts.get(i).getAccountId().equals(accountId))
               return this.accounts.get(i);
         throw new InvalidOperationException("Invalid Account!");
   }   
          
   public AccountSet queryByUserId(String userId)throws InvalidOperationException{
   /**
    * @ REQUIRES: userId!= null ;        
    * @ EFFECTS: 
    * (\all Account a;(\result.contains(a)==true)==>a.getUserId()==userId && (\this.contains(a)==true));
    * (\all Account a;a.getUserId()==userId && (\this.contains(a)==false))==>exceptional_behavior("Invalid User!") ;  
    */
   //请根据规格补充代码
       AccountSet accountSet = new AccountSet();
         for(int i=0;i < this.accounts.size();i++)
            if(this.accounts.get(i).getAccountId().equals(userId))
               accountSet.insert(this.accounts.get(i));
         if(accountSet.size() != 0)
             return accountSet;
         throw new InvalidOperationException("Invalid User!");
   }   
    
   public boolean repOK(){
   //请将repOK()方法补充完整
   //accounts!=null
        if(this.accounts == null)
           return false;
        return true;
   }
   
}

