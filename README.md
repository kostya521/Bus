# Bus Route Challenge

**DataFileParserImpl** - reads input data file, converts its content to integer values and stores
in a Map where key is route id, value is a list of stations. Order of stations is not changed.

**BusStationToRouteMapperImpl** - designed to convert Map created by DataFileParser to map which suites 
to current task.

Lets imagine case when we need to implement new endpoint to search direct route taking into account
order of stations. In this case we need implement new DataMapper which will transform Map created
by DataFileParser to suitable to this task collection.
