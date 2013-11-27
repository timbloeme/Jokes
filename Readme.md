Jokes App
=========
This app is meant for people who just like a good laugh because of jokes.
Or the ones that just like to be funny.
Basicly you can add your own jokes or search for new ones.

Features
--------

The following features will be in this app:
- A start screen with several options
	- a button wich will pop up a random joke
	- a search option wich will return jokes of a specific genre or from a specific user
	- a small list with the most recent jokes.
	- (if not logged in) a button wich takes you to a log in screen.
	- (if logged in) a button wich will allow you to put a new joke in the database
	- (if logged in) a button wich takes you to a user settings screen
- A entry screen wich has several fields
	- a field in wich you can add a tittle 
	- a large text field for the joke itself
	- a set of checkboxes to set to wich genre or genres it belongs
		- The last checkbox contains a input field wich would allow a user to add a new genre to the database.
- A screen wich shows a list of jokes
	- it shows a list showing only the name of the joke
	- if a name is pressed it is opend in a new screen showing the entire joke
- A screen wich shows a joke
	- a next joke button
	- (if not in random mode) a previeus joke button 
	- a field wich shows the user name of the uploader
	- a field with the entire joke
	- a field wich shows to wich genre('s) the joke belongs to
- A user settings screen
	- a field to change user name
	- a field to change password

Database
--------
A database with the following tables and entries
	- the jokes
		- id
		- the tittle
		- user id
	- genre's
		- id
		- the name of the genre
	- users
		- id
		- username
		- password
	- many to one 
		- joke id
		- genre id

further information
-------------------

- we will work with as much viewflippers as to reduce strain on the device. (so we're not making an activity for every view)
- use as much as possible the android default menu bars.