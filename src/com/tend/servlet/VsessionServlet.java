package com.tend.servlet;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 视频会议轮询自动结束会议
 * 
 * @author xxs
 * 
 */
public class VsessionServlet extends HttpServlet {

	private static final long serialVersionUID = 8450145932208654773L;
	
	private VsessionOverLoop vsessionOverLoop;
	private Thread TvsessionOverLoop;

	public VsessionServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
			TvsessionOverLoop.join(1000L);
			if (TvsessionOverLoop.isAlive())
				System.out.println("视频会议自动结束轮询的线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
		vsessionOverLoop = new VsessionOverLoop();
		System.out.println("程序初始化！");
		TvsessionOverLoop = new Thread(vsessionOverLoop);
		
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
            	try {
					if(!TvsessionOverLoop.isAlive()){
						TvsessionOverLoop = new Thread(vsessionOverLoop);
						TvsessionOverLoop.start();
					}else{
						TvsessionOverLoop.start();
					}
				} catch (Exception e) {
					System.out.println("线程启动异常");
					e.printStackTrace();
				}
            }
        },1000,1000*60*15);
	}
}
