package com.example.ece381;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MixerActivity extends Activity{
	
	public static Mix theMix;
	private SeekBar Timeline;
	public static MixerCanvas mixerCanvas;
	private TextView textprog;
	private ListView songList;
	private Point p = new Point();
	public static ArrayList <String> songs = new ArrayList<String>();
	public static int idOfSongSelected = 0;
	public static boolean selSong = false; //true = a song is selected| false = mixelement is selected if songid =0
	public static int indexOfSel = -1;
	private ArrayAdapter<String> listAdapter;
	
	private Communication com = Communication.getInstance();
	private Database db = com.getDB();
	
	@Override		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mixer);
		setUP();
		refreshList();
	}

	public void onResume() {
		Log.v("PlaylistAcitivty Resume", "");
		super.onResume();
		refreshList();
		Command.syncOpenPlaylistsPanel();
	}
	
	public void onPause(){
		refreshList();
		mixerCanvas.invalidate();
	}
	
	public void setUP(){
		textprog = (TextView) findViewById(R.id.progress);
		textprog.setTextColor(Color.BLUE);
		songList = (ListView) findViewById(R.id.listView1);
		theMix = new Mix();
		addTimeline();
		String temp =  "0" + " / " + Integer.toString(Timeline.getMax());
		textprog.setText(temp);
		listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, songs);
		
		songList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adv, View v, int index,
					long it) {
				// TODO Auto-generated method stub
				selSong = true;
				indexOfSel = index;
				idOfSongSelected = theMix.getClipat(indexOfSel).getID();
				mixerCanvas.selIndex = -1;
				System.out.println( "When Clicked" + indexOfSel + " ID:" + idOfSongSelected  );
				mixerCanvas.invalidate();
			}
			
		});
		mixerCanvas = (MixerCanvas) this.findViewById(R.id.drawSurface);
		mixerCanvas.setBackgroundColor(Color.BLUE);
		mixerCanvas.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent mE) {
				p.x=(int) mE.getX();
				p.y=(int) mE.getY();
				p.y = decodePoint(p);
				if(selSong = true && idOfSongSelected != 0 && indexOfSel >= 0){
				mixerCanvas.addElement(p);
				//Timeline.setMax(theMix.lengthOf() + 3000 /*30 sec*/);
				}
				else{
					if(mixerCanvas.trySelElement(p)){
						idOfSongSelected = 0;
						selSong = false;
					}
				}
				

				
				return false;
			}
		});
		songList.setAdapter(listAdapter); 
		populateSongs();
		
	    
		//may add more init in here
		
	}
	
	private void addTimeline() {
		Timeline = (SeekBar) findViewById(R.id.scroll);
		Timeline.setMax(theMix.lengthOf() + 3000 /*30 sec*/);
		Timeline.setProgress(0);
		Timeline.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override 
			public void onProgressChanged(SeekBar Timeline, int progress,
										  boolean fromUser) {
				if(fromUser){
					theMix.seekTime(progress);
					Timeline.setProgress(progress);
					mixerCanvas.setProgress(progress);
					String temp = Integer.toString(progress) + " / " + Integer.toString(Timeline.getMax());
					textprog.setText(temp);
					mixerCanvas.invalidate();
				}
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
	}
	

	
	public int decodePoint(Point p){
		return (p.y/100);
	}
	
	public void populateSongs(){
		songs.add("Sheep1");
		songs.add("Sheep2");
		songs.add("sheep3");
		songs.add("Popsicle");
		/*
	    for(int i = 1; i <= db.getTotalSongs(); i++ ) {
			   songs.add(db.getSongs()[i].getSongName());
	    }*/
		int temp =200;
		for(String s: songs){
			int id = songs.indexOf(s)+1;
			theMix.addClip(new Clip(s, temp +(10* songs.indexOf(s)) , id));
			System.out.println( "when populating name: " + s + " ID: " + id);
		}
	}
	
	public void onPlay(){
		
	}
	
	public void onStopPlay(){
		
	}
	
	public void onSave(){
		
	}
	
	public void onDelete(){
		if(selSong = true && idOfSongSelected != 0 ){
			mixerCanvas.deleteAllInstanceOf(idOfSongSelected);}
		else if(selSong = false && idOfSongSelected ==0){
			mixerCanvas.deleteSelectedSeg();
		}
		
		else{
		selSong = false;
		idOfSongSelected = 0;
		}
	}
	
	public void onVolumeChange(){
		
	}
	
	public void onProperties(){
		
	}
	
	public int getSelectedSongId(String selectedValue) {
		int songid = 0;
		for(int i = 1; i < db.getTotalSongs()+1; i++) {
			if(selectedValue == db.getSongs()[i].getSongName() )
				songid = i;
			}
		return songid;
	}
	  
	
	public void refreshList() {
		//listAdapter.clear();
		//listAdapter.addAll(db.getSongsName());
		//listAdapter.notifyDataSetChanged();
		}
}
