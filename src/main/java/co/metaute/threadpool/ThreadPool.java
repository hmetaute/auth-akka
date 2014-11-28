package co.metaute.threadpool;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPool {
	
	private ArrayList<Thread> pool;
	
	private Queue<Task> availableTasks = new ArrayBlockingQueue<Task>(10000);
	
	private static class ThreadPoolInstance{
		private static ThreadPool instance = new ThreadPool();
	}
	
	public static ThreadPool getInstance(){
		return ThreadPoolInstance.instance;
	}
	
	
	public synchronized Task getTask(){
		return availableTasks.poll();
	}
	
	public void addTask(Task task){
		availableTasks.add(task);
	}

}
