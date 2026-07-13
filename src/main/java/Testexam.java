import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class Testexam {



    //Given a list of integers, find out all the numbers starting with 1 using Stream functions?



    public static void main(String[] args) {
        List<Integer> myList = Arrays.asList(10,15,8,49,25,98,19,32);



            myList.stream().filter(i->i.toString().startsWith("1")).forEach(j-> System.out.println(j));


    }
}
