import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vlukina on 23.10.2018.
 */
public class MainClassTest extends MainClass {

    @Test
    public void testGetLocalNumber()
    {
        Assert.assertTrue("Returned value not equal to 14",this.getLocalNumber() == 14);
    }

    @Test
    public void testGetClassNumber()
    {
        if (this.getClassNumber() == 45) {
            Assert.assertFalse("Returned value is equal to 45",this.getClassNumber() == 45);
        } else {
            Assert.assertTrue("Returned value less than 45",this.getClassNumber() > 45);
        }
    }
}
