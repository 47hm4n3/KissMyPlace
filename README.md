********************************** KissMyPlace **********************************

Created by :
	
        Athmane BENTAHAR (3410322)
        Zakaria	AMRI (3100248)

Description :

        KissMyPlace is a game for evaluating our geographic knowledge,
        by finding a place on the globe according to a given indication hidden in a street view.

Activities :

        AccuilActivity		(Home page always present, the other activities are recreated for need)
        MainActivity		    (Game page)
        ProfileActivity	    (Manage player profiles)
        ScoreActivity		(Display & reorder scores)

Fragments :
	  
        StreetFragment 	(StreetView)
        MapFragment		(Map)

Structure :

	  +-------------------------+
	  |      AccueiActivity     |
	  +-------------------------+
	  | +---------------------+ |
	  | |   ProfileActivity   | |
	  | +---------------------+ |
	  | |                     | |
	  | |                     | |
	  | |                     | |
	  | |   +--------------+  | |
	  | |   |    pixel...  |  | |
	  | |   +--------------+  | |
	  | |   +--------------+  | |
	  | |   |     DONE     |  | |
	  | |   +--------------+  | |
	  | |                     | |
	  | |                     | |
	  | |                     | |
	  | +---------------------+ |
	  +-------------------------+
         (name, |  /|\
         lname) |   |
               \|/  |
      +-------------|-----------+           +-------------------------+
      |    AccuilAct)ivity      |           |     AccueilActivity     |
      +-------------|-----------+           +-------------------------+
      | +-----------|---------+ |           | +---------------------+ |
      | |  pMode1+- |         | |           | |     MainActivity    | |
      | |  pMode2+- |         | |           | +---------------------+ |
      | |           |         | |           | |                     | |
      | |   +-------|------+  | |           | |    StreetFragment   | |
      | |   |  New Profile |  | |           | |                     | |
      | |   +--------------+  | | (profile, | |                     | |
      | |   |    NOVICE    |--|-|-    mode, | |                     | |
      | |   +--------------+  | |  \ level) | +---------------------+ |
      | |   |    MEDIUM    |--|-|- -------> | |                     | |
      | |   +--------------+  | |   |       | |                     | |
      | |   |    EXPERT    |--|-|- /        | |                     | |
      | |   +--------------+  | |           | |     MapFragment     | |
      | |   |    SCORES    |  | |           | |                     | |
      | |   +------|-------+  | |           | |                     | |
      | +----------|----------+ |           | +---------------------+ |
      +------------|------------+           +-------------------------+
                   |                                    |
                  \|/                                   |
      +-------------------------+                       | "every game"
      |     AccuilActivity      |                       | persist (score)
      +-------------------------+                       |
      | +---------------------+ |                       |
      | |    ScoreActivity    | |<-----------------------
      | +---------------------+ |
      | |                     | |
      | |                     | |
      | |+----+-----+--------+| |
      | ||prof|score|  date  || |
      | |+----+-----+--------+| |
      | ||toto| 550 |18/01/18|| |
      | |+----+-----+--------+| |
      | ||titi| 350 |15/01/18|| |
      | |+----+-----+--------+| |
      | ||tutu| 100 |20/01/18|| |
      | |+-------------------+| |
      | |                     | |
      | +---------------------+ |
      +-------------------------+


Entities :

        Score {
            String playerName;
            int level;
            int score;
            String date;
        }

        Profile {
            String name;
            String lname;
        }

Scores :

	NORMAL play mode
       +------------+------------+------------+------------+------------+------------+
       |   Level    |  Nb places |  d < 10km  |  d < 100km | d < 200km  | d > 500km  |
       +------------+------------+------------+------------+------------+------------+
       |   NOVICE   |      5     |     +400   |    +200    |     +50    |      +0    |
       +------------+------------+------------+------------+------------+------------+
       |   MEDIUM   |      7     |     +300   |    +200    |     +40    |     -30    |
       +------------+------------+------------+------------+------------+------------+
       |   EXPERT   |     10     |     +300   |    +100    |     +30    |     -50    |
       +------------+------------+------------+------------+------------+------------+

	COUNTRY play mode
       +------------+------------+------------+------------+
       |   Level    |  Nb places | == country | != country |
       +------------+------------+------------+------------+
       |   NOVICE   |      5     |     +10    |      -5    |
       +------------+------------+------------+------------+
       |   MEDIUM   |      7     |     +20    |     -10    |
       +------------+------------+------------+------------+
       |   EXPERT   |     10     |     +30    |     -20    |
       +------------+------------+------------+------------+

	REVERSE play mode
       +------------+------------+------------+------------+------------+------------+
       |   Level    |  Nb places |  d < 10km  |  d < 100km | d < 200km  | d > 500km  |
       +------------+------------+------------+------------+------------+------------+
       |   NOVICE   |      5     |        0   |     +50    |    +200    |    +400    |
       +------------+------------+------------+------------+------------+------------+
       |   MEDIUM   |      7     |      -30   |     +40    |    +100    |    +200    |
       +------------+------------+------------+------------+------------+------------+
       |   EXPERT   |     10     |      -50   |     +30    |    +100    |    +300    |
       +------------+------------+------------+------------+------------+------------+
       

Application basics :

        Home page   (done)
        Game view   (done) (displays both map and street view)
        Line        (done) (between the place to find and the player touch)
        Scores      (done)

Implemented Options :

        Profiles            (Use, Create new profile)
        Reorder scores      (By date, by profile, by score & by level)
        Percistancy         (Scores & last used profile are kept in local storage)
        Sharing score       (The player can share it's last game score)
        Zooming positions   ()
        Country play mode   (The player needs to pick the same country as the place to find)
        Reverse play mode   (The player needs to pick the farthest location from the place to find)
