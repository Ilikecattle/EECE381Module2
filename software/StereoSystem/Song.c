/*
 * Song.c
 *
 *  Created on: 2013-03-07
 *      Author: danny
 */
#include "Song.h"

struct Song* initSong(char* songname) {
	struct Song* this = (struct Song*)malloc(sizeof(struct Song));
	this->song_name = songname;
	this->isCached = false;
	this->pos = this->size = this->volume = 0;
	return this;
}

void killSong(struct Song** this) {

}
