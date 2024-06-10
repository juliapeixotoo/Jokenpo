package jokenpo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Cliente {
    public static void main(String[] args) {
        
         String ENDERECO_SERVIDOR = "localhost"; // "localhost" refere-se ao endereço do próprio computador
         int PORTA = 12345;
         String clienteIP;
         String clientePorta;

        try {
            Scanner teclado = new Scanner(System.in);

            System.out.printf("Informe o IP do servidor(default: %s): ", ENDERECO_SERVIDOR);
            clienteIP = teclado.nextLine();
            if (ENDERECO_SERVIDOR.length() > 0) {
                ENDERECO_SERVIDOR = clienteIP;
            }
            
            System.out.printf("Informe a porta do servidor(default: %d): ", PORTA);
            clientePorta = teclado.nextLine();
            if (clientePorta.length() > 0) {
                PORTA = Integer.parseInt(clientePorta);
            }
            Socket socket = new Socket(ENDERECO_SERVIDOR, PORTA);
            
            System.out.println("Conectado ao servidor.");

            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
           
            
            String modo;
            String mensagemDoServidor;
            String mensagemDoCliente;






            System.out.println("Bem vindo! ao Pedra,Papel e Tesoura\n"+
                                      "Escolha o modo de jogo\n"+
                                      "Singleplayer (S)\n"+
                                      "Multiplayer (M)" );

                modo = teclado.nextLine();
                saida.println(modo);
            if(modo.equalsIgnoreCase("S")){
               while (true) {
                
                System.out.println("Escolha entre Pedra,Papel ou Tesoura!");
                mensagemDoCliente = teclado.nextLine();
                
                saida.println(mensagemDoCliente);
                

                
                mensagemDoServidor = entrada.readLine();
                
                System.out.println("Servidor: " + mensagemDoServidor);
                mensagemDoServidor = entrada.readLine();
                System.out.println("Servidor: " + mensagemDoServidor);
                mensagemDoServidor = entrada.readLine();
                System.out.println("Servidor: " + mensagemDoServidor);
                mensagemDoServidor = entrada.readLine();
               
                System.out.println("Servidor: " + mensagemDoServidor);
                mensagemDoCliente = teclado.nextLine();
                saida.println(mensagemDoCliente);

                
                if (mensagemDoServidor.equals("Bem-vindo ao jogo Pedra, Papel e Tesoura!") ||
                    mensagemDoServidor.equals("Você venceu!") ||
                    mensagemDoServidor.equals("Você perdeu!") ||
                    mensagemDoServidor.equals("Empate!")) {
                   
                    continue;
                }

                
                else if (mensagemDoServidor.equals("sair")) {
                    
                    break;
                }

               }



            }else if(modo.equalsIgnoreCase("m")){

                while (true) {
                    
                
                    System.out.println("Escolha entre Pedra,Papel ou Tesoura!");
                    mensagemDoCliente = teclado.nextLine();
                    
                    saida.println(mensagemDoCliente);
                    
                    mensagemDoServidor = entrada.readLine();
                    
                    System.out.println("Servidor: " + mensagemDoServidor);
                    mensagemDoServidor = entrada.readLine();
                    System.out.println("Servidor: " + mensagemDoServidor);
                    mensagemDoServidor = entrada.readLine();
                     System.out.println("Servidor: " + mensagemDoServidor);
                    mensagemDoServidor = entrada.readLine();
                   
                    System.out.println("Servidor: " + mensagemDoServidor);
                    mensagemDoCliente = teclado.nextLine();
                    saida.println(mensagemDoCliente);
    
                    
                    if (mensagemDoServidor.equals("Bem-vindo ao jogo Pedra, Papel e Tesoura!") ||
                        mensagemDoServidor.equals("Você venceu!") ||
                        mensagemDoServidor.equals("Você perdeu!") ||
                        mensagemDoServidor.equals("Empate!")) {
                       
                        continue;
                    }
    
                    
                    else if (mensagemDoServidor.equals("sair")) {
                        
                        break;
                    }
    
                   }

                
            }
            

            // Fecha os scanners e o socket após a conclusão da interação
            entrada.close();
            saida.close();
            teclado.close();
            socket.close();
        } catch (IOException e) {
            // Em caso de exceção de entrada/saída (IO), imprime o rastreamento do erro
            e.printStackTrace();
        }
    }
}
