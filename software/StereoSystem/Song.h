/*
 * Song.h
 *
 *  Created on: 2013-03-06
 *      Author: danny
 */

#ifndef SONG_H_
#define SONG_H_
#include "Global.h"
#define SONGNAME_LENGTH 31
struct Song {
	struct Sound* sound;
	char song_name[SONGNAME_LENGTH];
	int volume;
	int pos;
	int size;
	bool isCached;
	int id;
	//struct Song* next;
	//struct Song* prev;
};

struct Song* initSong(char*);
void killSong(struct Song**);
void setSongName(struct Song*, char*);
void setSongId(struct Song*, int);
void playSong(struct Song*, float, int, int);
void stopSong(struct Song*);
void pauseSong(struct Song*);
void resumeSong(struct Song*);
void unloadSong(struct Song*);
void loadSong(struct Song*);
#endif /* SONG_H_ */
