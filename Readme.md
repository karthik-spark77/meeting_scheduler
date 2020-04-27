## Meeting room scheduler solution.

#### Execution 

    input file: rooms.txt
    Main class: com.meeting.scheduler.MeetingScheduler
### Solution:

1. Read rooms.txt and creation List of string with the info like - start time,duration seconds available,meeting room,capacity,end time.
  Example : 09:00,120,9.527,4,11:00
2. Sort the List with start time.
3. Iterate the list and find if any meeting room is available with start time equals to input and duration of meeting less than duration of meeting room availablity.
4. If Step:3 returns no meeting room , then we have to split the meeting into multiple rooms.
5. Iterate the list and find if start of meeting is equals to meeting start time and sublist the list and find for another meeting room availability from the end time of the meeting room availability as strt time.
6. If step: 5 is null after iteration then , it means no meeting rooms are available even on split bases.
7. if Step:5 has result , then it prints list of meeting rooms(comma separated )


### Sample input and output

#### Example 1
input:

    1. Rooms.txt
    
        7.11,8,09:00,09:30,14:30,15:00
        8.23,6,10:00,11:00,14:00,15:00
        8.43,7,11:30,12:30,17:00,17:30
        9.511,9,09:30,10:30,12:00,12:15,15:15,16:15
        9.527,4,09:00,11:00,14:00,16:00
        9.547,8,10:30,11:30,13:30,15:30,16:30,17:30
    
    2. 5,8,09:00,12:30


Output:

    7.11,9.511,9.547,8.43

#### Example 2
input:

    1. Rooms.txt
    
        7.11,8,09:00,09:30,14:30,15:00
        8.23,6,10:00,11:00,14:00,15:00
        8.43,7,11:30,12:30,17:00,17:30
        9.511,9,09:30,10:30,12:00,12:15,15:15,16:15
        9.527,4,09:00,11:00,14:00,16:00
        9.547,8,10:30,11:30,13:30,15:30,16:30,17:30
    
    2. 5,8,09:30,10:30


Output:

    9.511
    