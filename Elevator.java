/**
Purpose of program:
This program simulates one elevator servicing a 12-floor building,
with no other elevators. The floors of the building are numbered 1 to 12.
At the beginning of the program eight of the twelve floors will be randomly selected
to visit on an upward trip, and five will be randomly selected for a downward trip.

Data Structure Used: ArrayLists
Requirements/Algorithm
These will be sorted into two ArrayLists in ascending and descending order respectively.
The elevator will begin at the first floor, travel up and then down to each floor in order,
and remain on each floor for three seconds. Traveling between floors will take two seconds.


At each floor the current floor and direction will be displayed,
and the user will be prompted to select a floor to visit if they so desire. 
If the direction they choose is consistent with the direction the elevator is already taking, 
and not already present in the corresponding ArrayList of floors to be visited
that floor will be added to the corresponding ArrayList.   

*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
*creates an elevator class to maintain and modify class variables 
*for up and down trips of the elevator
*/
public class Elevator {

   //bool indicates if elevator is going up or down
   boolean goingUpBool;
   //int indicates next floor to actually be visited
   int currentFloor;
   //integer to correspond to floors passed while going up and down
   int incrementFloor;
   //ArrayList of floors to be visited on the up trip
   ArrayList<Integer> up;

   //ArrayList of floors to be visited on the down trip
   ArrayList<Integer> down;

   public Elevator() {
      goingUpBool = true;
      currentFloor = 1;
      up = upFl();
      down = downFl();
      incrementFloor = 1;
   }


   public static void main(String args[]) throws InterruptedException {
   
      Elevator elev = new Elevator();
      int userFloor;
      
      while (true) {
      
      //print inbetween floors
         elev.onTheWay();
      // print current floor and direction
         elev.doorsOpening();
      //waits two seconds
         Thread.sleep(2000);
      //prompts user for new floor
         userFloor = elev.newFloor();
         
         //if the elevator is going up
         //and if the user has made a valid selection, add it to the queue
         //set the current floor to the next floor in the queue and remove it 
         if (elev.goingUpBool) {
            if (userFloor != 0) {
               elev.goingUp(userFloor);
            }
            elev.incrementFloor++;
            elev.currentFloor = elev.up.get(0);
            elev.up.remove(0);
            
            //check for the case where the first floor of the going down ArrayList
            //is greater than the last floor of the going up ArrayList
            if (elev.up.isEmpty() && elev.down.get(0) > elev.incrementFloor){
               elev.up.add(elev.down.get(0));
               elev.down.remove(0);            
            }
            //if the up ArrayList is empty and the down ArrayList begins above the current floor
            //increment to that floor before going down, and otherwise change status to going down      
            if (elev.up.isEmpty()) {
               elev.goingUpBool = false;
               
               //addresses special case where first floor of going down ArrayList
               //is the same as the last floor going up
               if (elev.down.get(0) == elev.incrementFloor){
                  elev.down.remove(0);
               }
               
               //if both the going up and the going down ArrayLists are empty, prompt the user
               //to begin a new trip
               if (elev.down.isEmpty()) {
                  if (elev.goAgain()) {
                     elev = new Elevator();
                  }
                  else {
                     break;
                  }
               }
            }
         }
         //if the elevator is going down
         //and if the user has made a valid selection, add it to the queue
         //set the current floor to the next floor in the queue and remove it 
         else {
            if (userFloor != 0) {
               elev.goingDown(userFloor);
            }
            elev.incrementFloor--;
            elev.currentFloor = elev.down.get(0);
            elev.down.remove(0);
            Collections.sort(elev.down, Collections.reverseOrder());
            
                              //if both the going up and the going down ArrayLists are empty, prompt the user
               //to begin a new trip
            if (elev.down.isEmpty()) {
               elev.goingUpBool = true;
               if (elev.up.isEmpty()) {
                  if (elev.goAgain()) {
                     elev = new Elevator();
                  }
                  else {
                     break;
                  }
               }
            }
         }
         
         //does a countdown 
         elev.count3();
      }
   
   }
   
   /**
   *prints current floor and direction
   */
   public void doorsOpening(){
      System.out.print("Floor " + currentFloor);
      if (goingUpBool) {
         System.out.println(", GOING UP");
      }
      else {
         System.out.println(", GOING DOWN");
      }
   }
   
   /**
   *takes user input from scanner to verify desire to choose a floor
   *then allows user to enter an integer and check validity of choice
   */
   public int newFloor(){
      Scanner scan = new Scanner(System.in);
      int newFl = 0;
      System.out.println("Would you like to choose a floor?");
      System.out.println("Please type \'y\' or \'n\'");
      String yOrN = scan.next();
         
      if (yOrN.toUpperCase().equals("Y")) {  
         System.out.println("Please choose a floor...");
         newFl = scan.nextInt();
         //immediately verify this is a valid selection
         if (newFl < 1 || newFl > 12) {
            System.out.println("You did not make a valid selection");
            newFl = 0;
         }
      }
      
      return newFl;
   }
   
   /**
   *takes the previously validated choice of new floor and adds it to the up
   *ArrayList or lets the user know this choice is already there
   */
   public void goingUp(int fl) {
      int userFloor = fl;
      
      //if user selects a higher floor
      if (userFloor > currentFloor) {
         
         //and that floor was not already in the queue
         if(!up.contains(userFloor)) {
            up.add(userFloor);
            System.out.println("Your floor will be added to the queue");  
         }
         //but that floor was already in the queue
         else {
            System.out.println("Your floor is already in the queue");
         }
      }
      else {
         System.out.println("Your desired floor is down, this elevator is currently going up.");
      }  
      
     //  
   
   }
   
   /**
   *takes the previously validated choice of new floor and adds it to the down
   *ArrayList or lets the user know this choice is already there
   */
   public void goingDown(int fl) {
      
      int userFloor = fl; 
                  //and user selects a lower floor
      if (userFloor < currentFloor) {
               //and that floor was not already in the queue
         if(!down.contains(userFloor)){
            down.add(userFloor);
            System.out.println("Your floor will be added to the queue");
            //floorsToGo++;   
         }
               //but that floor was already in the queue
         else {
            System.out.println("Your floor is already in the queue");
         }      
      }   
      else {
         System.out.println("Your desired floor is up, this elevator is currently going down.");
      }  
   }
   
   /**
   *does a 3 second countdown
   */
   public void count3() throws InterruptedException {
   
      System.out.print("\nDoors closing in 3");
      Thread.sleep(1000);
      System.out.print(" 2");
      Thread.sleep(1000);
      System.out.println(" 1");
      Thread.sleep(1000);         
   }

   /**
   * Returns an ArrayList of 8 random numbers for the initial upward trip 
   */
   private ArrayList<Integer> upFl() {
      ArrayList<Integer> arr = new ArrayList<Integer>();
      Random rand = new Random();
      int i = 0; 
      int randNum;
      
      while (i < 8) {
         randNum = rand.nextInt(11) + 2;
         if (!arr.contains(randNum)) {
            arr.add(randNum);
            i++;
         }
      }
      Collections.sort(arr);
      return arr;
   }


   /**
   * Returns an ArrayList of 5 random numbers for the initial downward trip 
   */
   public ArrayList<Integer> downFl() {
      ArrayList<Integer> arr = new ArrayList<Integer>();
      Random rand = new Random();
      int i = 0; 
      int randNum;
      
      while (i < 5) {
         randNum = rand.nextInt(11) + 1;
         if (!arr.contains(randNum)) {
            arr.add(randNum);
            i++;
         }
      }
      Collections.sort(arr, Collections.reverseOrder());
       
      return arr;
   }

   /**
   *prints floors not visited
   */
   public void onTheWay() throws InterruptedException {
                          
      while (incrementFloor != currentFloor){
         if (goingUpBool){
            System.out.print("Floor " + incrementFloor++);
            System.out.println(", GOING UP");
            Thread.sleep(2000);
         }
         else{
            System.out.print("Floor " + incrementFloor--);
            System.out.println(", GOING DOWN");
            Thread.sleep(2000);
         }
      }
   
   }
   
   /**
   *takes user input from scanner to break out of main method while loop 
   *with bool value returned
   */
   public boolean goAgain() {
      Scanner scan = new Scanner(System.in);
      String yOrN;
      System.out.println("Shall we go again?");
      yOrN = scan.next();
      if (yOrN.toUpperCase().equals("Y")) {
         return true;
      }
      return false;
   }
}