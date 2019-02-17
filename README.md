# KnapsackOptimizer


Project structure
KnapsackOptimizer
 .
    ├── Dockerfile              	# Docker file for creating images
    ├── docker-compose.yml           # commands to build run both /knapsack-optimizer and /mysql together in isolated environment
    ├── README.md                    # Project related information here. 
    └── mysql_Scripts                  # Mysql scripts for creating tables 
	
	
knapsack-optimizer-app -
Runs on port 6543 and context path /kanpsack supports following endpoints

Following Rest endpoints are exposed:

1) curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"problem": {"capacity": 15, "weights": [12,1,2,1,4], "values": [4,2,2,1,10]}}' 'http://localhost:6543/knapsack/tasks'

This endpoint is used for submitting the Task.

2) curl -X GET --header 'Accept: application/json' 'http://localhost:6543/knapsack/tasks/T27_840'

This endpoint is used for checking the status of the task.

3) curl -X GET --header 'Accept: application/json' 'http://localhost:6543/knapsack/solutions/T27_840'

This endpoint is used for retrieving the solution.

4) curl -X GET --header 'Accept: application/json' 'http://localhost:6543/knapsack/admin/tasks'

This endpoint is used for retrieving all the tasks present in the Optimizer Application.

5) curl -X POST \
  http://localhost:6543/knapsack/shutdown \
  -H 'authorization: Basic YWRtaW46YWRtaW4=' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 4bb23ef9-bcbf-43e6-e0cb-002200131dc8'

This endpoint is used for shutting down the service.

Steps to run the Application

1) Clone the code from Github repository
2) Ensure docker is running on your system
3) from the root of the project run command:
    docker-compose up

This will start knapsack-optimizer-app and mysql-docker-container.

Open the swagger URL:
http://localhost:6543/knapsack/swagger-ui.html

you can access the REST API endpoint from Swagger.

Commands to check container that are running on docker:
docker ps

You can check tables inside Mysql container using following commands:

docker exec -it mysql_container_name mysql -uroot -p

use optimizer;
show tables;

