
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MercadoClient {

	 private static int PORT = 12345;
	 static Scanner reader = new Scanner(System.in);
	 static Socket socket;
	 private static DataInputStream in;
	 private static DataOutputStream out;
	 private static String resp;
	 
	 public static void main(String[] args) throws UnknownHostException, IOException {
		//Setup networking
        socket = new Socket("localhost", PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        System.out.println("Cliente conectado ao servidor");
        
        
    	do {
    		System.out.printf("\n\t\t\tSUPERMERCADO");
	        System.out.printf("\n1-Login");
			System.out.printf("\n2-Cadastre-se\n");
			resp = reader.nextLine();
			if (resp.equals("1")){
				if (Login() == 1) resp="0";
			}
			else if (resp.equals("2"))
				Cadastrar();
				
    	} while (!resp.equals("0"));
    	
        do {
        	System.out.printf("\n\t\t\tSUPERMERCADO");
			System.out.printf("\n1-Listar produtos disponiveis");
			System.out.printf("\n2-Listar produtos indisponiveis");
			System.out.printf("\n3-Realizar compras");
			System.out.printf("\n0-Sair\n");
			resp = reader.nextLine();
			if (resp.equals("1"))
				ProdutosDispo();
			if (resp.equals("2"))
				ProdutosIn();
			if (resp.equals("3"))
				Compras();
        	
        } while (!resp.equals("0"));
		System.exit(0);
	 }
    /***************************************************************************************************/
    static void Cadastrar(){
    	resp = "add,";
    	System.out.printf("\nNome: ");
		resp = resp + reader.nextLine()+",";
		System.out.printf("Endereco: ");
		resp = resp + reader.nextLine()+",";
		System.out.printf("Telefone: ");
		resp = resp + reader.nextLine()+",";
		System.out.printf("Email: ");
		resp = resp + reader.nextLine()+",";
		System.out.printf("ID: ");
		resp = resp + reader.nextLine()+",";
		System.out.printf("Senha: ");
		resp = resp + reader.nextLine();
		try {
			out.writeUTF(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Cadastro efetuado com sucesso! ");
    }
    static int Login() {
		System.out.printf("\nDigite seu login: ");
		String login = reader.nextLine();
		System.out.printf("Digite sua senha: ");
		String senha = reader.nextLine();
		String line;
		try {
			out.writeUTF("login,"+login+","+senha);
			line = in.readUTF();
			if (line.equals("ok"))
				return 1;
			if (line.equals("error")){
				System.out.println("Usuario invalido ");
				return 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
    }
    /***************************************************************************************************/
    static void ProdutosDispo(){
    	try {
			out.writeUTF("dispo");
			String line = in.readUTF();
			System.out.printf(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /***************************************************************************************************/
    static void ProdutosIn(){
    	try {
			out.writeUTF("indis");
			String line = in.readUTF();
			System.out.printf(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /***************************************************************************************************/
    static void Compras(){
    	try {
    		System.out.printf("\nDigite o codigo do produto que deseja comprar: ");
    		String codigo = reader.nextLine();
    		out.writeUTF("compra,"+codigo);
			String line = in.readUTF();
			if (line.equals("NaoExiste"))
				System.out.println("Codigo invalido! ");
			else if (line.equals("indis"))
				System.out.println("Produto indisponivel ");
			else { //Encontrou o produto
				String[] values = line.split(",");
				System.out.printf("\nNome do produto: "+values[0]);
				System.out.printf("\nQuantidade em estoque: "+values[1]);
				System.out.printf("\n\nDigite a quantidade que deseja retirar: ");
				String aux = reader.nextLine();
				if (Integer.parseInt(aux) > 0 && Integer.parseInt(aux) <= Integer.parseInt(values[1])){
					out.writeUTF("compra2,"+codigo+","+aux);
				}
				else {
					System.out.printf("\n\nQuantidade invalida para realizar a compra ");
				}
			}
			
				//System.out.printf(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}