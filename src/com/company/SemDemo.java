package com.company;

import java.util.concurrent.Semaphore;

public class SemDemo {
    public static void main(String[] args) {
        Semaphore sem = new Semaphore(1);
        new Thread(new IncThread(sem, "A")).start();
        new Thread(new DecThread(sem, "B")).start();
    }
}

class Shared {
    static int count = 0;
}


class IncThread implements Runnable {
    String name;
    Semaphore sem;

    IncThread(Semaphore s, String n) {
        sem = s;
        name = n;
    }

    public void run() {
        System.out.println("Зaпycк " + name);
        try {

            System.out.println(name + " ожидает разрешения.");
            sem.acquire();
            System.out.println(name + " получил разрешение.");

            for (int i = 0; i < 5; i++) {
                Shared.count++;
                System.out.println(name + ": " + Shared.count);
// Разрешить переключение контекста по возможности .
                Thread.sleep(10);
            }
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
// Освободить разрешение.
        System.out.println(name + " освободил разрешение . ");
        sem.release();
    }
}

// Поток выполнения , который декрементирует счетчик ( переменную count ) .
class DecThread implements Runnable {
    String name;
    Semaphore
            sem;

    DecThread(Semaphore s, String n) {
        sem = s;
        name = n;
    }

    public void run() {
        System.out.println(" Зaпycк " + name);
        try {
            System.out.println(name + " ожидает разрешения . ");
            sem.acquire();
            System.out.println(name + " получил разрешение . ");

            for (int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + " : " + Shared.count);

                Thread.sleep(10);
            }
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        System.out.println(name + " освободил разрешение . ");
        sem.release();
    }
}