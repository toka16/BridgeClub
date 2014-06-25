package bridge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Timer;


public class MyTimer{
	private static final int SECOND = 1000;
	private static final int MINUT = 60;
	
	private int currentTime;
	private Timer t;
	private PartOfTable part;
	private ReentrantLock lock;
	
	public MyTimer(int startTimeMinuts, PartOfTable part){
		this.part = part;
		lock = new ReentrantLock();
		currentTime = startTimeMinuts*MINUT;
		t = new Timer(SECOND, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lock.lock();
				currentTime--;
				if(currentTime <= 0){
					currentTime = 0;
					MyTimer.this.part.leaveGame();
					t.stop();
				}
				lock.unlock();
			}
		});	
	}
	
	
	public void start(){
		t.start();
	}
	
	public void stop(){
		t.stop();
	}
	
	
	public int getTimeLefted(){
		lock.lock();
		int ans = currentTime;
		lock.unlock();
		return ans;
	}
	
	
}
