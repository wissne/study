/**
 * Created by maple on 2015/6/8.
 */
public class Test2 {

    public static void main(String[] args) {
        Person person = new Person();
        person.setAge(20);
        person.setName("Tester");
        
        String name = person.getName();
        int age = person.getAge();
        age += (1 + 3);
    }
}
