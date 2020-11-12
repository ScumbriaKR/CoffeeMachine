import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BumbleBee bumblebee = new BumbleBee();
        boolean SwitchPos = bumblebee.getSwitchPos();
        bumblebee.chooseAction();
        while (SwitchPos) {
            bumblebee.work(sc.nextLine());
            SwitchPos = bumblebee.getSwitchPos();
        }
    }
}

class BumbleBee {
    int water = 400;
    int milk = 540;
    int coffeeBeans = 120;
    int disposableCups = 9;
    int money = 550;
    States currentState = States.CHOOSING_AN_ACTION;
    boolean SwitchOn = true;

    public void setSwitchOff() {
        this.SwitchOn = false;
    }

    public boolean getSwitchPos() {
        return SwitchOn;
    }

    public void work(String str) {
        switch (currentState) {
            case CHOOSING_AN_ACTION:
                switch (str) {
                    case "buy":
                        currentState = States.BUY_COFFEE;
                        askWhichCoffee();
                        break;
                    case "fill":
                        currentState = States.FILL_WATER;
                        askFor();
                        break;
                    case "take":
                        takeMoney();
                        chooseAction();
                        break;
                    case "remaining":
                        showStatistics();
                        chooseAction();
                        break;
                    case "exit":
                        setSwitchOff();
                        break;
                    default:
                        System.out.println("Invalid action!");
                        break;
                }
                break;
            case BUY_COFFEE:
                buyCoffee(str);
                currentState = States.CHOOSING_AN_ACTION;
                chooseAction();
                break;
            case FILL_WATER:
                setWater(str);
                currentState = States.FILL_MILK;
                askFor();
                break;
            case FILL_MILK:
                setMilk(str);
                currentState = States.FILL_CB;
                askFor();
                break;
            case FILL_CB:
                setCoffeeBeans(str);
                currentState = States.FILL_DC;
                askFor();
                break;
            case FILL_DC:
                setDisposableCups(str);
                currentState = States.CHOOSING_AN_ACTION;
                chooseAction();
                break;
        }
    }

    public void askWhichCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n");
    }

    public void showStatistics() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water%n", this.water);
        System.out.printf("%d of milk%n", this.milk);
        System.out.printf("%d of coffee beans%n", this.coffeeBeans);
        System.out.printf("%d of disposable cups%n", this.disposableCups);
        System.out.printf("%d of money%n%n", this.money);
    }

    public void chooseAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit):\n");
    }

    public boolean checkRes(String str) {
        Coffee coffee;
        if (str.equals("1")) {
            coffee = Coffee.ESPRESSO;
        } else if (str.equals("2")) {
            coffee = Coffee.LATTE;
        } else {
            coffee = Coffee.CAPPUCCINO;
        }
        if (this.water < coffee.water) {
            System.out.println("Sorry, not enough water!\n");
            return false;
        } else if (this.milk < coffee.milk) {
            System.out.println("Sorry, not enough water!\n");
            return false;
        } else if (this.coffeeBeans < coffee.coffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!\n");
            return false;
        } else if (this.disposableCups < coffee.disposableCup) {
            System.out.println("Sorry, not enough disposable cups!\n");
            return false;
        } else {
            return true;
        }
    }

    public void setWater(String str) {
        this.water += Integer.parseInt(str);
    }

    public void setMilk(String str) {
        this.milk += Integer.parseInt(str);
    }

    public void setCoffeeBeans(String str) {
        this.coffeeBeans += Integer.parseInt(str);
    }

    public void setDisposableCups(String str) {
        this.disposableCups += Integer.parseInt(str);
    }

    public void buyCoffee(String str) {
        switch (str) {
            case "1":
                if (checkRes(str)) {
                    System.out.println("I have enough resources, making you a coffee!\n");
                    this.water -= Coffee.ESPRESSO.water;
                    this.coffeeBeans -= Coffee.ESPRESSO.coffeeBeans;
                    this.disposableCups -= Coffee.ESPRESSO.disposableCup;
                    this.money += Coffee.ESPRESSO.price;
                }
                break;
            case "2":
                if (checkRes(str)) {
                    System.out.println("I have enough resources, making you a coffee!\n");
                    this.water -= Coffee.LATTE.water;
                    this.milk -= Coffee.LATTE.milk;
                    this.coffeeBeans -= Coffee.LATTE.coffeeBeans;
                    this.disposableCups -= Coffee.LATTE.disposableCup;
                    this.money += Coffee.LATTE.price;
                }
                break;
            case "3":
                if (checkRes(str)) {
                    System.out.println("I have enough resources, making you a coffee!\n");
                    this.water -= Coffee.CAPPUCCINO.water;
                    this.milk -= Coffee.CAPPUCCINO.milk;
                    this.coffeeBeans -= Coffee.CAPPUCCINO.coffeeBeans;
                    this.disposableCups -= Coffee.CAPPUCCINO.disposableCup;
                    this.money += Coffee.CAPPUCCINO.price;
                }
                break;
            case "back":
                return;
            default:
                System.out.println("Choose another action!");
                break;
        }
    }

    public void askFor() {
        switch (this.currentState) {
            case FILL_WATER:
                System.out.println("Write how many ml of water do you want to add:\n");
                break;
            case FILL_MILK:
                System.out.println("Write how many ml of milk do you want to add:\n");
                break;
            case FILL_CB:
                System.out.println("Write how many grams of coffee beans do you want to add:\n");
                break;
            case FILL_DC:
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                break;
            default:
                break;
        }
    }

    public void takeMoney() {
        System.out.printf("I gave you $%d%n%n", this.money);
        this.money = 0;
    }
}

enum States {
    CHOOSING_AN_ACTION, BUY_COFFEE, FILL_WATER, FILL_MILK, FILL_CB, FILL_DC
}

enum Coffee {
    ESPRESSO(250, 0, 16, 1, 4),
    LATTE(350, 75, 20, 1, 7),
    CAPPUCCINO(200, 100, 12, 1, 6);

    int water;
    int milk;
    int coffeeBeans;
    int disposableCup;
    int price;

    Coffee(int water, int milk, int coffeeBeans, int disposableCup, int price) {
        this.coffeeBeans = coffeeBeans;
        this.disposableCup = disposableCup;
        this.milk = milk;
        this.price = price;
        this.water = water;
    }
}