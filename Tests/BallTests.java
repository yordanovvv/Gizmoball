import Model.Ball;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BallTests {
     @Test
     public void ballTests()
     {
          Ball b = new Ball ("B", 5, 5, 0, 10);
          b.setID("B1");
          assertEquals("B1", b.getID());
          assertEquals("Ball B1 5 5 0 0", b.toString());
     }
}
