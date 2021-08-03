package com.company;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main{
    public static void main(String[] args){
        Hero myHero = new Warrior();
        //    Hero myHero = new Archer();
        //    Hero myHero = new Magician();

        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(30, 20, 1200)); // 1 - Пряничный человек
        enemies.add(new Enemy(2018, 1, 350)); // 2 - Туча комаров
        enemies.add(new Enemy(280, 50, 3050)); // 3 - Вор
        enemies.add(new Enemy(100500, 100500, 100500)); // 4 - Гальватрон

        //найден клад
        for (int i = 0; i < 300; i++){
            Item item = new Item(0, 0, i % 3); // i%3 - будет задавать предмету тип 0, 1, 2
            myHero.openItem(item);  // герои получает предмет в руки
        }
        System.out.println("Всего собрано: "+myHero.items.size());

        // бой!
        int count = 1;
        for (Enemy enemy : enemies){
            System.out.println( count++ + "-й бой:");
            System.out.println("  Герои { hp=" + myHero.hp + " mana=" + myHero.mana + " }");
            System.out.println("  Враг { hp=" + enemy.hp + " }");
            int counter = 1;
            while (myHero.hp > 0 & enemy.hp > 0){
                System.out.println( counter++ + " раунд:");
                myHero.attack(enemy);
                myHero.defense(enemy);
                System.out.println("    Герои { hp=" + myHero.hp + " mana=" + myHero.mana + " }");
                System.out.println("    Враг { hp=" + enemy.hp + " }");
            }
            System.out.println(myHero.hp > 0 ? "Победа, герой получил опыт "+ enemy.exp : "Поражение");
            System.out.printf("Герой { HP=%d, MANNA=%d, damage=%d, exp=%d }\n", myHero.hp, myHero.mana, myHero.damage, myHero.exp);
        }
    }
}

class Item {
    int price;
    int weight;
    int type;
    public Item(int price,
                int weight,
                int type) {
        this.price = price;
        this.weight = weight;
        this.type = type;
    }
}

class Enemy {
    int hp;
    int damage;
    int exp;
    public Enemy(int hp,
                 int damage,
                 int exp) {
        this.hp = hp;
        this.damage = damage;
        this.exp = exp;
    }
}

abstract class Hero {

    protected String nickName;
    protected int s;  /*сила ловкость интеллект опыт здоровье мана*/
    protected int a;
    protected int i;
    protected int exp;
    protected int hp;
    protected int mana;
    protected int x;  /*координаты героя на карте*/
    protected int y;
    protected ArrayList<Item> items = new ArrayList<>();  /*список поднятых предметов*/
    protected int damage;  /*урон*/

    public int getMana() {
        return mana;
    }
    public int getDamage() {
        return damage;
    }
    public int getHp() {
        return hp;
    }
    public void goToCursor(int xx,
                           int yy) {
        x = xx;
        y = yy;
    }

    public abstract void attack(Enemy enemy);  /*этот метод должнен быть определен в классах наследниках*/

    public abstract void defense(Enemy enemy); /*этот метод должнен быть определен в классах наследниках*/

    public abstract void openItem(Item item);
}

class Warrior extends Hero {
    public Warrior() {
        this.hp = 500;
        this.mana = 10;
        this.s = 100;
        this.a = 50;
        this.i = 1;
        this.exp = 0;
        this.damage = 150;
    }

    @Override
    public void attack(Enemy enemy) {
        if (this.hp > 0) {
            enemy.hp -= this.damage;
        }
        if (enemy.hp <= 0) {
            this.exp += enemy.exp;
            while (this.exp >= 500) {
                this.mana += 10;
                this.s += 10;
                this.a += 10;
                this.i += 10;

                this.hp += 200;
                this.damage += 20;
                this.exp -= 500;
            }
        }
    }

    @Override
    public void defense(Enemy enemy) {
        if (enemy.hp > 0) {
            this.hp -= enemy.damage;
        }
        if (this.hp <= 0) {
            System.out.print("Ваш герой был убит");
        }
    }

    @Override
    public void openItem(Item item) {
        Random r = new Random();
        if (r.nextInt(10) >= 5) {
            this.items.add(item);
            if (item.type == 1) {
                this.damage += 100;
            }
        }
    }
}

class Archer extends Hero {
    public Archer() {
        this.hp = 200;
        this.mana = 50;
        this.s = 20;
        this.a = 150;
        this.i = 30;
        this.exp = 0;
        this.damage = 200;
    }

    @Override
    public void attack(Enemy enemy) {
        if (this.hp > 0) {
            enemy.hp -= this.damage;
        }
        if (enemy.hp <= 0) {
            this.exp += enemy.exp;
            while (this.exp >= 500) {
                this.mana += 10;
                this.s += 10;
                this.i += 10;

                this.hp += 50;
                this.damage += 50;
                this.a += 30;

                this.exp -= 500;
            }
        }
    }

    @Override
    public void defense(Enemy enemy) {
        Random r = new Random();
        /*if (r.nextInt(10) >= 7) {

        }*/
        if (enemy.hp > 0  && r.nextInt() < 7) {
            this.hp -= enemy.damage;
        }
        if (this.hp <= 0) {
            System.out.print("Ваш герой был убит");
        }
    }

    @Override
    public void openItem(Item item) {
        this.items.add(item);
    }
}

class Magician extends Hero {

    private ArrayList<Item> casts = new ArrayList<>();

    public Magician() {
        this.hp = 100;
        this.mana = 5000;
        this.s = 5;
        this.a = 30;
        this.i = 300;
        this.exp = 0;
        this.damage = 40;
    }

    public void makeCast(Enemy enemy) {
        if (!casts.isEmpty()) {
            this.mana -= 100;
            enemy.hp -= 1000;
            casts.remove(casts.size() - 1);
        }
    }

    @Override
    public void attack(Enemy enemy) {
        if (this.hp > 0 && enemy.hp <= this.damage ||
            this.hp > 0 && this.mana <= 0) {
            enemy.hp -= this.damage;
        } else {
            this.makeCast(enemy);
        }
        if (enemy.hp <= 0) {
            this.exp += enemy.exp;
            while (this.exp >= 500) {
                this.s += 10;
                this.a += 10;
                this.i += 10;

                this.hp += 30;
                this.damage += 10;
                this.mana += 1000;

                this.exp -= 500;
            }
        }
    }

    @Override
    public void defense(Enemy enemy) {
        if (enemy.hp > 0 && this.mana > 0) {
            this.mana -= enemy.damage;
        }
        if (this.mana <= 0) {
            this.hp -= enemy.damage;
        }
        if (this.hp <= 0) {
            System.out.print("Ваш герой был убит");
        }
    }

    @Override
    public void openItem(Item item) {
        Random r = new Random();
        if (r.nextInt(10) >= 5) {
            this.items.add(item);
            eduCast(item);
        }
    }

    public void eduCast(Item item) {
        if (item.type == 2) {
            casts.add(item);
        }
    }
}
