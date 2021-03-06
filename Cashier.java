package PJ3;

class Cashier {

   // cashier id and current customer which is served by this cashier 
   private int cashierID;
   private Customer serveCustomer;

   // start time and end time of current interval
   private int startTime;
   private int endTime;

   // for keeping statistical data
   private int totalFreeTime;
   private int totalBusyTime;
   private int totalCustomers;

   // Constructor
   Cashier()
   {
        // add statements
	   cashierID=1;
	   serveCustomer= new Customer();
	   startTime=0;
	   endTime=0;
	   totalFreeTime=0;
	   totalBusyTime=0;
	   totalCustomers=0;

   }


   // Constructor with cashier id
   Cashier(int cashierId)
   {
        // add statements
	   cashierID=cashierId;
   }

   // accessor methods

   int getCashierID() 
   {
	return cashierID;
   }

   Customer getCurrentCustomer() 
   {
        // add statements
        return serveCustomer;
   }

   // Transition from free interval to busy interval
   void freeToBusy (Customer serveCustomer, int currentTime)
   {
  	// goal  : switch from free interval to busy interval
  	//         i.e. end free interval, start busy interval 
        //              to serve a new customer
        //
  	// steps : update totalFreeTime
  	// 	   set startTime, endTime, serveCustomer, 
  	// 	   update totalCustomers
	   
	   	totalFreeTime = totalFreeTime + (currentTime - startTime);
        startTime = currentTime; 
        endTime = currentTime + serveCustomer.getServiceTime() ;
        this.serveCustomer = serveCustomer;
        totalCustomers++;

        // add statements
   }

   // Transition from busy interval to free interval
   Customer busyToFree ()
   {
  	// goal  : switch from busy interval to free interval
  	//         i.e. end busy interval to return served customer, 
        //              start free interval 
   	// 
  	// steps     : update totalBusyTime 
  	// 	       set startTime 
  	//             return serveCustomer
	   
       totalBusyTime = totalBusyTime + serveCustomer.getServiceTime();
       startTime = endTime;
       return serveCustomer;
        // add statements
   }

   // Return end busy clock time, use in priority queue
   int getEndBusyTime() 
   {

	   return endTime;
        // add statements

   }

   // For free interval at the end of simulation, 
   // update totalFreeTime 
   void setEndFreeTime (int endsimulationtime)
   {
  	// for free interval at the end of simulation:
  	// set endTime, update totalFreeTime
	   endTime = endsimulationtime;
	   totalFreeTime = totalFreeTime + (endsimulationtime - startTime);

        // add statements
   }

   // For busy interval at the end of simulation, 
   // update totalBusyTime 
   void setEndBusyTime (int endsimulationtime)
   {
  	// for busy interval at the end of simulation:
  	// set endTime, update totalBusyTime
	   endTime = endsimulationtime;
	   totalBusyTime = totalBusyTime + (endsimulationtime - startTime);
        // add statements
   }

   // functions for printing statistics :
   void printStatistics () 
   {
  	// print cashier statistics, see project statement

  	System.out.println("\t\tCashier ID             : "+cashierID);
  	System.out.println("\t\tTotal free time        : "+totalFreeTime);
  	System.out.println("\t\tTotal busy time        : "+totalBusyTime);
  	System.out.println("\t\tTotal # of customers   : "+totalCustomers);
  	if (totalCustomers > 0)
  	    System.out.format("\t\tAverage checkout time  : %.2f%n\n",(totalBusyTime*1.0)/totalCustomers);
   }

   public String toString()
   {
	return "CashierID="+cashierID+":startTime="+startTime+
               ":endTime="+endTime+">>serveCustomer:"+serveCustomer;
   }

   public static void main(String[] args) {
        // quick check
        Customer mycustomer = new Customer(1,15,5);
	Cashier mycashier = new Cashier(5);
        mycashier.freeToBusy (mycustomer, 12);
        System.out.println(mycashier);

   }

};

