package ru.javawebinar.basejava;

public class Deadlock {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();

        new Thread(() -> blockObject(obj1, obj2)).start();
        new Thread(() -> blockObject(obj2, obj1)).start();
    }

    private static void blockObject(Object obj1, Object obj2) {
        synchronized (obj1) {
            System.out.println(Thread.currentThread().getName() + " blocked " + obj1.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is waiting to block " + obj2.toString());
            synchronized (obj2) {
            }
        }
    }

}