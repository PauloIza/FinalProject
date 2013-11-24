package finalGame;

import java.util.ArrayList;

public class Team {
	private ArrayList<Player> team;
	private String teamName;
	private int teamScore;
	
	public Team() {
		super();
		team = new ArrayList<Player>();
		teamScore = 0;
		teamName = null;
	}

	public Team(String teamName) {
		super();
		team = new ArrayList<Player>();
		teamScore = 0;
		this.teamName = teamName;
	}
	
	public void addPlayer(Player person) {
		team.add(person);
	}

	public int getTeamScore() {
		return teamScore;
	}

	public void teamScore() {
		teamScore++;
	}

	public ArrayList<Player> getTeam() {
		return team;
	}

	public String getTeamName() {
		return teamName;
	}
	
	
	
}
