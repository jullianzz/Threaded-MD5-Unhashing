# Threaded MD5 Unhashing

Implementation of a concurrent MD5 unhasher that spawns a user-defined number of threads to uncrack MD5 input hashes. Assumptions: all simple hashes have an integral unhash (key) and all complex hashes can be uncracked using compound strings of the form "a;k;b" where a and b are keys to simple hashes a`<`b and k is some integer between a and b. On the first round of uncracking (unhashing and uncracking will be used interchangeably) all **simple** hashes with integer solutions will be solved within the timeout frame. These simple unhashes are simply performed using a for-loop that iterates through every integer value till the hash of that integer matches the hash to uncrack. All hashes that result in a thread timeout error are **complex** hashes and have a non-integer solution of the form "a;k;b". The values a and b are the stored keys to simple hashes, where a`<`b and k is some integer between a and b. 

The solution to this problem touches upon concepts of concurrency and synchronization with multi-threading to parallelize requests and semaphores to protect access/modification to shared data. 

*Note: This problem was created for educational purposes for the CS-350 course at Boston University (BU). All credit for the design of the problem goes to Renato Mancuso, an assistant professor at the department of Computer Science at BU.*

### Download and Run 
* Download the version in the main branch
* Navigate to local directory containing the files and compile all .java files
* Run Simulator main using ```java Pirate <param0> <param1> <param2>```

1. param0: path to input file containing hashes 
2. param1: number of processors, i.e., worker threads to parallelize the work
3. param2: timeout in milliseconds, i.e. the length of time a thread spends uncracking the hash

* ##### Sample Runs Using ```test_inputs``` folder
* *Note: Both (2) input files and their corresponding (2) solutions are located in the ```test_inputs``` folder. The solution does not follow a strict-ordering. Try re-running and adjusting the timeout if the process hangs.*
java Pirate test_inputs/dathashlist_test.txt 4 2000
java Pirate test_inputs/dathashlist_test2.txt 4 3000

### Please Read
***ALL code written in this repository is under the authorship of Julia Zeng (@jullianzz), who belongs to the Electrical & Computer Engineering Department at Boston University. All code is written strictly for educational purposes and not authorized for redistribution or re-purposing in any domain or by any individual or enterprise.***