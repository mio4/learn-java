public class Test extends Thread {
    Taxi[] taxis;
    Test(Taxi[] taxis){
        super("Test");
        this.taxis = taxis;
    }
    public void catchtaxi(int index){
        long time = System.currentTimeMillis();
        System.out.println("查询时刻:"+time);
        System.out.println("出租车当前坐标:("+taxis[index].getPoint().x+","+taxis[index].getPoint().x+")");
        System.out.println("出租车当前状态:"+taxis[index].getStatus());
    }
    public void catchstatus(int status){
        if ((status<0)||(status>3)){
            System.out.println("your status is out of range");
            return;
        }
        for (int i=0;i<100;i++){
            System.out.println("当前状态的出租车有:");
            if (taxis[i].getStatus()==status){
                System.out.println("i");
            }
            System.out.println();
        }
    }
    public void run(){
        //
    }
}
