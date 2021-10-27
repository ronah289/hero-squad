package models;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {
    Hero testHero1 = new Hero("Reese",23,"run fast","get tired easily");

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        Hero.clearAll();
    }
    @AfterAll
    public static void clearTestValues(){
        Hero.clearAll();
    }
    @Test
    public void checkCorrectInstantiationOfNewHeroMember() throws Exception{
        assertNotNull(testHero1);
    }
    @Test
    public void checkHeroNamePicked() throws Exception{
        assertEquals("Reese",testHero1.getName());
    }
    @Test
    public void checkHeroAgeGetterWorkingCorrectly() throws Exception{
        assertEquals(23,testHero1.getAge());
    }
    @Test
    public void checkHeroSpecialPowerInstantiates() throws Exception{
        assertEquals("run fast",testHero1.getPowers());
    }
    @Test
    public void checkHeroWeaknessInstantiates() throws Exception{
        assertEquals("get tired easily",testHero1.getWeakness());
    }

    @Test
    public void checkHeroIdValueCorrect() throws Exception{
        assertEquals(1,testHero1.getId());
    }
    @Test
    public void checkAccuracyOfSetHeroIdMethod() throws Exception{
        testHero1.setId(3);
        assertEquals(3,testHero1.getId());
    }
    @Test
    public void checkAvailabilityMethod() throws Exception{
        assertFalse(testHero1.isAvailable());
    }
}