package com.mio4.demo2;

public class UserServiceImpl implements UserService{

	@Override
	public void sayHello() {
		System.out.println("Hello,Spring");
	}

	public void init(){
		System.out.println("init~");
	}

	public void destroy(){
		System.out.println("destroy");
	}
}
