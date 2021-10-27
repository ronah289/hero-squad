package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

class SquadTest {
    private Squad createTestSquad(){
        ArrayList<Hero> testHeroes= new ArrayList<>();
        Hero testHero1 = new Hero("reese",21,"flying","lazy");
        Hero testHero2 = new Hero("brian",23,"flying","weak");
        testHeroes.add(testHero1);
        testHeroes.add(testHero2);
        AtomicReference<Squad> testSquad = new AtomicReference<>(new Squad(2, "avengers", "fight sexism", testHeroes));
        return testSquad.get();
    }
    @AfterEach
    public void tearDown() throws Exception {
        Squad.clearAll();
    }

    @Test
    public void checkNewSquadInstantiatesCorrectly() throws Exception{
        Squad mySquad = createTestSquad();
        assertNotNull(mySquad);
    }

    @Test
    public void checkGetSquadIdGetterWorking() throws Exception{
        Squad mySquad = createTestSquad();
        assertEquals(1,mySquad.getId());
    }

    @Test
    public void clearAll() throws Exception{
        assertEquals(0, Squad.clearAll());
    }

    @Test
    public void checkFindByIdMethod() throws Exception{
        Squad mySquad = createTestSquad();
        assertEquals(1,mySquad.getId());
    }

    @Test
    public void checkWorkOfSquadGetterWorksCorrectly() throws Exception{
        Squad mySquad = createTestSquad();
        assertEquals("fight sexism",mySquad.getCause());
    }
    @Test
    public void checkSquadMembersGetterWorking() throws Exception{
        Squad mySquad = createTestSquad();
        assertEquals(2,mySquad.getHeroes().size());
    }
}