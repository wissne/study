package com.maple.study.google.guava.demo;

import org.junit.Test;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class ObjectTest {

	@Test
	public void testEqual1() {
		System.out.println(Objects.equal("a", "a"));
		System.out.println(Objects.equal(null, "a"));
		System.out.println(Objects.equal("a", null));
		System.out.println(Objects.equal(null, null));
	}

	@Test
	public void testEqual2() {
		System.out.println(Objects.equal(new Person("peida",23), new Person("peida",23)));
        Person person=new Person("peida",23);
        System.out.println(Objects.equal(person,person));
	}

	@Test
    public void hashcodeTest() {
        System.out.println(Objects.hashCode("a"));
        System.out.println(Objects.hashCode("a"));
        System.out.println(Objects.hashCode("a","b"));
        System.out.println(Objects.hashCode("b","a"));
        System.out.println(Objects.hashCode("a","b","c"));

        System.out.println("New1:" + Objects.hashCode(new Person("peida",23)));
        System.out.println("New2:" + Objects.hashCode(new Person("peida",23)));
        Person person=new Person("peida",23);
        System.out.println(Objects.hashCode(person));
        System.out.println(Objects.hashCode(person));
    }

	@Test
    public void toStringTest() {
        System.out.println(Objects.toStringHelper(this).add("x", 1).toString());
        System.out.println(Objects.toStringHelper(Person.class).add("x", 1).toString());

        Person person=new Person("peida",23);
        String result = Objects.toStringHelper(Person.class)
        .add("name", person.name)
        .add("age", person.age).toString();
        System.out.print(result);

    }
}


class Person {
    public String name;
    public int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Student implements Comparable<Student>{
    public String name;
    public int age;
    public int score;


    Student(String name, int age,int score) {
        this.name = name;
        this.age = age;
        this.score=score;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, age);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student that = (Student) obj;
            return Objects.equal(name, that.name)
                    && Objects.equal(age, that.age)
                    && Objects.equal(score, that.score);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .addValue(name)
                .addValue(age)
                .addValue(score)
                .toString();
    }


    @Override
    public int compareTo(Student other) {
        return ComparisonChain.start()
        .compare(name, other.name)
        .compare(age, other.age)
        .compare(score, other.score, Ordering.natural().nullsLast())
        .result();
    }
}

