package com.meeting.scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;


public class MeetingScheduler {
    public static void main(String[] args) {

        String input = "5,8,09:00,12:30";

        try {

            String[] inputArr=input.split(",");

            if(inputArr.length != 4){
                System.exit(0);
            }

            int teamSize = Integer.parseInt(inputArr[0]);
            String floor = inputArr[1];
            String startTime = inputArr[2];
            long duration = getTimediff(inputArr[2],inputArr[3]);



            File myObj = new File("rooms.txt");
            Scanner myReader = new Scanner(myObj);

            List<String> roomList = new ArrayList<String>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arr = data.split(",");

                if(arr.length>3){

                    for(int i=2;i<arr.length;i++){
                        long diff = getTimediff(arr[i],arr[i+1]);
                        roomList.add(arr[i]+","+diff+","+arr[0]+","+arr[1]+","+arr[i+1]);
                        i++;
                    }

                }

            }
            myReader.close();
            Collections.sort(roomList);

            System.out.println(roomList);

            String roomNumber = getAvailableMeetingRoom(teamSize,floor,startTime,duration,roomList);
            // If single meeting room is not available then split the meeting into multiple available rooms.
            if(roomNumber == null) {
                roomNumber = getAvailableMeetingRooms(teamSize,floor,startTime,duration,roomList);
            }

            System.out.println(roomNumber);


        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static long getTimediff(String s1,String s2){
        String dateStart = "04/26/2020 "+s1;
        String dateStop = "04/26/2020 "+s2;

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        Date d1 = null;
        Date d2 = null;
        long diffMinutes=0;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();
            diffMinutes = diff / (60 * 1000);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffMinutes;

    }

    public static String getAvailableMeetingRoom(int teamSize,String floor,String startTime,long duration,List<String> roomList){

        String roomNumber = null;

        for(String room:roomList){
            String[] arr = room.split(",");

            if(Integer.parseInt(arr[3]) < teamSize){
                continue;
            }

            if(startTime.equals(arr[0]) && duration <= Integer.parseInt(arr[1])) {
                    roomNumber = arr[2];
            }
            if(roomNumber != null && roomNumber.startsWith(floor)){
                break;
            }
        }
        return roomNumber;

    }

    public static String getAvailableMeetingRooms(int teamSize,String floor,String startTime,long duration,List<String> roomList) {

        String roomNumber = null;

        for(String room:roomList){
            String[] arr = room.split(",");

            if(Integer.parseInt(arr[3]) < teamSize){
                continue;
            }

            // && duration <= Integer.parseInt(arr[1])

            if(startTime.equals(arr[0]) ) {
                roomNumber = arr[2];
                String secondM = getAvailableMeetingRoomsSub(teamSize,floor,arr[4],duration-Integer.parseInt(arr[1]),roomList.subList(roomList.indexOf(room),roomList.size()));
                if( secondM != null) {
                    roomNumber=roomNumber+","+secondM;
                }else{
                    roomNumber = null;
                }

            }
        }
        return roomNumber;
    }

    public static String getAvailableMeetingRoomsSub(int teamSize,String floor,String startTime,long duration,List<String> roomList) {

        String roomNumber = null;

        for(String room:roomList){
            String[] arr = room.split(",");

            if(Integer.parseInt(arr[3]) < teamSize){
                continue;
            }

            if(startTime.equals(arr[0])) {
                if (duration - Integer.parseInt(arr[1]) <= 0){
                    roomNumber = arr[2];
                }else{
                    roomNumber = arr[2];
                    if(roomList.size()>1){
                        roomNumber=roomNumber+","+getAvailableMeetingRoomsSub(teamSize,floor,arr[4],duration-Integer.parseInt(arr[1]),roomList.subList(roomList.indexOf(room),roomList.size()));
                    }
                }
            }

        }
        return roomNumber;
    }

}



