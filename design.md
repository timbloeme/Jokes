Jokes app design doc
====================

Database
--------
####Jokes
<table>
	<tr>
		<th>Name</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>Id</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>Title</td>
		<td>String</td>
	</tr>
	<tr>
		<td>Joke</td>
		<td>String</td>
	</tr>
	<tr>
		<td>UserId</td>
		<td>Integer</td>
	</tr>
</table>

####Category
<table>
	<tr>
		<th>Name</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>Id</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>Category</td>
		<td>String</td>
	</tr>
</table>

####User
<table>
	<tr>
		<th>Name</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>Id</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>Username</td>
		<td>String</td>
	</tr>
	<tr>
		<td>Password</td>
		<td>String??</td>
	</tr>
</table>

####JokesCategories
<table>
	<tr>
		<th>Name</th>
		<th>Type</th>
	</tr>
	<tr>
		<td>JokeId</td>
		<td>Integer</td>
	</tr>
	<tr>
		<td>CategoryId</td>
		<td>Integer</td>
	</tr>
</table>

Set-up design
-------------
![alt-text](https://github.com/timbloeme/Jokes/blob/master/setup.png?raw=trye)
####Main activity
In the main activity only the search class will be called.
This class will return the list of jokes that will be showed in the main view.
####The main view
The main view will consist of an include of the top bar view and a viewflipper for the content below the top bar. So the joke view / the user view / the jokes list view.
This is so that there is no need to load another screen/activity.

Design
------
![alt-text](https://github.com/timbloeme/Jokes/blob/master/design.png?raw=trye)
We will decide if the random button should be next to the search button or under the three dots while we are coding. We are not sure yet how much space there is needed and how it will look like.

Notes
-----
The ultimate goal is to be able to get the database to run on a server so you can see jokes made by anyone and not only the ones made on your phone.

