import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.spec.PSource;


public class DiskSim {
   
   public enum eDirection {LEFT, RIGHT};
   
   public static int startPosition = 0;
   static eDirection direction = eDirection.LEFT;
   public static ArrayList<Integer> positions = new ArrayList<Integer>();
   public static final int MIN_POSITION = 0;
   public static final int MAX_POSITION = 4999;
   
   
   public static void main(String[] args) throws FileNotFoundException {
      startPosition = Math.abs(Integer.parseInt(args[0]));
      direction = Integer.parseInt(args[0]) < 0 ? eDirection.LEFT : eDirection.RIGHT;
      Random random = new Random();
      
      if(args.length ==1) {
         for(int counter = 0; counter < 100; counter++) {
            positions.add(random.nextInt(4999) + 1);
         }
      }
      else {
         Scanner scanner = new Scanner(new File(args[0]));
         
         while(scanner.hasNextInt()) {
            positions.add(new Integer(scanner.nextInt()));
         }
         scanner.close();
      }
      
      FCFS();
      SSTF();
      SCAN();
      CSCAN();
      LOOK();
      CLOOK();
   }
   
   public static void FCFS() {
      if(positions.isEmpty()) {
         System.out.println("FCFS 0");
         return;
      }
      
      int distanceTraveled = 0;
      int lastDistance = positions.get(0);
      distanceTraveled+= Math.abs(startPosition - lastDistance);
      
      for(int counter = 1; counter < positions.size(); counter++) {
         distanceTraveled += Math.abs(lastDistance - positions.get(counter));
         lastDistance = positions.get(counter);
      }
      System.out.println("FSFS " + distanceTraveled);
   }
   
   public static void SSTF() {
      ArrayList<Integer> positionsCopy = new ArrayList<Integer>(positions);
      if(positionsCopy.isEmpty()) {
         System.out.println("SSTF 0");
         return;
      }
      
      Integer lastNodeRemoved = positionsCopy.remove(0);
      ArrayList<Integer> positionsWithTheLastOneRemoved = new ArrayList<Integer>(positionsCopy);
      
      int distanceTraveled = 0;
      int lastDistance = lastNodeRemoved;
      distanceTraveled+= Math.abs(startPosition - lastDistance);
      
      while(!positionsCopy.isEmpty()) {
         int findMinDistance = Integer.MAX_VALUE;
         
         for(Integer i : positionsWithTheLastOneRemoved) {
            if(Math.abs(lastDistance - i) < findMinDistance) {
               findMinDistance = Math.abs(lastDistance - i);
               lastNodeRemoved = i;
            }
         }
         
         distanceTraveled += findMinDistance;
         lastDistance = lastNodeRemoved;
         positionsCopy.remove(lastNodeRemoved);
         positionsWithTheLastOneRemoved = new ArrayList<Integer>(positionsCopy);
         
      }
      
      System.out.println("SSTF " +distanceTraveled);
   }
   
   public static void SCAN() {
      if(positions.isEmpty()) {
         System.out.println("SCAN 0");
         return;
      }
      
      ArrayList<Integer> positionsCopy = new ArrayList<Integer>(positions);
      int distanceTraveled = 0;
      
      if(direction == eDirection.LEFT) {
         positionsCopy.add(MIN_POSITION);
      }
      else {
         positionsCopy.add(MAX_POSITION);
      }
      
      Collections.sort(positionsCopy);
      
      if(direction == eDirection.LEFT && positionsCopy.get(positionsCopy.size() - 1) > startPosition) {
         distanceTraveled = 2*startPosition + positionsCopy.get(positionsCopy.size() - 1) - startPosition;
      }
      else if(direction == eDirection.RIGHT && positionsCopy.get(0) < startPosition) {
         distanceTraveled = 2 *(MAX_POSITION - startPosition) + startPosition - positionsCopy.get(0);
      } 
      else if(direction == eDirection.RIGHT && positionsCopy.get(positionsCopy.size() - 2) < startPosition) {
         distanceTraveled = 2*(MAX_POSITION - startPosition) + startPosition - positionsCopy.get(0);
      }
      else if(direction == eDirection.LEFT && positionsCopy.get(1) > startPosition) {
         distanceTraveled = 2*(startPosition) + positionsCopy.get(positionsCopy.size() - 1) - startPosition;
      }
      else if(direction == eDirection.RIGHT) {
         distanceTraveled = positionsCopy.get(positionsCopy.size() - 2) - startPosition;
      }
      else if(direction == eDirection.LEFT) {
         distanceTraveled = startPosition - positionsCopy.get(1);
      }
      else {
         System.out.println("MISSED A CASE REMOVE BE SUBMIT");
      }
      System.out.println("SCAN " + distanceTraveled);
   }
   
   public static void CSCAN() {
      if(positions.isEmpty()) {
         System.out.println("CSCAN 0");
         return;
      }
      ArrayList<Integer> positionsCopy = new ArrayList<Integer>(positions);
      int distanceTraveled = 0;
      
      positionsCopy.add(startPosition);
      
      Collections.sort(positionsCopy);
      
      int index = positionsCopy.indexOf(startPosition);
      
      if(index == 0 && direction == eDirection.RIGHT) {
         distanceTraveled = positionsCopy.get(positionsCopy.size() - 1) - startPosition;
      }
      else if(index == positionsCopy.size() - 1 && direction == eDirection.LEFT) {
         distanceTraveled = startPosition - positionsCopy.get(0);
      }
      else if(direction == eDirection.LEFT) {
         distanceTraveled = MAX_POSITION - (positionsCopy.get(index + 1) - startPosition);
      }
      else if(direction == eDirection.RIGHT) {
         distanceTraveled = MAX_POSITION - (positionsCopy.get(index - 1) - startPosition);
      }
      else {
         System.out.println("MISSED A CASE REMOVE BE SUBMIT");
      }
      System.out.println("CSCAN " + distanceTraveled);
      
   }
   
   public static void LOOK() {
      if(positions.isEmpty()) {
         System.out.println("LOOK 0");
         return;
      }
      ArrayList<Integer> positionsCopy = new ArrayList<Integer>(positions);
      int distanceTraveled = 0;   
      positionsCopy.add(startPosition);
      
      int index = positionsCopy.indexOf(startPosition);
      
      Collections.sort(positionsCopy);
      
      if(index == 0 || index == positionsCopy.size() - 1) {
         distanceTraveled = positionsCopy.get(positions.size() - 1) - positionsCopy.get(0); 
      }
      else if(direction == eDirection.RIGHT) {
         distanceTraveled = 2 * (positionsCopy.get(positionsCopy.size() - 1) - startPosition) + startPosition - positionsCopy.get(0);
      }
      else if(direction == eDirection.LEFT){
         distanceTraveled = (positionsCopy.get(positionsCopy.size() - 1) - startPosition) + 2 * (startPosition - positionsCopy.get(0));
      }
      else {
         System.out.println("ERROR MISSED A CASE REMOVE BEFORE TURN IN");
      }
      
      System.out.println("LOOK " + distanceTraveled);
      
   }
   
   public static void CLOOK() {
      if(positions.isEmpty()) {
         System.out.println("CLOOK 0");
         return;
      }
      
      int distanceTraveled = 0;   
      positions.add(startPosition);
      
      int index = positions.indexOf(startPosition);
      
      Collections.sort(positions);
      
      if(index == 0 || index == positions.size() - 1) {
         distanceTraveled = positions.get(positions.size() - 1) - positions.get(0); 
      }
      else if(direction == eDirection.RIGHT) {
         distanceTraveled = 2 * (positions.get(positions.size() - 1) - startPosition) + startPosition - positions.get(0);
      }
      else if(direction == eDirection.LEFT){
         distanceTraveled = (positions.get(positions.size() - 1) - startPosition) + 2 * (startPosition - positions.get(0));
      }
      else {
         System.out.println("ERROR MISSED A CASE REMOVE BEFORE TURN IN");
      }
      
      System.out.println("CLOOK " + distanceTraveled);
      
   }
   
}
