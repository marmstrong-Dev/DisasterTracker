# Disaster Tracker
## Project One - Big Data / Scala

<br />

### Overview
Using NASAs EONet Event API, I can pull in data to allow a simplified view of natural disasters around the USA and allow for commentary from users. This output will be visible from the terminal to an end user.

This application will:
* Pull natural disaster data from NASA EONet API
* Aggregate data into HDFS through Spark
* Allow for seeing open natural events
* Add ability to add comments for specific events
* Allow for deleting comments or editing
* Allow for disabling users(Admin only)
* Allow for updating profile information

### Analysis Questions

* 1.) Where do the natural disasters occur (filter by location)?
* 2.) Are they ongoing or ended?
* 3.) What type of natural disaster occurred?
* 4.) Which locations had the most disasters (by category)?
* 5.) Where was the start / end of the disaster?
* 6.) Source for more information about the event.

### Tech Stack Used

* Hadoop (MapReduce, HDFS, Hive)
* YARN
* Scala 2.11.12
* OpenJDK Version 1.8.0
* Akka HTTP
* NASA EONet API