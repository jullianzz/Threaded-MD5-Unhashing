# Threaded MD5 Unhashing

Implementation of a concurrent MD5 unhasher that spawns a user-defined number of threads to uncrack MD5 input hashes. Assumptions: all simple hashes have an integral unhash (key) and all complex hashes can be uncracked using compound strings of the form "a;k;b" where a and b are keys to simple hashes a`<`b and k is some integer between a and b. 

*Note: This problem was created for educational purposes for the CS-350 course at Boston University (BU). All credit for the design of the problem goes to Renato Mancuso, an assistant professor at the department of Computer Science at BU.*

### Download and Run 
* Download the version in the main branch
* Navigate to local directory containing the files and compile all .java files
* Run Simulator main using ```java Pirate <param0> <param1> <param2>```

1. param0: path to input file containing hashes 
2. param1: number of processors, i.e., worker threads to parallelize the work
3. param2: timeout in milliseconds, i.e. the length of time a thread spends uncracking the hash

##### Sample Runs Using ```test_inputs``` folder
*Note: The solutions are also located in the ```test_inputs``` folder. The solution does not follow a strict-ordering. Try re-running and adjusting the timeout if the process hangs.*
java Pirate test_inputs/dathashlist_test.txt 4 2000
java Pirate test_inputs/dathashlist_test2.txt 4 3000

### Please Read
***ALL code written in this repository is under the authorship of Julia Zeng (@jullianzz), who belongs to the Electrical & Computer Engineering Department at Boston University. All code is written strictly for educational purposes and not authorized for redistribution or re-purposing in any domain or by any individual or enterprise.***