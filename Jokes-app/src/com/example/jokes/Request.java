package com.example.jokes;

public class Request {
	String url = "www.timbloeme.nl/app/getjokes.php";
	int type = 0;
	int operation = 0;
	int[] ids = {0};
	Joke joke = null;
	User user = null;
	

	public Request(int type,int operation, String url){
		this.type = type;
		this.operation = operation;
		this.url = url;
	}
	public Request(int type,int operation, String url, Joke joke){
		this.type = type;
		this.operation = operation;
		this.url = url;
		this.joke = joke;
	}
	public Request(int type,int operation, String url,User user){
		this.type = type;
		this.operation = operation;
		this.url = url;
		this.user = user;
	}
	public Request(int type,int operation, String url, int[] ids){
		this.type = type;
		this.operation = operation;
		this.url = url;
		this.ids = ids;
	}
}

