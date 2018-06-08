CREATE TABLE WORKSHIFT(
	masterCode decimal(10,0) NOT NULL PRIMARY KEY,
	worktimeStart decimal(10,0) NOT NULL,
	worktimeEnd decimal(10,0) NOT NULL,
	overtime1Start decimal(10,0) NOT NULL,
	overtime1End decimal(10,0) NOT NULL,
	overtime2Start decimal(10,0) NOT NULL,
	overtime2End decimal(10,0) NOT NULL,
	resttime1Start decimal(10,0) NOT NULL,
	resttime1End decimal(10,0) NOT NULL,
	resttime2Start decimal(10,0) NOT NULL,
	resttime2End decimal(10,0) NOT NULL
);

INSERT INTO WORKSHIFT values(1,510,1050,1080,1320,1320,1440,720,780,1080,1110);