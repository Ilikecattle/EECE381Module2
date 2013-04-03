package com.example.ece381;

import java.util.ArrayList;

//Clips are mutable Android objects representations of sound clips that are physically on the DE2. Manipulation of Clips
//in Android will directly or indirectly affect the physical representations on the DE2


public class Clip {
	private int ID;
	private String name;
	private int numberOfPlays = 0;
	private ArrayList<Integer> startTimes = new ArrayList<Integer>();
	ArrayList<Integer> ofTheSet = new ArrayList<Integer>();
	private int length;
	private int volume;
	private int location; 
	
	//TODO: DE2 implementation of clips
	
	//defualt constructor
	public Clip(){
		name= "default";
		numberOfPlays = 0;
	}
	
	//constructor with name and length of song
	public Clip(String name, int length){
		this.name = name;
		this.length = length;
	}
	
	//conctructor to add 2 clips together
	public Clip(Clip a, Clip b){
		name = "new appended clip";
		length =a.length + b.length;
	}
	
	//constructor to make a new sub clip 
	public Clip(Clip a, int start, int end){
		name = "newsubclip";
		length = end - start;
	}
	
	//create a new clip from a old clip by chopping off the ending starting from location
	public Clip split(int location){
		if(length > location && location > 0){
			return new Clip("new clip", length - location);
		}
		else{
			return null;
		}
	}
	
	//set up a clip to be played at a time
	public void setToPlayAt(int time){
		numberOfPlays++;
		startTimes.add(time);
		this.ofTheSet.add(1);
	}
	
	public void setToPlayAt(int time, int set){
		numberOfPlays++;
		startTimes.add(time);
		this.ofTheSet.add(set);
	}
	
	//remove a play time at an index
	public void removePlayAt(int index){
		startTimes.remove(index);
		this.ofTheSet.remove(index);
		numberOfPlays--;
	}
	
	//chop current clip from both ends at start to end
	public void cutClip(int start, int end){
		if(end - start < length){
			//TODO Chop clip on DE2 
			length = end - start;
		}
		else{
			System.out.println("did not cut");
		}
	}
	
	//attach another clip to the end of current clip
	public void appendClip(Clip other){
		//TODO append on DE2
		if(this.numberOfPlays ==0){
		length += other.length;
		}
		
		else{
			new Clip(this, other);
		}
	}
	
	//merge the sound of 2 clips
	public Clip mergeClips( Clip other){
		//TODO merge sounds on DE2
		Clip temp = (other.length>this.length) ? other : this;
		return temp;
	}
	
	public void wipePlayData(){
		numberOfPlays = 0;
		this.ofTheSet.clear();
		startTimes.clear();
	}

	public int getLength() {
		return length;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public ArrayList<Integer> timesUsed(){
		return (ArrayList<Integer>) startTimes.clone();
	}
	
	public void changePlayTime(int index, int time){
		startTimes.add(index, time);
		startTimes.remove(index ++);
	}
}
