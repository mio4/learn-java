package FactoryMethod;

public interface Sender {
    public void send();
}

class MailSender implements Sender{
    @Override
    public void send(){
        System.out.println("This is a mail sender~");
    }
}

class SmsSender implements Sender{
    @Override
    public void send(){
        System.out.println("This is a sms sender~");
    }
}

class SendFactory{

    //普通工厂模式
//    public Sender produce(String type){
//        if("mail".equals(type)){
//            return new MailSender();
//        }
//        else if("sms".equals(type)){
//            return new SmsSender();
//        }
//        else{
//            System.out.println("类型错误~");
//            return null;
//        }
//    }

    public Sender produceMail(){
        return new MailSender();
    }

    public Sender produceSms(){
        return new SmsSender();
    }
}
