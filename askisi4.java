import java.util.Arrays;
import java.util.Collections;
import java.util.List;

abstract class Person
{

  final String name;
  final int floor_number;
  final int class_number;
  boolean has_arrived;
  int fatigue;

  public Person(final String name_ , final int floor_number_ , final int class_number_)
  {

    name = name_;
    floor_number = floor_number_;
    class_number = class_number_;
    has_arrived = false;
    fatigue = 0;

  }
  public String get_name(){return name;}
  public int get_floor_number(){return floor_number;}
  public int get_class_number(){return class_number;}
  public void arrived(){has_arrived = true;}
  public void left(){has_arrived = false;}
  public abstract void print();

}
final class Teacher extends Person
{

  public static int Lt;
  public Teacher(final String name_ , final int floor_number_ , final int class_number_)
  {

    super(name_ , floor_number_ , class_number_);
    System.out.println("A New Teacher has been created!");
    System.out.println(name + " " + (floor_number + 1) + " " + (class_number + 1));

  }
  public void print()
  {

    System.out.println("The teacher is: ");
    System.out.println(name + " " + fatigue);

  }
  public void teach(final int hours){fatigue += hours * Teacher.Lt;}

}
abstract class Student extends Person
{

  public Student(final String name_ , final int floor_number_ , final int class_number_)
  {


    super(name_ , floor_number_ , class_number_ );
    System.out.println("A New Student has been created!");
    System.out.println(name + " " + (floor_number + 1) + " " + (class_number + 1));

  }
  abstract void attend(final int hours);
  public void print(){System.out.println(name + " " + fatigue);}

}
final class Junior extends Student
{

  public static int Lj;
  Junior(final String name_ , final int floor_number_ , final int class_number_)
  {

    super(name_ , floor_number_ , class_number_);

  }
  public void attend(final int hours){fatigue += hours * Junior.Lj;}

}
final class Senior extends Student
{

  public static int Ls;
  Senior(final String name_ , final int floor_number_ , final int class_number_)
  {

    super(name_ , floor_number_ , class_number_);

  }
  public void attend(final int hours){fatigue += hours * Senior.Ls;}

}
abstract class Space
{

  static Student student_in_space;
  public static void enter(Student student_entering)
  {student_in_space = student_entering;}
  public static Student exit()
  {

    Student student_exiting = student_in_space;
    student_in_space = null;
    return student_exiting;

  }

}
final class Yard extends Space
{

  public Yard(){System.out.println("A New Yard has been created!");}
  public static void enter(Student student_entering_)
  {

    System.out.println(student_entering_.get_name() + " enters schoolyard!");
    Space.enter(student_entering_);

  }
  public static Student exit()
  {

    System.out.println(student_in_space.get_name() + " exits schoolyard!");
    return Space.exit();

  }

}
final class Stairs extends Space
{

  public Stairs(){System.out.println("New Stairs have been created!");}
  public static void enter(Student student_entering_)
  {

    System.out.println(student_entering_.get_name() + " enters stairs!");
    Space.enter(student_entering_);

  }
  public static Student exit()
  {

    System.out.println(student_in_space.get_name() + " exits stairs!");
    return Space.exit();

  }

}
final class Corridor extends Space
{

  public Corridor(){System.out.println("A New Corridor has been created!");}
  public static void enter(Student student_entering_)
  {

    System.out.println(student_entering_.get_name() + " enters corridor!");
    Space.enter(student_entering_);

  }
  public static Student exit()
  {

    System.out.println(student_in_space.get_name() + " exits corridor!");
    return Space.exit();

  }

}
class Classroom
{

  private int count;
  private Student[] students;
  private Teacher teacher;
  public static int Cclass;
  public Classroom()
  {

    System.out.println("A New Classroom has been created!");
    students = new Student[Cclass];

  }
  public void enter(Student student_entering)
  {

    System.out.println(student_entering.get_name() + " enters classroom!");
    students[count++] = student_entering;
    student_entering.arrived();

  }
  public void place(Teacher teacher)
  {

    this.teacher = teacher;
    teacher.arrived();

  }
  public void operate(final int hours){

    for(int i = 0; i < count; i++)

      students[i].attend(hours);

    teacher.teach(hours);

  }
  public void print(){

    for(int i = 0; i < count; i++)

      students[i].print();

    teacher.print();

  }
  public Student exit()
  {

    count--;
    System.out.println(students[count].get_name() + " starts exiting!");
    System.out.println(students[count].get_name() + " exits classroom!");
    students[count].left();
    Student student_exiting = students[count];
    students[count] = null;
    return student_exiting;

  }
  public int get_count(){return count;}
  public void teacher_out()
  {

    teacher.left();
    System.out.println(teacher.get_name() + " teacher is out!");

  }

}
class Floor
{

  private Corridor corridor = new Corridor();
  private Classroom[] classroom = new Classroom[6];
  public Floor()
  {

    for(int i = 0; i < 6; i++)

      classroom[i] = new Classroom();

    System.out.println("A New Floor has been created!");

  }
  public void enter(Student student_entering)
  {

    System.out.println(student_entering.get_name() + " enters floor!");
    corridor.enter(student_entering);
    classroom[student_entering.get_class_number()].enter(corridor.exit());

  }
  public void place(Teacher teacher)
  {classroom[teacher.get_class_number()].place(teacher);}
  public void operate(final int hours)
  {

    for(int i = 0; i < 6; i++)

      classroom[i].operate(hours);

  }
  public void print()
  {

    for(int i = 0; i < 6; i++)
    {

      System.out.println("Printing classroom " + (i + 1) + " :");
      classroom[i].print();

    }

  }
  public Student exit()
  {

    for(int i = 0; i < 6; i++)
    {

      if(classroom[i].get_count() != 0 )
      {

        corridor.enter(classroom[i].exit());
        break;

      }

    }
    return corridor.exit();

  }
  public void teachers_out()
  {

    for(int i = 0; i < 6; i++)

      classroom[i].teacher_out();

  }

}
class School
{

  private Yard yard = new Yard();
  private Stairs stairs = new Stairs();
  private Floor[] floor = new Floor[3];
  public School()
  {

    for(int i = 0; i < 3; i++)

      floor[i] = new Floor();

    System.out.println("A New School has been created!");

  }
  public void enter(Student student_entering)
  {

    System.out.println(student_entering.get_name() + " enters school!");
    yard.enter(student_entering);
    stairs.enter(yard.exit());
    floor[student_entering.get_floor_number()].enter(stairs.exit());

  }
  public void place(Teacher teacher){floor[teacher.get_floor_number()].place(teacher);}
  public void operate(final  int hours){

    for(int i = 0; i < 3; i++)

      floor[i].operate(hours);

  }
  public void print()
  {

    System.out.println("Printing school: ");
    for(int i = 0; i < 3; i++)
    {

      System.out.println("Printing floor " + (i + 1) + " :");
      floor[i].print();

    }

  }
  public void empty()
  {

    for(int i = 0; i < 3; i++)
    {

      for(int k = 0; k < 6 * Classroom.Cclass; k++)
      {

        stairs.enter(floor[i].exit());
        yard.enter(stairs.exit());
        yard.exit();

      }

      floor[i].teachers_out();

    }

  }

}
public class askisi4{

  public static void main(String args[])
  {

    Classroom.Cclass = Integer.parseInt(args[0]);
    Junior.Lj = Integer.parseInt(args[1]);
    Senior.Ls = Integer.parseInt(args[2]);
    Teacher.Lt = Integer.parseInt(args[3]);
    int N = Integer.parseInt(args[4]);
    School school = new School();
    Student[] students = new Student[Classroom.Cclass * 18];
    String name;
    int i , j , k , count = 0;
    for(i = 0; i < 3; i++)
    {

      for(j = 0; j < 6; j++)
      {

        if( j < 3 ){

          for(k = 0; k < Classroom.Cclass; k++)

            students[count++] = new Junior("Student " + count , i , j );

        }
        else
        {

          for(k = 0; k < Classroom.Cclass; k++)

            students[count++] = new Senior("Student " + count , i , j );

        }

      }

    }
    Teacher[] teachers = new Teacher[18];
    count = 0;
    for( i = 0; i < 3; i++)
    {

      for( j = 0; j < 6; j++)

        teachers[count++] = new Teacher("Teacher " + count , i , j );

    }
    List<Student> StuList = Arrays.asList(students);
		Collections.shuffle(StuList);
		StuList.toArray(students);

    List<Teacher> TeachList = Arrays.asList(teachers);
		Collections.shuffle(TeachList);
		TeachList.toArray(teachers);

    for(i = 0; i < Classroom.Cclass * 18; i++)

      school.enter(students[i]);

    for(i = 0; i < 18; i++)

      school.place(teachers[i]);

    school.operate(N);
    school.print();
    school.empty();

  }

}
