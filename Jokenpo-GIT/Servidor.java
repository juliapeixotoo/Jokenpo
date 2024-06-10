package jokenpo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Servidor {
    private   static final int PORTA = 12345;
     private   static ArrayList<Socket> single = new ArrayList<>();
      private   static ArrayList<Socket> multiplayer = new ArrayList<>();
    

    public static void main(String[] args)  throws IOException{
        
        
         
         
         ServerSocket serverSocket = new ServerSocket(PORTA);
        System.out.println("Servidor aguardando conexões...");
        // Inicializa o placar
        
        
int client = 0;

        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader ClienteTCL = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter ServidorResposta = new PrintWriter(socket.getOutputStream(), true);
            
            

            System.out.println("Clientes conectados: " + client);

            // Recebe o nome do jogador
            String modo = ClienteTCL.readLine();
            

            ;
            if (modo.equalsIgnoreCase("s") ){
                // Jogador escolheu jogar sozinho
                single.add(socket);
                Thread singleThread = new Thread(new JogoSingleplayer());
                singleThread.start();
            } else if(modo.equalsIgnoreCase("m")){
                // Jogador escolheu jogar multiplayer
                multiplayer.add(socket);
                

                Thread multiplayerThread = new Thread(new JogoMultiplayer());
                multiplayerThread.start();
            }
        }

        }



    // Método para gerar uma escolha aleatória para o servidor
    private static String gerarEscolhaAleatoria() {
         Random random = new  Random();
        
         // Usa a classe Random para gerar um número aleatório entre 0 e 2
         int escolha = random.nextInt(3);
         // Converte o número aleatório em uma escolha (0 = Pedra, 1 = Papel, 2 = Tesoura)
         switch (escolha) {
             case 0:
                 return "Pedra";
             case 1:
                 return "Papel";
             default:
                 return "Tesoura";
         }
    }
    

    // Método para determinar o resultado do jogo
    private static String determinarResultadoS(String escolhaCliente, String escolhaServidor) {
        if (escolhaCliente.equalsIgnoreCase(escolhaServidor)) {
            return "Empate!";
        } else if ((escolhaCliente.equalsIgnoreCase("Pedra") && escolhaServidor.equalsIgnoreCase("Tesoura")) ||
                   (escolhaCliente.equalsIgnoreCase("Papel") && escolhaServidor.equalsIgnoreCase("Pedra")) ||
                   (escolhaCliente.equalsIgnoreCase("Tesoura") && escolhaServidor.equalsIgnoreCase("Papel"))) {
            return "Você Venceu!";
        } else if((escolhaCliente.equalsIgnoreCase("Papel") && escolhaServidor.equalsIgnoreCase("Tesoura")) ||
        (escolhaCliente.equalsIgnoreCase("Tesoura") && escolhaServidor.equalsIgnoreCase("Pedra")) ||
        (escolhaCliente.equalsIgnoreCase("Pedra") && escolhaServidor.equalsIgnoreCase("Papel"))){
            return "Você perdeu!";
        }else {
            return "Escolha Invalida, Jogue novamente!";
        }
    }




    private static String determinarResultadoM1(String escolhaCliente1, String escolhaCliente2) {
        if (escolhaCliente1.equalsIgnoreCase(escolhaCliente2)) {
            return "Empate!";
        } else if ((escolhaCliente1.equalsIgnoreCase("Pedra") && escolhaCliente2.equalsIgnoreCase("Tesoura")) ||
                   (escolhaCliente1.equalsIgnoreCase("Papel") && escolhaCliente2.equalsIgnoreCase("Pedra")) ||
                   (escolhaCliente1.equalsIgnoreCase("Tesoura") && escolhaCliente2.equalsIgnoreCase("Papel"))) {
            return "Você Venceu!";
        } else if((escolhaCliente1.equalsIgnoreCase("Papel") && escolhaCliente2.equalsIgnoreCase("Tesoura")) ||
        (escolhaCliente1.equalsIgnoreCase("Tesoura") && escolhaCliente2.equalsIgnoreCase("Pedra")) ||
        (escolhaCliente1.equalsIgnoreCase("Pedra") && escolhaCliente2.equalsIgnoreCase("Papel"))){
            return "Você perdeu!";
        }else {
            return "Escolha Invalida, Jogue novamente!";
        }
    }

    private static String determinarResultadoM2(String escolhaCliente1, String escolhaCliente2) {
        if (escolhaCliente1.equalsIgnoreCase(escolhaCliente2)) {
            return "Empate!";
        } else if ((escolhaCliente2.equalsIgnoreCase("Pedra") && escolhaCliente1.equalsIgnoreCase("Tesoura")) ||
                   (escolhaCliente2.equalsIgnoreCase("Papel") && escolhaCliente1.equalsIgnoreCase("Pedra")) ||
                   (escolhaCliente2.equalsIgnoreCase("Tesoura") && escolhaCliente1.equalsIgnoreCase("Papel"))) {
            return "Você Venceu!";
        } else if((escolhaCliente2.equalsIgnoreCase("Papel") && escolhaCliente1.equalsIgnoreCase("Tesoura")) ||
        (escolhaCliente2.equalsIgnoreCase("Tesoura") && escolhaCliente1.equalsIgnoreCase("Pedra")) ||
        (escolhaCliente2.equalsIgnoreCase("Pedra") && escolhaCliente1.equalsIgnoreCase("Papel"))){
            return "Você perdeu!";
        }else {
            return "Escolha Invalida, Jogue novamente!";
        }
    }

    private static class JogoSingleplayer implements Runnable {
        @Override
        public void run() {
            while (true) {
                if(single.size() == 1) {
                    Socket socket = single.remove(0);

                    try {
                        playSingle(socket);
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private static void playSingle(Socket socket) throws NumberFormatException, IOException {

        int placarCliente = 0;
        int placarServidor = 0;
        String escolhaCliente;

        
    
        BufferedReader ClienteTCL = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter ServidorResposta = new PrintWriter(socket.getOutputStream(), true);
        while(true){
                escolhaCliente = ClienteTCL.readLine();

                // Gera uma escolha aleatória para o servidor
                String escolhaServidor = gerarEscolhaAleatoria();

                // Determina o resultado do jogo
                String resultado = determinarResultadoS(escolhaCliente, escolhaServidor);

                // Atualiza o placar
                if (resultado.equalsIgnoreCase("Você venceu!")) {
                    placarCliente++;
                } else if (resultado.equalsIgnoreCase("Você perdeu!")) {
                    placarServidor++;
                }else if (resultado.equalsIgnoreCase("Empate!")) {
                    
                }else if (resultado.equalsIgnoreCase("Escolha Invalida!, Jogue novamente!")){

                }
                // Envia o resultado para o cliente
                ServidorResposta.println(resultado);
                 ServidorResposta.println("Você escolhe:   "+escolhaCliente + "/  Servidor:   " + escolhaServidor );   
                  ServidorResposta.println( "Placar:   Jogador " + placarCliente + "  -  " + placarServidor + " Servidor ");           
                                                                                         
                // Verifica se o jogo deve continuar
                ServidorResposta.println("Deseja jogar novamente? (S/N)");

                String resposta1 = ClienteTCL.readLine();
    
                
                if (resposta1.equalsIgnoreCase("N")) {
                    
                    break;
                }
            }
    }
    private static class JogoMultiplayer implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (multiplayer.size() >= 2) {
                    // Encontra dois jogadores que desejam jogar multiplayer
                    Socket SocketCliente1 = multiplayer.remove(0);
                    Socket SocketCliente2 = multiplayer.remove(0);

                    // Cria uma instância de jogo multiplayer para os dois jogadores
                    try {
                        playMultiplayer(SocketCliente1, SocketCliente2);
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    // Aguarda um pouco antes de verificar novamente
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
    }  
    
private static void playMultiplayer(Socket SocketCliente1, Socket SocketCliente2) throws NumberFormatException, IOException {


        BufferedReader ClienteTCL1 = new BufferedReader(new InputStreamReader(SocketCliente1.getInputStream()));
        PrintWriter ServidorResposta1 = new PrintWriter(SocketCliente1.getOutputStream(), true);


        BufferedReader ClienteTCL2 = new BufferedReader(new InputStreamReader(SocketCliente2.getInputStream()));
        PrintWriter ServidorResposta2 = new PrintWriter(SocketCliente2.getOutputStream(), true);

        int placarCliente1 = 0;
        int placarCliente2 = 0;



    while (true) {
            
        
        // Recebe a escolha do cliente
        String escolhaCliente1 =ClienteTCL1.readLine();
                
        // Gera uma escolha aleatória para o servidor
        String escolhaCliente2 = ClienteTCL2.readLine();

        // Determina o resultado do jogo
        String resultado1 = determinarResultadoM1(escolhaCliente1, escolhaCliente2);
        String resultado2 = determinarResultadoM2(escolhaCliente1, escolhaCliente2);
        // Atualiza o placar
        if (resultado1.equalsIgnoreCase("Você venceu!")) {
            placarCliente1++;
        } else if (resultado1.equalsIgnoreCase("Você perdeu!")) {
            placarCliente2++;
        }else if (resultado1.equalsIgnoreCase("Empate!")) {
            
        }else if (resultado1.equalsIgnoreCase("Escolha Invalida!, Jogue novamente!")){

        }
        // Envia o resultado para o cliente

                       ServidorResposta1.println(resultado1);
                       ServidorResposta1.println("Você jogou "+escolhaCliente1 +" O adversário jogou " + escolhaCliente2  );   
                        ServidorResposta1.println( "Placar: Cliente " + placarCliente1 + " - " + placarCliente2 + " Oponente"); 

                        ServidorResposta2.println(resultado2);
                        ServidorResposta2.println("Você jogou "+escolhaCliente2 +" O adversário jogou " + escolhaCliente1  );   
                         ServidorResposta2.println( "Placar: Cliente " + placarCliente2 + " - " + placarCliente1 + " Oponente"); 
 
                       
        // Verifica se o jogo deve continuar
        ServidorResposta1.println("Deseja jogar novamente? (S/N)");
        ServidorResposta2.println("Deseja jogar novamente? (S/N)");

        String resposta1 = ClienteTCL1.readLine();
        String resposta2 = ClienteTCL2.readLine();
        
        if (resposta1.equalsIgnoreCase("N")) {
            ServidorResposta2.println("Oponente Desconectado!!");
            ServidorResposta1.println("sair");
            ServidorResposta2.println("sair");

        }else if(resposta2.equalsIgnoreCase("n")){
            ServidorResposta1.println("Oponente Desconectado!!");
            ServidorResposta1.println("sair");
            ServidorResposta2.println("sair");
        }
    }

}




    
}
