import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Junit {

    @Test
    public void Mat(){
        int result = 100;
        int given = 0;

        result = Main.mat(result);

        Assertions.assertEquals(result,given);
    }
}
