import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String customerName = "Matheus Martins";
        String accountType = "Conta Corrente";
        double customerBalance = 300.00;

        displayInitialInformation(customerName, accountType, customerBalance);
        while (true) {
            displayMenu();
            int userInput = getUserInput();
            if (!validateUserInput(userInput)) {
                handleInvalidInput(userInput);
                continue;
            }
            boolean shouldTerminateProgram = checkTerminateProgram(userInput);
            if (shouldTerminateProgram)
                break;
            customerBalance = handleUserInput(userInput, customerBalance);
        }
    }

    public static double handleUserInput(int userInput, double customerAmount) {
        switch (userInput) {
            case 1:
                consultarBalance(customerAmount);
                break;
            case 2:
                customerAmount = depositAmount(customerAmount);
                break;
            case 3:
                customerAmount = transferAmount(customerAmount);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userInput);
        }
        return customerAmount;
    }

    public static void consultarBalance(double customerAmount) {
        System.out.printf(">> O Saldo atual é %.2f%n", customerAmount);
    }

    public static double depositAmount(double customerAmount) {
        Scanner entrada = new Scanner(System.in);
        double depositAmount = 0;

        while (true) {
            System.out.print("Informe o valor a depositar: ");
            depositAmount = entrada.nextDouble();

            if (!isValuePositive(depositAmount)) {
                System.out.println("Deposit amount must be positive! <" + depositAmount + ">");
                continue;
            }

            break;
        }

        return customerAmount + depositAmount;

    }

    public static double transferAmount(double customerAmount) {
        Scanner entrada = new Scanner(System.in);
        double transferAmount = 0;

        while (true) {
            System.out.print("Informe o valor que deseja transferir: ");
            transferAmount = entrada.nextDouble();

            if (!isValuePositive(transferAmount)) {
                System.out.println("Transfer amount must be positive! <" + transferAmount + ">");
                continue;
            }

            if (!isBalanceAvailableToTransfer(transferAmount, customerAmount)) {
                System.out.println("Customer doesn't have this amount available to transfer! <" + transferAmount + ">");
                continue;
            }

            break;
        }

        return customerAmount - transferAmount;
    }

    public static void displayInitialInformation(String customerName, String accountType, double initialBalance) {
        String info = String.format("""
        ********************************************************
        
        Dados iniciais do cliente:
        
        Nome:                 %s
        Tipo conta:           %s
        Saldo inicial:        R$ %.2f
        ********************************************************
        """, customerName, accountType, initialBalance);
        System.out.println(info);
    }

    public static void displayMenu() {
        System.out.println("\nOperações:\n\n");
        System.out.println("1- Consultar saldo");
        System.out.println("2- Receber valor");
        System.out.println("3- Transferir valor");
        System.out.println("4- Sair\n");
    }

    public static int getUserInput() {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Digite a opção desejada: ");
        return entrada.nextInt();
    }

    public static boolean validateUserInput(int userInput) {
        return userInput >= 1 && userInput <= 4;
    }

    public static void handleInvalidInput(int userInput) {
        System.out.println("<"+userInput+"> is not a valid input!");
    }

    public static boolean checkTerminateProgram(int userInput) {
        if (userInput != 4)
            return false;
        System.out.println("-------- Stopping program execution --------");
        return true;
    }

    public static boolean isValuePositive(double value) {
        return value >= 0;
    }

    public static boolean isBalanceAvailableToTransfer(double amount, double balance) {
        return amount <= balance;
    }
}