import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vlukina on 22.10.2018.
 */
public class MainClass {

    public int getLocalNumber()
    {
        return 14;
    }

    @Test
    public void testGetLocalNumber()
    {
        Assert.assertTrue("Returned value not equal to 14",this.getLocalNumber() == 14);
    }
}
