package PJ3;

import java.util.*;

import javax.swing.JFrame;

import java.io.*;

// You may add new functions or data fields in this class 
// You may modify any functions or data members here
// You must use Customer, Cashier and CheckoutArea classes
// to implement SuperMart simulator

class SuperMart{
	

  // input parameters
  private int numCashiers, customerQLimit;
  private int maxServiceTime, chancesOfArrival;
  private int simulationTime, dataSource;
  boolean check;

  // statistical data
  private int numGoaway, numServed, totalWaitingTime, customersarrive;

  // internal data
  private int counter;	             // customer ID counter
  private CheckoutArea checkoutarea; // checkout area object
  private Scanner dataFile;	     // get customer data from file
  private Random dataRandom;	     // get customer data using random function

  // most recent customer arrival info, see getCustomerData()
  private boolean anyNewArrival;  
  private int serviceTime;
  
  //create my own
  Cashier freecashier, busycashier;
  Customer customer;
  int cashierID;
  String file;
  private int  timeWaited;
  
  // initialize data fields
  private SuperMart()
  {
	  numGoaway = 0;
	  numServed = 0;
	  totalWaitingTime = 0;
	  cashierID=1;
	  counter=1;
	  timeWaited=0;
	  dataRandom= new Random();
  }
  
  //generate data for chance of new customer arrival
  public static int getPoisson(int lambda) {
	  double L = Math.exp(-lambda);
	  double p = 1.0;
	  int k = 0;

	  do {
	    k++;
	    p *= Math.random();
	  } while (p > L);

	  return k - 1;
	}
  
public double getExp(double lambda) {
          return (-(Math.log(Math.random()) / lambda));
      }
  

  private void setupParameters()
  {
        // read input parameters from user
        // setup dataFile or dataRandom
	    Scanner userParameter = new Scanner(System.in);
		check=true;
	    
	    //////////////create max simulation time variable
	    while(check){
		    System.out.print("Enter simulation time (max 1000): ");
		    simulationTime = userParameter.nextInt();
		    if (simulationTime> 1000 || simulationTime<0){
		    	System.out.println("Try again");
		    }
		    else{
			    check=false;
			    System.out.print("\n");
		    }
		    }
	    
	    /////////////////// create max checkout time variable
	    check=true;
	    while(check){
		    System.out.println("Enter maximum checkout time for customer (max 500): ");
		    maxServiceTime = userParameter.nextInt();
		    if (maxServiceTime> 500 || maxServiceTime<0){
		    	System.out.println("Try again");
		    }
		    else{
			    check=false;
		    }
		    System.out.print("\n");
	    }
	    
	    ////////////////variable for the prob of a new customer arriving
	    check =true;
	    while(check){
		    System.out.println("Enter the probability of a new customer arriving (1-100): ");
		    chancesOfArrival= userParameter.nextInt();
		    if (chancesOfArrival> 100|| chancesOfArrival<1){
		    	System.out.println("Try again");
		    }
		    else{
			    check=false;
		    }
		    System.out.print("\n");
	    }
	    
	    ////////////create variable for the number of cashiers
	    check=true;
	    while(check){
		    System.out.println("Enter the number of cashiers (max 10): ");
		    numCashiers = userParameter.nextInt();
		    if (numCashiers> 10 || numCashiers<0){
		    	System.out.println("Try again");
		    }
		    else{
			    check=false;
		    }
		    System.out.print("\n");
	    }
	    
	    //////////create variable for the number of cashiers
	    check=true;
	    while(check){
		    System.out.println("Enter customer queue limit (max 50): ");
		    customerQLimit = userParameter.nextInt();
		    if (customerQLimit> 50 || customerQLimit<0){
		    	System.out.println("Try again");
		    }
		    else{
			    check=false;
		    }
		    System.out.print("\n");
	    }
	    
	    ////////////////////give user options for file or randomly generated data
	    check=true;
	    while(check){
	    	System.out.print("Enter 0 for Random data or 1 for data from file:");
	    	dataSource = userParameter.nextInt();
		    if (dataSource!= 0 && dataSource!= 1){
		    	System.out.println("Try again");
		    }
		    else{
			    check=false;
		    }
	    }
	    
	    ////////Setup the data
	    //String fileName = userParameter.nextLine();
	    switch(dataSource){
	      case 0:
	        System.out.println("Generating the requested random data");
	        break;
	        
	      case 1:
				System.out.println("Enter filename:");//this asks the user to enter a filename
				String file;
				Scanner scanner = new Scanner(System.in);
				file = scanner.nextLine();
	        try{
	        	 dataFile = new Scanner(new File(file)); //scan the file
	        }
	        catch (FileNotFoundException e ){
	        	e.printStackTrace();
	        }

	        break;
	      default:
	        break;
	    }
	    userParameter.close();
  }
  
  
  // file C:\Users\Court\Documents\spring 2016\csc 220\pj3-s2016-stud\DataFile.txt
  private void getCustomerData()
  {
	// get next customer data : from file or random number generator
	// set anyNewArrival and serviceTime
	  if (dataSource == 0)
	  {
		serviceTime = dataRandom.nextInt(maxServiceTime)+1;
		anyNewArrival = ((dataRandom.nextInt(100)+1) <= chancesOfArrival); // new Customer created when anyNewArrival is true
		//serviceTime= (int) getExp(chancesOfArrival*100);
        //int poissonProb= getPoisson(chancesOfArrival*100);
		//if(poissonProb !=0)
		if((dataRandom.nextInt(100)+1)<= chancesOfArrival)
			 anyNewArrival = true;
		else 
			 anyNewArrival = false;
	  }
	  
	  else
	  {
			int data1 = dataFile.nextInt(); // get the data for anyNewArrival
			int data2 = dataFile.nextInt(); //get the data for checkoutTime
			anyNewArrival = (((data1%100)+1)<= chancesOfArrival);
			serviceTime = (data2%maxServiceTime)+1;
		  
	  }
	 // dataFile.close();
  }

  private void doSimulation()
  {
     // add statements
	// Initialize CheckoutArea
	  checkoutarea = new CheckoutArea(numCashiers, customerQLimit);
	  System.out.println("\n");
	  System.out.println("\t*** Start Simulation ***\t");
	  System.out.println("\n");

	// Time driver simulation loop
  	for (int currentTime = 1; currentTime < simulationTime; currentTime++) {

    		// Step 1: any new customer enters the checkout area?
    		getCustomerData();

    		if (anyNewArrival) {

      		    // Step 1.1: setup customer data
    			System.out.println("---------------------------------------------");
    			System.out.println("Time : " + currentTime );
    			customer = new Customer(counter, serviceTime, currentTime );
    			System.out.println("Customer #" + (customer.getCustomerID()) 
    					+ " has max transaction time of " + customer.getServiceTime() + " min");
    			customersarrive++;
      		   
    			
    			// Step 1.2: check customer waiting queue too long?
    			if (checkoutarea.isCustomerQTooLong()){
    				System.out.println("Customer #" + customer.getCustomerID() + " goes away due to long customer queue");
    				numGoaway++;
    			}
    			else {
    				System.out.println("Customer #" + customer.getCustomerID() + " is waiting in line");
    				checkoutarea.insertCustomerQ(customer);
    			}
    			
    			counter++;
    		    
    		} 
    		else {
    			System.out.println("---------------------------------------------");
    		     System.out.println("Time : " + currentTime);
    		     System.out.println("\tNo new customer");
    		}

    		// Step 2: free busy cashiers, add to free cashierQ
    		if (!checkoutarea.emptyBusyCashierQ() && (checkoutarea.peekBusyCashierQ().getEndBusyTime() == currentTime)){
    			while(!checkoutarea.emptyBusyCashierQ() && (checkoutarea.peekBusyCashierQ().getEndBusyTime() == currentTime)){
    				busycashier = checkoutarea.removeBusyCashierQ();
    				System.out.println("Customer #" + busycashier.busyToFree().getCustomerID() + " is done");
    		        System.out.println("Cashier  #"+ busycashier.getCashierID()  + " is free");
    		        checkoutarea.insertFreeCashierQ(busycashier);
    		       
    		    }
    		}
    		
    		// Step 3: get free cashiers to serve waiting customers 
		    if (!checkoutarea.emptyFreeCashierQ() && !checkoutarea.emptyCustomerQ()){
		    	while (!checkoutarea.emptyFreeCashierQ() && !checkoutarea.emptyCustomerQ()){
		    	    customer = checkoutarea.removeCustomerQ();
    				System.out.println("Customer #" + customer.getCustomerID() + " gets a cashier");
    				freecashier = checkoutarea.removeFreeCashierQ();
    				System.out.println("Cashier #" + freecashier.getCashierID()  + " will checkout customer " + customer.getCustomerID() + " for " + customer.getServiceTime() + " min");
    				freecashier.freeToBusy(customer, currentTime);
    				checkoutarea.insertBusyCashierQ(freecashier);
    				timeWaited = currentTime - customer.getArrivalTime(); 
    				totalWaitingTime = totalWaitingTime + timeWaited; 
    				numServed++;
		    	}
		    } // end simulation loop
  	}
  }

  private void printStatistics()
  {
	// print out simulation results
	// see the given example in project statement
        // you need to display all free and busy cashiers 
	  
		System.out.println("\n");
		System.out.println("============================================");
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("          End of Simulation Report");
		System.out.println("\n");
		System.out.println("\t#total arrival customers : " + customersarrive);
		System.out.println("\t#customers gone-away : " + numGoaway);
		System.out.println("\t#customers served : " + numServed   );
		System.out.println("\n");
	    System.out.println("\t***Current Cashier's information***\t");
		checkoutarea.printStatistics();
		System.out.println("\n");
		System.out.println("\tTotal waiting time : " + totalWaitingTime );
		System.out.printf("\tAverage waiting time : %.3f%n\n", (totalWaitingTime*1.0)/numServed);
		System.out.println("\n");
		System.out.println("\tFree cashier's information\t");
		
		if (checkoutarea.emptyFreeCashierQ()){
			System.out.println("\tThere aren't any free cashiers!\t");
			System.out.println("\n");
		}
		while(!checkoutarea.emptyFreeCashierQ()){
			freecashier = checkoutarea.removeFreeCashierQ();
			freecashier.setEndFreeTime (simulationTime);
			freecashier.printStatistics(); 
			System.out.println("\n");
		}
		
		System.out.println("\n");
		System.out.println("\tBusy Cashier's information\t");
		if (checkoutarea.emptyBusyCashierQ()){
			System.out.println("\tThere weren't any busy Cashiers!\t");
			System.out.println("\n");
		}
		while(!checkoutarea.emptyBusyCashierQ()){
			busycashier =checkoutarea.removeBusyCashierQ();
			busycashier.setEndFreeTime(simulationTime);
			busycashier.printStatistics(); 
			System.out.println("\n");
		}
  }
  
  

  // *** main method to run simulation ****

  public static void main(String[] args) {
   	SuperMart runSuperMart=new SuperMart();
   	runSuperMart.setupParameters();
   	runSuperMart.doSimulation();
   	runSuperMart.printStatistics();
  }

}
