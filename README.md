**************************KissMyPlace *********************

Created by : Athmane BENTAHAR (3410322)

Description :
	    KissMyPlace is a game for evaluating our geographic knowledge, by finding a place on the globe according to a given indication.

Activities :
	   MainActivity

Fragments :
	  FirstFragment		(Home page)
	  ProfileFragment	(Manage player profiles)
	  ScoreFragment		(Display & reorder scores)
	  StreetFragment 	(StreetView)
	  MapFragment		(Map)

Scores :
       +------------+------------+------------+------------+------------+------------+
       |   Level    |  Nb places |  d < 10km  |  d < 100km | d < 1000km	| d > 1000km |
       +------------+------------+------------+------------+------------+------------+
       |   Level    |      5     |     +400   |    +200    |     +50	|      +0    |
       +------------+------------+------------+------------+------------+------------+
       |   Level    |      7     |     +300   |    +200    |	 +40 	|     -30    |
       +------------+------------+------------+------------+------------+------------+
       |   Level    |     10     |     +300   |    +100    |	 +30	|     -50    |
       +------------+------------+------------+------------+------------+------------+

Implemented Options :
	    Profiles		(Use &Create & Delete profiles)
	    Reorder scores	(By date & by profile)
	    Rotation		(Keep game status across rotations)
	    List places		(List last game places & distances)

OpenData :
	 Started GenerateNewPlaces implementation using OpenDate but not finished.
