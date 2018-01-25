********************************** KissMyPlace **********************************

Created by :
	
	Athmane BENTAHAR (3410322)

Description :

	    KissMyPlace is a game for evaluating our geographic knowledge, by finding a place on the globe according to a given indication.

Activities :

	   MainActivity

Fragments :
	  
	  FirstFragment		(Home page)
	  ProfileFragment	(Manage player profiles)
	  ScoreFragment		(Display & reorder scores)
	  PlacesFragment    (Display last game places list)
	  StreetFragment 	(StreetView)
	  MapFragment		(Map)

Structure :

	  +-------------------------+
	  |      MainActivity       |
	  +-------------------------+
	  | +---------------------+ |
	  | |   ProfileFragment   | |
	  | +---------------------+ |
	  | |                     | |
	  | |                     | |
	  | |                     | |
	  | |   +--------------+  | |
	  | |   |    pixel...  |  | |
	  | |   +--------------+  | |
	  | |   +--------------+  | |
	  | |   |     SAVE     |  | |
	  | |   +--------------+  | |
	  | |                     | |
	  | |                     | |
	  | |                     | |
	  | +---------------------+ |
	  +-------------------------+
                   /|\
                    |
                    |
      +-------------|-----------+           +-------------------------+
      |      MainAct)ivity      |           |      MainActivity       |
      +-------------|-----------+           +-------------------------+
      | +-----------|---------+ |           | +---------------------+ |
      | |    FirstFr)agment   | |           | |                     | |
      | +-----------|---------+ |           | |                     | |
      | |           |         | |           | |                     | |
      | |   +-------|------+  | |           | |    StreetFragment   | |
      | |   |  New Profile |  | |           | |                     | |
      | |   +--------------+  | |           | |                     | |
      | |   |    NOVICE    |--|-|-          | |                     | |
      | |   +--------------+  | |  \  level | +---------------------+ |
      | |   |    MEDIUM    |--|-|- -------> | |                     | |
      | |   +--------------+  | |           | |                     | |
      | |   |    EXPERT    |--|-|- /        | |                     | |
      | |   +--------------+  | |           | |     MapFragment     | |
      | |   |    SCORES    |  | |           | |                     | |
      | |   +------|-------+  | |           | |                     | |
      | +----------|----------+ |           | +---------------------+ |
      +------------|------------+           +-------------------------+
                   |
                  \|/
      +-------------------------+           +-------------------------+
      |      MainActivity       |           |      MainActivity       |
      +-------------------------+           +-------------------------+
      | +---------------------+ |           | +---------------------+ |
      | |    ScoreFragment    | |           | |    PlacesFragment   | |
      | +---------------------+ |           | +---------------------+ |
      | |    +-----------+    | |           | |+----------+--------+| |
      | |    |   Places  |----|-|---------> | || NY       |  112km || |
      | |    +-----------+    | |           | |+----------+--------+| |
      | |                     | |           | || Paris    |  247km || |
      | |+----+-----+--------+| |           | |+----------+--------+| |
      | ||toto| 550 |18/01/18|| |           | || Tokyo    | 1359km || |
      | |+----+-----+--------+| |           | |+----------+--------+| |
      | ||titi| 350 |15/01/18|| |           | || Venise   |   73km || |
      | |+----+-----+--------+| |           | |+----------+--------+| |
      | ||tutu| 100 |20/01/18|| |           | || Rio      |  718km || |
      | |+-------------------+| |           | |+----------+--------+| |
      | |                     | |           | |           |  Done  || |
      | +---------------------+ |           | +-----------+--------++ |
      +-------------------------+           +-------------------------+


Scores :

       +------------+------------+------------+------------+------------+------------+
       |   Level    |  Nb places |  d < 10km  |  d < 100km | d < 200km | d > 500km |
       +------------+------------+------------+------------+------------+------------+
       |   NOVICE   |      5     |     +400   |    +200    |     +50    |      +0    |
       +------------+------------+------------+------------+------------+------------+
       |   MEDIUM   |      7     |     +300   |    +200    |     +40    |     -30    |
       +------------+------------+------------+------------+------------+------------+
       |   EXPERT   |     10     |     +300   |    +100    |     +30    |     -50    |
       +------------+------------+------------+------------+------------+------------+

Implemented Options :

	    Profiles		(Use, Create & Delete profiles)
	    Reorder scores	(By date & by profile)
	    Rotation		(Keep game status across rotations)
	    List places		(List last game places & distances)

OpenData :

	 Started GenerateNewPlaces implementation using OpenDate but not finished.
