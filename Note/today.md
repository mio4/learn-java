







面向对象6大原则

- **开闭原则**：**对扩展开放,对修改关闭**，多使用抽象类和接口。
  - 比如代理模式，不修改原有的类，而是在基础上做扩展
- **里氏替换原则**：基类可以被子类替换，使用抽象类继承,不使用具体类继承。
  - **子类可以扩展父类的功能，但不能改变父类原有的功能。**
- 依赖倒转原则：要依赖于抽象,不要依赖于具体，针对接口编程,不针对实现编程。
- **接口隔离原则**：**使用多个隔离的接口,比使用单个接口好，建立最小的接口。**
- 迪米特法则：一个软件实体应当尽可能少地与其他实体发生相互作用，通过中间类建立联系。
- 合成复用原则：尽量使用合成/聚合,而不是使用继承。







- isHeldExclusively()：该线程是否正在独占资源。只有用到condition才需要去实现它。
- tryAcquire(int)：独占方式。尝试获取资源，成功则返回true，失败则返回false。
- tryRelease(int)：独占方式。尝试释放资源，成功则返回true，失败则返回false。
- tryAcquireShared(int)：共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
- tryReleaseShared(int)：共享方式。尝试释放资源，如果释放后允许唤醒后续等待结点返回true，否则返回false。

　　以ReentrantLock为例，state初始化为0，表示未锁定状态。A线程lock()时，会调用tryAcquire()独占该锁并将state+1。此后，其他线程再tryAcquire()时就会失败，直到A线程unlock()到state=0（即释放锁）为止，其它线程才有机会获取该锁。当然，释放锁之前，A线程自己是可以重复获取此锁的（state会累加），这就是可重入的概念。但要注意，获取多少次就要释放多么次，这样才能保证state是能回到零态的。







## 偏向锁/轻量级锁/重量级锁

这三种锁是指锁的状态，并且是针对`Synchronized`。在Java 5通过引入锁升级的机制来实现高效`Synchronized`。这三种锁的状态是通过对象监视器在对象头中的字段来表明的。
偏向锁是指一段同步代码一直被一个线程所访问，那么该线程会自动获取锁。降低获取锁的代价。
轻量级锁是指当锁是偏向锁的时候，被另一个线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，提高性能。
重量级锁是指当锁为轻量级锁的时候，另一个线程虽然是自旋，但自旋不会一直持续下去，当自旋一定次数的时候，还没有获取到锁，就会进入阻塞，该锁膨胀为重量级锁。重量级锁会让其他申请的线程进入阻塞，性能降低。











连接：mysql -h[10.92.162.45](/var/folders/rh/jgv9z9yj5g5860ss7tvyyjq40000gp/T/abnerworks.Typora/11AD66A3-D8C0-45F8-BB44-EF8BF44B2194/10.92.162.45) -P8309 -uroot -pcdn-testing\!\!\!

数据库：cdn_cboard和cdn_data



cdn_cboard：全量表表结构。

cdn_data：一堆带随机串的而且名字比较长的表（直接drop也可以没影响）不要，其它的都要。



只需要表结构，不需要数据。

比如用navicat等工具可以导出，可以直接生成个.sql文件。



```
{
  "K" : "NAVHT4PXWT8WQBL5",
  "P" : "Mac 10.13",
  "DI" : "NzA4MzUxZjkzYzRlMzgx"
}
```



```
Y2QicJyjIiXSMCPyrBHpcDJIoVFr9AFXszK9zuwvuEIaQKol/8pYc5mig+HX6T6VF4U6SnkaEcIUVZWkpoRAxrjoV/PqHNhbpnGbyh+pZ9wcdfkMDpQEBWqeIC9hIWaYGidF2RvLpHH3A9pdIe8colO8tNrRF312Q0czq5qHBAzR2k4py9telSi8gx8pH3NOYl3dP3MtuyANkKb6wwqx9/f5os38e2xHca4xjj5edVEuyXHvpog08oXV1HkqVwSQh7QFaIDy9dHs/1nnDiL24X75HoTPym0dsWKsT6trXNe0/psJk5GP4a+nD1MEVKQwMGYAszr4+wMtvs8iXqkSVA==
```



```
{"K":"NAVHT4PXWT8WQBL5", "N":"marswill", "O":"weiyongqiang.com", "DI":"ODQ2Yjg2ZDBjMTEzMjhh", "T":1516939200}
```











































