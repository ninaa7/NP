package Kolokviumski2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Team {
    String teamName;
    int numGames;
    int goalsScored;
    int goalsConceded;
    int wins;
    int draws;
    int losses;

    public Team(String teamName) {
        this.teamName = teamName;
        this.numGames = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getNumGames() {
        return numGames;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getPoints() {
        return wins * 3 + draws;
    }

    public int goalDifference() {
        return goalsScored - goalsConceded;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d\n", teamName, numGames, wins, draws, losses, getPoints());
    }
}

class FootballTable {
    Map<String, Team> teams;

    public FootballTable() {
        this.teams = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Team home = teams.computeIfAbsent(homeTeam, x -> new Team(homeTeam));
        Team away = teams.computeIfAbsent(awayTeam, x -> new Team(awayTeam));

        //setting goals
        home.setGoalsScored(home.getGoalsScored() + homeGoals);
        home.setGoalsConceded(home.getGoalsConceded() + homeGoals);
        home.setGoalsConceded(home.getGoalsConceded() + awayGoals);
        home.setGoalsScored(home.getGoalsScored() + awayGoals);

        //setting wins, losses, draws
        if (homeGoals > awayGoals) {
            home.setWins(home.getWins() + 1);
            away.setLosses(away.getLosses() + 1);
        } else if (homeGoals < awayGoals) {
            home.setLosses(home.getLosses() + 1);
            away.setWins(away.getWins() + 1);
        } else {
            home.setDraws(home.getDraws() + 1);
            away.setDraws(away.getDraws() + 1);
        }

        //setting games playes
        home.setNumGames(home.getNumGames() + 1);
        away.setNumGames(away.getNumGames() + 1);
    }

    public void printTable() {
        Comparator<Team> c = Comparator.comparing(Team::getPoints)
                .thenComparing(Team::goalDifference).reversed()
                .thenComparing(Team::getTeamName);
        StringBuilder sb=new StringBuilder();
        AtomicInteger i= new AtomicInteger(1);
        teams.values().stream()
                .sorted(c)
                .forEach(t->sb.append(String.format("%2s. %s",i.getAndIncrement(),t.toString())));
        System.out.println(sb.toString());
    }
}

public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here


