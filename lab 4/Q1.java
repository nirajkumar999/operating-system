import java.util.*;
import java.lang.*;
// Java program to implement solution of producer
//consumer problem.
public class producer_consumer_problem //Main class
{
public static void main(String[] args)throws Exception
{
//This is the shared array variable between producer and

consumer

ArrayList<Integer> sharedarray = new

ArrayList<Integer>();

//creating objects of thread class and passing the

objects of producer

//and consumer class and along with that we set the

proper name of both threads

Thread thread1 = new Thread(new

Producer(sharedarray),"Producer Thread"); // producer thread

Thread thread2 = new Thread(new

Consumer(sharedarray),"Consumer Thread"); // consumer thread

//starting both the threads parallely
thread1.start();
thread2.start();
}
}
//Producer class
class Producer implements Runnable
{
ArrayList<Integer> sharedarray = null;
int max_size=5; // max limit for the item to be produced at
once is set to 5
int i=0;
//Constructor
public Producer(ArrayList<Integer> sharedarray)
{
this.sharedarray = sharedarray;
}
//Override run method from Runnable interface
public void run()
{
while(true)
try
{

produce(i++);
}
catch (Exception e)
{
System.out.println("Exception Arised");
}
}
//produce() function which produces items one by one
//and store inside the sharedarray
public void produce(int i) throws Exception
{
//Synchronizing the common resource i.e shared array
synchronized(sharedarray)

{

while(sharedarray.size()==max_size) //no. of items

produced , reached max. capacity

{
System.out.println("Shared array is full ...

waiting for the Consumer thread to consume");

//we put Producer thread in waiting state and allow

Consumer thread to execute first
sharedarray.wait();
}
//Producer ready to produce, array contains at least one

free space

sharedarray.add(i);
System.out.println("Producer Thread , produced element

"+i);

// We put Producer thread to sleep for 1 sec. so that

all operations are done smoothly
Thread.sleep(1000);
// notifies the Consumer thread that now it can start

consuming

sharedarray.notify();
}
}
}
//Consumer class
class Consumer implements Runnable
{
ArrayList<Integer> sharedarray=null;
//constructor
public Consumer(ArrayList<Integer> sharedarray)
{
this.sharedarray = sharedarray;
}
//Override run method from Runnable interface
public void run()
{
while(true)
try
{
consume();
}

catch (Exception e)
{
System.out.println("Exception arised");
}
}
// consume() function which consumes item one by one
// and gradually creates free space for new items inside
sharedarray
public void consume()throws Exception
{
// Synchronizing the common resource i.e sharedarray

synchronized(sharedarray)
{
while(sharedarray.isEmpty()) // array is empty and
nothing to consume
{
System.out.println("Shared array is empty ...

waiting for the Producer thread to produce the object");

//we put Consumer thread in waiting state and allow

Producer thread to execute first
sharedarray.wait();
}
//Consumer thread ready to consume , array contains
atleast one item
System.out.println("Consumer Thread consumed element
"+sharedarray.remove(0));
//we put Consumer thread to sleep for 1 sec so that all
operations are done smoothly
Thread.sleep(1000);
//notifies the Producer thread that it can start
producing

sharedarray.notify();
}
}
}
