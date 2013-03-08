/*
 * CommandScheduler.h
 *
 *  Created on: 2013-03-08
 *      Author: danny
 */

#ifndef COMMANDSCHEDULER_H_
#define COMMANDSCHEDULER_H_
#include "Global.h"

struct CmdScheduler {
	struct Queue* cmds;
};

struct CmdScheduler* initCmdScheduler();
void cmdProcessing(struct CmdScheduler*);
#endif /* COMMANDSCHEDULER_H_ */
