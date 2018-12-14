package Proxy;

interface Subject{
    void visit();
}

class RealSubject implements Subject{
    private String name = "mio";
    @Override
    public void visit(){
        System.out.println("visit " + name);
    }
}

class ProxySubject implements Subject{
    private Subject subject;

    public ProxySubject(Subject subject){
        this.subject = subject;
    }

    @Override
    public void visit(){
        subject.visit();
    }
}

public class Client {

    public static void main(String[] args){
        ProxySubject subject = new ProxySubject(new RealSubject());
        subject.visit();
    }
}
