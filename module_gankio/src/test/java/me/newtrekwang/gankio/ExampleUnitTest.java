package me.newtrekwang.gankio;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.newtrekwang.base.utils.TimeUtils;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDateSt(){
        String dateSt = "2019-04-10";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = TimeUtils.string2Date(dateSt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        System.out.println("year: "+cal.get(Calendar.DAY_OF_MONTH)+"  month: "+(cal.get(Calendar.MONTH)+1)+" day: "+cal.get(Calendar.YEAR));
        System.out.println(TimeUtils.date2String(date,simpleDateFormat));
    }
}