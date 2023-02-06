import java.time.ZonedDateTime;

/**
 * FileName:Test.class
 * Author:ly
 * Date:2023/2/6 0006
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();

        //带地区的时间
        System.out.println(now); //2023-02-06T14:03:15.778+08:00[Asia/Shanghai]
        System.out.println(now.plusMinutes(5l)); //2023-02-06T14:08:15.778+08:00[Asia/Shanghai]
    }
}
