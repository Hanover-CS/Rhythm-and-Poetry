package com.rap.rhythmandpoetry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rysha on 3/23/2018.
 */
public class UserTest {

    // creating 3 users two with the same name becuse user will be able to have the same name
    private User charles = new User("Charles","charles@hanover.edu","i am definitely cool");
    private User Kwame = new User("Kwame Opong","opong@hanover.edu","i am really cool");
    private User charles2 = new User("Charles","charles2@hanover.edu","i am definitely cool");


    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void setEmail() throws Exception {
        charles.setEmail("charles19@hanover.edu");
        assertTrue(charles.email == "charles19@hanover.edu");

        Kwame.setEmail("Opong20@hanover.edu");
        assertFalse(Kwame.email == "charles19@hanover.edu");
        assertTrue(Kwame.email == "Opong20@hanover.edu");

        charles2.setEmail("charlesa19@hanover.edu");
        assertFalse(charles2.email == "charles19@hanover.edu");
        assertTrue(charles2.email == "charlesa19@hanover.edu");
    }

    @Test
    public void setBio() throws Exception {
        charles.setBio("hello world im charles");
        assertTrue(charles.bio == "hello world im charles");

        Kwame.setBio("im kwame");
        assertFalse(Kwame.bio == "im kwawe");
        assertTrue(Kwame.bio == "im kwame");}

    @Test
    public void getBio() throws Exception {
        assertTrue(charles.getBio() == "hello world im charles");
        assertFalse(charles.getBio() == "hello world");

        assertTrue(charles2.getBio() == "i am definitely cool");
        assertFalse(charles2.getBio() == "i am cool");

        assertTrue(Kwame.getBio() == "hello world im charles");
        assertFalse(charles.getBio() == "hello world");

    }

    @Test
    public void getEmail() throws Exception {
        assertTrue(charles.getEmail() == "charles19@hanover.edu");
        assertFalse(charles.getEmail() == "charlesa19@hanover.edu");

        assertTrue(charles2.getEmail() == "charlesa19@hanover.edu");
        assertFalse(charles2.getEmail() == "charles19@hanover.edu");

        assertTrue(Kwame.getEmail() == "Opong20@hanover.edu");
        assertFalse(charles.getEmail() == "opong20@hanover.edu");
    }

    @Test
    public void getUsername() throws Exception {
        assertTrue(charles.getUsername() == "Charles");
        assertFalse(charles.getUsername() == "charles");

        assertTrue(charles2.getUsername() == "Charles");
        assertFalse(charles2.getUsername() == "charles");

        assertTrue(Kwame.getUsername() == "Kwame Opong");
        assertFalse(charles.getUsername() == "kwame opong");
    }

}