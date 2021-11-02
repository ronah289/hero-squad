package models;

import java.util.ArrayList;

public class Squad {

    private final int maxSize;
    private final String name;
    private final String cause;
    private int id;
    private final ArrayList<Hero> heroes;
    public static ArrayList<Squad> squads= new ArrayList<>();

    public Squad(int maxSize, String name, String cause, ArrayList<Hero> heroes) {

        this.maxSize = maxSize;
        this.name = name;
        this.cause = cause;
        this.heroes = heroes;
        squads.add(this);
        this.id=squads.size();
    }

    public static ArrayList<Squad> getSquads() {
        return squads;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void deleteSquad(){
        squads.remove(id-1);
    }



    public static int clearAll(){
        squads.clear();
        return 0;
    }

    public static Squad findById(int id){
        try {
            return squads.get(id-1);
        } catch (IndexOutOfBoundsException exception) {
            return null;
        }

    }

    public int getId() {
        return id;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getName() {
        return name;
    }

    public String getCause() {
        return cause;
    }
}
