import models.Hero;
import models.Squad;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static spark.Spark.*;

public class App {
    static int getHerokuPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
    public static void main(String[] args) {
        port(getHerokuPort());
        staticFileLocation("/public");
        Hero hero1 = new Hero("hero1",23,"fly","weak");
        //default page
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        // create a hero
        get("/create/hero",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "create-hero.hbs");
        },new HandlebarsTemplateEngine());
        post("/heroes/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            int age=Integer.parseInt(request.queryParams("age"));
            String powers=request.queryParams("powers");
            String weakness=request.queryParams("weakness");
            Hero newHero = new Hero(name,age,powers,weakness);
            model.put("heroes", newHero);
            return new ModelAndView(model, "create-hero.hbs");
        }, new HandlebarsTemplateEngine());
        //delete a hero
        get("/heroes/delete",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Hero.clearAll();
            model.put("heroes",Hero.getHeroes());
            return new ModelAndView(model,"all-heroes.hbs");

        },new HandlebarsTemplateEngine());
        get("/hero", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Hero> heroes = Hero.getHeroes();
            model.put("heroes", heroes);

            return new ModelAndView(model, "all-heroes.hbs");
        }, new HandlebarsTemplateEngine());


        get("/heroes/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToFind = Integer.parseInt(request.params(":id")); //pull id - must match route segment
            Hero foundHero = Hero.findHeroById(idOfHeroToFind); //use it to find post
            model.put("hero", foundHero); //add it to model for template to display
            ArrayList<Hero> heroes = Hero.getHeroes();
            model.put("heroes", heroes);
            return new ModelAndView(model, "all-heroes.hbs"); //individual post page.
        }, new HandlebarsTemplateEngine());

        //squad
        get("/squads/delete",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Squad.clearAll();
            ArrayList<Hero> heroes=Hero.getHeroes();
            for (Hero hero : heroes) {
                hero.updateHero(false);
            }
            model.put("squads",Squad.getSquads());
            return new ModelAndView(model,"all-squads.hbs");

        },new HandlebarsTemplateEngine());

        get("/create/squad",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Hero> heroes=Hero.getHeroes();
            AtomicReference<ArrayList<Hero>> heroList= new AtomicReference<>(new ArrayList<>());
            for (Hero hero : heroes) {
                if (!hero.isAvailable()) {
                    heroList.get().add(hero);
                }
            }

            model.put("heroes",Hero.getHeroes());
            return new ModelAndView(model,"create-squad.hbs");
        },new HandlebarsTemplateEngine());


        //create a new squad
        post("/squads/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<>();

            String name = request.queryParams("name");
            int maxSize=Integer.parseInt(request.queryParams("size"));
            String cause=request.queryParams("cause");
            ArrayList<Hero> heroes=new ArrayList<>();
            if(request.queryParamsValues("heroes")!=null){
                String[] heroesList = request.queryParamsValues("heroes");

                for(int i=0;i<heroesList.length;i++){
                    Hero addHero=Hero.findHeroById(Integer.parseInt(heroesList[i]));
                    if(heroes.size()<maxSize){
                        addHero.updateHero(true);
                        heroes.add(addHero);
                    }

                }
            }
            Squad newSquad= new Squad(maxSize,name,cause,heroes);

            model.put("heroes",Hero.getHeroes());

            return new ModelAndView(model, "create-squad.hbs");
        }, new HandlebarsTemplateEngine());

        //display a squad
        get("/squad",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("squads",Squad.getSquads());
            return new ModelAndView(model,"all-squads.hbs");

        },new HandlebarsTemplateEngine());

        get("/squads/:id",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSquadToFind=Integer.parseInt(request.params(":id"));
            Squad foundSquad=Squad.findById(idOfSquadToFind);
            model.put("squad",foundSquad);
            ArrayList<Squad> squads=Squad.getSquads();
            model.put("squads",squads);
            return new ModelAndView(model,"all-squads.hbs");
        },new HandlebarsTemplateEngine());

        //delete hero
        get("/hero/:id/delete",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfHeroToDelete=Integer.parseInt(request.params(":id"));
            Hero foundHero=Hero.findHeroById(idOfHeroToDelete);
            for (int i=idOfHeroToDelete;i<Hero.getHeroes().size();i++){
                Hero.getHeroes().get(i).setId(Hero.getHeroes().get(i).getId()-1);
            }
            assert foundHero != null;
            foundHero.deleteHero();
            ArrayList<Hero> heroes = Hero.getHeroes();
            model.put("heroes", heroes);
            return new ModelAndView(model,"all-heroes.hbs");
        },new HandlebarsTemplateEngine());

        //delete a squad
        get("/squad/:id/delete",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSquadToDelete=Integer.parseInt(request.params(":id"));
            Squad foundSquad=Squad.findById(idOfSquadToDelete);
            assert foundSquad != null;
            ArrayList<Hero> heroes=foundSquad.getHeroes();
            for (Hero hero : heroes) {
                hero.updateHero(false);
            }
            for (int i=idOfSquadToDelete;i<Squad.getSquads().size();i++){
                Squad.getSquads().get(i).setId(Squad.getSquads().get(i).getId()-1);
            }
            foundSquad.deleteSquad();

            ArrayList<Squad> squads = Squad.getSquads();
            model.put("squads", squads);
            return new ModelAndView(model,"all-squads.hbs");

        },new HandlebarsTemplateEngine());
    }
}
