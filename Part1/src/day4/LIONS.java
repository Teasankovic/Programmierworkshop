package day4;

public class LIONS {
    public static void main(String[] args) {

       class Lion {
            String name;
            int age;

            Lion(String name, int age){
            this.name = name;
            this.age = age;
        }
        void makeSound(){
                System.out.println(name + " very loudly: ROARS!!!");
        }
        }

        Lion m1 = new Lion("Viki", 5);
        Lion m2 = new Lion("Laura", 4);
        Lion m3 = new Lion("Tea", 3);

        m1.makeSound();
        m2.makeSound();
        m3.makeSound();
    }
}
