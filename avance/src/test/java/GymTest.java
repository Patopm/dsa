import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GymTest {

    private Gym gym;

    @BeforeEach
    void setUp() {
        gym = new Gym(2);
    }

    @Test
    void arriveWithSpaceGoesInside() {
        Person ana = new Person("Ana", "A001", Grade.PREPA);
        assertEquals(Gym.ArriveResult.Status.ENTERED, gym.arrive(ana).getStatus());
        assertEquals("[A001:Ana:PREPA]", gym.insideString());
        assertEquals("[]", gym.waitingString());
        assertEquals(1, gym.insideCount());
        assertEquals(2, gym.capacity());
    }

    @Test
    void arriveWhenFullGoesToQueue() {
        gym.arrive(new Person("Ana", "A001", Grade.PREPA));
        gym.arrive(new Person("Luis", "A002", Grade.UNI));
        Person mia = new Person("Mia", "A003", Grade.PREPA);
        assertEquals(Gym.ArriveResult.Status.QUEUED, gym.arrive(mia).getStatus());
        assertEquals("[A001:Ana:PREPA -> A002:Luis:UNI]", gym.insideString());
        assertEquals("[A003:Mia:PREPA]", gym.waitingString());
        assertEquals("A003", gym.waitingFront().getId());
    }

    @Test
    void leaveWithoutQueueFreesASlot() {
        gym.arrive(new Person("Ana", "A001", Grade.PREPA));
        Gym.LeaveResult result = gym.leave("A001");
        assertEquals(Gym.LeaveResult.Status.LEFT, result.getStatus());
        assertEquals("A001", result.getLeft().getId());
        assertNull(result.getEntered());
        assertEquals("[]", gym.insideString());
        assertEquals(0, gym.insideCount());
    }

    @Test
    void leaveWithQueueAutoEntersTheFront() {
        gym.arrive(new Person("Ana", "A001", Grade.PREPA));
        gym.arrive(new Person("Luis", "A002", Grade.UNI));
        gym.arrive(new Person("Mia", "A003", Grade.PREPA));
        Gym.LeaveResult result = gym.leave("A001");
        assertEquals(Gym.LeaveResult.Status.LEFT_AND_ENTERED, result.getStatus());
        assertEquals("A001", result.getLeft().getId());
        assertEquals("A003", result.getEntered().getId());
        assertEquals("[A002:Luis:UNI -> A003:Mia:PREPA]", gym.insideString());
        assertEquals("[]", gym.waitingString());
    }

    @Test
    void leaveUnknownIdFails() {
        Gym.LeaveResult result = gym.leave("NOPE");
        assertEquals(Gym.LeaveResult.Status.NOT_INSIDE, result.getStatus());
        assertNull(result.getLeft());
    }
}
