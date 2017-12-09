package busyShedule.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import busyShedule.model.bizmodel.ArrayListGame;
import busyShedule.model.bizmodel.Competition;
import busyShedule.model.bizmodel.Game;
import busyShedule.model.service.GameService;
import busyShedule.model.service.RefreshManager;

@Path("/api")
public class RestEndPoints {
	
	@GET
	@Produces({"application/xml","application/json"})
	public List<ArrayListGame> getGames(@QueryParam("num") int number,@QueryParam("day") int day,@QueryParam("last") int mode, @QueryParam("comp") String comp){
		int[] comps=toIntArray(comp);
		boolean b=true;
		//odyn prosliy
		if(mode==1)
			b=false;
		
		Map<Integer,List<Game>> map=new GameService().getGames(number, day, b, comps);
		List<ArrayListGame> list=new ArrayList();
		Iterator it=map.keySet().iterator();
		for(int i=0;i<map.size();i++)
			list.add(new ArrayListGame(map.get(it.next())));		
		return list;
	}
	/*
	// country -> array of competitions 
	@GET
	@Path("{comp}")
	public List<Competition> getCompetition(@PathParam("comp") String country){
		return new ArrayList();
	}
	
	// array of country -> map of array of competitions
	@GET
	public Map<String,List<Competition>> getCompetitions(@QueryParam("comp") String country){
		String[] countrys=country.split(",");
		return new HashMap();
	}
	
	// id of competition -> String info about this competition
	@GET
	public String getInfoCompetition(@QueryParam("comp") int comp){
		return comp+"";
	}
	*/
	@GET
	@Path("/refresh")
	public String updateData(){
		new RefreshManager().updateData();
		return "OK!";
	}
	
	
	private int[] toIntArray(String s){
		String[] str=s.split(",");
		int [] arr=new int[str.length];
		for(int i=0;i<str.length;i++)
			arr[i]=Integer.valueOf(str[i]);
		return arr;
	}
}
