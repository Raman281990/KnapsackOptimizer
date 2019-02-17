
CREATE TABLE optimizer.tasks
(
task_id varchar(20) NOT NULL,
task_status varchar(10) NOT NULL,
task_submitted_time int(11) NULL ,
task_started_time int(11) NULL,
task_completed_time int(11) NULL,
PRIMARY KEY(task_id)

);

CREATE TABLE optimizer.knapsack
(
knapsack_id int  NOT NULL AUTO_INCREMENT,
capacity int(10) NOT NULL,
weights TEXT NOT NULL ,
weight_values TEXT NOT NULL,
solution TEXT NULL,
task_id varchar(20) ,
PRIMARY KEY(knapsack_id),
FOREIGN KEY(task_id) REFERENCES tasks(task_id)
);